package Modelo;

/**
 * Representa un nodo en una tabla hash, que contiene un par clave-valor
 * y una referencia al siguiente nodo en la cadena para manejar colisiones.
 */
public class NodoHash {

    // La clave asociada con este nodo.
    int clave;
    
    // El valor asociado con este nodo.
    String valor;
    
    // El siguiente nodo en la cadena, utilizado para manejar colisiones.
    NodoHash siguiente;

    /**
     * Construye un nuevo NodoHash con la clave y el valor especificados.
     * Inicializa la referencia al siguiente nodo a null.
     * 
     * @param clave la clave asociada con este nodo
     * @param valor el valor asociado con este nodo
     */
    public NodoHash(int clave, String valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}
