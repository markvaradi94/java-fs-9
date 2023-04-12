package ro.fasttrackit.course9.homework.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.course9.homework.exception.ValidationException;
import ro.fasttrackit.course9.homework.model.entity.CleanupEntity;
import ro.fasttrackit.course9.homework.repository.CleanupRepo;
import ro.fasttrackit.course9.homework.repository.RoomRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.join;
import static java.util.Optional.empty;
import static java.util.Optional.of;

@Component
@RequiredArgsConstructor
public class CleanupValidator {
    private final CleanupRepo cleanupRepo;
    private final RoomRepo roomRepo;

    public void validateNewThrow(CleanupEntity cleanup) {
        validate(cleanup, true)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateExistsOrThrow(String cleanupId) {
        exists(cleanupId)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateRoomExistsOrThrow(String roomId) {
        roomExists(roomId)
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    public void validateReplaceThrow(String cleanupId, CleanupEntity cleanup) {
        exists(cleanupId)
                .or(() -> validate(cleanup, false))
                .ifPresent(validationException -> {
                    throw validationException;
                });
    }

    private Optional<ValidationException> validate(CleanupEntity cleanup, boolean newCleanup) {
        List<String> errorMessages = new ArrayList<>();
        if (isNullOrEmpty(cleanup.getId()) && !newCleanup) {
            errorMessages.add("Cleanup id cannot be null or empty.");
        } else if (isNullOrEmpty(cleanup.getRoomId())) {
            errorMessages.add("Cleanup must be attributed to a room");
        } else if (newCleanup && cleanup.getId() != null && roomRepo.existsById(cleanup.getId())) {
            errorMessages.add("Cannot have duplicate id cleanups.");
        }
        return errorMessages.isEmpty()
                ? empty()
                : of(new ValidationException(join(", ", errorMessages)));
    }

    private Optional<ValidationException> roomExists(String roomId) {
        return roomRepo.existsById(roomId)
                ? empty()
                : of(new ValidationException("Room with id " + roomId + " could not be found."));
    }

    private Optional<ValidationException> exists(String cleanupId) {
        return cleanupRepo.existsById(cleanupId)
                ? empty()
                : of(new ValidationException("Cleanup with id " + cleanupId + " could not be found."));
    }
}
