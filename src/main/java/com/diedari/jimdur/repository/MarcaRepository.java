package com.diedari.jimdur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diedari.jimdur.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository <Marca, Long> {

    public List<Marca> findByNombre(String nombre);
    public List<Marca> findByActivo(Boolean activo);
}
