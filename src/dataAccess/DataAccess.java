package dataAccess;

import entidades.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataAccess{

    private static Connection conexion;
    private
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

    public void consultarDatosLogin(Usuario usuario){
        Statement consulta;ResultSet resultado;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT FROM USUARIOS WHERE Nombre=%s AND Contrasenhia=%s",usuario.getNombre(),usuario.getContrasenhia()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
