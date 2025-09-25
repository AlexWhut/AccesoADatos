package Excepciones;

public class ExcepcionesConFinally {
    public static void main(String[] args) {
        int a = 5;
        int b = 0;

        try {
            System.out.println("El resultado de la division es: " + (a / b));
        } catch (ArithmeticException e) {
            System.out.println("Error: No se puede dividir por cero.");
        } finally {
            System.out.println("Ejecución del bloque finally.");
        }
    }
}
