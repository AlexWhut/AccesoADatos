package EJercicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EscribeNumerosDeLineas {
    public static void main(String[] args) {

        // Ruta del archivo
        String nombreArchivo = "/home/whut/Desktop/GitHub/AccesoADatos/Ejemplo.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int numeroLinea = 1;
            while ((linea = br.readLine()) != null) {
                System.out.println(numeroLinea + ": " + linea);
                numeroLinea++;
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        } finally {
            System.out.println("Finalizando la lectura de líneas.");
        }
    }
}
