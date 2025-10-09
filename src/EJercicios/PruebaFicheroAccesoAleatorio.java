package EJercicios;

import java.io.*;

public class PruebaFicheroAccesoAleatorio {
    public static void main(String[] args) {
        String nombreFichero = "registros.dat";
        int tamanoRegistro = 20; // Por ejemplo, cada registro ocupa 20 bytes

        try (RandomAccessFile raf = new RandomAccessFile(nombreFichero, "rw")) {
            // Escribimos 2 registros
            raf.seek(0);
            raf.writeUTF("Registro1");
            raf.seek(tamanoRegistro);
            raf.writeUTF("Registro2");

            // Intentamos escribir en la posición 5 (índice 4), que no existe aún
            int posicion = 4; // Quinto registro (índice base 0)
            raf.seek(posicion * tamanoRegistro);
            raf.writeUTF("Registro5");

            System.out.println("Registro escrito en posición mayor que el número de registros actuales.");

            // Mostramos el tamaño final del fichero
            System.out.println("Tamaño final del fichero: " + raf.length() + " bytes");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}