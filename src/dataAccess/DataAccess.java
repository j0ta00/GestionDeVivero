package dataAccess;

import entidades.*;

import java.io.*;
import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class DataAccess{

    private static Connection conexion;

    private static final String PROPERTIESFILEPATH="configuracion.properties",URL="jdbc:sqlserver"
            ,BASE_DE_DATOS="database=MunozArenas;trustServerCertificate=true";

    public static Connection getConexion() {
        return conexion;
    }

    public static void setConexion(Connection conexion) {
        DataAccess.conexion = conexion;
    }

    public static void escribirDatosEnFicheroProperties(String contrasenhia,String usuario,String puerto,String localhost){
        Properties configuracion = new Properties();
        try (OutputStream os = new FileOutputStream(PROPERTIESFILEPATH)) {
            configuracion.setProperty("URL",URL);
            configuracion.setProperty("BBDD",BASE_DE_DATOS);
            configuracion.setProperty("CLAVE",contrasenhia);
            configuracion.setProperty("USUARIO",usuario);
            configuracion.setProperty("PUERTO",new StringBuilder(":").append(puerto).append(";").toString());
            configuracion.setProperty("LOCALHOST",new StringBuilder("://").append(localhost).toString());
            configuracion.store(os,"");
        }  catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static boolean incializarConexion() {
        Properties configuracion = new Properties();boolean conexionExitosa=false;
        try (InputStream is = new FileInputStream(PROPERTIESFILEPATH)) {
            configuracion.load(is);
            conexion = DriverManager.getConnection((new StringBuilder(configuracion.getProperty("URL")).append(configuracion.getProperty("LOCALHOST")).append(configuracion.getProperty("PUERTO")).append(configuracion.getProperty("BBDD")).toString()), configuracion.getProperty("USUARIO"), configuracion.getProperty("CLAVE"));
            conexionExitosa=true;
        } catch (SQLException e) {
        } catch (FileNotFoundException e) {} catch (IOException e) {}
        return conexionExitosa;
    }


    public static boolean comprobarDniExiste(String dni,boolean esCliente){
        Statement consulta;ResultSet resultado=null;boolean existe=false;String tabla=esCliente?"Clientes":"Usuarios";
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM %s Where Dni='%s'",tabla,dni));
            existe=resultado.next();
        } catch (SQLException e) {
        }
        return existe;
    }


    public static List<Usuario> obtenerUsuarioSegunTipo(boolean esGestor){
        Statement consulta;ResultSet resultado=null;List<Usuario> usuarios=new LinkedList<>();
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM USUARIOS WHERE esGestor=%d",esGestor?1:0));
            while(resultado.next()){
               usuarios.add(DataAccessUsuario.construirUsuario(resultado));
            }
        } catch (SQLException e) {

        }
        return usuarios;
    }


    public static void aplicarDescuentoClienteRegistrado(int idFactura){
     Statement consulta;
     try {
         consulta=conexion.createStatement();
         consulta.executeUpdate(String.format("UPDATE Facturas Set Importe=Importe*0.95 Where Id=%d",idFactura));
     }catch (SQLException e) {}
 }

    public static void introducirDatosPorDefecto(){
        CallableStatement callStmt=null;
        try {
            callStmt=conexion.prepareCall("{CALL IntroducirDatosPorDefecto()}");
            callStmt.execute();
        } catch (SQLException throwables){}
    }
}
