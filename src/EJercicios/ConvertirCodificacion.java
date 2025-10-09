package EJercicios;

import java.io.*;

public class ConvertirCodificacion {
    public static void main(String[] args) {
        // Archivo de entrada (UTF-8)
        String archivoEntrada = "entrada_utf8.txt";

        // Archivos de salida
        String archivoISO = "salida_iso8859_1.txt";
        String archivoUTF16 = "salida_utf16.txt";

        try (
            // Leer el archivo UTF-8
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(archivoEntrada), "UTF-8")
            );

            // Escribir el archivo en ISO-8859-1
            BufferedWriter writerISO = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(archivoISO), "ISO-8859-1")
            );

            // Escribir el archivo en UTF-16
            BufferedWriter writerUTF16 = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(archivoUTF16), "UTF-16")
            );
        ) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                writerISO.write(linea);
                writerISO.newLine();

                writerUTF16.write(linea);
                writerUTF16.newLine();
            }

            System.out.println("Conversión completada correctamente.");
            System.out.println("Archivos generados:");
            System.out.println("- " + archivoISO);
            System.out.println("- " + archivoUTF16);

        } catch (IOException e) {
            System.out.println("Error al procesar los archivos: " + e.getMessage());
        }
    }
}
