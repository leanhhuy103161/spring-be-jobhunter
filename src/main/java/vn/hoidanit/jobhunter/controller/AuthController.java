package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.domain.dto.LoginDTO;
import vn.hoidanit.jobhunter.domain.dto.ResLoginDTO;
import vn.hoidanit.jobhunter.util.SecurityUtil;

@RestController
public class AuthController {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final SecurityUtil securityUtil;

  public AuthController(
      AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
    this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.securityUtil = securityUtil;
  }

  @PostMapping("/login")
  public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
    Authentication authenticaion =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    String accessToken = this.securityUtil.createToken(authenticaion);
    SecurityContextHolder.getContext().setAuthentication(authenticaion);

    ResLoginDTO response = new ResLoginDTO();
    response.setAccessToken(accessToken);
    return ResponseEntity.ok().body(response);
  }
}
