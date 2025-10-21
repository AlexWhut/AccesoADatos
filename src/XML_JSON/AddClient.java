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

            // Comprobar que hay un único elemento al nivel del documento
            NodeList topChildren = doc.getChildNodes();
            int elemCount = 0;
            for (int i = 0; i < topChildren.getLength(); i++) {
                if (topChildren.item(i).getNodeType() == Node.ELEMENT_NODE) elemCount++;
            }
            if (elemCount != 1) {
                System.err.println("Error: el documento debe tener exactamente un elemento raíz.");
                return;
            }

            Element root = doc.getDocumentElement();

            // Comprobar que el elemento raíz contiene al menos un elemento hijo (clientes existentes)
            NodeList rootChildren = root.getChildNodes();
            boolean hasClientElement = false;
            Element firstClientElement = null;
            for (int i = 0; i < rootChildren.getLength(); i++) {
                Node n = rootChildren.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    if (!hasClientElement) {
                        firstClientElement = (Element) n;
                    }
                    hasClientElement = true;
                }
            }
            if (!hasClientElement) {
                System.err.println("Error: el elemento raíz no contiene elementos de cliente en el nivel superior.");
                return;
            }

            // Crear el nuevo elemento <cliente> con <dni>, <apellidos>, <cp>
            Element newClient = doc.createElement("cliente");

            Element eDni = doc.createElement("dni");
            eDni.setTextContent(dni);
            newClient.appendChild(eDni);

            Element eAp = doc.createElement("apellidos");
            eAp.setTextContent(apellidos);
            newClient.appendChild(eAp);

            Element eCp = doc.createElement("cp");
            eCp.setTextContent(cp);
            newClient.appendChild(eCp);

            // Insertar antes del primer cliente existente
            root.insertBefore(newClient, firstClientElement);

            // Serializar a System.out con Transformer (formateado)
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // intentos para establecer cantidad de indentación (según implementación)
            try {
                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            } catch (IllegalArgumentException ignored) {}
            try {
                t.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            } catch (IllegalArgumentException ignored) {}

            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(System.out);
            t.transform(src, res);

        } catch (Exception e) {
            System.err.println("Error al procesar el XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}