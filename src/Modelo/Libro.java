package Modelo;

import java.util.LinkedList;

public class Libro {

    // Atributos de la clase Libro
    private LinkedList<Hoja> hojas; // Lista enlazada que almacena las hojas del libro

    // Constructor que inicializa la lista de hojas
    public Libro() {
        hojas = new LinkedList<>(); // Inicialización de la lista de hojas
    }

    // Método para agregar una hoja al libro
    public void agregarHoja(Hoja hoja) {
        hojas.add(hoja); // Agrega una hoja al final de la lista
    }

    // Getter para obtener la lista de hojas
    public LinkedList<Hoja> getHojas() {
        return hojas; // Retorna la lista de hojas
    }

    // Método para obtener el número de hojas en el libro
    public int getNumeroHojas() {
        return hojas.size(); // Retorna el tamaño de la lista de hojas
    }

    // Método para obtener una hoja específica por índice
    public Hoja getHoja(int i) {
        if (i >= 0 && i < hojas.size()) {
            return hojas.get(i); // Retorna la hoja en la posición i si el índice es válido
        } else {
            return null; // Retorna null si el índice no es válido
        }
    }

    // Método para obtener una hoja por su nombre
    public Hoja getHojaPorNombre(String nombre) {
        try {
            for (Hoja hoja : hojas) {
                if (hoja.getNombre().equals(nombre)) {
                    return hoja; // Retorna la hoja si el nombre coincide
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la traza de la excepción en caso de error
        }
        return null; // Retorna null si no se encuentra ninguna hoja con el nombre dado
    }

}
