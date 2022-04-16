package cz.upce.fei.sem_pr_backend.dto.authentication;

import cz.upce.fei.sem_pr_backend.dto.applicationuser.ApplicationUserGetDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken;
    private String refreshToken;
    private ApplicationUserGetDto userGetDto;
}
