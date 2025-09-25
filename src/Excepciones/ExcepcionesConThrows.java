package Excepciones;

public class ExcepcionesConThrows {
    // Metodo que puede lanzar una excepcion
    public static void metodoQueLanzaExcepcion() throws Exception {
        // Simulamos una condicion que lanza una excepcion
        boolean condicionDeError = true;
        if (condicionDeError) {
            throw new Exception("Se ha producido un error en el metodo.");
        }
    }

    public static void main(String[] args) {
        try {
            metodoQueLanzaExcepcion();
        } catch (Exception e) {
            System.out.println("Excepcion capturada: " + e.getMessage());
        }
    }
}
