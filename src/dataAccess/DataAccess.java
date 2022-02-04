package dataAccess;

import entidades.Cliente;
import entidades.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class DataAccess{

    private static Connection conexion;
    private static final String PROPERTIESFILEPATH="configuracion.properties";

    public static Connection getConexion() {
        return conexion;
    }

    public static void setConexion(Connection conexion) {
        DataAccess.conexion = conexion;
    }

    public static boolean incializarConexion() {
        Properties configuracion = new Properties();
        boolean conexionExitosa=false;
        try (InputStream is = new FileInputStream(PROPERTIESFILEPATH)) {
            configuracion.load(is);
            conexion = DriverManager.getConnection(configuracion.getProperty("URL"), configuracion.getProperty("USUARIO"), configuracion.getProperty("CLAVE"));
            conexionExitosa=true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conexionExitosa;
    }

    public static Usuario consultarDatosLogin(String usuario, String contrasenhia){
        Statement consulta;ResultSet resultado=null;Usuario usuarioEncontrado=null;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM USUARIOS WHERE Usuario='%s' AND Contrasenhia='%s'",usuario,contrasenhia));
            if(resultado.next()){
                usuarioEncontrado=new Usuario(resultado.getInt("Id"),resultado.getString("usuario"),resultado.getString("contrasenhia"),resultado.getBoolean("esGestor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEncontrado;
    }

    public static Cliente consultarDatosDni(String dniCliente) {
        boolean dniEncontrado=false;Statement consulta;ResultSet resultado=null;Cliente cliente=null;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Clientes WHERE Dni='%s'",dniCliente));
            if(resultado.next()){
                cliente=new Cliente(resultado.getString("nombre"),resultado.getString("dni"),resultado.getString("direccion"),resultado.getString("codigoPostal"),
                        resultado.getString("telefono"),resultado.getString("correoElectronico"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;

    }

    public static Factura crearFactura(Cliente cliente,Usuario usuario) {
        Factura factura =null;LocalDate tiempo=LocalDate.now();
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.execute(String.format("INSERT INTO FACTURAS VALUES (%d,%d,'%s',%d)",cliente.getDni(),usuario.getId(),tiempo.toString(),0));
            factura=new factura(cliente.getDni(),usuario.getId(),tiempo,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  creadoConExito;
    }

    public static boolean insertarProductoEnPedido() {
        boolean insertadoConExito=false;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.execute(String.format("INSERT INTO PRODUCTOS_FACTURAS"));
            insertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  insertadoConExito;
    }
}
