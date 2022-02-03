package main;

import dataAccess.DataAccess;
import entidades.Usuario;
import gestora.Gestora;
import mensaje.Mensaje;

import javax.xml.validation.Validator;
import java.util.Scanner;

public class Main {
    private static Scanner teclado;

    public static void main(String[] args) {
        teclado=new Scanner(System.in);
        Gestora.Instalador();
        Mensaje.introducirDatosLogin("USUARIO:");
        DataAccess.incializarConexion();
        Logearse();
    }

    public static void realizarInstalacion(){ }


    public static void Logearse(){
        String usuario,contraseña;
        Mensaje.introducirDatosLogin("USUARIO:");
        usuario=teclado.nextLine();
        Mensaje.introducirDatosLogin("CONTRASEÑA:");
        contraseña=teclado.nextLine();
        if(DataAccess.consultarDatosLogin(contraseña,usuario)!=null){
            System.out.println("HA");
        }

    }

    public static void mostrarMenuVendedor(){
        int eleccion;
        Mensaje.menuPrincipalVendedor();
        eleccion=teclado.nextInt();
        while (eleccion == 1) {
            realizarVenta();
            eleccion=teclado.nextInt();
        }

    }

    public static void realizarVenta(){
        String dniCliente;
        Mensaje.introducirDniCliente();
        Mensaje.confirmarAnulacion();
        dniCliente=teclado.nextLine();
        if(Validacion.ValidarDNI(dniCliente)){

        }else if(dniCliente.equals("0")){}else{}
    }
public static void introducirProducto() {
    int codigoProducto;
    DataAccess.crearPedido();
    Mensaje.introducirCodigoProducto();
    Mensaje.confirmarAnulacion();
    codigoProducto = teclado.nextInt();
    if (DataAccess.insertarProductoEnPedido()) {
        Mensaje.productoIntroducidoConExito();
    }else if(codigoProducto==0){

    }else{
            Mensaje.productoNoEncontrado();
        }

}

}
