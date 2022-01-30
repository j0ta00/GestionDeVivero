public class Mensaje{

    public static void introducirDatosLogin(String usuarioOContraseña){
        System.out.println(String.format("Introduce tu %s",usuarioOContraseña));
    }

    public static void menuPrincipalVendedor(){
        System.out.println("1) Comenzar una venta");
        System.out.println("2) Finalizar sesión");
    }

    public static void introducirDniCliente(){
        System.out.println("Introduce el Dni o Telefono del cliente");
    }

    public static void introducirCodigoProducto(){
        System.out.println("Introduce el código del producto que desea vender");

    }

    public static void introducirCantidadProducto(){
        System.out.println("Introduce la cantidad de dicho producto que desea vender");
    }

    public static void imprimirFactura(){
        System.out.println("Imprimiendo Factura...");
        System.out.println("Factura Imprimida:");
    }

    public static void confirmarVenta(){
        System.out.println("0) Anular venta");
    }
    public static void confirmarAnulacion(){
        System.out.println("¿Estás seguro de que deseas anular la venta?");
    }



}
