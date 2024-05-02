package com.duxsoftware.pruebaTecnica;

import com.duxsoftware.pruebaTecnica.entity.Equipo;
import com.duxsoftware.pruebaTecnica.exceptions.BadRequestException;
import com.duxsoftware.pruebaTecnica.exceptions.NotFoundException;
import com.duxsoftware.pruebaTecnica.repository.EquipoRepository;
import com.duxsoftware.pruebaTecnica.service.EquipoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepositoryMock;

    @InjectMocks
    private EquipoService equipoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllEquipos_shouldReturnEmptyList() {
        when(equipoRepositoryMock.findAll()).thenReturn(new ArrayList<>());

        ArrayList<Equipo> equipos = equipoService.getAllEquipos(null);

        assertNotNull(equipos);
        assertTrue(equipos.isEmpty());
    }

    @Test
    public void testGetAllEquipos_shouldReturnListOfEquipos() {
        Equipo equipo1 = new Equipo(1L, "Real Madrid", "La Liga", "España");
        Equipo equipo2 = new Equipo(2L, "Barcelona", "La Liga", "España");
        ArrayList<Equipo> equipos = new ArrayList<>();
        equipos.add(equipo1);
        equipos.add(equipo2);
        when(equipoRepositoryMock.findAll()).thenReturn(equipos);

        ArrayList<Equipo> returnedEquipos = equipoService.getAllEquipos(null);

        assertNotNull(returnedEquipos);
        assertEquals(2, returnedEquipos.size());
        assertEquals(equipos, returnedEquipos); // Check for object equality
    }
    @Test
    public void testGetAllEquipos_shouldReturnListByNombre() {
        Equipo equipo1 = new Equipo(1L, "Real Madrid", "La Liga", "España");
        Equipo equipo2 = new Equipo(2L, "Barcelona", "La Liga", "España");
        ArrayList<Equipo> equipos = new ArrayList<>();
        equipos.add(equipo1);
        equipos.add(equipo2);
        when(equipoRepositoryMock.findByNombre("Real Madrid")).thenReturn(equipos);

        ArrayList<Equipo> returnedEquipos = equipoService.getAllEquipos("Real Madrid");

        assertNotNull(returnedEquipos);
        assertEquals(2, returnedEquipos.size());
        assertEquals(equipos, returnedEquipos); // Check for object equality
    }

    @Test
    public void testGetEquipoById_shouldReturnEmptyOptional() {
        when(equipoRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            equipoService.getEquipoById(1L);
        });
    }


    @Test
    public void testGetEquipoById_shouldReturnEquipo() throws NotFoundException {
        Equipo equipo = new Equipo(1L, "Real Madrid", "La Liga", "España");
        when(equipoRepositoryMock.findById(1L)).thenReturn(Optional.of(equipo));

        Optional<Equipo> equipoOptional = equipoService.getEquipoById(1L);

        assertTrue(equipoOptional.isPresent());
        assertEquals(equipo, equipoOptional.get());
    }

    @Test
    public void testSaveEquipo_shouldSaveEquipo() throws BadRequestException {
        Equipo equipo = new Equipo(null, "Nuevo Equipo", "Nueva Liga", "Nuevo País");

        when(equipoRepositoryMock.save(equipo)).thenReturn(equipo);

        Equipo savedEquipo = equipoService.saveEquipo(equipo);

        assertNotNull(savedEquipo);
        assertEquals(equipo.getNombre(), savedEquipo.getNombre()); // Check specific properties
        assertEquals(equipo.getLiga(), savedEquipo.getLiga());
        assertEquals(equipo.getPais(), savedEquipo.getPais());
        verify(equipoRepositoryMock).save(equipo);
    }

    @Test
    public void testUpdateEquipo_shouldUpdateEquipo() throws NotFoundException {
        Equipo existingEquipo = new Equipo(1L, "Real Madrid", "La Liga", "España");
        when(equipoRepositoryMock.findById(1L)).thenReturn(Optional.of(existingEquipo));

        Equipo updateData = new Equipo(null, "Real Madrid CF", "Primera División", "España");

        when(equipoRepositoryMock.save(any(Equipo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Equipo updatedEquipo = equipoService.updateEquipo(1l, updateData);

        assertNotNull(updatedEquipo);
        assertEquals(1l, updatedEquipo.getId()); // Verify ID remains the same
        assertEquals(updateData.getNombre(), updatedEquipo.getNombre());
        assertEquals(updateData.getLiga(), updatedEquipo.getLiga());
        assertEquals(updateData.getPais(), updatedEquipo.getPais());
        verify(equipoRepositoryMock).save(any(Equipo.class));
    }

}