package com.sat.serviciodescargamasiva.Automatizador.Controllers;

import com.sat.serviciodescargamasiva.Automatizador.ProductoServicio.OperacionesProductoServicio;
import com.sat.serviciodescargamasiva.Automatizador.ProductoServicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto-servicio")
public class ProductoServicioController {
    @Autowired
    private OperacionesProductoServicio operacionesProductoServicio;

    @GetMapping("/{claveProdServ}")
    public ResponseEntity<ProductoServicio> cargaProductoServicio(@PathVariable("claveProdServ") long claveProdServ) {
        ProductoServicio productoServicio = operacionesProductoServicio.encuentraProductoServicio(claveProdServ);
        System.out.println("ProductoServicio: "+productoServicio);
        if(productoServicio == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(productoServicio, HttpStatus.OK);
    }
}
