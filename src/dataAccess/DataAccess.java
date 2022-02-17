package dataAccess;

import entidades.Cliente;
import entidades.Factura;
import entidades.Producto;
import entidades.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
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
    public static boolean consultarSiProductoEsPlanta(Producto producto){
        Statement consulta;ResultSet resultado=null;boolean esPlanta=false;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM ProductosPlanta WHERE codigo='%s'",producto.getCodigo()));
            esPlanta=resultado.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esPlanta;
    }

    public static Producto obtenerProducto(int codigoProducto) {
        Producto producto=null;ResultSet resultado=null;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Productos WHERE Codigo='%s'",codigoProducto));
            if(resultado.next()){
                producto=new Producto(resultado.getString("Descripcion"),resultado.getString("Codigo"),
                        resultado.getInt("Unidades_Disponibles"),resultado.getDouble("Precio_Unitario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;

    }



    public static Cliente consultarSiClienteExiste(String dniOTelefonoCliente, boolean esDni) {
        Statement consulta;ResultSet resultado=null;Cliente cliente=null;
        String query=esDni?new StringBuilder("Dni=").append(String.format("'%s'",dniOTelefonoCliente)).toString():new StringBuilder("Telefono=").append(String.format("'%s'",dniOTelefonoCliente)).toString();
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Clientes Where %s",query));
            if(resultado.next()){
                cliente=new Cliente(resultado.getString("Nombre"),resultado.getString("Dni"),resultado.getString("Direccion"),resultado.getString("Codigo_Postal"),
                        resultado.getString("Ciudad"),resultado.getString("Telefono"),resultado.getString("Correo_Electronico"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;

    }

    public static Factura crearFactura(Cliente cliente,Usuario usuario) {
        boolean creadoConExito=false;LocalDate tiempo=LocalDate.now();Factura factura=null;ResultSet resultado=null;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO FACTURAS VALUES ('%s',%d,'%s',%d)",cliente.getDni(),usuario.getId(),tiempo,0));
            resultado=consulta.executeQuery(String.format("SELECT MAX(Id) as Id FROM Facturas"));
            if(resultado.next()) {
                factura = new Factura(resultado.getInt("Id"),cliente.getDni(),usuario.getId(),tiempo,0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return factura;
    }

    public static boolean borrarFactura(){
        boolean facturaBorradaConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("DELETE FROM FACTURAS WHERE Id=(SELECT MAX(Id) FROM FACTURAS)"));
            facturaBorradaConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturaBorradaConExito;
    }

    public static boolean modificarCliente(Cliente cliente){
        boolean clienteModificadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("UPDATE CLIENTES SET  nombre='%s',dni=%d,direccion='%s', Codigo_Postal='%s', ciudad='%s', Telefono='%s', Coreo_Electronico='%s'",cliente.getNombre(),cliente.getDni(),cliente.getDireccion()
            ,cliente.getCodigoPostal(),cliente.getCiudad(),cliente.getTelefono(),cliente.getCorreoElectronico()));
            clienteModificadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienteModificadoConExito;
    }
    public static boolean modificarUsuario(Usuario usuario){
        boolean usuarioModificadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("UPDATE Usuarios SET id=%d,esGestor=%b,nombre=%d,contrasenhia='%s' ",usuario.getId(),usuario.isEsGestor(),usuario.getNombre(),usuario.getContrasenhia()));
            usuarioModificadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioModificadoConExito;
    }
    public static boolean modificarProducto(Producto producto){
        boolean productoModificadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("UPDATE Productos SET descripcion='%s',codigo=%d,precioUnitario=%d,unidades_Disponibles=%d",producto.getDescripcion(),producto.getCodigo(),producto.getPrecioUnitario(),producto.getUnidadesDisponibles()));
            productoModificadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoModificadoConExito;
    }
//    public static boolean modificarTipoPlanta(TipoPlanta tipoPlanta){
//        boolean tipoPlantaModificadoConExito=false;Statement consulta;
//        try {
//            consulta=conexion.createStatement();
//            consulta.executeUpdate(String.format("UPDATE Tipo_Planta SET id=%d,tipo='%s'",tipoPlanta.getId(),tipoPlanta.getTipo()));
//            tipoPlantaModificadoConExito=true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return tipoPlantaModificadoConExito;
//    }

    public static boolean insertarCliente(Cliente cliente){
        boolean clienteInsertadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO CLIENTES VALUES ('%s',%d,'%s','%s','%s','%s','%s')", cliente.getDni(),cliente.getNombre(),cliente.getDireccion()
                    ,cliente.getCodigoPostal(),cliente.getCiudad(),cliente.getTelefono(),cliente.getCorreoElectronico()));
            clienteInsertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienteInsertadoConExito;
    }
    public static boolean insertarUsuario(Usuario usuario){
        boolean usuarioInsertadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO Usuarios VALUES (%d,%b,%d,'%s')",usuario.getId(),usuario.isEsGestor(),usuario.getNombre(),usuario.getContrasenhia()));
            usuarioInsertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioInsertadoConExito;
    }
    public static boolean insertarProducto(Producto producto){
        boolean usuarioInsertadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO Productos VALUES ('%s',%d,%d,%d)",producto.getDescripcion(),producto.getCodigo(),producto.getPrecioUnitario(),producto.getUnidadesDisponibles()));
            usuarioInsertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioInsertadoConExito;
    }
//    public static boolean insertarTipoPlanta(TipoPlanta tipoPlanta){
//        boolean usuarioInsertadoConExito=false;Statement consulta;
//        try {
//            consulta=conexion.createStatement();
//            consulta.executeUpdate(String.format("INSERT INTO Tipo_Planta VALUES (%d,'%s')",tipoPlanta.getId(),tipoPlanta.getTipo()));
//            usuarioInsertadoConExito=true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return usuarioInsertadoConExito;
//    }


    public static boolean borrarProducto(int idProducto){
        boolean productoEliminadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("DELETE FROM Productor WHERE codigo=%d",idProducto));
            productoEliminadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoEliminadoConExito;
    }

    public static boolean borrarUsuario(int idUsuario){
        boolean usuarioEliminadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("DELETE FROM Usuarios WHERE id=%d",idUsuario));
            usuarioEliminadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEliminadoConExito;
    }

    public static boolean borrarCliente(int idCliente){
        boolean clienteEliminadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("DELETE FROM Clientes WHERE id=%d",idCliente));
            clienteEliminadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clienteEliminadoConExito;
    }

    public static boolean borrarTipoPlanta(int idTipoPlanta){
        boolean tipoPlantaEliminadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("DELETE FROM Tipo_planta WHERE id=%d",idTipoPlanta));
            tipoPlantaEliminadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoPlantaEliminadoConExito;
    }

    public static boolean insertarProductoEnPedido(Factura factura,Producto producto,int cantidadProducto) {//en procesos de hacerlo
        boolean insertadoConExito=false;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO PRODUCTOS_FACTURAS VALUES(%d,%d,%)",factura.getId(),cantidadProducto,producto.getCodigo()));
            insertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  insertadoConExito;
    }
}
