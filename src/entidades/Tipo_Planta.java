package entidades;

public class Tipo_Planta {
    int Id;
    String nombreTipoPlanta;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombreTipoPlanta() {
        return nombreTipoPlanta;
    }

    public void setNombreTipoPlanta(String nombreTipoPlanta) {
        this.nombreTipoPlanta = nombreTipoPlanta;
    }

    public Tipo_Planta(int id, String nombreTipoPlanta) {
        Id = id;
        this.nombreTipoPlanta = nombreTipoPlanta;
    }
}
