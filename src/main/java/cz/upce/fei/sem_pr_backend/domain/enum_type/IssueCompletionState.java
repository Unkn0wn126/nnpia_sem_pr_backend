package cz.upce.fei.sem_pr_backend.domain.enum_type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum IssueCompletionState {
    TODO("TODO"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private static Map<String, IssueCompletionState> FORMAT_MAP = Stream
            .of(IssueCompletionState.values())
            .collect(Collectors.toMap(s -> s.formatted, Function.identity()));

    private final String formatted;

    IssueCompletionState(String formatted) {
        this.formatted = formatted;
    }

    @JsonCreator
    public static IssueCompletionState fromString(String string){
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
