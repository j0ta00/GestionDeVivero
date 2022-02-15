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
        int eleccion=0;
       while (eleccion !=2){
            Mensaje.menuPrincipalVendedor();
            eleccion = teclado.nextInt();
            if(eleccion==1){
                teclado.nextLine();
                realizarVenta();
            }
        }
    }

    public static void realizarVenta(){
        String dniCliente="";
        Cliente cliente;
        while(!dniCliente.equals("0")) {
            Mensaje.introducirDniCliente();
            Mensaje.mostrarOpcionAnular();
            dniCliente=teclado.nextLine();
            if (new DniValidator(dniCliente).validar()) {
                if ((cliente = DataAccess.consultarDatosDni(dniCliente)) != null) {
                    gestora.setCliente(cliente);
                    introducirProducto();
                    dniCliente="0";
                } else {
                    Mensaje.dniNoEncontrado();
                }
            }else {
                Mensaje.dniInvalido();
            }
        }
    }
    public static void introducirProducto() {
        int codigoProducto=1;Producto producto=null;
        gestora.setFactura(DataAccess.crearFactura(gestora.getCliente(),gestora.getUsuario()));
        while(codigoProducto!=0) {
            Mensaje.introducirCodigoProducto();
            Mensaje.mostrarOpcionAnular();
            codigoProducto = teclado.nextInt();
            if (codigoProducto!=0 && (producto=DataAccess.obtenerProducto(codigoProducto))!=null && DataAccess.insertarProductoEnPedido(gestora.getFactura(),producto)) {
                Mensaje.productoIntroducidoConExito();
            } else {
                Mensaje.productoNoEncontrado();
            }
        }
        teclado.nextLine();
    }

}
