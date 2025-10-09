package EJercicios;

import java.io.FileInputStream;
import java.io.IOException;

public class VolcadoBinario {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Por favor, indica el nombre del archivo binario como argumento.");
            return;
        }

        String nombreArchivo = args[0];

        try (FileInputStream fis = new FileInputStream(nombreArchivo)) {
            int byteLeido;
            int contador = 0;
            while ((byteLeido = fis.read()) != -1) {
                System.out.printf("%02X ", byteLeido);
                contador++;
                if (contador % 16 == 0) {
                    System.out.println();
                }
            }
            if (contador % 16 != 0) {
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }
}
