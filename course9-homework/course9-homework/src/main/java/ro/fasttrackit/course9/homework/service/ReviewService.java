package ro.fasttrackit.course9.homework.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.fasttrackit.course9.homework.exception.ResourceNotFoundException;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.filters.ReviewFilters;
import ro.fasttrackit.course9.homework.model.mappers.ModelMapper;
import ro.fasttrackit.course9.homework.model.mappers.ReviewMappers;
import ro.fasttrackit.course9.homework.repository.ReviewRepository;
import ro.fasttrackit.course9.homework.validator.ReviewValidator;

import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.mapPagedContent;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final ReviewValidator validator;
    private final ObjectMapper objectMapper;
    private final ReviewMappers reviewMappers = new ReviewMappers();
    private final ModelMapper<ReviewEntity, ReviewDto> apiMapper = reviewMappers.toApiMapper();
    private final ModelMapper<ReviewDto, ReviewEntity> dbMapper = reviewMappers.toDbMapper();

    public Page<ReviewDto> findAll(ReviewFilters filters, Pageable pageable) {
        return mapPagedContent(apiMapper, repository.findAll(filters, pageable), pageable);
    }

    public ReviewDto add(String roomId, ReviewDto newReview) {
        validator.validateRoomExistsOrThrow(roomId);
        ReviewDto reviewWithRoomId = newReview.withRoomId(roomId);
        validator.validateNewThrow(dbMapper.of(reviewWithRoomId));
        return apiMapper.of(repository.addReview(dbMapper.of(reviewWithRoomId)));
    }

    public ReviewDto delete(String roomId, String reviewId) {
        validator.validateRoomExistsOrThrow(roomId);
        validator.validateExistsOrThrow(reviewId);
        ReviewDto reviewToDelete = getOrThrow(reviewId, roomId);
        return repository.deleteById(reviewToDelete.id(), roomId)
                .map(apiMapper::of)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find review with id " + reviewId + "for room with id " + roomId));
    }

    private ReviewDto getOrThrow(String reviewId, String roomId) {
        return repository.findByReviewIdAndRoomId(reviewId, roomId)
                .map(apiMapper::of)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find review with id " + reviewId + "for room with id " + roomId));
    }

    @SneakyThrows
    public ReviewDto patch(String roomId, String reviewId, JsonPatch patch) {
        validator.validateRoomExistsOrThrow(roomId);
        validator.validateExistsOrThrow(reviewId);
        ReviewEntity reviewToPatch = repository.findByReviewIdAndRoomId(reviewId, roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find review with id " + reviewId + "for room with id " + roomId));
        JsonNode patchedReviewJson = patch.apply(objectMapper.valueToTree(reviewToPatch));
        ReviewEntity patchedReview = objectMapper.treeToValue(patchedReviewJson, ReviewEntity.class);
        validator.validateReplaceThrow(reviewId, patchedReview);
        return apiMapper.of(repository.addReview(patchedReview));
    }
}
