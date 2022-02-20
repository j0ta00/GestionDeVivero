package entidades;

public class Producto {
    private String descripcion;
    private int unidadesDisponibles,codigo;
    private double precioUnitario;

    public Producto(String descripcion, int codigo, int unidadesDisponibles, double precioUnitario) {
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.unidadesDisponibles = unidadesDisponibles;
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int  codigo) {
        this.codigo = codigo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
