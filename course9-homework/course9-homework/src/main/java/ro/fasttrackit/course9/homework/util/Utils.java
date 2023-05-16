package ro.fasttrackit.course9.homework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS;
import static java.lang.String.valueOf;

@Slf4j
@UtilityClass
public class Utils {
    private static final ObjectMapper objcetMapper = JsonMapper.builder()
            .enable(ACCEPT_CASE_INSENSITIVE_ENUMS)
            .build()
            .setSerializationInclusion(NON_NULL);

    public static <T, R> BiConsumer<T, R> biConsumeNothing() {
        return (t, r) -> {
        };
    }

    public static String limitMessage(Object msg) {
        return limitMessage(msg, 5000);
    }

    public static String limitMessage(Object msg, int count) {
        String message = valueOf(msg);
        if (message != null && message.length() > count) {
            return message.substring(0, count / 2) + " ... " + message.substring(message.length() - count / 2);
        } else {
            return message;
        }
    }

    public static String objectToJson(Object source) {
        return objectToJson(source, String::valueOf);
    }

    public static String objectToJson(Object source, Function<Object, String> fallback) {
        if (source instanceof String sourceString) {
            return sourceString;
        } else if (source instanceof Number) {
            return String.valueOf(source);
        }
        try {
            return objcetMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            return fallback.apply(source);
        }
    }
}
