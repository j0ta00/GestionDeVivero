package main;

import dataAccess.DataAccess;
import entidades.Cliente;
import entidades.Producto;
import gestora.Gestora;
import mensaje.Mensaje;
import validaciones.DniValidator;

import java.util.Scanner;

public class Main {
    private static Scanner teclado;
    private static  Gestora gestora;
    public static void main(String[] args) {
        new Gestora().Instalador();
        teclado=new Scanner(System.in);
        gestora=new Gestora();
        DataAccess.incializarConexion();
        Logearse();
    }

    public static void realizarInstalacion(){ }


    public static void Logearse(){
        String usuario,contraseña;
        do{
            Mensaje.introducirDatosLogin("USUARIO:");
            usuario=teclado.nextLine();
            Mensaje.introducirDatosLogin("CONTRASEÑA:");
            contraseña=teclado.nextLine();
            gestora.setUsuario(DataAccess.consultarDatosLogin(usuario,contraseña));
        }while(gestora.getUsuario()==null);
        mostrarMenuVendedor();
    }



    public static void mostrarMenuVendedor(){
        String eleccion="";
        while (!eleccion.equals("2")){
            Mensaje.menuPrincipalVendedor();
            eleccion = teclado.nextLine();
            if(eleccion.equals("1")){
                realizarVenta();
            }
        }
    }

    public static void realizarVenta() {
        String dniOTelefonoCliente = "";
        Cliente cliente;
        while (!dniOTelefonoCliente.equals("0")) {
            Mensaje.introducirDniCliente();
            Mensaje.mostrarOpcionAnular();
            dniOTelefonoCliente = teclado.nextLine();
            if (!dniOTelefonoCliente.equals("0")){
                if (dniOTelefonoCliente.matches(".*[a-zA-Z].*")) {
                    if (new DniValidator(dniOTelefonoCliente).validar()) {
                        intrducirTelefonoODni(dniOTelefonoCliente, true);
                        dniOTelefonoCliente = "0";
                    } else {
                        Mensaje.dniInvalido();
                    }
                } else {
                    if (dniOTelefonoCliente.matches("[6|7|9][0-9]{8}$")) {
                        intrducirTelefonoODni(dniOTelefonoCliente, false);
                        dniOTelefonoCliente = "0";
                    } else {
                        Mensaje.imiprimirTelefonoInvalido();
                    }
                }
        }
        }
    }

    private static void intrducirTelefonoODni(String dniOTelefonoCliente,boolean esDni) {
        Cliente cliente;
        String mensaje=esDni?"dni":"telefono";
        if ((cliente = DataAccess.consultarSiClienteExiste(dniOTelefonoCliente,mensaje.equals("dni"))) != null) {
            gestora.setCliente(cliente);
            introducirProducto();
        } else {
            Mensaje.imprimirMensajeNoEncontradoGenerico(mensaje);
        }
    }

    public static void introducirProducto() {
        String codigoProducto="";Producto producto=null;
        gestora.setFactura(DataAccess.crearFactura(gestora.getCliente(),gestora.getUsuario()));
        while(!codigoProducto.equals("0")){
            Mensaje.introducirCodigoProducto();
            Mensaje.salirDeLaVenta();
            codigoProducto=teclado.nextLine();
            if((producto=DataAccess.obtenerProducto(Integer.parseInt(codigoProducto)))!=null){
                if(introducirCantidad(producto)){
                }else{
                    Mensaje.productoIntroducidoConExito();
                }
            }else if(!codigoProducto.equals("0")){
                Mensaje.productoNoEncontrado();
            }
        }
        imprimirOGuardarFactura();
    }

    private static void imprimirOGuardarFactura(){
        String respuesta="";
        while(!respuesta.equals("1") && !respuesta.equals("2")) {
            Mensaje.imprimirGuardarOAnular();
            respuesta=teclado.nextLine();
        }
        if(respuesta.equals("1")){
            DataAccess.borrarFactura(gestora.getFactura());
        }
    }

    private static boolean introducirCantidad(Producto producto) {
        boolean finalizarVenta=false;
        boolean cantidadDisponible=false;
        String cantidadProducto;
        while(!cantidadDisponible && !finalizarVenta) {
            Mensaje.introducirCantidadProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            cantidadProducto = teclado.nextLine();
            if(!cantidadProducto.equals("0")) {
                if (DataAccess.comprobarCantidadDeProducto(producto.getCodigo(), Integer.parseInt(cantidadProducto))) {
                    cantidadDisponible = true;
                    introducirOActualizarProducto(producto,Integer.parseInt(cantidadProducto));
                }else{
                    Mensaje.imprimirCantidadDeProductoInvalida();
                }
            }else{
                finalizarVenta=true;
            }
        }
        return finalizarVenta;
    }

    private static void introducirOActualizarProducto(Producto producto,int cantidadProducto){
        if(!DataAccess.comprobarSiProductoYaExisteEnFactura(producto.getCodigo(),gestora.getFactura().getId())){
            DataAccess.insertarProductoEnPedido(gestora.getFactura(),producto,cantidadProducto);
        }else{
            DataAccess.actualizarProductoEnPedido(cantidadProducto,producto.getCodigo(),gestora.getFactura().getId());
        }
    }


}
