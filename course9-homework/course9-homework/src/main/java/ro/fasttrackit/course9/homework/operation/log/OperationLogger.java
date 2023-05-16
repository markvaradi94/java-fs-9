package ro.fasttrackit.course9.homework.operation.log;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;
import static ro.fasttrackit.course9.homework.util.Utils.biConsumeNothing;

@RequiredArgsConstructor
public class OperationLogger {
    private final BiConsumer<String, Object[]> loggerFunction;
    private final Supplier<Boolean> loggingCheck;
    private final Logger logger;

    public static OperationLogger infoLogger(Logger logger) {
        return new OperationLogger(logger::info, logger::isInfoEnabled, logger);
    }

    public static OperationLogger debugLogger(Logger logger) {
        return new OperationLogger(logger::debug, logger::isDebugEnabled, logger);
    }

    public static OperationLogger noLogging() {
        return new OperationLogger(biConsumeNothing(), () -> false, null);
    }

    public void error(String msg, Throwable exception) {
        if (check(Logger::isErrorEnabled)) {
            logger.error(msg, exception);
        }
    }

    public void warn(String msg) {
        warn(msg, new Object[0]);
    }

    public void warn(String msg, Throwable throwable) {
        if (check(Logger::isWarnEnabled)) {
            logger.warn(msg, throwable);
        }
    }

    public void warn(String msg, Object... args) {
        if (check(Logger::isWarnEnabled)) {
            logger.warn(msg, args);
        }
    }

    public void debugLogger(String msg) {
        debugLogger(msg, new Object[0]);
    }

    public void debugLogger(String msg, Object... args) {
        if (check(Logger::isDebugEnabled)) {
            logger.debug(msg, args);
        }
    }

    public void log(String msg) {
        log(msg, () -> new Object[]{});
    }

    public void log(String msg, Supplier<Object[]> args) {
        if (this.isEnabled()) {
            loggerFunction.accept(msg, args.get());
        }
    }

    public boolean isEnabled() {
        return loggingCheck.get();
    }

    public String getName() {
        return ofNullable(logger)
                .map(Logger::getName)
                .orElse("");
    }

    private boolean check(Predicate<Logger> check) {
        return ofNullable(logger)
                .filter(check)
                .isPresent();
    }
}
