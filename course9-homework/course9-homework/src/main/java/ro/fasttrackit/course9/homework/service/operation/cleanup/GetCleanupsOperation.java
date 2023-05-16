package ro.fasttrackit.course9.homework.service.operation.cleanup;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.model.request.cleanup.GetCleanupsRequest;
import ro.fasttrackit.course9.homework.operation.Operation;
import ro.fasttrackit.course9.homework.repository.CleanupRepository;

@Component
@RequiredArgsConstructor
public class GetCleanupsOperation implements Operation<GetCleanupsRequest, Page<CleanupEntity>> {
    private final CleanupRepository repository;

    @Override
    public Page<CleanupEntity> doExecute(GetCleanupsRequest request) {
        return repository.findAll(request);
    }
}
