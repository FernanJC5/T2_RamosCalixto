package com.example.laboratorio4.controller;

import com.example.laboratorio4.model.Alumno;
import com.example.laboratorio4.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class ProductoController {

    private final AlumnoService service;
    public AlumnoController(AlumnoService service) { this.service = service; }

    @GetMapping
    public List<Alumno> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> buscar(@PathVariable Long id) {
        return service.buscar(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(a));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @RequestBody Alumno a) {
        return service.actualizar(id, a).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build()
                                    : ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    @GetMapping("/precio")
    public List<Producto> buscarPorPrecio(
            @RequestParam double minimo,
            @RequestParam double maximo) {
        return productoService.buscarPorRangoPrecio(minimo, maximo);
    }

    @GetMapping("/stock")
    public List<Producto> buscarPorStock(
            @RequestParam int minimo) {
        return productoService.buscarConStockMinimo(minimo);
    }
}
