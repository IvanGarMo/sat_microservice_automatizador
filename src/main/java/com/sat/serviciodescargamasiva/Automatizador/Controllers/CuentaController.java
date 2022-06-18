package com.sat.serviciodescargamasiva.Automatizador.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sat.serviciodescargamasiva.Automatizador.Automatizador.ResponseData;
import com.sat.serviciodescargamasiva.Automatizador.Cuentas.*;
import com.sat.serviciodescargamasiva.Automatizador.permisos.Autorizacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private OperacionesCuenta cuentaRepo;
    @Autowired
    private Autorizacion autorizacion;

    @GetMapping("/categorias")
    public ResponseEntity<Iterable<CategoriaCuenta>> cargaCategorias(@RequestHeader("uuid") String uidUserFirebase) {
        Iterable<CategoriaCuenta> categorias = cuentaRepo.cargaCategoriasCuentas();
        System.out.println("Categorias: "+categorias);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cuenta[]> cargaCuentas(@RequestHeader("uuid") String uidUserFirebase,
                                                 @PathVariable("idCliente") long idCliente)
            throws JsonProcessingException {
        long idUsuario = autorizacion.cargaIdUsaurio(uidUserFirebase);
        System.out.println("IdUsuario: "+idUsuario+" idCliente: "+idCliente);
        Cuenta[] cuentas = cuentaRepo.cargaCuentas(idUsuario, idCliente);
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/lista-simplificada")
    public ResponseEntity<ClienteInfoBasica[]> cargaClientes(@RequestHeader("uuid") String uidUserFirebase) throws JsonProcessingException {
        long idUsuario = autorizacion.cargaIdUsaurio(uidUserFirebase);
        ClienteInfoBasica[] clientes = cuentaRepo.cargaClientes(idUsuario);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseData> creaCuenta(@RequestHeader("uuid") String uidUserFirebase,
                                                   @RequestBody CuentaReprFrontEnd cuentaFrontEnd) {
        long idUsuario = autorizacion.cargaIdUsaurio(uidUserFirebase);
        Cuenta cuenta = new Cuenta(cuentaFrontEnd);
        System.out.println("CuentaFrontEnd: "+cuentaFrontEnd);
        System.out.println("Cuenta creada: "+cuenta);
        cuenta.setIdUsuario(idUsuario);
        ResponseData rd = cuentaRepo.creaCuenta(cuenta);
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @DeleteMapping("/{idCuenta}")
    public ResponseEntity<ResponseData> eliminaCuenta(@RequestHeader("uuid") String uidUserFirebase,
                                                      @PathVariable Long idCuenta) {
        ResponseData rd = cuentaRepo.eliminaCuenta(idCuenta);
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ResponseData> cambiaNombreCuenta(@RequestBody Cuenta cuenta) {
        ResponseData rd = cuentaRepo.actualizaNombreCuenta(cuenta.getIdCuenta(), cuenta.getDescripcion());
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @GetMapping("/busca-por-categoria")
    public ResponseEntity<Cuenta[]> cargaCuentasPorCategoriaPorClientePorUsuario(
                @RequestHeader("uuid") String uidUserFirebase,
                @RequestParam long idCategoria,
                @RequestParam long idCliente
                ) throws JsonProcessingException {
        long idUsuario = autorizacion.cargaIdUsaurio(uidUserFirebase);
        Cuenta[] cuentas = cuentaRepo.cuentasPorCategoriaCliente(idCategoria, idUsuario, idCliente);

        if(cuentas == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/resumen-reglas")
    public ResponseEntity<List<CuentaReglaListado>> cargaReglasPorCuentaPorUsuario(
            @RequestHeader("uuid") String uidUserFirebase,
            @RequestParam("idCliente") long idCliente,
            @RequestParam("idCuenta") long idCuenta
    ) {
        System.out.println("IdCliente: "+idCliente+" idCuenta: "+idCuenta);
        List<CuentaReglaListado> cuentas = cuentaRepo.cargaListadoReglasPorCuentaCliente(idCuenta, idCliente);
        if(cuentas == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}