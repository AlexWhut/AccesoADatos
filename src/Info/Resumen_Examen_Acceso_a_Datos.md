# 📘 Resumen Completo para Examen de Acceso a Datos

## 🧩 Tema 1: Introducción y Conceptos Básicos

### Almacenamiento primario vs secundario
- **Primario:** Memoria volátil (RAM), donde se ejecutan los programas.
- **Secundario:** Almacenamiento permanente (SSD, HDD), donde se guardan los ficheros.

### Persistencia de datos
- Los **datos persisten** cuando se mantienen almacenados incluso tras apagar el sistema.
- Permite guardar información importante de forma **válida y coherente**.

### API
- Una **API (Application Programming Interface)** es un conjunto de funciones que permiten a los programas comunicarse con otras aplicaciones o sistemas.
- Facilita el **acceso y manejo de datos** (guardar, recuperar, actualizar).

### Transacciones
- Una **transacción** agrupa varias operaciones que deben ejecutarse todas o ninguna.
- Garantiza coherencia e integridad de los datos.

### Sistema de ficheros
- Conjunto de estructuras que organiza cómo se **almacenan y acceden** los datos en un medio físico.

---

## ⚙️ Tema 2: Restricciones, Iteradores y Transacciones

### Restricciones de integridad
- Reglas que aseguran que los datos sean **válidos y consistentes**.
- Si se violan, el sistema **rechaza el cambio**.

### Iteradores
- Se usan para **recorrer colecciones o resultados** uno por uno, evitando cargar todo en memoria.

### Motivo para no cargar todos los datos a la vez
- Cargar todos los resultados **consume mucha memoria y recursos**, ralentizando el sistema.

### Transacción bancaria (ejemplo)
- Si al transferir dinero una operación falla, **todas las operaciones se revierten**.

---

## 🧱 Propiedades ACID

| Propiedad | Significado | Descripción |
|------------|--------------|-------------|
| **A** | Atomicidad | Todas las operaciones se realizan o ninguna. |
| **C** | Consistencia | Los datos deben seguir reglas y restricciones. |
| **I** | Aislamiento | Cada transacción se ejecuta de forma independiente. |
| **D** | Durabilidad | Los datos se mantienen incluso ante fallos del sistema. |

### Interbloqueo (Deadlock)
- Ocurre cuando **dos transacciones esperan recursos** entre sí. El sistema debe detectar y liberar una.

### Relacionales vs NoSQL
- **Relacionales:** Soportan ACID → seguras pero más lentas.
- **NoSQL:** Priorizan **velocidad y escalabilidad**, sacrificando ACID (usan BASE).

---

## 🗄️ Persistencia en Ficheros y Bases de Datos

### Limitaciones de los ficheros
- Difícil gestión de grandes volúmenes de datos.
- No garantizan integridad ni permiten consultas complejas.

### Desfase objeto-relacional
- Los objetos Java y las tablas SQL tienen **estructuras diferentes**, por eso se usan librerías (p.ej., GSON, Jackson).

### Lenguaje SQL
- Lenguaje estándar para **consultar, modificar y gestionar** bases de datos relacionales.
- Características: estructurado, declarativo y universal.

### Evolución de las bases de datos
- Soporte de **JSON, XML**, y **almacenamiento en la nube**.

### Big Data
- Se aborda con tecnologías **NoSQL y distribuidas**.

---

## 🧮 Bases de Datos de Objetos, XML y NoSQL

### BD de objetos
- Almacenan objetos directamente, pero **poco compatibles con otros sistemas**.

### BD XML nativas
- Almacenan y consultan directamente **documentos XML** sin convertirlos.

### NoSQL
- Enfocadas en **rapidez, escalabilidad y flexibilidad**.
- No cumplen totalmente con ACID, sino con **BASE** (Basically Available, Soft-state, Eventually consistent).

---

## 🗃️ Tema 3: Ficheros en Java

### Ficheros
- Un fichero es una **secuencia de bytes** almacenada en disco.

### Acceso a ficheros
- **Secuencial:** Lee o escribe en orden.
- **Aleatorio:** Permite acceder a cualquier parte del fichero.

### Codificaciones de texto
- **UTF-8:** Compatible con ASCII (los primeros 128 caracteres son iguales).

### Índices
- Mejoran la **búsqueda rápida** sin recorrer todo el fichero.

---

## 💻 Java y Ficheros

### Clase File
- Permite **consultar información** sobre ficheros y directorios, **crear y eliminar** archivos.

### Excepciones
- Errores en tiempo de ejecución que pueden **capturarse con try-catch** para evitar que el programa finalice abruptamente.

### Bloque finally
- Código que **siempre se ejecuta**, se usa para cerrar recursos (p.ej. cerrar ficheros).

### Try con recursos
- Cierra automáticamente los recursos que implementan **AutoCloseable** o **Closeable**.

---

## 🔁 Operaciones con Ficheros

### Buffering
- Guarda datos en memoria temporal para **acelerar lectura/escritura**.

### Operaciones básicas
1. Crear (`createNewFile()`)
2. Leer (`read()` / `readLine()`)
3. Escribir (`write()`)
4. Cerrar (`close()`)
5. Borrar (`delete()`)

### Flujos (Streams)
- **InputStream / OutputStream:** para datos binarios.  
- **Reader / Writer:** para texto.  
- `BufferedReader` y `BufferedWriter` permiten leer y escribir por líneas.

### Términos clave
- **Flushing:** vaciar el buffer al fichero.
- **Recodificación:** convertir texto entre distintas codificaciones.

### Clases comunes
| Operación | Clase Java |
|------------|-------------|
| Leer texto | FileReader / BufferedReader |
| Escribir texto | FileWriter / BufferedWriter |
| Acceso aleatorio | RandomAccessFile |

---

## ⚡ Acceso Aleatorio y Organización de Ficheros

### Limitaciones
- No se puede **insertar datos en medio** del fichero sin sobrescribir.
- Solución: **usar ficheros temporales**.

### Organización secuencial
- Se lee en orden. Ventaja: simple y consistente. Desventaja: lenta.

### Organización secuencial indexada
- Usa un índice para **buscar más rápido**, pero **ocupa más espacio** y requiere mantenimiento.

### RandomAccessFile
- Permite posicionarse con `seek()` en cualquier parte del fichero.

### Clave
- Campo o conjunto de campos que **identifica de forma única** a cada registro.

---

## 🧾 XML y JSON

### DOM vs SAX
| Parser | Descripción | Ventajas | Inconvenientes |
|--------|--------------|-----------|----------------|
| **DOM** | Carga todo el XML en memoria como árbol | Permite modificar y recorrer | Consume mucha memoria |
| **SAX** | Procesa evento a evento, no guarda todo | Rápido y eficiente | No permite modificar |

### Serialización y deserialización
- **Serialización:** convertir objetos en formato XML/JSON.
- **Deserialización:** convertir de XML/JSON a objetos Java.

### Validación XML
- Verifica que un documento cumple una estructura.
- Se hace con **DTD** o **XSD**.

### XPath
- Lenguaje para **navegar y consultar** partes de un XML.

### Librerías para JSON
- **Jackson** → clase principal: `ObjectMapper`  
- **GSON (Google)** → más simple, sin dependencias externas.

### XML bien formado
- Cumple reglas sintácticas: etiquetas cerradas, anidamiento correcto.

### DTD: símbolo ?
- El elemento **puede aparecer 0 o 1 vez**.

---

## 🧠 Recomendaciones Finales
- Aprende los **conceptos clave**: persistencia, ACID, buffering, flujos, DOM/SAX.
- Memoriza las clases principales de **Java I/O**.
- Revisa la diferencia entre **ACID vs BASE** y **SQL vs NoSQL**.
- Practica ejemplos con `BufferedReader`, `File`, y `RandomAccessFile`.

---
🟢 **Consejo final:** Si entiendes cómo se guardan, leen y validan los datos, ¡ya tienes el 80% del examen ganado! 💪
