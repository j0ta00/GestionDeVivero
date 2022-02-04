package entidades;

public class Cliente {
private String nombre,Dni,Direccion,CodigoPostal,telefono,correoElectronico;

    public Cliente(String nombre, String dni, String direccion, String codigoPostal, String telefono, String correoElectronico) {
        this.nombre = nombre;
        Dni = dni;
        Direccion = direccion;
        CodigoPostal = codigoPostal;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return Dni;
    }

    public void setDni(String dni) {
        Dni = dni;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        CodigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
}
