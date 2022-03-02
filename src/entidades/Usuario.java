package entidades;

public class Usuario {
    private String Usuario, contrasenhia,nombre,Dni,Direccion,CodigoPostal,telefono,correoElectronico,ciudad;;
    private int id;
    private boolean esGestor;

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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        this.Usuario = usuario;
    }

    public String getContrasenhia() {
        return contrasenhia;
    }

    public void setContrasenhia(String contrasenhia) {
        this.contrasenhia = contrasenhia;
    }

    public boolean isEsGestor() {
        return esGestor;
    }

    public void setEsGestor(boolean esGestor) {
        this.esGestor = esGestor;
    }

    public Usuario(String usuario, String contrasenhia, String nombre, String dni, String direccion, String codigoPostal, String telefono, String correoElectronico, String ciudad, int id, boolean esGestor) {
        Usuario = usuario;
        this.contrasenhia = contrasenhia;
        this.nombre = nombre;
        Dni = dni;
        Direccion = direccion;
        CodigoPostal = codigoPostal;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.ciudad = ciudad;
        this.id = id;
        this.esGestor = esGestor;
    }
    public Usuario(int id,String usuario, String contrasenhia, boolean esGestor) {
        this.id = id;
        Usuario = usuario;
        this.contrasenhia = contrasenhia;
        this.esGestor = esGestor;
    }
}
