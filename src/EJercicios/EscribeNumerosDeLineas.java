package EJercicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EscribeNumerosDeLineas {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("PRUEBA1");
            return;
        }

        String textoBuscar = args[0];
        if (textoBuscar.isEmpty()) {
            System.out.println("El texto a buscar no puede estar vacío.");
            return;
        }

        // Ruta por defecto si no se pasa como argumento
        String nombreArchivo = (args.length >= 2)
                ? args[1]
                : "/home/whut/Desktop/GitHub/AccesoADatos/Ejemplo.txt";

        int numeroLinea = 1;
        int totalApariciones = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                int desde = 0;
                while (true) {
                    int idx = linea.indexOf(textoBuscar, desde);
                    if (idx == -1) break;
                    // columna 1-based
                    System.out.println("Línea " + numeroLinea + ", columna " + (idx + 1) + ": " + linea);
                    totalApariciones++;
                    // avanzar para permitir solapamientos
                    desde = idx + 1;
                }
                numeroLinea++;
            }
            System.out.println("Total apariciones: " + totalApariciones);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        } finally {
            System.out.println("Finalizando la búsqueda.");
        }
    }
}
