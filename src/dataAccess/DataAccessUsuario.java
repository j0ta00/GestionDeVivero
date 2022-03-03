package dataAccess;

import entidades.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccessUsuario {
    public static Usuario consultarDatosLogin(String usuario, String contrasenhia){
        Statement consulta;
        ResultSet resultado=null;Usuario usuarioEncontrado=null;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT Id,Usuario,Contrasenhia,EsGestor FROM USUARIOS WHERE Usuario='%s' AND Contrasenhia='%s'",usuario,contrasenhia));
            if(resultado.next()){
                usuarioEncontrado=new Usuario(resultado.getInt("Id"),resultado.getString("usuario"),resultado.getString("contrasenhia"),resultado.getBoolean("esGestor"));
            }
        } catch (SQLException e) {}
        return usuarioEncontrado;
    }

    public static boolean modificarUsuario(Usuario usuario){
        boolean usuarioModificadoConExito=false;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("UPDATE Usuarios SET EsGestor=%d,Usuario='%s',Contrasenhia='%s',Nombre='%s',Dni='%s',Direccion='%s',Codigo_Postal='%s',Ciudad='%s',Telefono='%s',Correo_Electronico='%s' WHERE Id=%d"
                    ,usuario.isEsGestor()?1:0,usuario.getUsuario(),usuario.getContrasenhia(),usuario.getNombre(), usuario.getDni(),usuario.getDireccion(),usuario.getCodigoPostal(),usuario.getCiudad(),usuario.getTelefono(),usuario.getCorreoElectronico(),usuario.getId()));
            usuarioModificadoConExito=true;
        } catch (SQLException e) {}
        return usuarioModificadoConExito;
    }

    public static boolean insertarUsuario(Usuario usuario){
        boolean usuarioInsertadoConExito=false;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("INSERT INTO Usuarios VALUES (%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s')",usuario.isEsGestor()?1:0,usuario.getUsuario(),usuario.getContrasenhia(),
                    usuario.getNombre(),usuario.getDni(),usuario.getDireccion()
                    ,usuario.getCodigoPostal(),usuario.getCiudad(),usuario.getTelefono(),usuario.getCorreoElectronico()));
            usuarioInsertadoConExito=true;
        } catch (SQLException e) {}
        return usuarioInsertadoConExito;
    }

    public static int borrarUsuario(String usuario){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Usuarios WHERE Usuario=%'s'",usuario));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static boolean comprobarSiUsuarioExiste(String usuario){
        boolean existe=false;
        Statement consulta;
        ResultSet resultado=null;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Usuarios WHERE usuario='%s'",usuario));
            existe=resultado.next();
        } catch (SQLException e) {}
        return existe;
    }

    public static Usuario obtenerUsuarioPorDni(String dni){
        Statement consulta;ResultSet resultado=null;Usuario usuarioEncontrado=null;
        try {
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM USUARIOS WHERE dni='%s'",dni));
            if(resultado.next()){
                usuarioEncontrado=construirUsuario(resultado);
            }
        } catch (SQLException e) {}
        return usuarioEncontrado;
    }

    public static Usuario obtenerUsuarioPorId(int id){
        Statement consulta;ResultSet resultado=null;Usuario usuarioEncontrado=null;
        try {
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM USUARIOS WHERE id=%d",id));
            if(resultado.next()){
                usuarioEncontrado=construirUsuario(resultado);
            }
        } catch (SQLException e) {}
        return usuarioEncontrado;
    }

    static Usuario construirUsuario(ResultSet resultado) throws SQLException {
        return new Usuario(resultado.getString("usuario"),resultado.getString("contrasenhia"),resultado.getString("nombre"),resultado.getString("dni"),
                resultado.getString("direccion"),resultado.getString("Codigo_Postal"),resultado.getString("ciudad"),resultado.getString("Telefono"),
                resultado.getString("Correo_Electronico"),resultado.getInt("id"),resultado.getBoolean("EsGestor"));
    }
}
