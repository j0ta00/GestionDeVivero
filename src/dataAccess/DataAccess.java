package dataAccess;

import entidades.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
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
        Properties configuracion = new Properties();boolean conexionExitosa=false;
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
            resultado=consulta.executeQuery(String.format("SELECT Id,Usuario,Contrasenhia,EsGestor FROM USUARIOS WHERE Usuario='%s' AND Contrasenhia='%s'",usuario,contrasenhia));
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
                producto=new Producto(resultado.getString("Descripcion"),resultado.getInt("Codigo"),
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
        } catch (SQLException e) {}
        return factura;
    }

    public static boolean modificarTipoPlantaDeUnaPlanta(int codigoPlanta,int idTipoPlanta,int idNuevoTipoPlanta){
        boolean tipoPlantaModificadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            if(consulta.executeUpdate(String.format("UPDATE Tipo_Plantas_Plantas SET Id_Tipo_Planta=%d Where Id_Tipo_Planta=%d AND Codigo_Planta=%d",idNuevoTipoPlanta,idTipoPlanta,codigoPlanta))!=0) {
                tipoPlantaModificadoConExito = true;
            }
        } catch (SQLException e) {}
        return tipoPlantaModificadoConExito;
    }
    public static boolean modificarCliente(Cliente cliente){
        boolean clienteModificadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("UPDATE CLIENTES SET  nombre='%s',dni='%s',direccion='%s', Codigo_Postal='%s', ciudad='%s', Telefono='%s', Correo_Electronico='%s' WHERE Dni='%s'",cliente.getNombre(),cliente.getDni(),cliente.getDireccion()
                    ,cliente.getCodigoPostal(),cliente.getCiudad(),cliente.getTelefono(),cliente.getCorreoElectronico(),cliente.getDni()));
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
            consulta.executeUpdate(String.format("UPDATE Usuarios SET EsGestor=%d,Usuario='%s',Contrasenhia='%s',Nombre='%s',Dni='%s',Direccion='%s',Codigo_Postal='%s',Ciudad='%s',Telefono='%s',Correo_Electronico='%s' WHERE Id=%d"
                    ,usuario.isEsGestor()?1:0,usuario.getUsuario(),usuario.getContrasenhia(),usuario.getNombre(), usuario.getDni(),usuario.getDireccion(),usuario.getCodigoPostal(),usuario.getCiudad(),usuario.getTelefono(),usuario.getCorreoElectronico(),usuario.getId()));
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
            consulta.executeUpdate(String.format("UPDATE Productos SET Descripcion='%s',Precio_Unitario=%s,Unidades_Disponibles=%d Where Codigo=%d",producto.getDescripcion(),String.valueOf(producto.getPrecioUnitario()).replace(',','.'),producto.getUnidadesDisponibles(),producto.getCodigo()));
            productoModificadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoModificadoConExito;
    }
    public static boolean modificarTipoPlanta(int idTipoPlanta,String tipo){
        boolean productoModificadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("UPDATE Tipo_Plantas SET Tipo='%s'Where Id=%d",tipo,idTipoPlanta));
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
            consulta.executeUpdate(String.format("INSERT INTO CLIENTES VALUES ('%s','%s','%s','%s','%s','%s','%s')",cliente.getNombre(),cliente.getDni(),cliente.getDireccion()
                    ,cliente.getCodigoPostal(),cliente.getCiudad(),cliente.getTelefono(),cliente.getCorreoElectronico()));
            clienteInsertadoConExito=true;
        } catch (SQLException e) {}
        return clienteInsertadoConExito;
    }
    public static boolean insertarUsuario(Usuario usuario){
        boolean usuarioInsertadoConExito=false;Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO Usuarios VALUES (%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s')",usuario.isEsGestor()?1:0,usuario.getUsuario(),usuario.getContrasenhia(),
                    usuario.getNombre(),usuario.getDni(),usuario.getDireccion()
                    ,usuario.getCodigoPostal(),usuario.getCiudad(),usuario.getTelefono(),usuario.getCorreoElectronico()));
            usuarioInsertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioInsertadoConExito;
    }

    public static boolean comprobarDniExiste(String dni,boolean esCliente){
        Statement consulta;ResultSet resultado=null;boolean existe=false;String tabla=esCliente?"Clientes":"Usuarios";
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM %s Where Dni='%s'",tabla,dni));
            existe=resultado.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static boolean insertarProducto(Producto producto){
        boolean productoInsertadoConExito=false;Statement consulta;String precioUnitario="";
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO Productos(Descripcion,Precio_Unitario,Unidades_Disponibles) VALUES ('%s',%s,%d)",producto.getDescripcion(),String.valueOf(producto.getPrecioUnitario()).replace(',','.'),producto.getUnidadesDisponibles()));
            productoInsertadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoInsertadoConExito;
    }
    public static void insertarProductoPlantaOJardineria(List<Tipo_Planta> listaTipoPlantas){
        Statement consulta;String query="";
        try {
            if(listaTipoPlantas==null) {
                query=new StringBuilder("INSERT INTO ProductosJardineria values").append("(").append("(SELECT MAX(Codigo) FROM Productos)").append(")").toString();
            }else{
                query=new StringBuilder("INSERT INTO ProductosPlanta values").append("(").append("(SELECT MAX(Codigo) FROM Productos)").append(")").toString();
            }
            consulta=conexion.createStatement();
            consulta.executeUpdate(query);
            if(listaTipoPlantas!=null) {
                insertarTiposDeLaPlanta(listaTipoPlantas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertarTiposDeLaPlanta(List<Tipo_Planta> listaTipoPlantas) throws SQLException {
        Statement consulta;
        consulta=conexion.createStatement();
            listaTipoPlantas.forEach(tipoPlanta -> {
                try {
                    consulta.executeUpdate(String.format("INSERT INTO Tipo_Plantas_Plantas values (%d,(SELECT MAX(Codigo) FROM Productos))",tipoPlanta.getId()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });


    }
    public static boolean insertarTipoPlanta(String tipo){
      boolean tipoPlantaInsertadoConExito=false;Statement consulta;
       try {
            consulta=conexion.createStatement();
           consulta.executeUpdate(String.format("INSERT INTO Tipo_Plantas(Tipo) VALUES ('%s')",tipo));
            tipoPlantaInsertadoConExito=true;
       } catch (SQLException e) {
           e.printStackTrace();
       }
        return tipoPlantaInsertadoConExito;
   }
    public static int borrarTipoPlantaEnPlanta(int codigoPlanta,int idTipoPlanta){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Tipo_Plantas_Plantas WHERE Codigo_Planta=%d AND Id_Tipo_Planta=%d",codigoPlanta,idTipoPlanta));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static int borrarUsuario(String usuario){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Usuarios WHERE Usuario=%'s'",usuario));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static int borrarCliente(String idCliente){
       int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Clientes WHERE Dni='%s'",idCliente));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static int borrarTipoPlanta(int idTipoPlanta){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Tipo_Plantas WHERE Id=%d",idTipoPlanta));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }
    public static int borrarProducto(int idProducto,boolean esPlanta){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Tipo_Plantas WHERE Id=%d",idProducto));
            if(esPlanta && resultadoDelBorrado!=0){
                resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM ProductosJardineria WHERE Id=%d",idProducto));
            }else{
                resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM ProductosPlanta WHERE Id=%d",idProducto));
            }
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static boolean comprobarCantidadDeProducto(int idProducto,int cantidadDeProducto){
        boolean esSuficiente=false;ResultSet resultado=null;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT Unidades_Disponibles FROM Productos WHERE Codigo=%d",idProducto));
            if(resultado.next() && resultado.getInt("Unidades_Disponibles")>=cantidadDeProducto){
                esSuficiente=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esSuficiente;
    }

    public static boolean comprobarSiProductoYaExisteEnFactura(int idProducto,int idFactura){
        boolean existe=false;
        Statement consulta;
        ResultSet resultado=null;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT Codigo_Producto FROM Productos_Facturas WHERE Codigo_Producto=%d AND Id_Factura=%d",idProducto,idFactura));
            if(resultado.next()){
                existe=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }


    public static boolean actualizarProductoEnPedido(int cantidad,int idFactura,int idProduto){
        boolean actualizadoConExito=false;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("UPDATE PRODUCTOS_FACTURAS SET Cantidad=%d Where Codigo_Producto=%d AND Id_Factura=%d",cantidad,idProduto,idFactura));
            actualizadoConExito=true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actualizadoConExito;
    }

    public static Tipo_Planta obtenerTipoPlanta(int idTipoPlanta){
        Tipo_Planta tipo_planta=null;ResultSet resultado=null;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Tipo_Plantas Where Id=%d",idTipoPlanta));
            if(resultado.next()){
                tipo_planta=new Tipo_Planta(idTipoPlanta,resultado.getString("Tipo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipo_planta;
    }

    public static boolean comprobarSiUsuarioExiste(String usuario){
        boolean existe=false;
        Statement consulta;
        ResultSet resultado=null;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Usuarios WHERE usuario='%s'",usuario));
            existe=resultado.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public static Usuario obtenerUsuarioPorDni(String dni){
        Statement consulta;ResultSet resultado=null;Usuario usuarioEncontrado=null;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM USUARIOS WHERE dni='%s'",dni));
            if(resultado.next()){
                usuarioEncontrado=construirUsuario(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEncontrado;
    }
    public static Usuario obtenerUsuarioPorId(int id){
        Statement consulta;ResultSet resultado=null;Usuario usuarioEncontrado=null;
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM USUARIOS WHERE id=%d",id));
            if(resultado.next()){
                usuarioEncontrado=construirUsuario(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioEncontrado;
    }

    private static Usuario construirUsuario(ResultSet resultado) throws SQLException {
        return new Usuario(resultado.getString("usuario"),resultado.getString("contrasenhia"),resultado.getString("nombre"),resultado.getString("dni"),
                resultado.getString("direccion"),resultado.getString("Codigo_Postal"),resultado.getString("ciudad"),resultado.getString("Telefono"),
                resultado.getString("Correo_Electronico"),resultado.getInt("id"),resultado.getBoolean("EsGestor"));
    }

    public static boolean insertarProductoEnPedido(Factura factura,Producto producto,int cantidadProducto) {//en procesos de hacerlo
        boolean insertadoConExito=false;
        Statement consulta;
        try {
            consulta=conexion.createStatement();
            consulta.executeUpdate(String.format("INSERT INTO PRODUCTOS_FACTURAS VALUES(%d,%d,%d)",factura.getId(),cantidadProducto,producto.getCodigo()));
            insertadoConExito=true;
        } catch (SQLException e) {}
        return  insertadoConExito;
    }

 public static void aplicarDescuentoClienteRegistrado(int idFactura){
     Statement consulta;
     try {
         consulta=conexion.createStatement();
         consulta.executeUpdate(String.format("UPDATE Facturas Set Importe=Importe*0.95 Where Id=%d",idFactura));
     }catch (SQLException e) {}
 }


    public static List<FacturaProducto> obtenerFactura(int idFactura){
         Statement consulta;ResultSet resultado=null;List<FacturaProducto>listaFacturaProductos=new LinkedList<FacturaProducto>();
        try {
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT P.Codigo,P.Descripcion,P.Precio_Unitario,PF.Cantidad,(PF.Cantidad*P.Precio_Unitario) AS Total FROM Productos_Facturas as PF INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo WHERE PF.Id_Factura=%d",idFactura));
            while(resultado.next()){listaFacturaProductos.add(new FacturaProducto(resultado.getInt("Codigo"),resultado.getInt("Cantidad"),resultado.getString("Descripcion"),resultado.getDouble("Total"),resultado.getDouble("Precio_Unitario"))); }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaFacturaProductos;
    }

    public static boolean borrarFactura(int idFactura){
        CallableStatement callStmt=null;boolean borrado=false;
        try {
            callStmt=conexion.prepareCall("{CALL BorrarFactura(?)}");
            callStmt.setInt(1,idFactura);
            callStmt.execute();
            borrado=true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return borrado;
    }

    public static List<InformeVenta> obtenerImporteVentasProductosMensuales(int anhio,int mes){
        List<InformeVenta> informeMensual=new LinkedList<InformeVenta>();
        informeMensual.add(obtenerImporteVentasTodosLosProductosMensuales(anhio,mes));
        informeMensual.add(obtenerImporteVentasProductosPlantaMensuales(anhio,mes));
        informeMensual.add(obtenerImporteVentasProductosJardineriaMensuales(anhio,mes));
        return informeMensual;
    }
    private static InformeVenta obtenerImporteVentasTodosLosProductosMensuales(int anhio,int mes){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorMesEnTodosLosProductos(%d,%d)",mes,anhio));
            if(resultado.next()){
               informe=new InformeVenta("Todos los productos",resultado.getDouble("ImporteTotal"),resultado.getInt("CantidadTotal"));
            }
        }catch(SQLException ex){}
        return informe;
    }
    private static InformeVenta obtenerImporteVentasProductosPlantaMensuales(int anhio,int mes){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorMesEnLosProductosPlanta(%d,%d)",mes,anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Planta",resultado.getDouble("ImporteTotalPlantas"),resultado.getInt("CantidadTotalPlantas"));
            }
        }catch(SQLException ex){}
        return informe;
    }
    private static InformeVenta obtenerImporteVentasProductosJardineriaMensuales(int anhio,int mes){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorMesEnLosProductosJardineria(%d,%d)",mes,anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Jardineria",resultado.getDouble("ImporteTotalJardineria"),resultado.getInt("CantidadTotalJardineria"));
            }
        }catch(SQLException ex){}
        return informe;
    }

    public static List<InformeVenta> obtenerImporteVentasProductosAnuales(int anhio){
        List<InformeVenta> informeAnual=new LinkedList<InformeVenta>();
        informeAnual.add(obtenerImporteVentasTodosLosProductosAnuales(anhio));
        informeAnual.add(obtenerImporteVentasProductosPlantaAnuales(anhio));
        informeAnual.add(obtenerImporteVentasProductosJardineriaAnuales(anhio));
        return informeAnual;
    }
    private static InformeVenta obtenerImporteVentasTodosLosProductosAnuales(int anhio){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorAnhioEnTodosLosProductos(%d)",anhio));
            if(resultado.next()){
                informe=new InformeVenta("Todos los productos",resultado.getDouble("ImporteTotal"),resultado.getInt("CantidadTotal"));
            }
        }catch(SQLException ex){}
        return informe;
    }
    private static InformeVenta obtenerImporteVentasProductosPlantaAnuales(int anhio){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorAnhioEnLosProductosPlanta(%d)",anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Planta",resultado.getDouble("ImporteTotalPlantas"),resultado.getInt("CantidadTotalPlantas"));
            }
        }catch(SQLException ex){}
        return informe;
    }
    private static InformeVenta obtenerImporteVentasProductosJardineriaAnuales(int anhio){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=conexion.createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorAnhioEnLosProductosJardineria(%d)",anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Jardineria",resultado.getDouble("ImporteTotalJardineria"),resultado.getInt("CantidadTotalJardineria"));
            }
        }catch(SQLException ex){}
        return informe;
    }




//    private static InformeVenta obtenerImporteVentasProductosPlantaMensuales(){
//
//    }
//    private static InformeVenta obtenerImporteVentasJardineriaMensuales(){
//
//    }
//
//
//
//
//
//    public static List<InformeVenta> obtenerImporteVentasProductosAnuales(int anhio){}



}
