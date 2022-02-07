package main;

import dataAccess.DataAccess;
import entidades.Cliente;
import gestora.Gestora;
import mensaje.Mensaje;
import validaciones.DniValidator;

import java.util.Scanner;

public class Main {
    private static Scanner teclado;
    private static  Gestora gestora;
    public static void main(String[] args) {
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
            realizarVenta();
    }

    public static void realizarVenta(){
        String dniCliente;
        Cliente cliente;
        Mensaje.introducirDniCliente();
        Mensaje.confirmarAnulacion();
        dniCliente=teclado.nextLine();
        if(new DniValidator(dniCliente).validar()){
            if((cliente=DataAccess.consultarDatosDni(dniCliente))!=null){
                    gestora.setCliente(cliente);
            }else{
                Mensaje.dniNoEncontrado();
            }

        }else if(dniCliente.equals("0")){}else{
            Mensaje.dniInvalido();
        }
    }
    public static void introducirProducto() {
        int codigoProducto;
        DataAccess.crearFactura(gestora.getCliente(),gestora.getUsuario());
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
