package validaciones;
/**
 * Esta es una clase revisada por mí y mejorada, de forma que aunque el algoritmo original provenía de
 * otro programador de internet, lo he acabado adaptando a nuestras buenas praxis
 * */
public class DniValidator {
    private String dni;
    private final int DNI_LENGTH=9;
    private final String T="T", R="R", W="W", A="A", G="G", M="M", Y="Y", F="F", P="P",D="D",X="X", B="B", N="N", J="J", Z="Z",
            S="S", Q="Q", V="V", H="H", L="L", C="C", K="K", E="E",CERO="0", UNO="1",DOS="2", TRES="3",CUATRO="4",CINCO="5"
            , SEIS="6",SIETE="7",OCHO="8",NUEVE="9";
/**
 * <p><b>Proposito:</b> Constructor por parámetros que usa crea un objeto dniValidator, que nos permitirá validar si el dni
 * introducido es o no correcto</p>
 * @param inputDni - type:String
 * */
    public DniValidator(String inputDni) {
        this.dni = inputDni;
    }

/**
 * <p><b>Proposito:</b> Valida que el DNI cumpla las características de un DNI real</p>
 *<p><b>Precondición:</b> Ninguna</p>
 * <p><b>Postcondición:</b> Ninguna</p>
 * @return dni valido--type: boolean
 * */
    public boolean validar() {
        String letraMayuscula;
        boolean valido=false;
        if (dni.length() == DNI_LENGTH && Character.isLetter(this.dni.charAt(8))) {
            letraMayuscula = (this.dni.substring(8)).toUpperCase();
            if (soloNumeros() && letraDNI().equals(letraMayuscula)) {
                valido = true;
            }
        }
        return valido;
    }

/**
 * <p><b>Proposito:</b> Valida que los 8 primeros caracteres del DNI sean numeros</p>
 *<p><b>Precondición:</b> Ninguna</p>
 * <p><b>Postcondición:</b> Ninguna</p>
 * @return dni valido--type: boolean
 * */
    private boolean soloNumeros() {
        boolean valido=true;
        int i, j;
        String numero;
        String miDNI = "";
        String[] unoNueve = {CERO,UNO,DOS,TRES,CUATRO,CINCO,SEIS,SIETE,OCHO,NUEVE};
        for (i = 0; i < this.dni.length() - 1; i++) {
            numero = this.dni.substring(i, i + 1);
            for (j = 0; j < unoNueve.length; j++) {
                if (numero.equals(unoNueve[j])) { miDNI += unoNueve[j];}
            }
        }
        if (miDNI.length() != 8) {
            valido= false;
        }
        return valido;
    }
/**
 * <p><b>Proposito:</b> Comprueba que la letra del dni valida y se corresponda con la cadena de numeros del dni</p>
 *<p><b>Precondición:</b> Ninguna</p>
 * <p><b>Postcondición:</b> Ninguna</p>
 * @return letra del dni--type: String
 * */

    private String letraDNI() {
        int miDNI = Integer.parseInt(this.dni.substring(0, 8));
        String miLetra;
        String[] asignacionLetra = {T, R, W, A, G, M, Y, F, P, D,X, B,N,J, Z, S, Q, V, H, L, C, K, E};
        int resto = miDNI % 23;
        miLetra = asignacionLetra[resto];
        return miLetra;
    }
}
