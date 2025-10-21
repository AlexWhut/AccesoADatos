package XML_JSON; // corregido el nombre del paquete
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DOMParser {
    public static void main(String[] args) {
        // Depuración: imprimir working dir y contenido de algunas carpetas
        System.out.println("Working dir: " + new File(".").getAbsolutePath());
        File projSrc = new File("AccesoADatos\\src");
        System.out.println("AccesoADatos\\src exists: " + projSrc.exists());
        if (projSrc.exists()) {
            for (File f : projSrc.listFiles()) {
                System.out.println("  " + f.getName());
            }
        }
        
        String inputXml = (args.length > 0) ? args[0] : "Ejemplo.xml";
        // intentar localizar el fichero: ruta dada, algunas rutas conocidas y búsqueda recursiva
        File inputFile = new File(inputXml);
        if (!inputFile.exists()) {
            File cand1 = new File("src", inputXml);
            File cand2 = new File("AccesoADatos\\src", inputXml);
            File cand3 = new File("AccesoADatos\\src\\EJercicios", inputXml);
            if (cand1.exists()) {
                inputFile = cand1;
            } else if (cand2.exists()) {
                inputFile = cand2;
            } else if (cand3.exists()) {
                inputFile = cand3;
            } else {
                // búsqueda recursiva desde el directorio de trabajo
                File found = findFile(inputXml, new File("."));
                if (found != null) {
                    inputFile = found;
                } else {
                    System.err.println("Fichero no encontrado en (se intentó búsqueda recursiva):");
                    System.err.println("  " + new File(inputXml).getAbsolutePath());
                    System.err.println("  " + cand1.getAbsolutePath());
                    System.err.println("  " + cand2.getAbsolutePath());
                    System.err.println("  " + cand3.getAbsolutePath());
                    System.err.println("Ejecuta: java EJercicios.DOMParser <ruta_al_xml>");
                    return;
                }
            }
        }

        // Opción A: nombre fijo
        // String outName = "parsing_dom.txt";

        // Opción B: nombre con fecha/hora (habilitado)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String outName = "parsing_dom_" + sdf.format(new Date()) + ".txt";

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();

            try (PrintWriter pw = new PrintWriter(new FileWriter(outName, false))) {
                pw.println("Parseado de: " + inputXml);
                pw.println("Fecha: " + new Date());
                pw.println();

                Node root = doc.getDocumentElement();
                printNode(root, pw, 0);
            }

            System.out.println("Salida escrita en: " + outName);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, PrintWriter pw, int indent) {
        // compatibilidad Java 8: construir la sangría manualmente
        StringBuilder sbPad = new StringBuilder();
        for (int i = 0; i < Math.max(0, indent); i++) {
            sbPad.append("  ");
        }
        String pad = sbPad.toString();
        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                pw.print(pad + "<" + node.getNodeName());
                NamedNodeMap attrs = node.getAttributes();
                if (attrs != null) {
                    for (int i = 0; i < attrs.getLength(); i++) {
                        Node a = attrs.item(i);
                        pw.print(" " + a.getNodeName() + "=\"" + a.getNodeValue() + "\"");
                    }
                }
                pw.println(">");

                NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    printNode(children.item(i), pw, indent + 1);
                }

                pw.println(pad + "</" + node.getNodeName() + ">");
                break;

            case Node.TEXT_NODE:
                String text = node.getTextContent().trim();
                if (!text.isEmpty()) {
                    pw.println(pad + text);
                }
                break;

            case Node.COMMENT_NODE:
                pw.println(pad + "<!-- " + node.getNodeValue() + " -->");
                break;

            default:
                // otros tipos (CDATA_SECTION_NODE, etc.)
                break;
        }
    }

    // busca recursivamente un fichero por nombre comenzando en startDir (Java 8 compatible)
    private static File findFile(String name, File startDir) {
        if (startDir == null || !startDir.exists()) return null;
        File[] list = startDir.listFiles();
        if (list == null) return null;
        for (File f : list) {
            if (f.isDirectory()) {
                File found = findFile(name, f);
                if (found != null) return found;
            } else if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        return null;
    }
}