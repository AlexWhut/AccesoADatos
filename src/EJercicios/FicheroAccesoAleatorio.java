package EJercicios;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FicheroAccesoAleatorio {
    private RandomAccessFile raf;
    private int tamanoRegistro = 24; // 20 bytes para Nombre, 4 bytes para Edad

    public FicheroAccesoAleatorio(String nombreFichero) throws IOException {
        raf = new RandomAccessFile(nombreFichero, "rw");
    }

    public void insertar(int posicion, String nombre, int edad) throws IOException {
        raf.seek(posicion * tamanoRegistro);
        byte[] nombreBytes = new byte[20];
        byte[] nombreOriginal = nombre.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(nombreOriginal, 0, nombreBytes, 0, Math.min(nombreOriginal.length, 20));
        raf.write(nombreBytes);
        raf.writeInt(edad);
    }

    public String obtenValorCampo(int posicion, String campo) throws IOException {
        raf.seek(posicion * tamanoRegistro);
        if ("Nombre".equalsIgnoreCase(campo)) {
            byte[] nombreBytes = new byte[20];
            raf.readFully(nombreBytes);
            return new String(nombreBytes, StandardCharsets.UTF_8).trim();
        } else if ("Edad".equalsIgnoreCase(campo)) {
            raf.skipBytes(20);
            return String.valueOf(raf.readInt());
        } else {
            throw new IllegalArgumentException("Campo no válido: " + campo);
        }
    }

    public void close() throws IOException {
        raf.close();
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        try {
            FicheroAccesoAleatorio fichero = new FicheroAccesoAleatorio("registros.dat");
            fichero.insertar(0, "Ana", 25);
            fichero.insertar(1, "Luis", 30);
            fichero.insertar(4, "Maria", 40); // posición 4, deja huecos

            System.out.println("Nombre en posición 1: " + fichero.obtenValorCampo(1, "Nombre"));
            System.out.println("Edad en posición 1: " + fichero.obtenValorCampo(1, "Edad"));
            System.out.println("Nombre en posición 4: " + fichero.obtenValorCampo(4, "Nombre"));
            System.out.println("Edad en posición 4: " + fichero.obtenValorCampo(4, "Edad"));

            fichero.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}