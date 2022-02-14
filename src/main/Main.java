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
        int eleccion;
        do {
            Mensaje.menuPrincipalVendedor();
            eleccion = teclado.nextInt();
        }while (eleccion !=1);
        teclado.nextLine();
        realizarVenta();
    }

    public static void realizarVenta(){
        String dniCliente;
        Cliente cliente;
        Mensaje.introducirDniCliente();
        Mensaje.mostrarOpcionAnular();
        dniCliente=teclado.nextLine();
        if(!dniCliente.equals("0")) {
            if (new DniValidator(dniCliente).validar()) {
                if ((cliente = DataAccess.consultarDatosDni(dniCliente)) != null) {
                    gestora.setCliente(cliente);
                    introducirProducto();
                } else {
                    Mensaje.dniNoEncontrado();
                }
            }else {
                Mensaje.dniInvalido();
            }
        }
    }
    public static void introducirProducto() {
        int codigoProducto=1;
        DataAccess.crearFactura(gestora.getCliente(),gestora.getUsuario());
        while(codigoProducto==0) {
            Mensaje.introducirCodigoProducto();
            Mensaje.confirmarAnulacion();
            codigoProducto = teclado.nextInt();
            if (DataAccess.insertarProductoEnPedido()) {
                DataAccess.insertarProducto(new Producto(DataAccess. ))
                Mensaje.productoIntroducidoConExito();
            } else {
                Mensaje.productoNoEncontrado();
            }
        }
    }

}
