package com.sat.serviciodescargamasiva.Automatizador.Cuentas;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;

@Data
@NoArgsConstructor
public class Cuenta {
    private long idCuenta;
    @NotNull(message="Es necesario que seleccione un campo de código padre")
    @Size(max=1000, message="El campo Código Padre no puede exceder de 1000 caracteres. Si tiene necesidades especiales, contacte al desarrollador")
    private String codigoPadre;
    @NotNull(message="Es necesario que seleccione un campo de código padre")
    @Size(max=1000, message="El campo Código Padre no puede exceder de 1000 caracteres. Si tiene necesidades especiales, contacte al desarrollador")
    private String codigo;
    @NotNull(message="Es necesario que seleccione una categoría")
    private int idCategoria;
    @NotNull(message="Es necesario que seleccione un nivel")
    private int nivel;
    @NotNull(message="El campo descripción no puede ir vacío")
    @Size(max=1000, message="La longitud del campo descripción no puede ser mayor a 1000 caracteres")
    private String descripcion;
    private long idUsuario;
    private long idCliente;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean esCuentaOrigen;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean esCuentaPersonalizada;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean cuentaNivelUsuario;
    @JsonDeserialize(using = CustomBooleanDeserializer.class)
    private boolean cuentaNivelCliente;

    public Cuenta(CuentaReprFrontEnd cuenta) {
        this.idCuenta = -1;
        this.codigoPadre = cuenta.getCodigoPadre();
        this.codigo = cuenta.getCodigo();
        this.idCategoria = ((int) cuenta.getIdCategoria());
        this.nivel = cuenta.getNivel();
        this.descripcion = cuenta.getDescripcion();
        this.idUsuario = cuenta.getIdUsuario();
        this.idCliente = cuenta.getIdCliente();
        this.esCuentaOrigen = cuenta.isEsCuentaOrigen();
        this.esCuentaPersonalizada = cuenta.isEsCuentaPersonalizada();
        this.cuentaNivelUsuario = cuenta.isCuentaNivelUsuario();
        this.cuentaNivelCliente = cuenta.isCuentaNivelCliente();
    }
}

class CustomBooleanDeserializer extends StdDeserializer<Boolean> {

    public CustomBooleanDeserializer(){ this(null); }

    protected CustomBooleanDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        return jsonParser.getText().equals("1");
    }
}