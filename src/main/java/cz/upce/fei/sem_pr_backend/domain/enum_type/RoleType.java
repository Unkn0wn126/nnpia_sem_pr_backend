package cz.upce.fei.sem_pr_backend.domain.enum_type;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

public enum RoleType {
    ROLE_ADMIN,
    ROLE_USER;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority(this.name()));
        return permissions;
    }
}
