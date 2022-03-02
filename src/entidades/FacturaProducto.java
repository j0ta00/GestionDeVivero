package entidades;

public class FacturaProducto {
    private int codigo,cantidad;
    private String descripcion;
    private double total,precioUnitario;

    public FacturaProducto(int codigo, int cantidad, String descripcion, double total, double precioUnitario) {
        this.codigo = codigo;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.total = total;
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
