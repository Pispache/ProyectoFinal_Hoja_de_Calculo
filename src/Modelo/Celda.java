package Modelo;

public class Celda {

    // Atributos de la celda
    private int fila;        // Índice de fila de la celda
    private int columna;     // Índice de columna de la celda
    private String valor;    // Valor almacenado en la celda
    private Celda arriba;    // Referencia a la celda de arriba
    private Celda abajo;     // Referencia a la celda de abajo
    private Celda izquierda; // Referencia a la celda de la izquierda
    private Celda derecha;   // Referencia a la celda de la derecha

    // Constructor que inicializa fila y columna, y asigna un valor vacío a la celda
    public Celda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.valor = ""; // Inicialización del valor de la celda como cadena vacía
    }

    // Métodos getters y setters para los atributos de la celda

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Celda getArriba() {
        return arriba;
    }

    public void setArriba(Celda arriba) {
        this.arriba = arriba;
    }

    public Celda getAbajo() {
        return abajo;
    }

    public void setAbajo(Celda abajo) {
        this.abajo = abajo;
    }

    public Celda getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Celda izquierda) {
        this.izquierda = izquierda;
    }

    public Celda getDerecha() {
        return derecha;
    }

    public void setDerecha(Celda derecha) {
        this.derecha = derecha;
    }
}
