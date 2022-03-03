package mensaje;

import entidades.Factura;
import entidades.FacturaProducto;
import entidades.InformeVenta;

import java.util.List;
import java.util.stream.Collectors;

public class Mensaje{

    private final static String
            MENU_MODIFICACION_USUARIO= """
            0) Finalizar Modificación
            1) Modificar Nombre
            2) Modificar Dni
            3) Modificar Direccion
            4) Modificar Codigo Postal
            5) Modificar Ciudad
            6) Modificar Teléfono
            7) Modificar Correo Electronico
            8) Modificar nombre de Usuario
            9) Modificar contraseña
            10) Modificar si es Gestor o Vendedor"""
            ,PREGUNTAR_ID_MODIFICACION="Introduce el id/codigo/dni del elemento a modificar:"
            ,MENU_MODIFICACION_PRODUCTOPLANTA="4) Modificar Tipo de la Planta"
            ,MENU_MODIFICACION_PRODUCTO= """
            0) Finalizar Modificación
            1) Modificar Descripción
            2) Modificar Precio
            3) Modificar Unidades Disponibles
            """,MENU_MODIFICACION_CLIENTE= """
            0) Finalizar Modificación
            1) Modificar Nombre
            2) Modificar Dni
            3) Modificar Direccion
            4) Modificar Codigo Postal
            5) Modificar Ciudad
            6) Modificar Teléfono
            7) Modificar Correo Electronico
            """,GUARDAR_MODIFICAION= """
            ¿Desea guardar los cambios realizados o borrarlos?
            1) Guardarlos
            2) Borrarlos
            
            """, MENU_PRINCIPAL_MODIFICACION = """
            0) Retroceder
            1) Modificar Cliente
            2) Modificar Producto
            3) Modificar Tipo de Planta
            4) Modificar Usuario
            """,MENU_PRINCIPAL_BORRADO="""
                0) Retroceder
                1) Borrar Cliente
                2) Borrar Producto
                3) Borrar Factura
                4) Borrar Tipo de Planta
                5) Borrar Usuario
                6) Borrar Tipo de Planta asignado a una Planta
                """,ERROR_MODIFICACION="El elemento no ha podido ser modificado, revisa tu conexión con la bbdd",TIPO_PLANTA_INVALIDO="El id introducido es inválido, introduce uno numérico existente",PEDIR_ID_FACTURA="Introduce el id de la factura que deseas eliminar",ELEMENTO_BORRADO="Elemento borrado con éxito",ELEMENTO_NO_ENCONTRADO="El id/Dni/Codigo del elemento introducido no existe",ELEMENTO_CON_DEPENDENCIAS="No se ha podido eliminar dicho elemento, ya que tiene dependencias",TIPO_PLANTA_INSERTADO_EXITO="Tipo planta insertado con éxito",TIPO_PLANTA_INSERCION_FALLIDA="No se ha podido realizar la inserción de ese tipo planta, revisa tu conexión con la bbdd",
            TIPO_PLANTA_ANHIADIDO="Tipo de la planta asignado a la planta con éxito",FACTURA_GUARDADA="La factura se ha guardado exitosamente",TIPO_PLANTA_NO_ENCONTRADO="El id no corresponde con ningún id de tipo planta",ERROR_NUMEROS="Introduce un caracter numérico",CLIENTE_INSERTADO="El cliente ha sido insertado con éxito",CLIENTE_NO_INSERTADO="El cliente no se ha podido insertar con éxito, revisa que no haya uno introducido con el mismo dni o que estes conectado adecuadamente a la base de datos",
            CONTRASENHIA_INVALIDA="contrasenhia inválida, la longitud debe ser mayor de 6 y contener numeros y letras",MODIFICADO_CON_EXITO="Elemento modificado con éxito",USUARIO_INSERTADO_EXITO="El usuario ha sido insertado con éxito",ERROR_INSERCION_USUARIO="Ha ocurrido un error durante la isnercion del usuario y no se ha podido insertar, revisa la conexión con la base de datos",
            PRECIO_INVALIDO="Precio inadecuado para el tipo de producto, recuerda que no puede tener más de 2 decimales, ni más de 6 cifras",USUARIO_YA_EXISTE="El nombre de usuario introducido ya existe en la base de datos, usa otro",PEDIR_USUARIO="Introduce tu usuario de sql:",PEDIR_CONTRASENHIA="Introduce la contraseña de tu usuario de sql:",
            MENU_TIPO_PRODUCTO= """
            ¿De qué será el producto?
            1) Jardinería
            2) Planta
            """,MENU_INFORMES= """
            0) Retroceder
            1) Generar informe ventas totales en un mes 
            2) Generar informe ventas totales en un año
            3) Mostrar lista de productos indicando el tipo
            4) Mostrar lista de clientes
            5) Mostrar lista de vendedores
            6) Mostrar lista de gestores
            7) Mostrar facturas de un cliente concreto
            """,BORRAR_CLIENTE="Introduce el dni del cliente que deseas eliminar" ,CONEXION_CORRECTA="Conexión realizada con éxito",CONEXION_INCORRECTA="Conexión fallida, revisa los datos de la conexión",CLIENTE_GENERICO="1) Si quieres usar el cliente genérico",CODIGO_POSTAL_INVALIDO="Introduce un código postal español válido",ERROR_LONGITUD_DATO="El dato introducido tiene una longitud inválida, el rango sería -> la máxima longitud: %d y la minima: %d",ERROR_LONGITUD_NOMBRE="El usuario no puede tener un nombre cuya longitud sea menor a 1 carácter, ni podrá ser superior a 30",
            CORREO="Introduce el correo del cliente:", PEDIR_ANHIO="Introduce un año válido:",PEDIR_LOCALHOST="Introduce el localhost:",PEDIR_PUERTO="Introduce el puerto para la conexión:",UNIDADES_DISPONIBLES="Introduce las unidades que estarán disponibles del producto:",DESCRIPCION="Introduce la descripción del producto:",PRECIO="Introduce el precio que quieres que tenga el producto, recuerda que no puede tener más de 2 decimales, ni más de 6 cifras:",
            TELEFONO="Introduce el teléfono (recuerda que debe ser uno español para que sea válido):",PEDIR_MES="Introduce un més del año:",TIPO_PLANTA="Introduce el tipo de planta:",ID_TIPO_PLANTA="Introduce el id del tipo planta:",
            CIUDAD="Introduce la ciudad:",ERROR_NO_HAY_DATOS_EN_ESA_FECHA="Parece que no hay datos en esa fecha",NUEVO_ID_TIPO_PLANTA="Introduce el id del nuevo tipo de planta que quieres que tenga:",CODIGO_POSTAL="Introduce el codigo postal(Recuerda que debe ser uno español válido)",DIRECCION="Introduce la dirección:",ES_GESTOR="Introduce 1 si quieres que sea vendedor y 2 si quieres que sea gestor",USUARIO="Introduce el nombre de usuario:",CONTRASENHIA="Introduce la contraseña que tendrá el usuario,la longitud debe ser mayor de 6 y contener numeros y letras:",
            DNI="Introduce el dni(recuerda que debe ser uno español para que sea válido):",USUARIO_NO_ENCONTRADO="No se ha encontrado ningun usuario con ese nombre de usuario y contraseña",NOMBRE_CLIENTE="Introduce el nombre:",CANTIDAD_DE_PRODUCTO_INVALIDA="No queda esa cantidad de productos disponibles", OPCION_FINALIZAR="1) Finalizar Factura", CANTIDAD_PRODUCTO="Introduce la cantidad de producto que deseas vender", TELEFONO_INVALIDO="Teléfono inválido, introduce un teléfono español de 9 dígitos",TELEFONO_NO_ENCONTRADO="No hay ningún teléfono en la base de datos que corresponda con el introducido";


    public static void imprimirErrorEnLaConexion(){
        System.out.println(CONEXION_INCORRECTA);

    }
    public static void imprimirConexionCorrecta(){
        System.out.println(CONEXION_CORRECTA);

    }

    public static void setPedirLocalhost(){
        System.out.println(PEDIR_LOCALHOST);
    }

    public static void pedirPuerto(){
        System.out.println(PEDIR_PUERTO);
    }

    public static void pedirContrsenhiaSQL(){
        System.out.println(PEDIR_CONTRASENHIA);
    }

    public static void pedirUsuarioSQL(){
        System.out.println(PEDIR_USUARIO);
    }

    public static void pedirAnhio(){
        System.out.println(PEDIR_ANHIO);
    }

    public static void imprimirInformeVentas(List<InformeVenta> informeDeVentas){
        if(informeDeVentas!=null && !informeDeVentas.isEmpty()){
            informeDeVentas.forEach(informeVenta -> {
                if(informeVenta!=null) {
                    System.out.println(String.format("Tipo: %s", informeVenta.getTipo()));
                    System.out.println(String.format("Cantidad: %d ", informeVenta.getCantidadTotal()));
                    System.out.println(String.format("Importe total: %s ", informeVenta.getImporteTotal()));
                }
            });
        }else{
            System.out.println(ERROR_NO_HAY_DATOS_EN_ESA_FECHA);
        }

    }

    public static void pedirMes(){
        System.out.println(PEDIR_MES);
    }

    public static void imprimirMenuInformes(){
        System.out.println(MENU_INFORMES);
    }

    public static void imprimirOpcionClienteGenerico(){
        System.out.println(CLIENTE_GENERICO);
    }

    public static void imprimirFacturaGuardadDeFormaExitosa(){
        System.out.println(FACTURA_GUARDADA);
    }

    public static void imprimirErrorEnLaModificacion(){
        System.out.println(ERROR_MODIFICACION);
    }

    public static void imprimirFactura(Factura factura,List<FacturaProducto> listaFacturaProducto){
        double totalFactura=0;
        System.out.println(factura);
        for (FacturaProducto lineaProducto:listaFacturaProducto) {
            System.out.println(String.format("Codigo:%d",lineaProducto.getCodigo()));
            System.out.println(String.format("Descripción:%s",lineaProducto.getDescripcion()));
            System.out.println(String.format("Precio Unitario:%s",lineaProducto.getPrecioUnitario()));
            System.out.println(String.format("Cantidad:%d",lineaProducto.getCantidad()));
            System.out.println(String.format("Total de la Línea:%s",lineaProducto.getTotal()));
            totalFactura+=lineaProducto.getTotal();
        }
        System.out.println(String.format("El total de la factura es de: %s €",!factura.getDniCliente().equals("77863714C")?(totalFactura*0.95):totalFactura));
    }

    public static void imprimirElementoModificadoConExito(){
        System.out.println(MODIFICADO_CON_EXITO);
    }

    public static void imprimirTipoPlantaInvalido(){
        System.out.println(TIPO_PLANTA_INVALIDO);
    }

    public static void pedirNuevoIdTipoPlanta(){
        System.out.println(NUEVO_ID_TIPO_PLANTA);
    }
    public static void imprimirMenuModificacionPlanta(){
        System.out.println(MENU_MODIFICACION_PRODUCTOPLANTA);
    }
    public static void imprimirMenuModificacionProducto(){
        System.out.println(MENU_MODIFICACION_PRODUCTO);
    }
    public static void imprimirGuardarModificacion(){
        System.out.println(GUARDAR_MODIFICAION);
    }
    public static void imprimirMenuModificacionUsuario(){
        System.out.println(MENU_MODIFICACION_USUARIO);
    }
    public static void imprimirMenuModificacionCliente(){
        System.out.println(MENU_MODIFICACION_CLIENTE);
    }

    public static void preguntarIdElementoAModificar(){
        System.out.println(PREGUNTAR_ID_MODIFICACION);
    }

    public static void usuarioYaExiste(){
        System.out.println(USUARIO_YA_EXISTE);
    }

    public static void usuarioNoEncontrado(){
        System.out.println(USUARIO_NO_ENCONTRADO);
    }

    public static void imprimirMenuModificacion(){
        System.out.println(MENU_PRINCIPAL_MODIFICACION);

    }

    public static void pedirIdFactura(){
        System.out.println(PEDIR_ID_FACTURA);
    }

    public static void imprimirElementoBorradoConExito(){
        System.out.println(ELEMENTO_BORRADO);
    }

    public static void menuPrincipalBorrado(){
        System.out.println(MENU_PRINCIPAL_BORRADO);

    }

    public static void elementoNoEncontrado(){
        System.out.println(ELEMENTO_NO_ENCONTRADO);
    }

    public static void pedirDniClienteABorrar(){
        System.out.println(BORRAR_CLIENTE);
    }

    public static void errorBorrado(){
        System.out.println(ELEMENTO_CON_DEPENDENCIAS);
    }

    public static void imprimirErrorInsercionUsuario(){
        System.out.println(ERROR_INSERCION_USUARIO);
    }
    public static void imprimirUsuarioInsertadoExito(){
        System.out.println(USUARIO_INSERTADO_EXITO);
    }
    public static void imprimirErrorInsercionTipoPlanta(){
        System.out.println(TIPO_PLANTA_INSERCION_FALLIDA);
    }

    public static void imprimirTipoPlantaInsertado(){
        System.out.println(TIPO_PLANTA_INSERTADO_EXITO);
    }

    public static void imprimirTipoPlantaExito(){
        System.out.println(TIPO_PLANTA_ANHIADIDO);
    }
    public static void imprimirErrorTipoPlantaNoEncontrado(){
        System.out.println(TIPO_PLANTA_NO_ENCONTRADO);
    }

    public static void imprimirErrorCaracteresNumericos(){
        System.out.println(ERROR_NUMEROS);
    }

    public static void imprimirClienteInsertadoConExito(){
        System.out.println(CLIENTE_INSERTADO);
    }

    public static void imprimirErroClienteNoInsertado(){
        System.out.println(CLIENTE_NO_INSERTADO);
    }

    public static void imprimirErrorContrasenhiaInvalida(){
        System.out.println(CONTRASENHIA_INVALIDA);
    }

    public static void imprimirMenuPrecioInvalido(){
        System.out.println(PRECIO_INVALIDO);
    }

    public static void imprimirMenuTipoProducto(){
        System.out.println(MENU_TIPO_PRODUCTO);
    }

    public static void errorCodigoPostalInvalido(){
        System.out.println(CODIGO_POSTAL_INVALIDO);
    }

    public static void errorGenericoLongituDato(int max,int min){
        System.out.println(String.format(ERROR_LONGITUD_DATO,max,min));
    }

    public static void preguntarContrasenhia(){
        System.out.println(CONTRASENHIA);
    }

    public static void imprimirErrorEnLaLongitudDelNombre(){
        System.out.println(ERROR_LONGITUD_NOMBRE);
    }

    public static void preguntarUsuario(){
        System.out.println(USUARIO);
    }


    public static void preguntarSiEsGestor(){
        System.out.println(ES_GESTOR);
    }

    public static void preguntarIdTipoPlanta(){
        System.out.println(ID_TIPO_PLANTA);
    }

    public static void preguntarTipoPlanta(){
        System.out.println(TIPO_PLANTA);
    }

    public static void preguntarUnidadesDisponibles(){
        System.out.println(UNIDADES_DISPONIBLES);

    }

    public static void preguntarDescripcion(){
        System.out.println(DESCRIPCION);
    }

    public static void preguntarPrecio(){
        System.out.println(PRECIO);
    }

    public static void preguntarCorreo(){
        System.out.println(CORREO);
    }

    public static void preguntarTelefono(){
        System.out.println(TELEFONO);
    }

    public static void preguntarCiudad(){
        System.out.println(CIUDAD);
    }

    public static void preguntarCodigoPostal(){
        System.out.println(CODIGO_POSTAL);
    }

    public static void preguntarNombre(){
        System.out.println(NOMBRE_CLIENTE);
    }
    public static void preguntarDni(){
        System.out.println(DNI);
    }

    public static void preguntarDireccion(){
        System.out.println(DIRECCION);
    }
    public static void imprimirCantidadDeProductoInvalida(){
        System.out.println(CANTIDAD_DE_PRODUCTO_INVALIDA);
    }

    public static void imprimirFinalizarFactura(){
        System.out.println(OPCION_FINALIZAR);
    }

    public static void imiprimirTelefonoInvalido(){
        System.out.println(TELEFONO_INVALIDO);
    }

    public static void imprimirTelefonoNoEncontrado(){
        System.out.println(TELEFONO_NO_ENCONTRADO);
    }


    public static void preguntarInstalacion(){
        System.out.println("Parece que su equipo no tiene instalado la base de datos necesaria para ejecutar el programa");
        System.out.println("¿Desea instalar la base de datos del programa?");
        System.out.println("1) Sí");
        System.out.println("2) No");
    }


    public static void modificarDatos(String datoAModificar){
        System.out.println("Introduce el Id/Codigo/Dni del elemento que deseas modificar");
    }




    /**
     * <p><b>Proposito:</b> Imprime los datos que se van a introducir para que se pueda realizar le login</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */

    public static void introducirDatosLogin(String usuarioOContraseña){
        System.out.println(String.format("Introduce tu %s",usuarioOContraseña));
    }

    /**
     * <p><b>Proposito:</b> Imprime el menú principal del vendedor</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */

    public static void menuPrincipalVendedor(){
        System.out.println("1) Comenzar una venta");
        System.out.println("2) Finalizar sesión");
    }
    /**
     * <p><b>Proposito:</b> Imprime el mensaje pidiendo los datos del cliente </p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void introducirDniCliente(){
        System.out.println("Introduce el Dni o Telefono del cliente(recuerda debe ser un teléfono o Dni espñol válido)");
    }

    /**
     * <p><b>Proposito:</b> Imprime un mensaje pidiendo el código del producto</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void introducirCodigoProducto(){
        System.out.println("Introduce el código del producto");

    }
    /**
     * <p><b>Proposito:</b> Imprime un mensaje que pide la cantidad de producto/p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void introducirCantidadProducto(){
        System.out.println("Introduce la cantidad de dicho producto que desea vender");
    }
    /**
     * <p><b>Proposito:</b> Imprime un mensje que informa al usuario de que la factura se está imprimiendo y cuando esta acaba de imprimirse</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void imprimirFactura(){
        System.out.println("Imprimiendo Factura...");
        System.out.println("Factura Imprimida:");
    }
    /**
     * <p><b>Proposito:</b> Imprime la opción de anular venta</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void mostrarOpcionAnular(){
        System.out.println("0) Anular Venta");
    }


    public static void confirmarVenta(){
        System.out.println("0) Anular venta");
    } /**
     * <p><b>Proposito:</b> Imprime un mensaje interrogativo para el usuario, para que este confirme si está seguro de que dese anular la venta</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void imprimirGuardarOAnular(){
        System.out.println("""
                ¿Desea guardar o anular la venta?
                1) Anular
                2) Guardar""");
    }

    public static void menuPrincipalGestor(){
        System.out.println("""
                0) Finalizar sesión
                1) Insertar algún elemento
                2) Modificar algún elemento
                3) Eliminar algún elemento
                4) Opciones Adicionales
                """);

    }
    public static void menuPrincipalInsercion(){
        System.out.println("""
                0) Retroceder
                1) Insertar Cliente
                2) Insertar Producto
                3) Insertar tipo planta
                4) Insertar Usuario
                """);

    }




    public static void retrocederHaciaOpcionAnterior(){
        System.out.println("0) Retroceder a la opción anterior");
    }

    public static void salirDeLaVenta(){
        System.out.println("0)Finalizar o Anular la venta");
    }

    public static void confirmarAnulacion(){
        System.out.println("¿Estás seguro de que deseas anular la venta?");
    }


    public static void dniNoEncontrado(){
        System.out.println("El dni introducido no corresponde con el de ningún cliente existente en la base de datos");

    }

    public static void imprimirMensajeNoEncontradoGenerico(String elementoNoEncontrado){
        System.out.println(String.format("%s no encontrado en la base de datos",elementoNoEncontrado));
    }

    public static void dniInvalido() {
        System.out.println("El dni es inválido o ya existe en la base de datos ");
    }

    public static void productoIntroducidoConExito() {
        System.out.println("El producto ha sido vendido con éxito");
    }

    public static void productoNoEncontrado() {
        System.out.println("No se ha encontrado el producto, escriba un código de producto existente");

    }
}
