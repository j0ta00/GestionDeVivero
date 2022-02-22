package main;

import com.microsoft.sqlserver.jdbc.StringUtils;
import dataAccess.DataAccess;
import entidades.Cliente;
import entidades.Producto;
import entidades.Tipo_Planta;
import gestora.Gestora;
import mensaje.Mensaje;
import validaciones.DniValidator;

import java.util.LinkedList;
import java.util.List;
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
        if(!gestora.getUsuario().isEsGestor()) {
            mostrarMenuVendedor();
        }else{
            mostrarMenuGestor();
        }
    }
    public static void mostrarMenuGestor(){
        String eleccion="";
        while (!eleccion.equals("0")){
            Mensaje.menuPrincipalGestor();
            eleccion = teclado.nextLine();
            realizarOpcionElegida(eleccion);
        }
    }

    public static void realizarOpcionElegida(String eleccion){
        switch(eleccion){
            case "1"->{
                realizarInserciones();
            }
        }
    }

    public static void realizarInserciones(){
        String eleccion="";
        while(!eleccion.equals("0")){
            Mensaje.menuPrincipalInsercion();
            eleccion=teclado.nextLine();
            realizarInsercionElegida(eleccion);
        }
    }
    public static void realizarInsercionElegida(String eleccion){
        switch(eleccion) {
            case "1" -> {
                realizarInsercionCliente();
            }
            case "2"->{
                realizarInsercionProducto();
            }
        }
    }

    public static void realizarInsercionProducto(){
        String tipoProducto="";Producto producto;
        while (!tipoProducto.equals("1") && !tipoProducto.equals("2")) {
                    Mensaje.imprimirMenuTipoProducto();
                    tipoProducto=teclado.nextLine();
            }
        DataAccess.insertarProducto((producto=new Producto(pedirDescripcion(),0,Integer.parseInt(pedirUnidadesDisponibles()),pedirPrecioUnitario())));
        if(tipoProducto.equals("1")){
            DataAccess.insertarProductoPlantaOJardineria(null);
        }else{
            DataAccess.insertarProductoPlantaOJardineria(pedirTipoPlantas());
        }
    }
    public static List<Tipo_Planta> pedirTipoPlantas(){
        List<Tipo_Planta> listaTipoPlanta=new LinkedList<Tipo_Planta>();String idTipoPlanta="";Tipo_Planta tipo_planta;
        while(!idTipoPlanta.equals("0") ||  0==listaTipoPlanta.size()){
            Mensaje.preguntarIdTipoPlanta();
            if(listaTipoPlanta.size()>0){
                Mensaje.retrocederHaciaOpcionAnterior();
            }
            idTipoPlanta=teclado.nextLine();
            if(!idTipoPlanta.equals("0") && !idTipoPlanta.matches(".*[\\D].*") && (tipo_planta=DataAccess.obtenerTipoPlanta(Integer.parseInt(idTipoPlanta)))!=null){
                listaTipoPlanta.add(tipo_planta);
                Mensaje.imprimirTipoPlantaExito();
            }else if(!idTipoPlanta.equals("0")){
                Mensaje.imprimirErrorTipoPlantaNoEncontrado();
            }
        }
        return listaTipoPlanta;
    }

    private static String pedirUnidadesDisponibles() {
        String unidadesDisponibles="";
        while(unidadesDisponibles.length()<1 || unidadesDisponibles.length()>8 || unidadesDisponibles.matches(".*[\\D].*")){
            Mensaje.preguntarUnidadesDisponibles();
            unidadesDisponibles = teclado.nextLine();
            if(unidadesDisponibles.length()<1 || unidadesDisponibles.length()>8){
                Mensaje.errorGenericoLongituDato(8,0);
            }
            if(unidadesDisponibles.matches(".*[\\D].*")){
                Mensaje.imprimirErrorCaracteresNumericos();
            }
        }
        return unidadesDisponibles;
    }
    private static String pedirUsuario() {
        String usuario="";
        while(usuario.length()<1 || usuario.length()>25){
            Mensaje.preguntarUsuario();
            usuario =teclado.nextLine();
            if(usuario.length()<1 || usuario.length()>25){
                Mensaje.errorGenericoLongituDato(25,0);
            }
        }
        return usuario;
    }
    private static String pedirContrasenhia() {
        String contrasenhia="";
        while(contrasenhia.length()<6 || contrasenhia.length()>25 || contrasenhia.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$")){
            Mensaje.preguntarContrasenhia();
            contrasenhia =teclado.nextLine();
            if(contrasenhia.length()<6 || contrasenhia.length()>25 || contrasenhia.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$") ){
                Mensaje.imprimirErrorContrasenhiaInvalida();
            }
        }
        return contrasenhia;
    }

    private static String pedirDescripcion() {
        String descripcion="";
        while(descripcion.length()<1 || descripcion.length()>40 ){
            Mensaje.preguntarDescripcion();
            descripcion =teclado.nextLine();
            if(descripcion.length()<1 || descripcion.length()>40){
                Mensaje.errorGenericoLongituDato(40,0);
            }
        }
        return descripcion;
    }
    private static Double pedirPrecioUnitario() {
        String precio="";double precioEnDouble=0;
        boolean valido=false;
        while(!valido){
            Mensaje.preguntarPrecio();
            precio=teclado.nextLine();
            try {
                precioEnDouble=Double.parseDouble(precio);
                valido=true;
            }
            catch(NumberFormatException e)
            {
                Mensaje.imprimirMenuPrecioInvalido();
            }
        }
        return precioEnDouble;
    }


    public static void realizarInsercionCliente(){
       if(!DataAccess.insertarCliente(new Cliente(pedirNombre(),pedirDni(),pedirDireccion(),pedirCodigoPostal(),pedirCiudad(),
                pedirTelefono(),pedirCorreo()))){
            Mensaje.imprimirErroClienteNoInsertado();
       }else{
           Mensaje.imprimirClienteInsertadoConExito();
       }
    }
    private static String pedirCiudad() {
        String ciudad="";
        while(ciudad.length()<1 || ciudad.length()>29 ){
            Mensaje.preguntarCiudad();
            ciudad =teclado.nextLine();
            if(ciudad.length()<1 || ciudad.length()>29){
                Mensaje.errorGenericoLongituDato(29,0);
            }
        }
        return ciudad;
    }
    private static String pedirTelefono() {
        String telefono="";
        while(!telefono.matches("[6|7|9][0-9]{8}$")){
            Mensaje.preguntarTelefono();
            telefono = teclado.nextLine();
            if(!telefono.matches("[6|7|9][0-9]{8}$")){
                Mensaje.imiprimirTelefonoInvalido();
            }
        }
        return telefono;
    }
    private static String pedirCorreo() {
        String correo="";
        while(correo.length()<1 || correo.length()>20){
            Mensaje.preguntarCorreo();
            correo = teclado.nextLine();
            if(correo.length()<1 || correo.length()>20){
                Mensaje.imiprimirTelefonoInvalido();
            }
        }
        return  correo;
    }
    private static String pedirNombre() {
        String nombre="";
        while(nombre.length()<1 || nombre.length()>29 ){
            Mensaje.preguntarNombreCliente();
            nombre =teclado.nextLine();
            if(nombre.length()<1 || nombre.length()>29){
                Mensaje.imprimirErrorEnLaLongitudDelNombre();
            }
        }
        return nombre;
    }

    private static String pedirDni() {
        String dni="";
        while(!new DniValidator(dni).validar()){
            Mensaje.preguntarDni();
            dni=teclado.nextLine();
            if(!new DniValidator(dni).validar()){
                Mensaje.dniInvalido();
            }
        }
        return dni;
    }
    private static String pedirCodigoPostal() {
        String codigoPostal="";
        while(!codigoPostal.matches("^(?:0[1-9]|[1-4]\\d|5[0-2])\\d{3}$")){
            Mensaje.preguntarCodigoPostal();
            codigoPostal=teclado.nextLine();
            if(!codigoPostal.matches("^(?:0[1-9]|[1-4]\\d|5[0-2])\\d{3}$")){
                Mensaje.errorCodigoPostalInvalido();
            }
        }
        return codigoPostal;
    }
    private static String pedirDireccion() {
        String direccion="";
        while(direccion.length()<1 || direccion.length()>50){
            Mensaje.preguntarDireccion();
            direccion=teclado.nextLine();
            if(direccion.length()<1 || direccion.length()>50){
                Mensaje.errorGenericoLongituDato(50,0);
            }
        }
        return direccion;
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
                if (dniOTelefonoCliente.matches(".*[\\D].*")) {
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
