package cz.upce.fei.sem_pr_backend.dto.applicationuser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.upce.fei.sem_pr_backend.domain.ApplicationUser;
import cz.upce.fei.sem_pr_backend.domain.Issue;
import cz.upce.fei.sem_pr_backend.domain.Role;
import cz.upce.fei.sem_pr_backend.domain.enum_type.RoleType;
import cz.upce.fei.sem_pr_backend.domain.enum_type.UserState;
import cz.upce.fei.sem_pr_backend.dto.DTO;
import cz.upce.fei.sem_pr_backend.dto.JsonTimeStampSerializer;
import cz.upce.fei.sem_pr_backend.dto.issue.IssueGetDto;
import cz.upce.fei.sem_pr_backend.dto.profile.ProfileGetDto;
import cz.upce.fei.sem_pr_backend.dto.role.RoleGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserGetDto implements Serializable, DTO {
    private Long id;
    @NotEmpty
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    private ProfileGetDto profile;
    private Set<RoleGetDto> roles;
    @NotNull
    private UserState state;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp created;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp lastEdited;

    @Override
    public ModelMapper updateModelMapper(ModelMapper modelMapper) {
        modelMapper.typeMap(ApplicationUser.class, ApplicationUserGetDto.class)
                .addMappings(mapping -> {
                    mapping.map(ApplicationUser::getRoles, ApplicationUserGetDto::setRoles);
                });

        return modelMapper;
    }
}
