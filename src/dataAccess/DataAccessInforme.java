package dataAccess;

import entidades.InformeVenta;
import entidades.Producto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataAccessInforme {
    public static Producto obtenerProducto(int codigoProducto) {
        Producto producto=null;
        ResultSet resultado=null;
        Statement consulta;
        try {
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Productos WHERE Codigo='%s'",codigoProducto));
            if(resultado.next()){
                producto=new Producto(resultado.getString("Descripcion"),resultado.getInt("Codigo"),
                        resultado.getInt("Unidades_Disponibles"),resultado.getDouble("Precio_Unitario"));
            }
        } catch (SQLException e) {}
        return producto;

    }

    public static List<InformeVenta> obtenerImporteVentasProductosMensuales(int anhio, int mes){
        List<InformeVenta> informeMensual=new LinkedList<InformeVenta>();
        informeMensual.add(obtenerImporteVentasTodosLosProductosMensuales(anhio,mes));
        informeMensual.add(obtenerImporteVentasProductosPlantaMensuales(anhio,mes));
        informeMensual.add(obtenerImporteVentasProductosJardineriaMensuales(anhio,mes));
        return informeMensual;
    }

    private static InformeVenta obtenerImporteVentasTodosLosProductosMensuales(int anhio, int mes){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorMesEnTodosLosProductos(%d,%d)",mes,anhio));
            if(resultado.next()){
               informe=new InformeVenta("Todos los productos",resultado.getDouble("ImporteTotal"),resultado.getInt("CantidadTotal"));
            }
        }catch(SQLException ex){}
        return informe;
    }

    private static InformeVenta obtenerImporteVentasProductosPlantaMensuales(int anhio, int mes){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorMesEnLosProductosPlanta(%d,%d)",mes,anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Planta",resultado.getDouble("ImporteTotalPlantas"),resultado.getInt("CantidadTotalPlantas"));
            }
        }catch(SQLException ex){}
        return informe;
    }

    private static InformeVenta obtenerImporteVentasProductosJardineriaMensuales(int anhio, int mes){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;
        try{
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorMesEnLosProductosJardineria(%d,%d)",mes,anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Jardineria",resultado.getDouble("ImporteTotalJardineria"),resultado.getInt("CantidadTotalJardineria"));
            }
        }catch(SQLException ex){}
        return informe;
    }

    public static List<Producto> obtenerTodosLosProductosDeUnTipo(String tipoProducto){
        ResultSet resultado;Statement consulta;InformeVenta informe=null;List<Producto> productos=new LinkedList<>();
        try{
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Productos as P INNER JOIN %s AS TP ON P.Codigo=TP.Codigo",tipoProducto));
            while (resultado.next()){
                productos.add(new Producto(resultado.getString("Descripcion"),resultado.getInt("Codigo"),
                        resultado.getInt("Unidades_Disponibles"),resultado.getDouble("Precio_Unitario")));
            }
        }catch(SQLException ex){}
        return productos;
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
            consulta= DataAccess.getConexion().createStatement();
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
            consulta= DataAccess.getConexion().createStatement();
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
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM  TotalVentasPorAnhioEnLosProductosJardineria(%d)",anhio));
            if(resultado.next()){
                informe=new InformeVenta("Productos Jardineria",resultado.getDouble("ImporteTotalJardineria"),resultado.getInt("CantidadTotalJardineria"));
            }
        }catch(SQLException ex){}
        return informe;
    }
}
