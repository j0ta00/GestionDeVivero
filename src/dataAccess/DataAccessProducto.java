package dataAccess;

import entidades.Producto;
import entidades.Tipo_Planta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataAccessProducto {
    public static boolean modificarTipoPlantaDeUnaPlanta(int codigoPlanta,int idTipoPlanta,int idNuevoTipoPlanta){
        boolean tipoPlantaModificadoConExito=false;
        Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            if(consulta.executeUpdate(String.format("UPDATE Tipo_Plantas_Plantas SET Id_Tipo_Planta=%d Where Id_Tipo_Planta=%d AND Codigo_Planta=%d",idNuevoTipoPlanta,idTipoPlanta,codigoPlanta))!=0) {
                tipoPlantaModificadoConExito = true;
            }
        } catch (SQLException e) {}
        return tipoPlantaModificadoConExito;
    }

    public static boolean modificarProducto(Producto producto){
        boolean productoModificadoConExito=false;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("UPDATE Productos SET Descripcion='%s',Precio_Unitario=%s,Unidades_Disponibles=%d Where Codigo=%d",producto.getDescripcion(),String.valueOf(producto.getPrecioUnitario()).replace(',','.'),producto.getUnidadesDisponibles(),producto.getCodigo()));
            productoModificadoConExito=true;
        } catch (SQLException e) {}
        return productoModificadoConExito;
    }

    public static boolean modificarTipoPlanta(int idTipoPlanta, String tipo){
        boolean productoModificadoConExito=false;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            productoModificadoConExito=consulta.executeUpdate(String.format("UPDATE Tipo_Plantas SET Tipo='%s'Where Id=%d",tipo,idTipoPlanta))==1;
        } catch (SQLException e) {}
        return productoModificadoConExito;
    }

    public static boolean insertarProducto(Producto producto){
        boolean productoInsertadoConExito=false;Statement consulta;String precioUnitario="";
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("INSERT INTO Productos(Descripcion,Precio_Unitario,Unidades_Disponibles) VALUES ('%s',%s,%d)",producto.getDescripcion(),String.valueOf(producto.getPrecioUnitario()).replace(',','.'),producto.getUnidadesDisponibles()));
            productoInsertadoConExito=true;
        } catch (SQLException e) {}
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
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(query);
            if(listaTipoPlantas!=null) {
                insertarTiposDeLaPlanta(listaTipoPlantas);
            }
        } catch (SQLException e) {}
    }

    public static void insertarTiposDeLaPlanta(List<Tipo_Planta> listaTipoPlantas) throws SQLException {
        Statement consulta;
        consulta= DataAccess.getConexion().createStatement();
            listaTipoPlantas.forEach(tipoPlanta -> {
                try {
                    consulta.executeUpdate(String.format("INSERT INTO Tipo_Plantas_Plantas values (%d,(SELECT MAX(Codigo) FROM Productos))",tipoPlanta.getId()));
                } catch (SQLException throwables) {}
            });


    }

    public static boolean insertarTipoPlanta(String tipo){
      boolean tipoPlantaInsertadoConExito=false;Statement consulta;
       try {
            consulta= DataAccess.getConexion().createStatement();
           consulta.executeUpdate(String.format("INSERT INTO Tipo_Plantas(Tipo) VALUES ('%s')",tipo));
            tipoPlantaInsertadoConExito=true;
       } catch (SQLException e) {}
        return tipoPlantaInsertadoConExito;
   }

    public static int borrarTipoPlantaEnPlanta(int codigoPlanta, int idTipoPlanta){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Tipo_Plantas_Plantas WHERE Codigo_Planta=%d AND Id_Tipo_Planta=%d",codigoPlanta,idTipoPlanta));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static int borrarTipoPlanta(int idTipoPlanta){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Tipo_Plantas WHERE Id=%d",idTipoPlanta));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static int borrarProducto(int idProducto, boolean esPlanta){
        int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
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

    public static boolean comprobarCantidadDeProducto(int idProducto, int cantidadDeProducto){
        boolean esSuficiente=false;
        ResultSet resultado=null;
        Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT Unidades_Disponibles FROM Productos WHERE Codigo=%d",idProducto));
            if(resultado.next() && resultado.getInt("Unidades_Disponibles")>=cantidadDeProducto){
                esSuficiente=true;
            }
        } catch (SQLException e) {}
        return esSuficiente;
    }

    public static Tipo_Planta obtenerTipoPlanta(int idTipoPlanta){
        Tipo_Planta tipo_planta=null;ResultSet resultado=null;
        Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Tipo_Plantas Where Id=%d",idTipoPlanta));
            if(resultado.next()){
                tipo_planta=new Tipo_Planta(idTipoPlanta,resultado.getString("Tipo"));
            }
        } catch (SQLException e) {}
        return tipo_planta;
    }
}
