package Modelo;

public class Hoja {

    // Atributos de la clase Hoja
    private Celda[][] celdas; // Matriz de celdas que representa la hoja de cálculo
    private int filas;        // Número de filas en la hoja
    private int columnas;     // Número de columnas en la hoja
    private String nombre;    // Nombre de la hoja

    // Constructor que inicializa la matriz de celdas y conecta las celdas entre sí
    public Hoja(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        celdas = new Celda[filas][columnas]; // Inicialización de la matriz de celdas
        inicializarCeldas();  // Llamada al método para crear las celdas
        conectarCeldas();     // Llamada al método para conectar las celdas entre sí
    }

    // Getters y setters para los atributos de la hoja
    public Celda[][] getCeldas() {
        return celdas;
    }

    public void setCeldas(Celda[][] celdas) {
        this.celdas = celdas;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método para inicializar las celdas en la matriz
    private void inicializarCeldas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda(i, j); // Creación de una nueva celda en la posición (i, j)
            }
        }
    }

    // Método para conectar las celdas adyacentes entre sí
    private void conectarCeldas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i > 0) {
                    celdas[i][j].setArriba(celdas[i - 1][j]); // Conectar celda actual con la de arriba
                }
                if (i < filas - 1) {
                    celdas[i][j].setAbajo(celdas[i + 1][j]); // Conectar celda actual con la de abajo
                }
                if (j > 0) {
                    celdas[i][j].setIzquierda(celdas[i][j - 1]); // Conectar celda actual con la de la izquierda
                }
                if (j < columnas - 1) {
                    celdas[i][j].setDerecha(celdas[i][j + 1]); // Conectar celda actual con la de la derecha
                }
            }
        }
    }

    // Método para obtener una celda específica en la posición (fila, columna)
    public Celda getCelda(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return celdas[fila][columna]; // Retorna la celda si la posición es válida
        } else {
            return null; // Retorna null si la posición no es válida
        }
    }

    // Método para establecer el valor de una celda específica
    public void setValorCelda(int fila, int columna, String valor) {
        Celda celda = getCelda(fila, columna);
        if (celda != null) {
            celda.setValor(valor); // Establece el valor si la celda es válida
        }
    }

    // Método para obtener el valor de una celda específica
    public String getValorCelda(int fila, int columna) {
        Celda celda = getCelda(fila, columna);
        return celda != null ? celda.getValor() : ""; // Retorna el valor si la celda es válida, de lo contrario retorna una cadena vacía
    }
}
