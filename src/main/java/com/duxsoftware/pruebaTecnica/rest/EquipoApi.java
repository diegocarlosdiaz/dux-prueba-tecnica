package com.duxsoftware.pruebaTecnica.rest;

import com.duxsoftware.pruebaTecnica.entity.Equipo;
import com.duxsoftware.pruebaTecnica.exceptions.BadRequestException;
import com.duxsoftware.pruebaTecnica.exceptions.NotFoundException;
import com.duxsoftware.pruebaTecnica.exceptions.error.ErrorMessage;
import com.duxsoftware.pruebaTecnica.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("equipos")
@Tag(name = "Equipos", description = "Equipos")
@Produces(value = {MediaType.APPLICATION_JSON})
@Consumes(value = {MediaType.APPLICATION_JSON})
@SecurityScheme(name = "SecurityScheme", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class EquipoApi {

    @Autowired
    EquipoService equipoService;

    @GetMapping(value = "")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(operationId = "getEquipos", summary="Lista los equipos de la bdd", description = "Permite listar todos los equipos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ArrayList<Equipo> getAllEquipos(@RequestParam(required = false) String nombre){
        return equipoService.getAllEquipos(nombre);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(operationId = "getEquiposById", summary = "Lista un equipo por id", description = "Permite obtener un equipo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND"),
    })
    public ResponseEntity<Equipo> getEquipoById(@PathVariable long id) throws NotFoundException {
        Optional<Equipo> optionalEquipo = equipoService.getEquipoById(id);
        if (optionalEquipo.isPresent()) {
            Equipo equipo = optionalEquipo.get();
            return ResponseEntity.ok().body(equipo);
        } else {
            throw new NotFoundException("Equipo no encontrado");
        }
    }

    @PostMapping(value = "")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(operationId = "postEquipo", summary="Crea un equipo en la bdd", description = "Permite crear un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) throws BadRequestException{
        try {
            Equipo savedEquipo = equipoService.saveEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipo);
        } catch (Exception e) {
            throw new BadRequestException("La solicitud es invalida");
        }
    }


    @SecurityRequirement(name = "SecurityScheme")
    @PutMapping(value = "/{id}")
    @Operation(operationId = "putEquipo", summary="Edita un equipo en la bdd", description = "Permite editar un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    public ResponseEntity<Equipo> updateEquipo(@PathVariable long id, @RequestBody Equipo equipo) throws NotFoundException {

        Optional<Equipo> optionalEquipo = Optional.ofNullable(equipoService.updateEquipo(id, equipo));
        if (optionalEquipo.isPresent()) {
            Equipo equipo1 = optionalEquipo.get();
            return ResponseEntity.ok().body(equipo1);
        } else {
            throw new NotFoundException("Equipo no encontrado");
        }
    }

    @DeleteMapping(value = "/{id}")
    @SecurityRequirement(name = "SecurityScheme")
    @Operation(operationId = "deleteEquipo", summary="Elimina un equipo en la bdd", description = "Permite eliminar un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND")
    })
    public ResponseEntity<Void> deleteEquipo(@PathVariable long id) throws NotFoundException {
        try {
            equipoService.deleteEquipo(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new NotFoundException("Equipo no encontrado");
        }

    }
}
