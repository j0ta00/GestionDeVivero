package gestora;

import dataAccess.*;
import entidades.*;
import mensaje.Mensaje;
import validaciones.DniValidator;

import java.util.LinkedList;
import java.util.List;

public class GestoraGestion {
    public static void mostrarMenuGestor(){
        String eleccion="";
        while (!eleccion.equals("0")){
            Mensaje.menuPrincipalGestor();
            eleccion = Gestora.teclado.nextLine();
            realizarOpcionElegida(eleccion);
        }
    }

    public static void realizarOpcionElegida(String eleccion){
        switch(eleccion){
            case "1"-> Gestora.realizarInserciones();
            case "2"->realizarModificaciones();
            case "3"->realizarBorrados();
            case "4"->realizarInformes();
        }
    }

    public static void realizarInformes(){
        String eleccion="";
        while (!eleccion.equals("0")){
            Mensaje.imprimirMenuInformes();
            eleccion = Gestora.teclado.nextLine();
            realizarInformeElegido(eleccion);
        }
    }

    public static void realizarInformeElegido(String eleccion){
        switch(eleccion){
            case "1"->realizarInformeVentasMensuales();
            case "2"-> Gestora.realizarInformeVentasAnuales();
            case "3"->realizarInformeProductosExistentes();
            case "4"->realizarInformeClientes();
            case "5"->realizarInformeUsuariosPorTipo(false);
            case "6"->realizarInformeUsuariosPorTipo(true);
            case "7"->realizarInformeFacturasPorCliente();

        }
    }

    private static void realizarInformeFacturasPorCliente() {
        String dni="";
        List<Factura> listadoFacturas;
        while(!new DniValidator(dni).validar()){
            Mensaje.introducirDniCliente();
            dni= Gestora.teclado.nextLine();
            if((listadoFacturas= DataAccessCliente.obtenerFacturasPorCliente(dni))!=null && !listadoFacturas.isEmpty()){
                listadoFacturas.forEach(factura ->{
                    if(factura!=null){
                        Mensaje.imprimirFactura(factura, DataAccessFactura.obtenerLineaPedidos(factura.getId()));
                    }
                });
            }
        }
    }

    private static void realizarInformeClientes(){
        Mensaje.imprimirClientes(DataAccessCliente.obtenerClientes());
    }

    private static void realizarInformeUsuariosPorTipo(boolean esGestor){
        Mensaje.imprimirUsuarios(DataAccess.obtenerUsuarioSegunTipo(esGestor));
    }

    private static void realizarInformeProductosExistentes(){
        String tipoProducto="";
        while (!tipoProducto.equals("1") && !tipoProducto.equals("2")) {
            Mensaje.imprimirMenuTipoProducto();
            tipoProducto= Gestora.teclado.nextLine();}
        Mensaje.imprimirProductos(DataAccessInforme.obtenerTodosLosProductosDeUnTipo(tipoProducto.equals(1)? Gestora.TABLA_PRODUCTOS_JARDINERIA: Gestora.TABLA_PLANTAS));
    }

    public static void realizarInformeVentasMensuales(){
        Mensaje.imprimirInformeVentas(DataAccessInforme.obtenerImporteVentasProductosMensuales(Gestora.pedirAnhio(), Gestora.pedirMes()));
    }

    public static void realizarModificaciones(){
        String eleccion="";
        while(!eleccion.equals("0")){
            Mensaje.imprimirMenuModificacion();
            eleccion= Gestora.teclado.nextLine();
            realizarModificacionElegida(eleccion);
        }
    }

    public static void realizarModificacionElegida(String eleccion){
        switch(eleccion) {
            case "1" ->modificarCliente();
            case "2"->modificarProducto();
            case "3"->modificarTipoPlanta();
            case "4"->modificarUsuario();

        }
    }

    public static void modificarTipoPlanta(){
        String idTipoPlanta="";String tipo;
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta= Gestora.teclado.nextLine();
            if(!idTipoPlanta.equals("0") && idTipoPlanta.matches(Gestora.REGEX_SOLO_NUMEROS) ){
                tipo=Gestora.pedirTipoPlanta();
                if((DataAccessProducto.modificarTipoPlanta(Integer.parseInt(idTipoPlanta),tipo))){
                    Mensaje.imprimirElementoModificadoConExito();
                }else{Mensaje.imprimirErrorEnLaModificacion();}}else if(!idTipoPlanta.equals("0")){Mensaje.elementoNoEncontrado();}
        }
    }

    public static void modificarUsuario(){
        String dniOId="";
        Usuario usuario;boolean esId;
        while(!dniOId.equals("0")) {
            Mensaje.preguntarIdElementoAModificar();
            Mensaje.retrocederHaciaOpcionAnterior();
            dniOId= Gestora.teclado.nextLine();
            esId=dniOId.matches(Gestora.REGEX_SOLO_NUMEROS);
            if(esId){
                usuario= DataAccessUsuario.obtenerUsuarioPorId(Integer.parseInt(dniOId));
            }else{usuario= DataAccessUsuario.obtenerUsuarioPorDni(dniOId); }
            if(!dniOId.equals("0") && usuario!=null){
                modificarAtributosUsuario(usuario);
            }else if(!dniOId.equals("0") && usuario==null){Mensaje.elementoNoEncontrado();}
        }
    }

    public static void modificarAtributosUsuario(Usuario usuario){
        String eleccion="";
        while(!eleccion.equals("0")) {
            Mensaje.imprimirMenuModificacionUsuario();
            eleccion= Gestora.teclado.nextLine();
            modificarAtributoDelUsuarioElegidoPorUsuario(eleccion,usuario);}
        while(!eleccion.equals("1") && !eleccion.equals("2")) {
            Mensaje.imprimirGuardarModificacion();
            eleccion= Gestora.teclado.nextLine();}
        if(eleccion.equals("1")){
            DataAccessUsuario.modificarUsuario(usuario); }
    }

    public static void modificarProducto(){
        String codigoProducto="";boolean esPlanta;
        Producto producto=null;
        while(!codigoProducto.equals("0")) {
            Mensaje.introducirCodigoProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            codigoProducto= Gestora.teclado.nextLine();
            if(!codigoProducto.equals("0") && codigoProducto.matches(Gestora.REGEX_SOLO_NUMEROS) && (producto = DataAccessInforme.obtenerProducto(Integer.parseInt(codigoProducto)))!=null) {
                esPlanta= DataAccessFactura.consultarSiProductoEsPlanta(producto);
                modificarAtributosProducto(producto,esPlanta);}
            else if(!codigoProducto.equals("0") && producto==null){Mensaje.elementoNoEncontrado();}
        }
    }

    public static void modificarAtributosProducto(Producto producto, boolean esPlanta){
        String eleccion="";
        while(!eleccion.equals("0")) {
            Mensaje.imprimirMenuModificacionProducto();
            if(esPlanta) {
                Mensaje.imprimirMenuModificacionPlanta();}
            eleccion= Gestora.teclado.nextLine();
            modificarAtributoDelProductoElegidoPorUsuario(eleccion,producto,esPlanta);}
        while(!eleccion.equals("1") && !eleccion.equals("2")) {
            Mensaje.imprimirGuardarModificacion();
            eleccion= Gestora.teclado.nextLine(); }
        if(eleccion.equals("1")){
            DataAccessProducto.modificarProducto(producto); }
    }

    public static void modificarCliente(){
        String dniCliente="";
        Cliente cliente=null;
        while(!dniCliente.equals("0")) {
            Mensaje.preguntarIdElementoAModificar();
            Mensaje.retrocederHaciaOpcionAnterior();
            dniCliente= Gestora.teclado.nextLine();
            if(!dniCliente.equals("0") && !dniCliente.equals("77863714C") && (cliente= DataAccessCliente.obtenerCliente(dniCliente,true))!=null){
                modificarAtributosCliente(cliente);
            }else if(!dniCliente.equals("0") && cliente==null){Mensaje.elementoNoEncontrado();}
        }
    }

    public static void modificarAtributosCliente(Cliente cliente){
        String eleccion="";
        while(!eleccion.equals("0")) {
            Mensaje.imprimirMenuModificacionCliente();
            eleccion= Gestora.teclado.nextLine();
            modificarAtributoDelClienteElegidoPorUsuario(eleccion,cliente);}
        while(!eleccion.equals("1") && !eleccion.equals("2")) {
            Mensaje.imprimirGuardarModificacion();
            eleccion= Gestora.teclado.nextLine(); }
        if(eleccion.equals("1")){ DataAccessCliente.modificarCliente(cliente); }
    }

    public static void modificarAtributoDelUsuarioElegidoPorUsuario(String eleccion, Usuario usuario){
        switch(eleccion){
            case "1"->usuario.setNombre(Gestora.pedirNombre());
            case "2"->usuario.setDni(Gestora.pedirDni(false));
            case "3"->usuario.setDireccion(Gestora.pedirDireccion());
            case "4"->usuario.setCodigoPostal(Gestora.pedirCodigoPostal());
            case "5"->usuario.setCiudad(Gestora.pedirCiudad());
            case "6"-> usuario.setTelefono(Gestora.pedirTelefono());
            case "7"->usuario.setCorreoElectronico(Gestora.pedirCorreo());
            case "8"->usuario.setUsuario(Gestora.pedirUsuario());
            case "9"-> usuario.setContrasenhia(Gestora.pedirContrasenhia());
            case "10"->usuario.setEsGestor(Gestora.pedirSiEsGestor());
        }
    }

    public static void modificarAtributoDelProductoElegidoPorUsuario(String eleccion, Producto producto, boolean esPlanta){
        switch(eleccion){
            case "1"->producto.setDescripcion(Gestora.pedirDescripcion());
            case "2"->producto.setPrecioUnitario(Gestora.pedirPrecioUnitario());
            case "3"->producto.setUnidadesDisponibles(Integer.parseInt(Gestora.pedirUnidadesDisponibles()));
            case "4"->{if(esPlanta) {modificarTipoPlantaDeUnaPlanta(producto);}}
        }
    }

    public static void modificarTipoPlantaDeUnaPlanta(Producto producto){
        String idTipoPlanta="";
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta=Gestora.teclado.nextLine();
            modificarTipoPlantaDeUnaPlantaElegida(producto, idTipoPlanta);
        }

    }

    private static void modificarTipoPlantaDeUnaPlantaElegida(Producto producto, String idTipoPlanta) {
        String idTipoPlantaNuevo;
        if(!idTipoPlanta.equals("0") && idTipoPlanta.matches(Gestora.REGEX_SOLO_NUMEROS)){
            Mensaje.pedirNuevoIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlantaNuevo= Gestora.teclado.nextLine();
            if(!idTipoPlantaNuevo.equals("0") && idTipoPlantaNuevo.matches(Gestora.REGEX_SOLO_NUMEROS)){
                if(DataAccessProducto.modificarTipoPlantaDeUnaPlanta(producto.getCodigo(),Integer.parseInt(idTipoPlanta),Integer.parseInt(idTipoPlantaNuevo))){
                    Mensaje.imprimirElementoModificadoConExito();
                }else{
                    Mensaje.imprimirErrorTipoPlantaNoEncontrado();
                }
            }
        }else{Mensaje.imprimirTipoPlantaInvalido(); }
    }

    public static void modificarAtributoDelClienteElegidoPorUsuario(String eleccion, Cliente cliente){
        switch(eleccion){
            case "1"->cliente.setNombre(Gestora.pedirNombre());
            case "2"->cliente.setDni(Gestora.pedirDni(true));
            case "3"->cliente.setDireccion(Gestora.pedirDireccion());
            case "4"->cliente.setCodigoPostal(Gestora.pedirCodigoPostal());
            case "5"->cliente.setCiudad(Gestora.pedirCiudad());
            case "6"-> cliente.setTelefono(Gestora.pedirTelefono());
            case "7"->cliente.setCorreoElectronico(Gestora.pedirCorreo());
        }
    }

    public static void realizarBorrados(){
        String eleccion="";
        while(!eleccion.equals("0")){
            Mensaje.menuPrincipalBorrado();
            eleccion= Gestora.teclado.nextLine();
            realizarBorradoElegido(eleccion);
        }
    }

    public static void realizarBorradoElegido(String eleccion){
        switch(eleccion) {
            case "1" ->borrarCliente();
            case "2"->borrarProducto();
            case "3"->borrarFactura();
            case "4"->borrarTipoPlanta();
            case "5"->borrarUsuario();
            case "6"->borrarTipoPlantaAsignadoAUnaPlanta();
        }
    }

    public static void borrarTipoPlantaAsignadoAUnaPlanta(){
        String idTipoPlanta="";
        while(!idTipoPlanta.equals("0")) {
            Mensaje.preguntarIdTipoPlanta();
            Mensaje.retrocederHaciaOpcionAnterior();
            idTipoPlanta= Gestora.teclado.nextLine();
            realizarBorradoTipoPlantaElegido(idTipoPlanta);
        }
    }

    private static void realizarBorradoTipoPlantaElegido(String idTipoPlanta) {
        int resultado=0;
        String codigoProducto;
        if(!idTipoPlanta.equals("0")  && idTipoPlanta.matches(Gestora.REGEX_SOLO_NUMEROS)){
            Mensaje.introducirCodigoProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            codigoProducto= Gestora.teclado.nextLine();
            if(!codigoProducto.equals("0")  && codigoProducto.matches(Gestora.REGEX_SOLO_NUMEROS) && (resultado= DataAccessProducto.borrarTipoPlantaEnPlanta(Integer.parseInt(codigoProducto),Integer.parseInt(idTipoPlanta)))==1){
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

    public static void borrarCliente(){
        String dniCliente="";int resultado=0;
        while(!dniCliente.equals("0")) {
            Mensaje.pedirDniClienteABorrar();
            Mensaje.retrocederHaciaOpcionAnterior();
            dniCliente= Gestora.teclado.nextLine();
            if(!dniCliente.equals("0") && !dniCliente.equals("77863714C") &&  (resultado= DataAccessCliente.borrarCliente(dniCliente))==1){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!dniCliente.equals("0") && resultado==0){Mensaje.elementoNoEncontrado();}else if(!dniCliente.equals("0") && resultado==-1){Mensaje.errorBorrado();}
        }
    }

    public static void borrarFactura(){
        String idFactura="";
        while(!idFactura.equals("0")) {
            Mensaje.pedirIdFactura();
            Mensaje.retrocederHaciaOpcionAnterior();
            idFactura= Gestora.teclado.nextLine();
            if(!idFactura.equals("0")  && idFactura.matches(Gestora.REGEX_SOLO_NUMEROS) && DataAccessFactura.borrarFactura(Integer.parseInt(idFactura))){
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
            idTipoPlanta= Gestora.teclado.nextLine();
            if(!idTipoPlanta.equals("0")  && idTipoPlanta.matches(Gestora.REGEX_SOLO_NUMEROS) && (resultado= DataAccessProducto.borrarTipoPlanta(Integer.parseInt(idTipoPlanta)))==1){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!idTipoPlanta.equals("0") && resultado==0){Mensaje.elementoNoEncontrado();}else if(!idTipoPlanta.equals("0") && resultado==-1){Mensaje.errorBorrado();}
        }
    }

    public static void borrarProducto(){
        String idProducto="";
        while(!idProducto.equals("0")) {
            Mensaje.introducirCodigoProducto();
            Mensaje.retrocederHaciaOpcionAnterior();
            idProducto= Gestora.teclado.nextLine();
            realizarBorradoProductoPlantaOJardineria(idProducto);
        }
    }

    private static void realizarBorradoProductoPlantaOJardineria(String idProducto) {
        Producto p;int resultado;boolean esPlanta;
        if(!idProducto.equals("0") && idProducto.matches(Gestora.REGEX_SOLO_NUMEROS)) {
            p= DataAccessInforme.obtenerProducto(Integer.parseInt(idProducto));
            if(p!=null) {
                esPlanta = DataAccessFactura.consultarSiProductoEsPlanta(p);
                if ((resultado = DataAccessProducto.borrarProducto(Integer.parseInt(idProducto), esPlanta)) == 1) {
                    Mensaje.imprimirElementoBorradoConExito();
                } else if (resultado == 0) {
                    Mensaje.elementoNoEncontrado();
                } else { Mensaje.errorBorrado(); }
            }else{ Mensaje.elementoNoEncontrado();}}
    }

    public static void borrarUsuario(){
        String usuario="";int resultado=0;
        while(!usuario.equals("0")) {
            Mensaje.preguntarUsuario();
            Mensaje.retrocederHaciaOpcionAnterior();
            usuario= Gestora.teclado.nextLine();
            if(!usuario.equals("0") && (resultado= DataAccessUsuario.borrarUsuario(usuario))==1){
                Mensaje.imprimirElementoBorradoConExito();
            }else if(!usuario.equals("0") && resultado==0){Mensaje.elementoNoEncontrado();}else if(!usuario.equals("0") && resultado==-1){Mensaje.errorBorrado();}
        }
    }

    public static void realizarInsercionElegida(String eleccion){
        switch(eleccion) {
            case "1" ->realizarInsercionCliente();
            case "2"->realizarInsercionProducto();
            case "3"->insertarTipoPlanta();
            case "4"->insertarUsuario();
        }
    }

    public static void insertarUsuario(){
        if(DataAccessUsuario.insertarUsuario(new Usuario(Gestora.pedirUsuario(),Gestora.pedirContrasenhia(), Gestora.pedirNombre(), Gestora.pedirDni(false), Gestora.pedirDireccion(), Gestora.pedirCodigoPostal(), Gestora.pedirTelefono(), Gestora.pedirCorreo(), Gestora.pedirCiudad(),0,Gestora.pedirSiEsGestor()))){
            Mensaje.imprimirUsuarioInsertadoExito();
        }else{
            Mensaje.imprimirErrorInsercionUsuario();
        }
    }

    public static void realizarInsercionProducto(){
        String tipoProducto="";Producto producto;
        while (!tipoProducto.equals("1") && !tipoProducto.equals("2")) {
            Mensaje.imprimirMenuTipoProducto();
            tipoProducto= Gestora.teclado.nextLine();}
        DataAccessProducto.insertarProducto((producto=new Producto(Gestora.pedirDescripcion(),0,Integer.parseInt(Gestora.pedirUnidadesDisponibles()),Gestora.pedirPrecioUnitario())));
        if(tipoProducto.equals("1")){
            DataAccessProducto.insertarProductoPlantaOJardineria(null);
        }else{
            DataAccessProducto.insertarProductoPlantaOJardineria(pedirTipoPlantasParaProducto()); }
    }

    public static List<Tipo_Planta> pedirTipoPlantasParaProducto(){
        List<Tipo_Planta> listaTipoPlanta=new LinkedList<Tipo_Planta>();String idTipoPlanta="";Tipo_Planta tipo_planta;
        while(!idTipoPlanta.equals("0") ||  0==listaTipoPlanta.size()){
            Mensaje.preguntarIdTipoPlanta();
            if(listaTipoPlanta.size()>0){Mensaje.retrocederHaciaOpcionAnterior(); }
            idTipoPlanta= Gestora.teclado.nextLine();
            if(!idTipoPlanta.equals("0") && !idTipoPlanta.matches(Gestora.REGEX_SOLO_NUMEROS) && (tipo_planta= DataAccessProducto.obtenerTipoPlanta(Integer.parseInt(idTipoPlanta)))!=null){
                listaTipoPlanta.add(tipo_planta);
                Mensaje.imprimirTipoPlantaExito();
            }else if(!idTipoPlanta.equals("0")){
                Mensaje.imprimirErrorTipoPlantaNoEncontrado();
            }
        }
        return listaTipoPlanta;
    }

    private static void insertarTipoPlanta(){
        if(DataAccessProducto.insertarTipoPlanta(Gestora.pedirTipoPlanta())){
            Mensaje.imprimirTipoPlantaInsertado();
        }else{
            Mensaje.imprimirErrorInsercionTipoPlanta();
        }
    }

    public static void realizarInsercionCliente(){
        if(!DataAccessCliente.insertarCliente(new Cliente(Gestora.pedirNombre(), Gestora.pedirDni(true), Gestora.pedirDireccion(), Gestora.pedirCodigoPostal(), Gestora.pedirCiudad(),
                Gestora.pedirTelefono(), Gestora.pedirCorreo()))){
            Mensaje.imprimirErroClienteNoInsertado();
        }else{
            Mensaje.imprimirClienteInsertadoConExito();
        }
    }
}
