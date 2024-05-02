package com.duxsoftware.pruebaTecnica.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "AuthResponse", name = "AuthResponse")
@Builder
public class AuthResponseDTO {
    @NotNull
    @Schema(title = "jwt", required = true, example = "eyJhbGciOiJSUzI1NiIsInR5cCIgOi...")
    private String jwt;
}
