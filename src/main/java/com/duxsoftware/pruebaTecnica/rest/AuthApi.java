package com.duxsoftware.pruebaTecnica.rest;

import com.duxsoftware.pruebaTecnica.dto.AuthDTO;
import com.duxsoftware.pruebaTecnica.dto.AuthResponseDTO;
import com.duxsoftware.pruebaTecnica.exceptions.UnauthorizedException;
import com.duxsoftware.pruebaTecnica.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@RestController
@Tag(name = "Autorización", description = "Autorización")
@RequestMapping("/auth")
public class AuthApi {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @SecurityRequirement(name = "BasicAuth")
    @Operation(operationId = "accessToken", summary = "Autorización para acceder a los webservices", description = "Obtiene el token de transacción para acceder al webservice.")
    public ResponseEntity<AuthResponseDTO> doAuthenticated(@RequestBody @Valid AuthDTO authDTO) throws UnauthorizedException {
        try{
            AuthResponseDTO jwtDto = authService.login(authDTO);
            return ResponseEntity.ok(jwtDto);
        } catch (Exception e) {
            throw new UnauthorizedException("Usuario invalido");
        }

    }
}
