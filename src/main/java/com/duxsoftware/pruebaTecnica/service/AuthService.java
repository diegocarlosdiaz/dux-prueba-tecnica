package com.duxsoftware.pruebaTecnica.service;
import com.duxsoftware.pruebaTecnica.dto.AuthDTO;
import com.duxsoftware.pruebaTecnica.dto.AuthResponseDTO;
import com.duxsoftware.pruebaTecnica.entity.Usuario;
import com.duxsoftware.pruebaTecnica.exceptions.UnauthorizedException;
import com.duxsoftware.pruebaTecnica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    public AuthResponseDTO login(AuthDTO authDTO) throws UnauthorizedException {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    authDTO.getUsername(), authDTO.getPassword()
            );
            authenticationManager.authenticate(authToken);

            Usuario user = usuarioRepository.findByUsername(authDTO.getUsername()).get();
            String jwt =  jwtService.generateToken(user, generateExtraClaims(user));

            return new AuthResponseDTO(jwt);
        } catch (Exception e){
            throw new UnauthorizedException("Usuario invalido");
        }
    }

    private Map<String, Object> generateExtraClaims(Usuario user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());
        return extraClaims;
    }
}
