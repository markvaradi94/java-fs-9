package ro.fasttrackit.course9.homework.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.course9.homework.model.entity.RoomEntity;
import ro.fasttrackit.course9.homework.model.filters.RoomFilters;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.Pageable.unpaged;
import static org.springframework.data.support.PageableExecutionUtils.getPage;
import static ro.fasttrackit.course9.homework.model.entity.RoomEntity.Fields.*;
import static ro.fasttrackit.course9.homework.util.MongoQueryUtils.inOrIs;

@Repository
@RequiredArgsConstructor
public class RoomRepository {
    private final RoomRepo repo;
    private final MongoTemplate mongo;

    public Page<RoomEntity> findAll(RoomFilters filters, Pageable pageable) {
        Pageable pagination = ofNullable(pageable).orElseGet(Pageable::unpaged);
        Query query = toQuery(filters).with(pagination);
        return getPage(mongo.find(query, RoomEntity.class),
                pagination,
                () -> mongo.count(query.with(unpaged()), RoomEntity.class));
    }

    public Optional<RoomEntity> findById(String roomId) {
        return repo.findById(roomId);
    }

    public Optional<RoomEntity> deleteById(String roomId) {
        Optional<RoomEntity> roomOptional = findById(roomId);
        roomOptional.ifPresent(repo::delete);
        return roomOptional;
    }

    public RoomEntity addRoom(RoomEntity newRoom) {
        return repo.save(newRoom);
    }

    private Query toQuery(RoomFilters filters) {
        return Query.query(toCriteria(filters));
    }

    private Criteria toCriteria(RoomFilters filters) {
        Criteria criteria = new Criteria();
        ofNullable(filters)
                .ifPresent(flt -> {
                            ofNullable(filters.id())
                                    .ifPresent(roomIds -> inOrIs(criteria, roomIds, id));
                            ofNullable(filters.number())
                                    .ifPresent(roomNumbers -> inOrIs(criteria, roomNumbers, number));
                            ofNullable(filters.floor())
                                    .ifPresent(roomFloors -> inOrIs(criteria, roomFloors, floor));
                            ofNullable(filters.tv())
                                    .ifPresent(hasTv -> criteria.and("facilities.tv").is(hasTv));
                            ofNullable(filters.doubleBed())
                                    .ifPresent(hasDoubleBed -> criteria.and("facilities.doubleBed").is(hasDoubleBed));
                        }
                );
        return criteria;
    }
}
