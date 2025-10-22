package XML_JSON.JSON;

import java.util.List;

public class Inventario {
    private String nombreTienda;
    private List<Producto> productos;

    // Constructor
    public Inventario(String nombreTienda, List<Producto> productos) {
        this.nombreTienda = nombreTienda;
        this.productos = productos;
    }

    // Getters y Setters
    public String getNombreTienda() {
        return nombreTienda;
    }

    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "nombreTienda='" + nombreTienda + '\'' +
                ", productos=" + productos +
                '}';
    }
}
