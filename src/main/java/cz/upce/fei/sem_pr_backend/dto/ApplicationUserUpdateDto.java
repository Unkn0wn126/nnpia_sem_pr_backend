package cz.upce.fei.sem_pr_backend.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationUserUpdateDto implements Serializable {
    private final Long id;
    private final ProfileUpdateDto profile;
}
