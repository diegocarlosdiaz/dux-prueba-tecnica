package com.duxsoftware.pruebaTecnica.service;

import com.duxsoftware.pruebaTecnica.entity.Equipo;
import com.duxsoftware.pruebaTecnica.exceptions.BadRequestException;
import com.duxsoftware.pruebaTecnica.exceptions.NotFoundException;
import com.duxsoftware.pruebaTecnica.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EquipoService {
    @Autowired
    EquipoRepository equipoRepository;

    public ArrayList<Equipo> getAllEquipos(String nombre){
        if(nombre == null){
            ArrayList<Equipo> equipos = (ArrayList<Equipo>) equipoRepository.findAll();
            return equipos;
        } else {
            return (ArrayList<Equipo>) equipoRepository.findByNombre(nombre);
        }

    }
    public Optional<Equipo> getEquipoById(long id) throws NotFoundException {
        Optional<Equipo> equipo = equipoRepository.findById(id);
        if(!equipo.isPresent()){
            throw new NotFoundException("Equipo no encontrado");
        }
        return Optional.of(equipo.get());
    }
    public Equipo saveEquipo(Equipo equipo) throws BadRequestException {
        try {
            return equipoRepository.save(equipo);
        } catch (Exception e) {
            throw new BadRequestException("La solicitud es invalida");
        }
    }

    public Equipo updateEquipo(long id, Equipo equipo) throws NotFoundException{
        Optional<Equipo> existingEquipoOptional = equipoRepository.findById(id);
        if (existingEquipoOptional.isEmpty()) {
            throw new NotFoundException("Equipo no encontrado");
        }
        Equipo existingEquipo = existingEquipoOptional.get();
        existingEquipo.setNombre(equipo.getNombre());
        existingEquipo.setLiga(equipo.getLiga());
        existingEquipo.setPais(equipo.getPais());

        return equipoRepository.save(existingEquipo);
    }

    public void deleteEquipo(long id) throws NotFoundException {
        Optional<Equipo> existingEquipoOptional = equipoRepository.findById(id);
        if (existingEquipoOptional.isEmpty()) {
            throw new NotFoundException("Equipo no encontrado");
        }
        equipoRepository.deleteById(id);
    }


}
