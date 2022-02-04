package mensaje;

public class Mensaje{

    public static void preguntarInstalacion(){
        System.out.println("Parece que su equipo no tiene instalado la base de datos necesaria para ejecutar el programa");
        System.out.println("¿Desea instalar la base de datos del programa?");
        System.out.println("1) Sí");
        System.out.println("2) No");
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
        System.out.println("Introduce el Dni o Telefono del cliente");
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
    public static void confirmarVenta(){
        System.out.println("0) Anular venta");
    } /**
     * <p><b>Proposito:</b> Imprime un mensaje interrogativo para el usuario, para que este confirme si está seguro de que dese anular la venta</p>
     * <p><b>Precondicion:</b> Ninguna</p>
     * <p><b>Postcondicion:</b> Ninguna</p>
     *
     * */
    public static void confirmarAnulacion(){
        System.out.println("¿Estás seguro de que deseas anular la venta?");
    }


    public static void dniNoEncontrado(){
        System.out.println("El dni introducido no corresponde con el de ningún cliente existente en la base de datos");

    }

    public static void dniInvalido() {
        System.out.println("El dni es inválido ");
    }
}
