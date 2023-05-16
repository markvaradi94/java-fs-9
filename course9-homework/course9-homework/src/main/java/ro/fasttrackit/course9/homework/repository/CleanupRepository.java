package ro.fasttrackit.course9.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.model.filters.CleanupFilters;
import ro.fasttrackit.course9.homework.model.request.cleanup.GetCleanupsRequest;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Pageable.unpaged;
import static org.springframework.data.support.PageableExecutionUtils.getPage;
import static ro.fasttrackit.course9.homework.model.entity.CleanupEntity.Fields.*;
import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.inOrIs;

@Repository
@RequiredArgsConstructor
public class CleanupRepository {
    private final CleanupRepo repo;
    private final MongoTemplate mongo;

    public Page<CleanupEntity> findAll(CleanupFilters filters, Pageable pageable) {
        Pageable pagination = ofNullable(pageable).orElseGet(Pageable::unpaged);
        Query query = toQuery(filters).with(pagination);
        return getPage(mongo.find(query, CleanupEntity.class),
                pagination,
                () -> mongo.count(query.with(unpaged()), RoomEntity.class));
    }

    public Page<CleanupEntity> findAll(GetCleanupsRequest request) {
        Query query = toQuery(request.filters()).with(request.pageable());
        return getPage(mongo.find(query, CleanupEntity.class),
                request.pageable(),
                () -> mongo.count(query.with(unpaged()), RoomEntity.class));
    }

    private Query toQuery(CleanupFilters filters) {
        return Query.query(toCriteria(filters));
    }

    private Criteria toCriteria(CleanupFilters filters) {
        Criteria criteria = new Criteria();
        ofNullable(filters)
                .ifPresent(flt -> {
                    ofNullable(filters.id())
                            .ifPresent(ids -> inOrIs(criteria, ids, id));
                    ofNullable(filters.roomId())
                            .ifPresent(roomIds -> inOrIs(criteria, roomIds, roomId));
                    ofNullable(filters.date())
                            .ifPresent(filterDate -> criteria.and(date).is(filterDate));
                    ofNullable(filters.procedureName())
                            .ifPresent(name -> inOrIs(criteria, name, "procedures.name"));
                    ofNullable(filters.procedureOutcome())
                            .ifPresent(outcome -> inOrIs(criteria, outcome, "procedures.outcome"));
                });
        return criteria;
    }

    public CleanupEntity addCleanup(CleanupEntity cleanup) {
        return repo.save(cleanup);
    }

    public Optional<CleanupEntity> findByCleanupIdAndRoomId(String cleanupId, String roomId) {
        return repo.findByIdAndRoomId(cleanupId, roomId);
    }

    public Optional<CleanupEntity> deleteById(String cleanupId, String roomId) {
        Optional<CleanupEntity> cleanupOptional = findByCleanupIdAndRoomId(cleanupId, roomId);
        cleanupOptional.ifPresent(repo::delete);
        return cleanupOptional;
    }

    public boolean existsByCleanupIdAndRoomId(String cleanupId, String roomId) {
        return repo.existsByIdAndRoomId(cleanupId, roomId);
    }
}
