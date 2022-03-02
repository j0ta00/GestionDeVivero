package entidades;

public class InformeVenta {
    String tipo;
    private double importeTotal;
    private int cantidadTotal;

    public InformeVenta(String tipo, double importeTotal, int cantidadTotal) {
        this.tipo = tipo;
        this.importeTotal = importeTotal;
        this.cantidadTotal = cantidadTotal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }
}
