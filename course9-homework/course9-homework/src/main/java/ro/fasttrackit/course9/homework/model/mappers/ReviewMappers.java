package ro.fasttrackit.course9.homework.model.mappers;

import ro.fasttrackit.course9.homework.model.domain.Tourist;
import ro.fasttrackit.course9.homework.model.dto.ReviewDto;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;

import static java.util.Optional.ofNullable;

public class ReviewMappers implements Mappers<ReviewDto, ReviewEntity> {

    @Override
    public ModelMapper<ReviewEntity, ReviewDto> toApiMapper() {
        return new ReviewMapper();
    }

    @Override
    public ModelMapper<ReviewDto, ReviewEntity> toDbMapper() {
        return new DbReviewMapper();
    }
}

class DbReviewMapper implements ModelMapper<ReviewDto, ReviewEntity> {

    @Override
    public ReviewEntity nullSafeMap(ReviewDto source) {
        return ReviewEntity.builder()
                .id(source.id())
                .message(source.message())
                .rating(source.rating())
                .tourist(Tourist.builder()
                        .age(source.touristAge())
                        .name(source.touristName())
                        .build())
                .roomId(source.roomId())
                .build();
    }
}

class ReviewMapper implements ModelMapper<ReviewEntity, ReviewDto> {

    @Override
    public ReviewDto nullSafeMap(ReviewEntity source) {
        return ReviewDto.builder()
                .id(source.getId())
                .message(source.getMessage())
                .rating(source.getRating())
                .touristName(ofNullable(source.getTourist())
                        .map(Tourist::name)
                        .orElse(null))
                .touristAge(ofNullable(source.getTourist())
                        .map(Tourist::age)
                        .orElse(null))
                .roomId(source.getRoomId())
                .build();
    }
}
