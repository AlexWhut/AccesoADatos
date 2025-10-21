package XML_JSON;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class AddClient {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Uso: java XML_JSON.AddClient <fichero.xml> <dni> <apellidos> <cp>");
            return;
        }

        String path = args[0];
        String dni = args[1];
        String apellidos = args[2];
        String cp = args[3];

        File xmlFile = new File(path);
        if (!xmlFile.exists()) {
            System.err.println("Fichero no encontrado: " + xmlFile.getAbsolutePath());
            return;
        }

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);

            // Verificar que el documento tiene un único elemento raíz llamado "clientes"
            Element root = doc.getDocumentElement();
            if (!"clientes".equals(root.getNodeName())) {
                System.err.println("Error: el documento no tiene un único elemento raíz llamado 'clientes'.");
                return;
            }

            // Crear el nuevo elemento <cliente> con sus hijos <DNI>, <apellidos>, <CP>
            Element newClient = doc.createElement("cliente");
            newClient.setAttribute("DNI", dni);

            Element eApellidos = doc.createElement("apellidos");
            eApellidos.setTextContent(apellidos);
            newClient.appendChild(eApellidos);

            Element eCP = doc.createElement("CP");
            eCP.setTextContent(cp);
            newClient.appendChild(eCP);

            // Insertar el nuevo cliente al principio de la lista de clientes
            Node firstClient = root.getFirstChild();
            root.insertBefore(newClient, firstClient);

            // Serializar el documento actualizado a la salida estándar
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (Exception e) {
            System.err.println("Error al procesar el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}