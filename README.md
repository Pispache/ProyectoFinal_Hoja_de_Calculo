# Proyecto de Hoja de Cálculo y Tabla Hash

Este proyecto consiste en una aplicación que incluye una hoja de cálculo y una tabla hash, implementadas en Java. La hoja de cálculo permite la evaluación de fórmulas y la tabla hash gestiona la inserción, búsqueda y eliminación de pares clave-valor.

## Clases

### ControladorHashTable

**Descripción:**  
Esta clase controla la interfaz gráfica y la lógica de una tabla hash.

**Atributos:**
- `vista`: Instancia de `HojaCalculo` que representa la vista de la aplicación.
- `tablaHash`: Instancia de `TablaHash` que representa la estructura de datos de la tabla hash.
- `tamañoTabla`: Entero que almacena el tamaño de la tabla hash.
- `cantidadClaves`: Entero que almacena la cantidad de claves que se van a insertar en la tabla hash.

**Métodos:**
- `ControladorHashTable(HojaCalculo vista)`: Constructor que inicializa la vista y llama al método `inicializar`.
- `inicializar()`: Configura el ActionListener para el menú de la tabla hash.
- `solicitarDatosIniciales()`: Solicita al usuario el tamaño de la tabla hash y la cantidad de claves mediante cuadros de diálogo.
- `mostrarTablaHash()`: Muestra una ventana con campos para insertar claves y valores en la tabla hash.
- `insertarClavesYValores(JFrame frame, JTextField[] camposClave, JTextField[] camposValor)`: Inserta las claves y valores proporcionados por el usuario en la tabla hash y llama al método `mostrarTabla` de `TablaHash`.

### ControladorHojaCalculo

**Descripción:**  
Esta clase controla la lógica y la interfaz gráfica de una hoja de cálculo.

**Atributos:**
- `vista`: Instancia de `HojaCalculo` que representa la vista de la aplicación.
- `libro`: Instancia de `Libro` que contiene las hojas de cálculo.

**Métodos:**
- `ControladorHojaCalculo(HojaCalculo vista, Libro libro)`: Constructor que inicializa la vista y el libro, y llama al método `inicializar`.
- `inicializar()`: Configura el ActionListener para crear una nueva hoja.
- `crearNuevaHoja(int filas, int columnas)`: Crea una nueva hoja con el número especificado de filas y columnas, y configura la tabla y su comportamiento.
- `procesarFormula(JTable tabla, Hoja hoja, int fila, int columna, String formula)`: Procesa una fórmula en una celda específica.
- `evaluarFormula(String formula, Hoja hojaActual)`: Evalúa una fórmula, que puede ser una expresión matemática o una función.
- `evaluarFuncion(String formula, Hoja hojaActual)`: Evalúa una función (por ejemplo, `SUMA`).
- `evaluarSUMA(String parametros, Hoja hojaActual)`: Evalúa la función `SUMA` para los parámetros dados.
- `transformarFormula(String formula, Hoja hojaActual)`: Transforma una fórmula con referencias de celdas a valores numéricos.
- `evaluarExpresion(String expresion)`: Evalúa una expresión matemática en notación postfija.
- `convertirAPostfijo(String expresion)`: Convierte una expresión infija a notación postfija.
- `esNumero(String token)`: Verifica si un token es un número.
- `esOperador(char c)`: Verifica si un carácter es un operador.
- `precedencia(char operador)`: Devuelve la precedencia de un operador.
- `parsearFila(String referencia)`: Parsea la fila de una referencia de celda.
- `parsearColumna(String referencia)`: Parsea la columna de una referencia de celda.

### Celda

**Descripción:**  
Esta clase representa una celda en una hoja de cálculo.

**Atributos:**
- `fila`: Entero que representa la fila de la celda.
- `columna`: Entero que representa la columna de la celda.
- `valor`: String que almacena el valor de la celda.
- `arriba`, `abajo`, `izquierda`, `derecha`: Referencias a las celdas adyacentes.

**Métodos:**
- Métodos getter y setter para todos los atributos.

### Hoja

**Descripción:**  
Esta clase representa una hoja de cálculo que contiene un conjunto de celdas.

**Atributos:**
- `celdas`: Matriz de `Celda` que almacena las celdas de la hoja.
- `filas`: Entero que representa el número de filas.
- `columnas`: Entero que representa el número de columnas.
- `nombre`: String que almacena el nombre de la hoja.

**Métodos:**
- `Hoja(int filas, int columnas)`: Constructor que inicializa la matriz de celdas y llama a los métodos `inicializarCeldas` y `conectarCeldas`.
- `inicializarCeldas()`: Inicializa cada celda de la matriz.
- `conectarCeldas()`: Conecta las celdas adyacentes entre sí.
- `getCelda(int fila, int columna)`: Devuelve la celda en una posición específica.
- `setValorCelda(int fila, int columna, String valor)`: Establece el valor de una celda específica.
- `getValorCelda(int fila, int columna)`: Devuelve el valor de una celda específica.

### Libro

**Descripción:**  
Esta clase representa un libro de hojas de cálculo.

**Atributos:**
- `hojas`: Lista enlazada de `Hoja` que almacena las hojas del libro.

**Métodos:**
- `Libro()`: Constructor que inicializa la lista de hojas.
- `agregarHoja(Hoja hoja)`: Agrega una hoja al libro.
- `getHojas()`: Devuelve la lista de hojas.
- `getNumeroHojas()`: Devuelve el número de hojas en el libro.
- `getHoja(int i)`: Devuelve una hoja específica por índice.
- `getHojaPorNombre(String nombre)`: Devuelve una hoja específica por nombre.

### NodoHash

**Descripción:**  
Esta clase representa un nodo en una lista enlazada utilizada en la tabla hash.

**Atributos:**
- `clave`: Entero que almacena la clave del nodo.
- `valor`: String que almacena el valor del nodo.
- `siguiente`: Referencia al siguiente nodo en la lista.

**Métodos:**
- `NodoHash(int clave, String valor)`: Constructor que inicializa la clave, el valor y el siguiente nodo.

### TablaHash

**Descripción:**  
Esta clase representa una tabla hash con manejo de colisiones mediante listas enlazadas.

**Atributos:**
- `tabla`: Arreglo de `NodoHash` que almacena las listas enlazadas.
- `tamano`: Entero que representa el tamaño de la tabla hash.

**Métodos:**
- `TablaHash(int tamano)`: Constructor que inicializa la tabla con el tamaño especificado.
- `funcionHash(int clave)`: Calcula el índice de la clave utilizando la función hash.
- `insertar(int clave, String valor)`: Inserta un nuevo nodo en la tabla hash.
- `obtener(int clave)`: Devuelve el valor asociado a una clave específica.
- `eliminar(int clave)`: Elimina un nodo con una clave específica de la tabla hash.
- `mostrarTabla()`: Muestra el contenido de la tabla hash en la consola.

## Instrucciones para la Ejecución

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en tu IDE de Java preferido.
3. Compila y ejecuta el proyecto.
