package entidades;

import java.util.Date;

public class Factura{
private int id,idUsuario;
private String dniCliente;
private Date fecha;
private double importe;

    public Factura(int id, String dniCliente, int idUsuario, Date fecha, double importe) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    @Override
    public String toString() {
        return new StringBuilder("Factura -> { id=").append(id).append(", idVendedor=").append(idUsuario)
                .append(", dniCliente='").append(dniCliente).append(", fecha=").append(fecha).append(" }").toString();
    }
}
