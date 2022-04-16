package cz.upce.fei.sem_pr_backend.domain.enum_type;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RoleType {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority(this.name()));
        return permissions;
    }

    private static Map<String, RoleType> FORMAT_MAP = Stream
            .of(RoleType.values())
            .collect(Collectors.toMap(s -> s.formatted, Function.identity()));

    private final String formatted;

    RoleType(String formatted) {
        this.formatted = formatted;
    }

    @JsonCreator
    public static RoleType fromString(String string){
        return Optional
                .ofNullable(FORMAT_MAP.get(string))
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
