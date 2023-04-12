package ro.fasttrackit.course9.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.filters.ReviewFilters;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Pageable.unpaged;
import static org.springframework.data.support.PageableExecutionUtils.getPage;
import static ro.fasttrackit.course9.homework.model.entity.ReviewEntity.Fields.*;
import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.inOrIs;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final ReviewRepo repo;
    private final MongoTemplate mongo;

    public Page<ReviewEntity> findAll(ReviewFilters filters, Pageable pageable) {
        Pageable pagination = ofNullable(pageable).orElseGet(Pageable::unpaged);
        Query query = toQuery(filters).with(pagination);
        return getPage(mongo.find(query, ReviewEntity.class),
                pagination,
                () -> mongo.count(query.with(unpaged()), ReviewEntity.class));
    }

    private Query toQuery(ReviewFilters filters) {
        return Query.query(toCriteria(filters));
    }

    private Criteria toCriteria(ReviewFilters filters) {
        Criteria criteria = new Criteria();
        ofNullable(filters)
                .ifPresent(flt -> {
                    ofNullable(filters.id())
                            .ifPresent(ids -> inOrIs(criteria, ids, id));
                    ofNullable(filters.roomId())
                            .ifPresent(roomIds -> inOrIs(criteria, roomIds, roomId));
                    ofNullable(filters.rating())
                            .ifPresent(ratings -> inOrIs(criteria, ratings, rating));
                    ofNullable(filters.touristName())
                            .ifPresent(names -> inOrIs(criteria, names, "tourist.name"));
                });
        return criteria;
    }

    public ReviewEntity addReview(ReviewEntity review) {
        return repo.save(review);
    }

    public Optional<ReviewEntity> findByReviewIdAndRoomId(String reviewId, String roomId) {
        return repo.findByIdAndRoomId(reviewId, roomId);
    }

    public Optional<ReviewEntity> deleteById(String reviewId, String roomId) {
        Optional<ReviewEntity> reviewOptional = findByReviewIdAndRoomId(reviewId, roomId);
        reviewOptional.ifPresent(repo::delete);
        return reviewOptional;
    }
}
