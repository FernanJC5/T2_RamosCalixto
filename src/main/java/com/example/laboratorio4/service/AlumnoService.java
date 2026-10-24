package com.example.laboratorio4.service;

import com.example.laboratorio4.model.Alumno;
import com.example.laboratorio4.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    private final AlumnoRepository repo;
    public AlumnoService(AlumnoRepository repo) { this.repo = repo; }

    private Alumno calcular(Alumno a) {
        a.setEstadoAcademico(a.getPromedio() >= 13 ? "REGULAR" : "EN RIESGO");
        return a;
    }

    public List<Alumno> listar() {
        return repo.findAll().stream().map(this::calcular).toList();
    }
    public Optional<Alumno> buscar(Long id) { return repo.findById(id).map(this::calcular); }
    public Alumno guardar(Alumno a) { return calcular(repo.save(a)); }

    public Optional<Alumno> actualizar(Long id, Alumno datos) {
        return repo.findById(id).map(a -> {
            a.setNombres(datos.getNombres());
            a.setCarrera(datos.getCarrera());
            a.setCiclo(datos.getCiclo());
            a.setPromedio(datos.getPromedio());
            return calcular(repo.save(a));
        });
    }
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorRangoPrecio(double minimo, double maximo) {
        return productoRepository.buscarPorRangoPrecio(minimo, maximo);
    }

    public List<Producto> buscarConStockMinimo(int stockMinimo) {
        return productoRepository.buscarConStockMinimo(stockMinimo);
    }
}
