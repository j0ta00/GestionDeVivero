package gestora;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class Gestora {

    private static String path = "ScriptBBDD/ScriptVivero.sql";
    private final static String PROPERTIESFILEPATH = "configuracion.properties";


    public static void comprobarInstalacion() {
        Properties configuracion = new Properties();
        Connection miConexion;
        Statement query;
        try (InputStream is = new FileInputStream(PROPERTIESFILEPATH)){
            configuracion.load(is);
            miConexion = getConnection(configuracion.getProperty("URL"), configuracion.getProperty("USUARIO"), configuracion.getProperty("CLAVE"));
        } catch (SQLException e) {
            Instalador();
        } catch (FileNotFoundException e) {
            System.out.println("error");
        } catch (IOException e) {
            System.out.println("error");
        }

    }
    public static void Instalador() {
        String execCommand = "sqlcmd -i " + path;
        try {
            /* directorio/ejecutable es el path del ejecutable y un nombre */
            Process p = Runtime.getRuntime().exec(execCommand);
        } catch (Exception e) {
            /* Se lanza una excepción si no se encuentra en ejecutable o el fichero no es ejecutable. */
            e.printStackTrace();
        }
    }

    public static void comprobarLogin() {
    }

}
