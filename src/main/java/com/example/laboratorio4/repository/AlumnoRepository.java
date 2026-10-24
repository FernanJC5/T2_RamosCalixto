package com.example.laboratorio4.repository;

import com.example.laboratorio4.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p " +
           "WHERE p.precio BETWEEN :minimo AND :maximo " +
           "ORDER BY p.precio")
    List<Alumno> buscarPorRangoPrecio(
            @Param("minimo") double minimo,
            @Param("maximo") double maximo);

    List<Alumno> buscarConStockMinimo(
            @Param("stockMinimo") int stockMinimo);
}
