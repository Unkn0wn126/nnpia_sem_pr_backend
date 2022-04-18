package cz.upce.fei.sem_pr_backend.service;

import java.security.Principal;

public interface AuthorizationService {
    boolean isAuthenticated(Principal principal);
    boolean isAdmin(Principal principal);
    boolean canAlterResource(Principal principal, Long authorId);
    String getPrincipalName(Principal principal);
}
