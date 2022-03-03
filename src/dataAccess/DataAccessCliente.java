package dataAccess;

import entidades.Cliente;
import entidades.Factura;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DataAccessCliente {

    public static List<Cliente> obtenerClientes() {
        Statement consulta;
        ResultSet resultado=null;List<Cliente> listadoClientes=new LinkedList<>();
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Clientes"));
            while(resultado.next()){
                listadoClientes.add(construirCliente(resultado));
            }
        } catch (SQLException e) {}
        return listadoClientes;

    }

    public static Cliente obtenerCliente(String dniOTelefonoCliente, boolean esDni) {
        Statement consulta;ResultSet resultado=null;Cliente cliente=null;
        String query=esDni?new StringBuilder("Dni=").append(String.format("'%s'",dniOTelefonoCliente)).toString():new StringBuilder("Telefono=").append(String.format("'%s'",dniOTelefonoCliente)).toString();
        try {
            consulta= DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT * FROM Clientes Where %s",query));
            if(resultado.next()){
                cliente=construirCliente(resultado);
            }
        } catch (SQLException e) {}
        return cliente;
    }

    public static Cliente construirCliente(ResultSet resultado) throws SQLException {
        return new Cliente(resultado.getString("Nombre"),resultado.getString("Dni"),resultado.getString("Direccion"),resultado.getString("Codigo_Postal"),
                resultado.getString("Ciudad"),resultado.getString("Telefono"),resultado.getString("Correo_Electronico"));
    }

    public static boolean modificarCliente(Cliente cliente){
        boolean clienteModificadoConExito=false;Statement consulta;
        try {
            consulta=DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("UPDATE CLIENTES SET  nombre='%s',dni='%s',direccion='%s', Codigo_Postal='%s', ciudad='%s', Telefono='%s', Correo_Electronico='%s' WHERE Dni='%s'",cliente.getNombre(),cliente.getDni(),cliente.getDireccion()
                    ,cliente.getCodigoPostal(),cliente.getCiudad(),cliente.getTelefono(),cliente.getCorreoElectronico(),cliente.getDni()));
            clienteModificadoConExito=true;
        } catch (SQLException e) {}
        return clienteModificadoConExito;
    }

    public static boolean insertarCliente(Cliente cliente){
        boolean clienteInsertadoConExito=false;Statement consulta;
        try {
            consulta=DataAccess.getConexion().createStatement();
            consulta.executeUpdate(String.format("INSERT INTO CLIENTES VALUES ('%s','%s','%s','%s','%s','%s','%s')",cliente.getNombre(),cliente.getDni(),cliente.getDireccion()
                    ,cliente.getCodigoPostal(),cliente.getCiudad(),cliente.getTelefono(),cliente.getCorreoElectronico()));
            clienteInsertadoConExito=true;
        } catch (SQLException e) {}
        return clienteInsertadoConExito;
    }

    public static int borrarCliente(String idCliente){
       int resultadoDelBorrado=0;Statement consulta;
        try {
            consulta=DataAccess.getConexion().createStatement();
            resultadoDelBorrado=consulta.executeUpdate(String.format("DELETE FROM Clientes WHERE Dni='%s'",idCliente));
        } catch (SQLException e) {
            resultadoDelBorrado=-1;
        }
        return resultadoDelBorrado;
    }

    public static List<Factura> obtenerFacturasPorCliente(String dni){
        boolean creadoConExito=false;List<Factura> facturas=new LinkedList<>();ResultSet resultado=null;
        Statement consulta;
        try {
            consulta=DataAccess.getConexion().createStatement();
            resultado=consulta.executeQuery(String.format("SELECT*FROM Facturas WHERE Dni_Cliente='%s'",dni));
            while(resultado.next()) {
                facturas.add(new Factura(resultado.getInt("Id"),resultado.getString("Dni_Cliente"),resultado.getInt("Vendedor"),resultado.getDate("Fecha"),resultado.getDouble("Importe")));
            }
        } catch (SQLException e) {}
        return facturas;
    }
}
