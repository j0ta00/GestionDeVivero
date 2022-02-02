package dataAccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataAccess{

    private static Connection conexion;
    private static final String PROPERTIESFILEPATH="configuracion.properties";

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

    public consultar(){


    }
}
