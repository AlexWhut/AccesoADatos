package Excepciones;
public class ExcepcionesDivPorCero {


        public int divide (int a, int b){
                return a/b;
            }
        public static void main(String[] args) {

            int a, b;
            a = 5;
            b = 2;

            // Se realiaza el try catch finally para controlar la excepcion y simplificar el codigo, 

            try {
                // esto ejecuta el codigo que puede lanzar una excepcion, intentando realizar la divison por cero incial del codigo
                // si no lo logra mandara el control al catch que dice que hacer en caso de que falle, y el finally se ejecuta siempre
                // para limpiar recursos o finalizar procesos
                ExcepcionesDivPorCero edpc = new ExcepcionesDivPorCero();
                System.out.println("El resultado de la division es: " + edpc.divide(a, b));
            } catch (ArithmeticException e) {
                System.out.println("Error: No se puede dividir por cero.");
            } finally {
                System.out.println("Ejecución del bloque finally.");
            }

        }
 }
    

