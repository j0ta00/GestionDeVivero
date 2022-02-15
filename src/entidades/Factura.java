package entidades;

import java.time.LocalDate;

public class Factura{
private int id,idUsuario;
private String dniCliente;
private LocalDate fecha;
private double importe;

    public Factura(int id, String dniCliente, int idUsuario, LocalDate fecha, double importe) {
        this.id = id;
        this.dniCliente = dniCliente;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.importe = importe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
