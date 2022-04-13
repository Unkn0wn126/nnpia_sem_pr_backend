package cz.upce.fei.sem_pr_backend.domain.enum_type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum UserState {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    BANNED("BANNED");

    private static Map<String, UserState> FORMAT_MAP = Stream
            .of(UserState.values())
            .collect(Collectors.toMap(s -> s.formatted, Function.identity()));

    private final String formatted;

    UserState(String formatted) {
        this.formatted = formatted;
    }

    @JsonCreator
    public static UserState fromString(String string){
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
