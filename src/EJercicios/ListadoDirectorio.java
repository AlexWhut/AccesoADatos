package EJercicios;
import java.io.File;
import java.util.Date;

public class ListadoDirectorio {
    // inicializacion de la app
    public static void main(String[] args) {
        
        // ruta a analizar que usaremos para el programa
        String ruta=".";
        if(args.length>=1) ruta=args[0];
        File fich=new File(ruta);


        // COn esto validamos si existe algun fichero o directorio
        if(!fich.exists()) {
            System.out.println("No existe el fichero o directorio ("+ruta+").");
            // de lo contrario que si existe entonces validamos si es un fichero o un directorio
        } else {
            if(fich.isFile()) {
                System.out.println(ruta+" es un fichero.");
            } else {
                System.out.println(ruta+" es un directorio. Contenidos: ");
                File[] ficheros=fich.listFiles();
                //Iteramos sobre el programa para especificar los datos del fichero o directorio
                for(File f: ficheros) {
                    String textoDescr= f.isDirectory() ? "/" : f.isFile() ? "_" : "?";

                    // Permisos en formato rwx
                    String permisos = (f.canRead() ? "r" : "-") +
                                      (f.canWrite() ? "w" : "-") +
                                      (f.canExecute() ? "x" : "-");

                    // Tamaño si es fichero
                    long tamanyo = f.isFile() ? f.length() : 0;

                    // Última modificación
                    Date fecha = new Date(f.lastModified());

                    // Mostrar todo
                    System.out.println("("+textoDescr+") " + f.getName() +
                                       " | Tamaño: " + tamanyo + " bytes" +
                                       " | Permisos: " + permisos +
                                       " | Última modif.: " + fecha);
                }
            }
        }
    }
}

