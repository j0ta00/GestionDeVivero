package main;

import dataAccess.DataAccess;
import entidades.Cliente;
import entidades.Producto;
import entidades.Tipo_Planta;
import entidades.Usuario;
import gestora.Gestora;
import mensaje.Mensaje;
import validaciones.DniValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
            if(gestora.getUsuario()==null){
                Mensaje.usuarioNoEncontrado();
            }
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
            case "2"->{
                realizarModificaciones();
            }
            case "3"->{
                realizarBorrados();
            }
        }
    }
    public static void realizarModificaciones(){
        String eleccion="";
        while(!eleccion.equals("0")){
            Mensaje.imprimirMenuModificacion();
            eleccion=teclado.nextLine();
            realizarModificacionElegida(eleccion);
        }
    }
    public static void realizarModificacionElegida(String eleccion){
        switch(eleccion) {
            case "1" -> {
                modificarCliente();
            }
            case "2"->{
                modificarProducto();
            }
            case "3"->{
                modificarTipoPlanta();
            }
            case "4"->{
                modificarUsuario();
            }
            case "5"->{

            }
        }
    }
    public static void modificarTipoPlanta(){
        String idTipoPlanta="";String tipo="";
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta=teclado.nextLine();
            if(!idTipoPlanta.equals("0") && idTipoPlanta.matches("[+-]?\\d*(\\.\\d+)?") ){
                tipo=pedirTipoPlanta();
                if((DataAccess.modificarTipoPlanta(Integer.parseInt(idTipoPlanta),tipo))){
                    Mensaje.imprimirElementoModificadoConExito();
                }else{
                    Mensaje.imprimirErrorEnLaModificacion();
                }
            }
            else if(!idTipoPlanta.equals("0")){Mensaje.elementoNoEncontrado();}
        }
    }

    public static void modificarUsuario(){
        String dniOId="";Usuario usuario=null;boolean esId=false;
        while(!dniOId.equals("0")) {
            Mensaje.preguntarIdElementoAModificar();
            Mensaje.retrocederHaciaOpcionAnterior();
            dniOId=teclado.nextLine();
            esId=dniOId.matches("[+-]?\\d*(\\.\\d+)?");
            if(esId){
                usuario=DataAccess.obtenerUsuarioPorId(Integer.parseInt(dniOId));
            }else{
                usuario=DataAccess.obtenerUsuarioPorDni(dniOId);
            }
            if(!dniOId.equals("0") && usuario!=null){
                modificarAtributosUsuario(usuario);
            }else if(!dniOId.equals("0") && usuario==null){Mensaje.elementoNoEncontrado();}
        }
    }
    public static void modificarAtributosUsuario(Usuario usuario){
        String eleccion="";
        while(!eleccion.equals("0")) {
            Mensaje.imprimirMenuModificacionUsuario();
            eleccion=teclado.nextLine();
            modificarAtributoDelUsuarioElegidoPorUsuario(eleccion,usuario);
        }
        while(!eleccion.equals("1") && !eleccion.equals("2")) {
            Mensaje.imprimirGuardarModificacion();
            eleccion=teclado.nextLine();
        }
        if(eleccion.equals("1")){
            DataAccess.modificarUsuario(usuario);
        }

    }
    public static void modificarProducto(){
        String codigoProducto="";boolean esPlanta=false;Producto producto=null;
        while(!codigoProducto.equals("0")) {
            Mensaje.introducirCodigoProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            codigoProducto=teclado.nextLine();
            if(!codigoProducto.equals("0") && codigoProducto.matches("[+-]?\\d*(\\.\\d+)?") && (producto = DataAccess.obtenerProducto(Integer.parseInt(codigoProducto)))!=null) {
                esPlanta=DataAccess.consultarSiProductoEsPlanta(producto);
                modificarAtributosProducto(producto,esPlanta);
            }
            else if(!codigoProducto.equals("0") && producto==null){Mensaje.elementoNoEncontrado();}
        }
    }
    public static void modificarAtributosProducto(Producto producto,boolean esPlanta){
        String eleccion="";
        while(!eleccion.equals("0")) {
            Mensaje.imprimirMenuModificacionProducto();
            if(esPlanta) {
                Mensaje.imprimirMenuModificacionPlanta();}
            eleccion=teclado.nextLine();
            modificarAtributoDelProductoElegidoPorUsuario(eleccion,producto,esPlanta);
        }
        while(!eleccion.equals("1") && !eleccion.equals("2")) {
            Mensaje.imprimirGuardarModificacion();
            eleccion=teclado.nextLine();
        }
        if(eleccion.equals("1")){
            DataAccess.modificarProducto(producto);
        }

    }

    public static void modificarCliente(){
        String dniCliente="";Cliente cliente=null;
        while(!dniCliente.equals("0")) {
            Mensaje.preguntarIdElementoAModificar();
            Mensaje.retrocederHaciaOpcionAnterior();
            dniCliente=teclado.nextLine();
            if(!dniCliente.equals("0") && (cliente=DataAccess.consultarSiClienteExiste(dniCliente,true))!=null){
                modificarAtributosCliente(cliente);
            }else if(!dniCliente.equals("0") && cliente==null){Mensaje.elementoNoEncontrado();}
        }
    }

    public static void modificarAtributosCliente(Cliente cliente){
        String eleccion="";
        while(!eleccion.equals("0")) {
            Mensaje.imprimirMenuModificacionCliente();
            eleccion=teclado.nextLine();
            modificarAtributoDelClienteElegidoPorUsuario(eleccion,cliente);
        }
        while(!eleccion.equals("1") && !eleccion.equals("2")) {
            Mensaje.imprimirGuardarModificacion();
            eleccion=teclado.nextLine();
        }
        if(eleccion.equals("1")){
            DataAccess.modificarCliente(cliente);
        }

    }
    public static void modificarAtributoDelUsuarioElegidoPorUsuario(String eleccion, Usuario usuario){
        switch(eleccion){
            case "1"->{
                usuario.setNombre(pedirNombre());
            }
            case "2"->{
                usuario.setDni(pedirDni(false));
            }
            case "3"->{
                usuario.setDireccion(pedirDireccion());
            }
            case "4"->{
                usuario.setCodigoPostal(pedirCodigoPostal());
            }
            case "5"->{
                usuario.setCiudad(pedirCiudad());
            }
            case "6"-> {
               usuario.setTelefono(pedirTelefono());
            }
            case "7"->{
                usuario.setCorreoElectronico(pedirCorreo());
            }
            case "8"->{
                usuario.setUsuario(pedirUsuario());
            }
            case "9"-> {
                usuario.setContrasenhia(pedirContrasenhia());
            }
            case "10"->{
                usuario.setEsGestor(pedirSiEsGestor());
            }
        }
    }
    public static void modificarAtributoDelProductoElegidoPorUsuario(String eleccion, Producto producto,boolean esPlanta){
        switch(eleccion){
            case "1"->{
                producto.setDescripcion(pedirDescripcion());
            }
            case "2"->{
                producto.setPrecioUnitario(pedirPrecioUnitario());
            }
            case "3"->{
                producto.setUnidadesDisponibles(Integer.parseInt(pedirUnidadesDisponibles()));
            }
            case "4"->{
                if(esPlanta) {
                    modificarTipoPlantaDeUnaPlanta(producto);
                }
            }
        }
    }

    public static void modificarTipoPlantaDeUnaPlanta(Producto producto){
        String idTipoPlanta="",idTipoPlantaNuevo="";
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta=teclado.nextLine();
            if(!idTipoPlanta.equals("0") && idTipoPlanta.matches("[+-]?\\d*(\\.\\d+)?")){
                Mensaje.pedirNuevoIdTipoPlanta();
                Mensaje.retrocederHaciaOpcionAnterior();
                idTipoPlantaNuevo=teclado.nextLine();
                if(!idTipoPlantaNuevo.equals("0") && idTipoPlantaNuevo.matches("[+-]?\\d*(\\.\\d+)?")){
                    if(DataAccess.modificarTipoPlantaDeUnaPlanta(producto.getCodigo(),Integer.parseInt(idTipoPlanta),Integer.parseInt(idTipoPlantaNuevo))){
                        Mensaje.imprimirElementoModificadoConExito();
                    }else{
                        Mensaje.imprimirErrorTipoPlantaNoEncontrado();
                    }
                }
            }else{
                Mensaje.imprimirTipoPlantaInvalido();
            }
        }

    }


    public static void modificarAtributoDelClienteElegidoPorUsuario(String eleccion, Cliente cliente){
        switch(eleccion){
            case "1"->{
                cliente.setNombre(pedirNombre());
            }
            case "2"->{
                cliente.setDni(pedirDni(true));
            }
            case "3"->{
                cliente.setDireccion(pedirDireccion());
            }
            case "4"->{
                cliente.setCodigoPostal(pedirCodigoPostal());
            }
            case "5"->{
                cliente.setCiudad(pedirCiudad());
            }
            case "6"-> {
                cliente.setTelefono(pedirTelefono());
            }
            case "7"->{
                cliente.setCorreoElectronico(pedirCorreo());
            }
        }
    }

    public static void realizarBorrados(){
        String eleccion="";
        while(!eleccion.equals("0")){
            Mensaje.menuPrincipalBorrado();
            eleccion=teclado.nextLine();
            realizarBorradoElegido(eleccion);
        }
    }
    public static void realizarBorradoElegido(String eleccion){
        switch(eleccion) {
            case "1" -> {
                borrarCliente();
            }
            case "2"->{
                borrarProducto();
            }
            case "3"->{
                borrarFactura();
            }
            case "4"->{
                borrarTipoPlanta();
            }
            case "5"->{
                borrarUsuario();
            }
            case "6"->{
                borrarTipoPlantaAsignadoAUnaPlanta();
            }
        }
    }
    public static void borrarTipoPlantaAsignadoAUnaPlanta(){
        String idTipoPlanta="",codigoProducto="";int resultado=0;
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta=teclado.nextLine();
            if(!idTipoPlanta.equals("0")  && idTipoPlanta.matches("[+-]?\\\\d*(\\\\.\\\\d+)?")){
                Mensaje.introducirCodigoProducto();
                Mensaje.retrocederHaciaOpcionAnterior();
                codigoProducto=teclado.nextLine();
                if(!codigoProducto.equals("0")  && codigoProducto.matches("[+-]?\\\\d*(\\\\.\\\\d+)?") && (resultado=DataAccess.borrarTipoPlantaEnPlanta(Integer.parseInt(codigoProducto),Integer.parseInt(idTipoPlanta)))==1){
                    Mensaje.imprimirElementoBorradoConExito();
                }else if(!codigoProducto.equals("0")  &&  resultado==0){
                    Mensaje.elementoNoEncontrado();
                }else if(!codigoProducto.equals("0")  &&  resultado==-1){
                    Mensaje.errorBorrado();
                }
            }else if(!idTipoPlanta.equals("0")){
                Mensaje.elementoNoEncontrado();
            }
        }
    }
    public static void borrarCliente(){
        String dniCliente="";int resultado=0;
        while(!dniCliente.equals("0")) {
            Mensaje.pedirDniClienteABorrar();
            Mensaje.retrocederHaciaOpcionAnterior();
            dniCliente=teclado.nextLine();
            if(!dniCliente.equals("0") && (resultado=DataAccess.borrarCliente(dniCliente))==1){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!dniCliente.equals("0") && resultado==0){Mensaje.elementoNoEncontrado();}else if(!dniCliente.equals("0") && resultado==-1){Mensaje.errorBorrado();}
        }
    }
    public static void borrarFactura(){
        String idFactura="";boolean resultado=false;
        while(!idFactura.equals("0")) {
            Mensaje.pedirIdFactura();
            Mensaje.retrocederHaciaOpcionAnterior();
            idFactura=teclado.nextLine();
            if(!idFactura.equals("0")  && idFactura.matches("[+-]?\\\\d*(\\\\.\\\\d+)?") && DataAccess.borrarFactura(Integer.parseInt(idFactura))){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!idFactura.equals("0")){
                Mensaje.elementoNoEncontrado();
            }
        }
    }
    public static void borrarTipoPlanta(){
        String idTipoPlanta="";int resultado=0;
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta=teclado.nextLine();
            if(!idTipoPlanta.equals("0")  && idTipoPlanta.matches("[+-]?\\\\d*(\\\\.\\\\d+)?") && (resultado=DataAccess.borrarTipoPlanta(Integer.parseInt(idTipoPlanta)))==1){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!idTipoPlanta.equals("0") && resultado==0){Mensaje.elementoNoEncontrado();}else if(!idTipoPlanta.equals("0") && resultado==-1){Mensaje.errorBorrado();}
        }
    }
    public static void borrarProducto(){
        String idProducto="";int resultado=0;boolean esPlanta=false;Producto p=null;
        while(!idProducto.equals("0")) {
            Mensaje.introducirCodigoProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            idProducto=teclado.nextLine();
            if(!idProducto.equals("0") && idProducto.matches("[+-]?\\d*(\\.\\d+)?")) {
                p=DataAccess.obtenerProducto(Integer.parseInt(idProducto));
                if(p!=null) {
                    esPlanta = DataAccess.consultarSiProductoEsPlanta(p);
                    if ((resultado = DataAccess.borrarProducto(Integer.parseInt(idProducto), esPlanta)) == 1) {
                        Mensaje.imprimirElementoBorradoConExito();
                    } else if (resultado == 0) {
                        Mensaje.elementoNoEncontrado();
                    } else {
                        Mensaje.errorBorrado();
                    }
                }else{
                    Mensaje.elementoNoEncontrado();
                }
            }
        }
    }
    public static void borrarUsuario(){
        String usuario="";int resultado=0;
        while(!usuario.equals("0")) {
            Mensaje.preguntarUsuario();
            Mensaje.retrocederHaciaOpcionAnterior();
            usuario=teclado.nextLine();
            if(!usuario.equals("0") && (resultado=DataAccess.borrarUsuario(usuario))==1){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!usuario.equals("0") && resultado==0){Mensaje.elementoNoEncontrado();}else if(!usuario.equals("0") && resultado==-1){Mensaje.errorBorrado();}
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
            case "3"->{
                insertarTipoPlanta();
            }
            case "4"->{
                insertarUsuario();
            }
        }
    }

    public static void insertarUsuario(){
        if(DataAccess.insertarUsuario(new Usuario(pedirUsuario(),pedirContrasenhia(),pedirNombre(),pedirDni(false),pedirDireccion(),pedirCodigoPostal(),pedirTelefono(),pedirCorreo(),pedirCiudad(),0,pedirSiEsGestor()))){
            Mensaje.imprimirUsuarioInsertadoExito();
        }else{
            Mensaje.imprimirErrorInsercionUsuario();
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
            DataAccess.insertarProductoPlantaOJardineria(pedirTipoPlantasParaProducto());
        }
    }
    public static List<Tipo_Planta> pedirTipoPlantasParaProducto(){
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
    private static void insertarTipoPlanta(){
        if(DataAccess.insertarTipoPlanta(pedirTipoPlanta())){
            Mensaje.imprimirTipoPlantaInsertado();
        }else{
         Mensaje.imprimirErrorInsercionTipoPlanta();
        }
    }

    private static String pedirTipoPlanta() {
        String tipo="";
        while(tipo.length()<1 || tipo.length()>15){
            Mensaje.preguntarTipoPlanta();
            tipo =teclado.nextLine();
            if(tipo.length()<1 || tipo.length()>15){
                Mensaje.errorGenericoLongituDato(15,0);
            }
        }
        return tipo;
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
        while(usuario.length()<1 || usuario.length()>25 || DataAccess.comprobarSiUsuarioExiste(usuario)){
            Mensaje.preguntarUsuario();
            usuario =teclado.nextLine();
            if(usuario.length()<1 || usuario.length()>25){
                Mensaje.errorGenericoLongituDato(25, 0);
            }else if(DataAccess.comprobarSiUsuarioExiste(usuario)) {
                Mensaje.usuarioYaExiste();
            }
        }
        return usuario;
    }
    private static String pedirContrasenhia() {
        String contrasenhia="";
        while(contrasenhia.length()<6 || contrasenhia.length()>25 || !contrasenhia.matches("^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$")){
            Mensaje.preguntarContrasenhia();
            contrasenhia =teclado.nextLine();
            if(contrasenhia.length()<6 || contrasenhia.length()>25 || !contrasenhia.matches("^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$")){
                Mensaje.imprimirErrorContrasenhiaInvalida();
            }
        }
        return contrasenhia;
    }
    private static boolean pedirSiEsGestor() {
       String esGestor="";
        while(!esGestor.equals("1") &&  !esGestor.equals("2")){
            Mensaje.preguntarSiEsGestor();
            esGestor=teclado.nextLine();
        }
        return esGestor.equals("2");
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
        if(!DataAccess.insertarCliente(new Cliente(pedirNombre(),pedirDni(true),pedirDireccion(),pedirCodigoPostal(),pedirCiudad(),
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
            Mensaje.preguntarNombre();
            nombre =teclado.nextLine();
            if(nombre.length()<1 || nombre.length()>29){
                Mensaje.imprimirErrorEnLaLongitudDelNombre();
            }
        }
        return nombre;
    }

    private static String pedirDni(boolean esCliente) {
        String dni="";
        while(!new DniValidator(dni).validar() || DataAccess.comprobarDniExiste(dni,esCliente)){
            Mensaje.preguntarDni();
            dni=teclado.nextLine();
            if(!new DniValidator(dni).validar() || DataAccess.comprobarDniExiste(dni,esCliente)){
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
            if( (producto=DataAccess.obtenerProducto(Integer.parseInt(codigoProducto)))!=null){
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
            DataAccess.borrarFactura(gestora.getFactura().getId());
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
