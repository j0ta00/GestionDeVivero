package main;

import dataAccess.DataAccess;
import gestora.Gestora;
import mensaje.Mensaje;

import java.util.Scanner;

public class Main {
    private static Scanner teclado;

    public static void main(String[] args) {
        teclado=new Scanner(System.in);
        Mensaje.introducirDatosLogin("USUARIO:");
    }

    public static void Logearse(){


        String usuario,contraseña;
        Mensaje.introducirDatosLogin("USUARIO:");
        usuario=teclado.nextLine();
        Mensaje.introducirDatosLogin("CONTRASEÑA:");
        contraseña=teclado.nextLine();
        DataAccess.comprobarConexion();
    }


}
