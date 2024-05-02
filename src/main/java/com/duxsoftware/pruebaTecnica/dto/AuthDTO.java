package com.duxsoftware.pruebaTecnica.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Schema(title = "Auth", name = "Auth")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotNull
    @Schema(title = "username", required = true)
    private String username;

    @NotNull
    @Schema(title = "password", required = true)
    private String password;

}
