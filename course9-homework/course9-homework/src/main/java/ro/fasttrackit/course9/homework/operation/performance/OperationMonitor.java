package ro.fasttrackit.course9.homework.operation.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fasttrackit.course9.homework.operation.log.OperationLogger;

import java.util.function.Supplier;

import static java.lang.System.currentTimeMillis;
import static java.util.Optional.ofNullable;
import static ro.fasttrackit.course9.homework.operation.log.OperationLogger.infoLogger;

public class OperationMonitor {
    private final OperationLogger log;
    private final String prefix;
    private long duration;

    public OperationMonitor() {
        this(infoLogger(LoggerFactory.getLogger(OperationLogger.class)));
    }

    public OperationMonitor(String prefix) {
        this(infoLogger(LoggerFactory.getLogger(OperationLogger.class)), prefix);
    }

    public OperationMonitor(Logger log) {
        this(new OperationLogger(log::info, log::isInfoEnabled, log), null);
    }

    public OperationMonitor(OperationLogger log) {
        this(log, null);
    }

    public OperationMonitor(OperationLogger log, String prefix) {
        this.log = log;
        this.prefix = prefix;
    }

    public long getDuration() {
        return duration;
    }

    public void monitor(Runnable runnable) {
        monitor(null, runnable);
    }

    public void monitor(String name, Runnable runnable) {
        long start = currentTimeMillis();
        runnable.run();
        long end = currentTimeMillis();
        duration = end - start;
        ofNullable(name)
                .ifPresent(n -> log.log(logPrefix() + name + " took " + duration));
    }

    private String logPrefix() {
        return ofNullable(prefix)
                .map(p -> "[" + p + "] ")
                .orElse("");
    }

    public <T> T monitor(Supplier<T> runnable) {
        return monitor(null, runnable);
    }

    public <T> T monitor(String name, Supplier<T> runnable) {
        long start = currentTimeMillis();
        T result = runnable.get();
        long end = currentTimeMillis();
        duration = end - start;
        ofNullable(name)
                .ifPresent(n -> log.log(logPrefix() + name + " took " + duration));
        return result;
    }
}
