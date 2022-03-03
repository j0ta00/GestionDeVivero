package gestora;

import dataAccess.*;
import entidades.Cliente;
import entidades.Producto;
import mensaje.Mensaje;
import validaciones.DniValidator;

public class GestoraVenta {
    public static void mostrarMenuVendedor(){
        String eleccion="";
        while (!eleccion.equals("2")){
            Mensaje.menuPrincipalVendedor();
            eleccion = Gestora.teclado.nextLine();
            if(eleccion.equals("1")){
                realizarVenta();
            }
        }
    }

    public static void realizarVenta() {
        String dniOTelefonoCliente = "";
        while (!dniOTelefonoCliente.equals("0")) {
            Mensaje.introducirDniCliente();
            Mensaje.imprimirOpcionClienteGenerico();
            Mensaje.mostrarOpcionAnular();
            dniOTelefonoCliente = Gestora.teclado.nextLine();
            if (!dniOTelefonoCliente.equals("0") && !dniOTelefonoCliente.equals("1")){
                comprobarDniOTelefonoParaVenta(dniOTelefonoCliente);
            }else if(dniOTelefonoCliente.equals("1")){comprobarDniOTelefonoParaVenta("77863714C");}
        }
    }

    private static void comprobarDniOTelefonoParaVenta(String dniOTelefonoCliente) {
        if (!dniOTelefonoCliente.matches(Gestora.REGEX_SOLO_NUMEROS)){
            if (new DniValidator(dniOTelefonoCliente).validar()) {
                intrducirTelefonoODni(dniOTelefonoCliente, true);
            } else {Mensaje.dniInvalido();}
        }else{
            if (dniOTelefonoCliente.matches("[6|7|9][0-9]{8}$")) {
                intrducirTelefonoODni(dniOTelefonoCliente, false);
            } else {Mensaje.imiprimirTelefonoInvalido();}
        }
    }

    private static void intrducirTelefonoODni(String dniOTelefonoCliente, boolean esDni) {
        Cliente cliente;
        String mensaje=esDni?"dni":"telefono";
        if ((cliente = DataAccessCliente.obtenerCliente(dniOTelefonoCliente,mensaje.equals("dni"))) != null) {
            Gestora.gestora.setCliente(cliente);
            introducirProducto();
        } else {
            Mensaje.imprimirMensajeNoEncontradoGenerico(mensaje);
        }
    }

    public static void introducirProducto() {
        String codigoProducto="";
        Producto producto;
        Gestora.gestora.setFactura(DataAccessFactura.crearFactura(Gestora.gestora.getCliente(), Gestora.gestora.getUsuario()));
        while(!codigoProducto.equals("0")){
            Mensaje.introducirCodigoProducto();
            Mensaje.salirDeLaVenta();
            codigoProducto= Gestora.teclado.nextLine();
            if(codigoProducto.matches(Gestora.REGEX_SOLO_NUMEROS) && (producto= DataAccessInforme.obtenerProducto(Integer.parseInt(codigoProducto)))!=null){
                if(!introducirCantidad(producto)){Mensaje.productoIntroducidoConExito();}
            }else if(!codigoProducto.equals("0")){Mensaje.productoNoEncontrado();}
        }
        Mensaje.imprimirFactura(Gestora.gestora.getFactura(), DataAccessFactura.obtenerLineaPedidos(Gestora.gestora.getFactura().getId()));
        imprimirOGuardarFactura();
    }

    private static void imprimirOGuardarFactura(){
        String respuesta="";
        while(!respuesta.equals("1") && !respuesta.equals("2")) {
            Mensaje.imprimirGuardarOAnular();
            respuesta= Gestora.teclado.nextLine();}
        if(respuesta.equals("1")){
            DataAccessFactura.borrarFactura(Gestora.gestora.getFactura().getId());
        }else{
            if(!Gestora.gestora.getFactura().getDniCliente().equals("77863714C")){
                DataAccess.aplicarDescuentoClienteRegistrado(Gestora.gestora.getFactura().getId());}
            Mensaje.imprimirFacturaGuardadDeFormaExitosa();
        }
    }

    private static boolean introducirCantidad(Producto producto) {
        boolean finalizarVenta=false;boolean cantidadDisponible=false;String cantidadProducto;
        while(!cantidadDisponible && !finalizarVenta) {
            Mensaje.introducirCantidadProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            cantidadProducto = Gestora.teclado.nextLine();
            if(cantidadProducto.matches(Gestora.REGEX_SOLO_NUMEROS) && !cantidadProducto.equals("0")) {
                if (DataAccessProducto.comprobarCantidadDeProducto(producto.getCodigo(), Integer.parseInt(cantidadProducto))) {
                    cantidadDisponible = true;
                    introducirOActualizarProducto(producto,Integer.parseInt(cantidadProducto));
                }else{Mensaje.imprimirCantidadDeProductoInvalida();}
            }else{finalizarVenta=cantidadProducto.matches(Gestora.REGEX_SOLO_NUMEROS);}
        }
        return finalizarVenta;
    }

    private static void introducirOActualizarProducto(Producto producto, int cantidadProducto){
        if(!DataAccessFactura.comprobarSiProductoYaExisteEnFactura(producto.getCodigo(), Gestora.gestora.getFactura().getId())){
            DataAccessFactura.insertarProductoEnPedido(Gestora.gestora.getFactura(),producto,cantidadProducto);
        }else{
            DataAccessFactura.actualizarProductoEnPedido(cantidadProducto,producto.getCodigo(), Gestora.gestora.getFactura().getId());
        }
    }
}
