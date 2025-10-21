package XML_JSON;
// Parsing DOM y visualización de documento DOM generado

import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class DOMParser {

    // Constante para definir la cadena que se usará para la indentación en la salida.
    private static final String INDENT_NIVEL = "  ";

    /**
     * Método recursivo que recorre el árbol DOM y muestra información de cada nodo.
     * @param nodo El nodo actual a procesar.
     * @param nivel La profundidad actual en el árbol, usada para la indentación.
     * @param ps El stream de salida donde se imprimirá la información (ej. System.out).
     */
    public static void muestraNodo(Node nodo, int nivel, PrintStream ps) {
        // --- FILTRO DE NODOS DE TEXTO VACÍOS ---
        // Los parsers DOM a menudo crean nodos de texto para los espacios en blanco
        // y saltos de línea del fichero XML original. Este bloque los ignora
        // para mantener la salida limpia.
        if (nodo.getNodeType() == Node.TEXT_NODE) {
            String text = nodo.getNodeValue();
            // Si el texto, después de quitarle los espacios en blanco, está vacío,
            // no procesamos más este nodo.
            if (text.trim().length() == 0) {
                return;
            }
        }

        // --- INDENTACIÓN ---
        // Imprime una cadena de indentación por cada nivel de profundidad en el árbol.
        for (int i = 0; i < nivel; i++) {
            ps.print(INDENT_NIVEL);
        }

        // --- PROCESAMIENTO DEL NODO SEGÚN SU TIPO ---
        // En DOM, todo es un nodo (elementos, atributos, texto, etc.).
        // Usamos un switch para tratar cada tipo de nodo de forma diferente.
        switch (nodo.getNodeType()) {
            // Caso 1: El nodo es el documento completo (la raíz del árbol DOM).
            case Node.DOCUMENT_NODE:
                Document doc = (Document) nodo;
                ps.println("Documento DOM, versión: " + doc.getXmlVersion()
                        + ", codificación: " + doc.getXmlEncoding());
                break;

            // Caso 2: El nodo es un elemento o etiqueta XML (ej: <libro>).
            case Node.ELEMENT_NODE:
                ps.print("<" + nodo.getNodeName());
                // Obtenemos el mapa de atributos del elemento.
                NamedNodeMap listaAtr = nodo.getAttributes();
                // Recorremos e imprimimos cada atributo con su valor.
                for (int i = 0; i < listaAtr.getLength(); i++) {
                    Node atr = listaAtr.item(i);
                    ps.print(" @" + atr.getNodeName() + "[" + atr.getNodeValue() + "]");
                }
                ps.println(">");
                break;

            // Caso 3: El nodo es el contenido de texto dentro de un elemento.
            case Node.TEXT_NODE:
                ps.println(nodo.getNodeName() + "[" + nodo.getNodeValue() + "]");
                break;

            // Caso por defecto: Para cualquier otro tipo de nodo (comentarios, etc.).
            default:
                ps.println("(nodo de tipo: " + nodo.getNodeType() + ")");
        }

        // --- LLAMADA RECURSIVA PARA LOS NODOS HIJOS ---
        // Obtenemos la lista de todos los nodos hijos del nodo actual.
        NodeList nodosHijos = nodo.getChildNodes();
        // Recorremos la lista de hijos y llamamos a este mismo método para cada uno,
        // incrementando el nivel de profundidad. Así se recorre el árbol completo.
        for (int i = 0; i < nodosHijos.getLength(); i++) {
            muestraNodo(nodosHijos.item(i), nivel + 1, ps);
        }
    }

    /**
     * Método principal que inicia el proceso de parseo del XML.
     * @param args Argumentos de línea de comandos. Se espera que args[0] sea la ruta al fichero XML.
     */
    public static void main(String[] args) {
        String nomFich;
        // Comprueba si se ha proporcionado un nombre de fichero.
        if (args.length < 1) {
            System.out.println("Indicar por favor nombre de fichero");
            return;
        } else {
            nomFich = args[0];
        }

        // --- CONFIGURACIÓN DEL PARSER DOM ---
        // 1. Obtenemos una instancia de la fábrica de constructores de documentos.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // 2. Configuramos la fábrica para que el parser ignore los comentarios del XML.
        dbf.setIgnoringComments(true);

        // 3. Configuramos que ignore los espacios en blanco que no son significativos.
        //    Esto es muy útil para evitar nodos de texto vacíos.
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            // 4. Creamos un DocumentBuilder a partir de la fábrica configurada.
            DocumentBuilder db = dbf.newDocumentBuilder();

            // 5. El paso más importante: parseamos el fichero XML.
            //    Esto lee el fichero completo y lo carga en memoria como un árbol de objetos DOM.
            Document domDoc = db.parse(new File(nomFich));

            // 6. Iniciamos el recorrido y la visualización del árbol desde su raíz (el documento).
            muestraNodo(domDoc, 0, System.out);

            // Capturamos las excepciones más comunes durante el parseo.
        } catch (FileNotFoundException | ParserConfigurationException | SAXException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

