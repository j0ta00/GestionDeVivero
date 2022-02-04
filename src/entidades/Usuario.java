package entidades;

public class Usuario {
    private String nombre, contrasenhia;
    private int id;
    private boolean esGestor;

    public Usuario(int id,String nombre, String contrasenhia, boolean esGestor) {
        this.nombre = nombre;
        this.contrasenhia = contrasenhia;
        this.esGestor = esGestor;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
