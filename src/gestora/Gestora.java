package gestora;

import entidades.Cliente;
import entidades.Factura;
import entidades.Usuario;
import mensaje.Mensaje;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class Gestora {

    private static String path = "ScriptBBDD/ScriptVivero.sql";
    private Factura factura;
    private Cliente cliente;
    private Usuario usuario;
    private static Connection miConexion;

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Connection getMiConexion() {
        return miConexion;
    }

    public void setMiConexion(Connection miConexion) {
        this.miConexion = miConexion;
    }

    public void Instalador() {
        String execCommand = "sqlcmd -i " + path;
        try {
            /* directorio/ejecutable es el path del ejecutable y un nombre */
            Process p = Runtime.getRuntime().exec(execCommand);
        } catch (Exception e) {
            /* Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable. */
            e.printStackTrace();
        }
    }

    public void comprobarLogin() {


    }

}
