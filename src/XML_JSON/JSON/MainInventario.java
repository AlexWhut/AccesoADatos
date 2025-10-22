package XML_JSON.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainInventario {
    public static void main(String[] args) {
        // 1. Crear productos de ejemplo
        Producto producto1 = new Producto("001", "Laptop", 1200.50, 10);
        Producto producto2 = new Producto("002", "Smartphone", 800.99, 20);
        Producto producto3 = new Producto("003", "Tablet", 300.00, 15);

        // 2. Crear inventario con los productos
        List<Producto> productos = Arrays.asList(producto1, producto2, producto3);
        Inventario inventario = new Inventario("Tienda Tech", productos);

        // 3. Serializar el inventario a JSON y guardarlo en un archivo
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formato legible (pretty print)

        try {
            // Serialización
            File archivoJson = new File("inventario.json");
            objectMapper.writeValue(archivoJson, inventario);
            System.out.println("Inventario guardado en inventario.json");

            // Deserialización
            Inventario inventarioDeserializado = objectMapper.readValue(archivoJson, Inventario.class);
            System.out.println("\nInventario deserializado:");
            System.out.println("Nombre de la tienda: " + inventarioDeserializado.getNombreTienda());
            System.out.println("Productos:");
            for (Producto producto : inventarioDeserializado.getProductos()) {
                System.out.println(producto);
            }

        } catch (IOException e) {
            System.err.println("Error al procesar el archivo JSON: " + e.getMessage());
        }
    }
}