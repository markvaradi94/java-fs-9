package ro.fasttrackit.course9.homework.operation;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;
import ro.fasttrackit.course9.homework.exception.MultipleValidationException;
import ro.fasttrackit.course9.homework.exception.ValidationException;
import ro.fasttrackit.course9.homework.operation.log.OperationLogger;
import ro.fasttrackit.course9.homework.operation.performance.OperationMonitor;
import ro.fasttrackit.course9.homework.operation.validator.OperationValidator;

import java.util.Collection;

import static java.util.Optional.ofNullable;
import static ro.fasttrackit.course9.homework.operation.log.OperationLogger.infoLogger;
import static ro.fasttrackit.course9.homework.util.Utils.limitMessage;
import static ro.fasttrackit.course9.homework.util.Utils.objectToJson;

public interface Operation<I, O> {
    default O execute(I input) {
        String operationToken = RandomStringUtils.randomAlphabetic(10);
        OperationLogger operationLogger = getOperationLogger();
        O output;
        OperationMonitor operationMonitor = null;
        if (operationLogger.isEnabled()) {
            operationMonitor = new OperationMonitor(operationLogger);
            output = operationMonitor.monitor(() -> this.singleExecution(operationLogger, input, operationToken));
        } else {
            output = this.singleExecution(operationLogger, input, operationToken);
        }

        String monitorMessage = operationMonitor == null ? "" : String.valueOf(operationMonitor.getDuration());
        operationLogger.log("[{}] Successfully finished {} in {} ms with no retrials, response: {}",
                () -> new Object[]{
                        operationToken,
                        this.getClass().getSimpleName(),
                        monitorMessage,
                        limitMessage(describeOutput(input, output), 5000)});
        return output;
    }

    default O singleExecution(OperationLogger operationLogger, I input, String operationToken) {
        Collection<ValidationException> validation = this.getValidator().validate(input);
        if (ofNullable(validation)
                .map(Collection::isEmpty)
                .orElse(true)) {
            operationLogger.log("[{}] Executing {} with input {}",
                    () -> new Object[]{
                            operationToken,
                            this.getClass().getSimpleName(),
                            limitMessage(describeInput(input))});
            return this.doExecute(input);
        } else {
            operationLogger.warn("[" + operationToken + "] Validator failed for " + this.getClass().getSimpleName());
            if (validation.size() == 1) {
                throw validation.iterator().next();
            } else {
                throw new MultipleValidationException(validation);
            }
        }
    }

    O doExecute(I input);

    default String describeInput(I input) {
        return objectToJson(input);
    }

    default String describeOutput(I input, O output) {
        return objectToJson(output);
    }

    default OperationValidator<I> getValidator() {
        return (validator, request) -> validator;
    }

    default OperationLogger getOperationLogger() {
        return infoLogger(LoggerFactory.getLogger(this.getClass()));
    }
}
