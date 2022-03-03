package dataAccess;

import entidades.*;

import java.sql.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DataAccessFactura {
    public static boolean consultarSiProductoEsPlanta(Producto producto){
        Statement consulta;
        ResultSet resultado=null;boolean esPlanta=false;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM ProductosPlanta WHERE codigo='%s'",producto.getCodigo()));
            esPlanta=resultado.next();
        } catch (SQLException e) {}
        return esPlanta;
    }

    public static Factura crearFactura(Cliente cliente, Usuario usuario) {
        boolean creadoConExito=false;
        Calendar calendario=Calendar.getInstance();Factura factura=null;ResultSet resultado=null;
        Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("INSERT INTO FACTURAS VALUES ('%s',%d,'%s',%d)",cliente.getDni(),usuario.getId(),new Date(calendario.getTime().getTime()),0));
            resultado=consulta.executeQuery(String.format("SELECT MAX(Id) as Id FROM Facturas"));
            if(resultado.next()) {
                factura = new Factura(resultado.getInt("Id"),cliente.getDni(),usuario.getId(),new Date(calendario.getTime().getTime()),0);
            }
        } catch (SQLException e) {}
        return factura;
    }

    public static boolean comprobarSiProductoYaExisteEnFactura(int idProducto, int idFactura){
        boolean existe=false;
        Statement consulta;
        ResultSet resultado=null;
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT Codigo_Producto FROM Productos_Facturas WHERE Codigo_Producto=%d AND Id_Factura=%d",idProducto,idFactura));
            if(resultado.next()){
                existe=true;
            }
        } catch (SQLException e) {}
        return existe;
    }

    public static boolean actualizarProductoEnPedido(int cantidad, int idFactura, int idProduto){
        boolean actualizadoConExito=false;
        Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("UPDATE PRODUCTOS_FACTURAS SET Cantidad=%d Where Codigo_Producto=%d AND Id_Factura=%d",cantidad,idProduto,idFactura));
            actualizadoConExito=true;
        } catch (SQLException e) { }
        return actualizadoConExito;
    }

    public static boolean insertarProductoEnPedido(Factura factura, Producto producto, int cantidadProducto) {//en procesos de hacerlo
        boolean insertadoConExito=false;
        Statement consulta;
        try {
            consulta= DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("INSERT INTO PRODUCTOS_FACTURAS VALUES(%d,%d,%d)",factura.getId(),cantidadProducto,producto.getCodigo()));
            insertadoConExito=true;
        } catch (SQLException e) {}
        return  insertadoConExito;
    }

    public static List<FacturaProducto> obtenerLineaPedidos(int idFactura){
         Statement consulta;ResultSet resultado=null;List<FacturaProducto>listaFacturaProductos=new LinkedList<FacturaProducto>();
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT P.Codigo,P.Descripcion,P.Precio_Unitario,PF.Cantidad,(PF.Cantidad*P.Precio_Unitario) AS Total FROM Productos_Facturas as PF INNER JOIN Productos AS P ON PF.Codigo_Producto=P.Codigo WHERE PF.Id_Factura=%d",idFactura));
            while(resultado.next()){listaFacturaProductos.add(new FacturaProducto(resultado.getInt("Codigo"),resultado.getInt("Cantidad"),resultado.getString("Descripcion"),resultado.getDouble("Total"),resultado.getDouble("Precio_Unitario"))); }
        } catch (SQLException e) {}
        return listaFacturaProductos;
    }

    public static boolean borrarFactura(int idFactura){
        CallableStatement callStmt=null;boolean borrado=false;
        try {
            callStmt= DataAccess.getConexion().prepareCall("{CALL BorrarFactura(?)}");
            callStmt.setInt(1,idFactura);
            callStmt.execute();
            borrado=true;
        } catch (SQLException throwables) {}
    return borrado;
    }
}
