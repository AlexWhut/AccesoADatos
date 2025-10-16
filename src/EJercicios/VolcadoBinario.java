package EJercicios;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class VolcadoBinario {

    // Método que hace el volcado a cualquier PrintStream
    public static void volcarArchivo(String nombreArchivo, PrintStream salida) throws IOException {
        try (FileInputStream fis = new FileInputStream(nombreArchivo)) {
            int byteLeido;
            int contador = 0;
            while ((byteLeido = fis.read()) != -1) {
                salida.printf("%02X ", byteLeido);
                contador++;
                if (contador % 16 == 0) {
                    salida.println();
                }
            }
            if (contador % 16 != 0) {
                salida.println();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java EJercicios.VolcadoBinario <archivo_entrada> <archivo_salida>");
            return;
        }

        String nombreArchivoEntrada = args[0];
        String nombreArchivoSalida = args[1];

        try (PrintStream ps = new PrintStream(new FileOutputStream(nombreArchivoSalida))) {
            volcarArchivo(nombreArchivoEntrada, ps);
            System.out.println("Volcado completado. Revisa el archivo: " + nombreArchivoSalida);
        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
