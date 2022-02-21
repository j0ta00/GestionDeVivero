package mensaje;

public class Mensaje{

    private final static String
    CONTRASENHIA_INVALIDA="contrasenhia inválida, la longitud debe ser mayor de 6 y contener numeros y letras",
    PRECIO_INVALIDO="Introduce un precio adecuado para el tipo de producto,recuerda que no puede tener más de 2 decimales, ni más de 6 cifras",
            MENU_TIPO_PRODUCTO= """
            ¿De qué será el producto?
            1) Jardinería
            2) Planta
            """,CODIGO_POSTAL_INVALIDO="Introduce un código postal español válido",ERROR_LONGITUD_DATO="El dato introducido tiene una longitud inválida, el rango sería -> la máxima longitud: %d y la minima: %d",ERROR_LONGITUD_NOMBRE="El usuario no puede tener un nombre cuya longitud sea menor a 1 carácter, ni podrá ser superior a 30",
    CORREO="Introduce el correo del cliente:", UNIDADES_DISPONIBLES="Introduce las unidades que estarán disponibles del producto:",DESCRIPCION="Introduce la descripción del producto:",PRECIO="Introduce el precio que quieres que tenga el producto, recuerda que no puede tener más de 2 decimales, ni más de 6 cifras:",
    TELEFONO="Introduce el teléfono del cliente(recuerda que debe ser uno español para que sea válido):",TIPO_PLANTA="Introduce el tipo de planta que quieres añadir",ID_TIPO_PLANTA="Introduce el id del tipo planta que tiene la planta:",
    CIUDAD="Introduce la ciudad en la que vive el cliente:",CODIGO_POSTAL="Introduce el codigo postal que deseas que tenga el cliente",DIRECCION="Introduce la dirección que deseas que tenga el cliente:",ES_GESTOR="Introduce 0 si quieres que sea vendedor y 1 si quieres que sea gestor",USUARIO="Introduce el nombre de usuario que tendrá dicho usuario:",CONTRASENHIA="Introduce la contraseña que tendrá el usuario,la longitud debe ser mayor de 6 y contener numeros y letras:",
            DNI="Introduce el dni que deseas que tenga el cliente(recuerda que debe ser uno español para que sea válido):",NOMBRE_CLIENTE="Introduce el nombre que deseas que tenga el cliente:",CANTIDAD_DE_PRODUCTO_INVALIDA="No queda esa cantidad de productos disponibles", OPCION_FINALIZAR="1) Finalizar Factura", CANTIDAD_PRODUCTO="Introduce la cantidad de producto que deseas vender", TELEFONO_INVALIDO="Teléfono inválido, introduce un teléfono español de 9 dígitos",TELEFONO_NO_ENCONTRADO="No hay ningún teléfono en la base de datos que corresponda con el introducido";


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

    public static void preguntarNombreCliente(){
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
        System.out.println("Introduce el código del producto que desea vender");

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
        System.out.println("0)Retroceder a introducir el id del producto");
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
        System.out.println("El dni es inválido ");
    }

    public static void productoIntroducidoConExito() {
        System.out.println("El producto ha sido vendido con éxito");
    }

    public static void productoNoEncontrado() {
        System.out.println("No se ha encontrado el producto, escriba un código de producto existente");

    }
}
