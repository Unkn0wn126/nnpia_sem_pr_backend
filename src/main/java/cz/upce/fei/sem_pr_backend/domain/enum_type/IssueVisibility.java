package cz.upce.fei.sem_pr_backend.domain.enum_type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IssueVisibility {
    PRIVATE("PRIVATE"),
    INTERNAL("INTERNAL"),
    PUBLIC("PUBLIC");

    private static Map<String, IssueVisibility> FORMAT_MAP = Stream
            .of(IssueVisibility.values())
            .collect(Collectors.toMap(s -> s.formatted, Function.identity()));

    private final String formatted;

    IssueVisibility(String formatted) {
        this.formatted = formatted;
    }

    @JsonCreator
    public static IssueVisibility fromString(String string){
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
