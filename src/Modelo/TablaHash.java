package Modelo;

/**
 * Esta clase representa una implementación de una tabla hash usando encadenamiento para manejar colisiones.
 * Cada entrada en la tabla es una lista enlazada de nodos (NodoHash).
 */
public class TablaHash {

    // Arreglo de NodoHash para almacenar los pares clave-valor
    private NodoHash[] tabla;

    // Tamaño de la tabla hash
    private int tamano;

    /**
     * Construye una nueva tabla hash con el tamaño especificado.
     * 
     * @param tamano el tamaño de la tabla hash
     */
    public TablaHash(int tamano) {
        this.tamano = tamano;
        tabla = new NodoHash[tamano];
    }

    /**
     * Calcula el valor hash para una clave dada.
     * 
     * @param clave la clave a hashear
     * @return el valor hash
     */
    private int funcionHash(int clave) {
        return clave % tamano;
    }

    /**
     * Inserta un par clave-valor en la tabla hash.
     * Si ocurre una colisión, el nuevo nodo se añade al final de la lista enlazada en el índice hasheado.
     * 
     * @param clave la clave a insertar
     * @param valor el valor asociado con la clave
     */
    public void insertar(int clave, String valor) {
        int indice = funcionHash(clave);
        NodoHash nuevoNodo = new NodoHash(clave, valor);
        if (tabla[indice] == null) {
            tabla[indice] = nuevoNodo;
        } else {
            NodoHash actual = tabla[indice];
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    /**
     * Recupera el valor asociado con la clave dada.
     * 
     * @param clave la clave a buscar
     * @return el valor asociado con la clave, o null si la clave no se encuentra
     */
    public String obtener(int clave) {
        int indice = funcionHash(clave);
        NodoHash actual = tabla[indice];
        while (actual != null) {
            if (actual.clave == clave) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    /**
     * Elimina el par clave-valor asociado con la clave dada de la tabla hash.
     * 
     * @param clave la clave a eliminar
     */
    public void eliminar(int clave) {
        int indice = funcionHash(clave);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;
        while (actual != null && actual.clave != clave) {
            anterior = actual;
            actual = actual.siguiente;
        }
        if (actual == null) {
            return;
        }
        if (anterior == null) {
            tabla[indice] = actual.siguiente;
        } else {
            anterior.siguiente = actual.siguiente;
        }
    }

    /**
     * Muestra el contenido de la tabla hash.
     * Se imprime cada índice y la correspondiente lista enlazada de nodos.
     */
    public void mostrarTabla() {
        for (int i = 0; i < tamano; i++) {
            System.out.print("Índice " + i + ": ");
            NodoHash actual = tabla[i];
            while (actual != null) {
                System.out.print("[" + actual.clave + ": " + actual.valor + "] -> ");
                actual = actual.siguiente;
            }
            System.out.println("null");
        }
    }
}
