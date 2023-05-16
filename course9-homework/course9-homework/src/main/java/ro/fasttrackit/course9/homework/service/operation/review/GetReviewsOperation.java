package ro.fasttrackit.course9.homework.service.operation.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.ReviewEntity;
import ro.fasttrackit.course9.homework.model.request.review.GetReviewsRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.repository.ReviewRepository;

@Component
@RequiredArgsConstructor
public class GetReviewsOperation implements Operation<GetReviewsRequest, Page<ReviewEntity>> {
    private final ReviewRepository repository;

    @Override
    public Page<ReviewEntity> doExecute(GetReviewsRequest request) {
        return repository.findAll(request);
    }
}
