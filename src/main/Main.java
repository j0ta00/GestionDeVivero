package main;

import gestora.Gestora;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gestora.Instalador();
        Gestora.teclado=new Scanner(System.in);
        Gestora.gestora=new Gestora();
        Gestora.comprobarConexion();
        Gestora.Logearse();
    }
}
