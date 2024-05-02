package com.duxsoftware.pruebaTecnica.repository;

import com.duxsoftware.pruebaTecnica.entity.Equipo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipoRepository extends CrudRepository<Equipo, Long> {
    public List<Equipo> findByNombre(String nombre);
}
