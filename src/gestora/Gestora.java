package gestora;

import dataAccess.DataAccess;
import dataAccess.DataAccessInforme;
import dataAccess.DataAccessUsuario;
import entidades.Cliente;
import entidades.Factura;
import entidades.Usuario;
import mensaje.Mensaje;
import validaciones.DniValidator;

import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class Gestora {

    public static final String REGEX_SOLO_NUMEROS="^[0-9]{1,45}$";
    public static final String TABLA_PLANTAS="ProductosPlanta";
    public static final String TABLA_PRODUCTOS_JARDINERIA="ProductosJardineria";
    private static String path = "ScriptBBDD/ScriptVivero.sql";
    private static Factura factura;
    private static  Cliente cliente;
    private static  Usuario usuario;

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static Scanner teclado;
    public static  Gestora gestora;
    public static void comprobarConexion(){
        boolean datosValidos= DataAccess.incializarConexion();
        DataAccess.introducirDatosPorDefecto();
        while(!datosValidos) {
            pedirDatosConexion();
            if(DataAccess.incializarConexion()){
                datosValidos=true;
                DataAccess.introducirDatosPorDefecto();
                Mensaje.imprimirConexionCorrecta();
                DataAccess.introducirDatosPorDefecto();
            }else{
                Mensaje.imprimirErrorEnLaConexion();}}
    }

    private static void pedirDatosConexion() {
        String usuario,contrasenhia,puerto,localhost;
        Mensaje.pedirUsuarioSQL();
        usuario = teclado.nextLine();
        Mensaje.pedirContrsenhiaSQL();
        contrasenhia = teclado.nextLine();
        Mensaje.setPedirLocalhost();
        localhost = teclado.nextLine();
        Mensaje.pedirPuerto();
        puerto = teclado.nextLine();
        DataAccess.escribirDatosEnFicheroProperties(contrasenhia,usuario,puerto,localhost);
    }

    public static void Logearse(){
        String usuario,contraseña;
        do{
            Mensaje.introducirDatosLogin("USUARIO:");
            usuario= teclado.nextLine();
            Mensaje.introducirDatosLogin("CONTRASEÑA:");
            contraseña= teclado.nextLine();
            gestora.setUsuario(DataAccessUsuario.consultarDatosLogin(usuario,contraseña));
            if(gestora.getUsuario()==null){Mensaje.usuarioNoEncontrado();}
        }while(gestora.getUsuario()==null);
        Mensaje.imprimirSesionInicadaConExito();
        if(!gestora.getUsuario().isEsGestor()){
            GestoraVenta.mostrarMenuVendedor();}else{
            GestoraGestion.mostrarMenuGestor(); }
    }

    public static void realizarInformeVentasAnuales(){
        Mensaje.imprimirInformeVentas(DataAccessInforme.obtenerImporteVentasProductosAnuales(pedirAnhio()));

    }

    public static int pedirMes(){
        String mes="";boolean valido=false;
        while(!valido){
            Mensaje.pedirMes();
            mes=teclado.nextLine();
            if(mes.matches(REGEX_SOLO_NUMEROS)){
                if(Integer.parseInt(mes)>0 && Integer.parseInt(mes)<13){
                    valido=true;
                }else{
                    Mensaje.errorGenericoLongituDato(12,1);
                }
            }else{
                Mensaje.imprimirErrorCaracteresNumericos();
            }
        }
        return Integer.parseInt(mes);
    }

    public static int pedirAnhio(){
        String anhio="";boolean valido=false;
        while(!valido){
            Mensaje.pedirAnhio();
            anhio=teclado.nextLine();
            if(anhio.matches(REGEX_SOLO_NUMEROS)){
                if(Integer.parseInt(anhio)>0 && Integer.parseInt(anhio)<9999){
                    valido=true;
                }else{
                    Mensaje.errorGenericoLongituDato(0,9999);
                }
            }else{
                Mensaje.imprimirErrorCaracteresNumericos();
            }
        }
        return Integer.parseInt(anhio);
    }

    public static void realizarInserciones(){
        String eleccion="";
        while(!eleccion.equals("0")){
            Mensaje.menuPrincipalInsercion();
            eleccion=teclado.nextLine();
            GestoraGestion.realizarInsercionElegida(eleccion);
        }
    }

    public static String pedirCiudad() {
        String ciudad="";
        while(ciudad.length()<1 || ciudad.length()>29 ){
            Mensaje.preguntarCiudad();
            ciudad=teclado.nextLine();
            if(ciudad.length()<1 || ciudad.length()>29){
                Mensaje.errorGenericoLongituDato(29,0);
            }
        }
        return ciudad;
    }

    public static String pedirTelefono() {
        String telefono="";
        while(!telefono.matches("[6|7|9][0-9]{8}$")){
            Mensaje.preguntarTelefono();
            telefono=teclado.nextLine();
            if(!telefono.matches("[6|7|9][0-9]{8}$")){
                Mensaje.imiprimirTelefonoInvalido();
            }
        }
        return telefono;
    }

    public static String pedirCorreo() {
        String correo="";
        while(correo.length()<1 || correo.length()>20){
            Mensaje.preguntarCorreo();
            correo=teclado.nextLine();
            if(correo.length()<1 || correo.length()>20){
                Mensaje.imiprimirTelefonoInvalido();
            }
        }
        return  correo;
    }

    public static String pedirNombre() {
        String nombre="";
        while(nombre.length()<1 || nombre.length()>29 ){
            Mensaje.preguntarNombre();
            nombre=teclado.nextLine();
            if(nombre.length()<1 || nombre.length()>29){
                Mensaje.imprimirErrorEnLaLongitudDelNombre();
            }
        }
        return nombre;
    }
    public static String pedirUnidadesDisponibles() {
        String unidadesDisponibles="";
        while(unidadesDisponibles.length()<1 || unidadesDisponibles.length()>8 || !unidadesDisponibles.matches(REGEX_SOLO_NUMEROS)){
            Mensaje.preguntarUnidadesDisponibles();
            unidadesDisponibles=teclado.nextLine();
            if(unidadesDisponibles.length()<1 || unidadesDisponibles.length()>8){Mensaje.errorGenericoLongituDato(8,0); }
            if(!unidadesDisponibles.matches(REGEX_SOLO_NUMEROS)){ Mensaje.imprimirErrorCaracteresNumericos();}
        }
        return unidadesDisponibles;
    }

    public static String pedirUsuario() {
        String usuario="";
        while(usuario.length()<1 || usuario.length()>25 || DataAccessUsuario.comprobarSiUsuarioExiste(usuario)){
            Mensaje.preguntarUsuario();
            usuario=teclado.nextLine();
            if(usuario.length()<1 || usuario.length()>25){
                Mensaje.errorGenericoLongituDato(25, 0);
            }else if(DataAccessUsuario.comprobarSiUsuarioExiste(usuario)) {
                Mensaje.usuarioYaExiste();
            }
        }
        return usuario;
    }

    public static String pedirContrasenhia() {
        String contrasenhia="";
        while(contrasenhia.length()<6 || contrasenhia.length()>25 || !contrasenhia.matches("^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$")){
            Mensaje.preguntarContrasenhia();
            contrasenhia=teclado.nextLine();
            if(contrasenhia.length()<6 || contrasenhia.length()>25 || !contrasenhia.matches("^.*(?=.{4,10})(?=.*\\d)(?=.*[a-zA-Z]).*$")){
                Mensaje.imprimirErrorContrasenhiaInvalida();
            }
        }
        return contrasenhia;
    }

    public static boolean pedirSiEsGestor() {
        String esGestor="";
        while(!esGestor.equals("1") &&  !esGestor.equals("2")){
            Mensaje.preguntarSiEsGestor();
            esGestor=teclado.nextLine();
        }
        return esGestor.equals("2");
    }

    public static String pedirDescripcion() {
        String descripcion="";
        while(descripcion.length()<1 || descripcion.length()>40 ){
            Mensaje.preguntarDescripcion();
            descripcion=teclado.nextLine();
            if(descripcion.length()<1 || descripcion.length()>40){
                Mensaje.errorGenericoLongituDato(40,0);
            }
        }
        return descripcion;
    }

    public static Double pedirPrecioUnitario() {
        String precio="";double precioEnDouble=0;boolean valido=false;
        while(!valido){
            Mensaje.preguntarPrecio();
            precio=teclado.nextLine();
            try {precioEnDouble=Double.parseDouble(precio);
                valido=true;}
            catch(NumberFormatException e)
            {Mensaje.imprimirMenuPrecioInvalido();}
        }
        return precioEnDouble;
    }
    public static String pedirTipoPlanta() {
        String tipo="";
        while(tipo.length()<1 || tipo.length()>15){
            Mensaje.preguntarTipoPlanta();
            tipo=teclado.nextLine();
            if(tipo.length()<1 || tipo.length()>15){
                Mensaje.errorGenericoLongituDato(15,0);
            }
        }
        return tipo;
    }

    public static String pedirDni(boolean esCliente) {
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

    public static String pedirCodigoPostal() {
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

    public static String pedirDireccion() {
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

    public static void Instalador() {
        String execCommand = "sqlcmd -i " + path;
        try {
            Process p = Runtime.getRuntime().exec(execCommand);
        } catch (Exception e) {}
    }
}
