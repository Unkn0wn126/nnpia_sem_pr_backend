package cz.upce.fei.sem_pr_backend.domain.enum_type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IssueSeverity {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private static Map<String, IssueSeverity> FORMAT_MAP = Stream
            .of(IssueSeverity.values())
            .collect(Collectors.toMap(s -> s.formatted, Function.identity()));

    private final String formatted;

    IssueSeverity(String formatted) {
        this.formatted = formatted;
    }

    @JsonCreator
    public static IssueSeverity fromString(String string){
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
