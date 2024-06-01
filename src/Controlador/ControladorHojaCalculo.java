/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Hoja;
import Modelo.Libro;
import Vista.HojaCalculo;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controlador para manejar la interacción entre la vista HojaCalculo y el modelo Libro.
 */
public class ControladorHojaCalculo {

    private static final String MENSAJE_ERROR = "Error";
    private static final String VALOR_CELDA_POR_DEFECTO = "0";

    private HojaCalculo vista;
    private Libro libro;

    /**
     * Constructor del controlador que inicializa la vista y el modelo.
     * @param vista La vista de la hoja de cálculo.
     * @param libro El modelo del libro.
     */
    public ControladorHojaCalculo(HojaCalculo vista, Libro libro) {
        this.vista = vista;
        this.libro = libro;
        inicializar();
    }

    /**
     * Inicializa los componentes de la vista y añade los listeners necesarios.
     */
    private void inicializar() {
        vista.getMenuHojaNueva().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearNuevaHoja(10, 10); // Crear una nueva hoja de 10x10
            }
        });
    }

    /**
     * Crea una nueva hoja con el número de filas y columnas especificado, y la añade al libro.
     * @param filas El número de filas de la nueva hoja.
     * @param columnas El número de columnas de la nueva hoja.
     */
    private void crearNuevaHoja(int filas, int columnas) {
        Hoja hoja = new Hoja(filas, columnas);
        String nombreHoja = "Hoja" + (libro.getNumeroHojas() + 1);
        hoja.setNombre(nombreHoja);
        libro.agregarHoja(hoja);
        JPanel panelHoja = new JPanel();
        panelHoja.setLayout(new BoxLayout(panelHoja, BoxLayout.Y_AXIS));

        JTable tabla = new JTable(filas + 1, columnas + 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row != 0 && column != 0;
            }
        };

        for (int i = 1; i <= filas; i++) {
            tabla.setValueAt(String.valueOf(i), i, 0);
        }
        for (int j = 1; j <= columnas; j++) {
            tabla.setValueAt(String.valueOf((char) ('A' + j - 1)), 0, j);
        }

        tabla.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (row > 0 && column > 0) {
                    String valor = (String) tabla.getValueAt(row, column);
                    hoja.setValorCelda(row - 1, column - 1, valor);
                    if (valor != null && valor.startsWith("=")) {
                        procesarFormula(tabla, hoja, row - 1, column - 1, valor.substring(1));
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);
        panelHoja.add(scrollPane);
        vista.agregarHoja(nombreHoja, panelHoja);
    }

    /**
     * Procesa una fórmula ingresada en una celda.
     * @param tabla La tabla donde se ingresa la fórmula.
     * @param hoja La hoja que contiene la celda.
     * @param fila La fila de la celda.
     * @param columna La columna de la celda.
     * @param formula La fórmula ingresada.
     */
    private void procesarFormula(JTable tabla, Hoja hoja, int fila, int columna, String formula) {
        try {
            formula = formula.toUpperCase(); // Convertir la fórmula a mayúsculas

            String resultado = evaluarFormula(formula, hoja);

            tabla.setValueAt(resultado, fila + 1, columna + 1);
            hoja.setValorCelda(fila, columna, resultado);
        } catch (Exception e) {
            tabla.setValueAt(MENSAJE_ERROR, fila + 1, columna + 1);
            hoja.setValorCelda(fila, columna, MENSAJE_ERROR);
        }
    }

    /**
     * Evalúa una fórmula y devuelve el resultado.
     * @param formula La fórmula a evaluar.
     * @param hojaActual La hoja actual donde se evalúa la fórmula.
     * @return El resultado de la fórmula.
     */
    private String evaluarFormula(String formula, Hoja hojaActual) {
        try {
            if (formula.matches("^[A-Z]+\\(.*\\)$")) {
                return evaluarFuncion(formula, hojaActual);
            } else {
                String expresion = transformarFormula(formula, hojaActual);
                double resultado = evaluarExpresion(expresion);
                return Double.toString(resultado);
            }
        } catch (Exception e) {
            return MENSAJE_ERROR;
        }
    }

    /**
     * Evalúa una función en la fórmula.
     * @param formula La fórmula que contiene la función.
     * @param hojaActual La hoja actual.
     * @return El resultado de la función.
     */
    private String evaluarFuncion(String formula, Hoja hojaActual) {
        Pattern pattern = Pattern.compile("^([A-Z]+)\\((.*)\\)$");
        Matcher matcher = pattern.matcher(formula);

        if (matcher.matches()) {
            String funcion = matcher.group(1);
            String parametros = matcher.group(2);

            switch (funcion) {
                case "SUMA":
                    return evaluarSUMA(parametros, hojaActual);
                // Aquí se pueden añadir más funciones como PROMEDIO, MIN, MAX, etc.
                default:
                    return MENSAJE_ERROR;
            }
        }
        return MENSAJE_ERROR;
    }

    /**
     * Evalúa la función SUMA.
     * @param parametros Los parámetros de la función SUMA.
     * @param hojaActual La hoja actual.
     * @return El resultado de la función SUMA.
     */
    private String evaluarSUMA(String parametros, Hoja hojaActual) {
        String[] args = parametros.split(",");
        double suma = 0;

        for (String arg : args) {
            String expresion = transformarFormula(arg, hojaActual);
            suma += evaluarExpresion(expresion);
        }

        return Double.toString(suma);
    }

    /**
     * Transforma una fórmula para evaluar referencias a celdas.
     * @param formula La fórmula a transformar.
     * @param hojaActual La hoja actual.
     * @return La fórmula transformada.
     */
    private String transformarFormula(String formula, Hoja hojaActual) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("([A-Z]+\\d+)|([A-Z]+![A-Z]+\\d+)");
        Matcher matcher = pattern.matcher(formula);

        int lastPos = 0;
        while (matcher.find()) {
            sb.append(formula.substring(lastPos, matcher.start()));
            lastPos = matcher.end();

            String ref = matcher.group(0);
            String hojaRef = matcher.group(2) != null ? matcher.group(2).split("!")[0] : null;
            String celdaRef = matcher.group(2) != null ? matcher.group(2).split("!")[1] : matcher.group(1);

            if (hojaRef == null) {
                // Referencia a la misma hoja
                int fila = parsearFila(celdaRef);
                int columna = parsearColumna(celdaRef);
                String valorCelda = hojaActual.getValorCelda(fila, columna);
                if (valorCelda.isEmpty()) {
                    valorCelda = VALOR_CELDA_POR_DEFECTO; // Tratar celdas vacías como 0
                }
                sb.append(valorCelda);
            } else {
                // Referencia a otra hoja
                Hoja hoja = libro.getHojaPorNombre(hojaRef);
                if (hoja == null) {
                    sb.append(MENSAJE_ERROR);
                    continue;
                }
                int fila = parsearFila(celdaRef);
                int columna = parsearColumna(celdaRef);
                String valorCelda = hoja.getValorCelda(fila, columna);
                if (valorCelda.isEmpty()) {
                    valorCelda = VALOR_CELDA_POR_DEFECTO; // Tratar celdas vacías como 0
                }
                sb.append(valorCelda);
            }
        }
        sb.append(formula.substring(lastPos));
        return sb.toString();
    }

    /**
     * Evalúa una expresión aritmética en notación infija.
     * @param expresion La expresión aritmética.
     * @return El resultado de la evaluación.
     */
    private double evaluarExpresion(String expresion) {
        String[] tokens = convertirAPostfijo(expresion);
        Stack<Double> pila = new Stack<>();
        for (String token : tokens) {
            if (esNumero(token)) {
                pila.push(Double.parseDouble(token));
            } else {
                double b = pila.pop();
                double a = pila.pop();
                switch (token) {
                    case "+":
                        pila.push(a + b);
                        break;
                    case "-":
                        pila.push(a - b);
                        break;
                    case "*":
                        pila.push(a * b);
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("División por cero");
                        }
                        pila.push(a / b);
                        break;
                    case "^":
                        pila.push(Math.pow(a, b));
                        break;
                }
            }
        }
        return pila.pop();
    }

    /**
     * Verifica si un token es un número.
     * @param token El token a verificar.
     * @return true si el token es un número, false en caso contrario.
     */
    private boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Convierte una expresión en notación infija a notación postfija.
     * @param expresion La expresión en notación infija.
     * @return Un arreglo de tokens en notación postfija.
     */
    private String[] convertirAPostfijo(String expresion) {
        StringBuilder sb = new StringBuilder();
        Stack<Character> pila = new Stack<>();
        boolean ultimoEsNumero = false;  // Para controlar la concatenación de números

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            
            if (Character.isDigit(c) || c == '.') {
                sb.append(c);
                ultimoEsNumero = true;
            } else {
                if (ultimoEsNumero) {
                    sb.append(' ');  // Añadir espacio después de un número
                    ultimoEsNumero = false;
                }
                if (c == '(') {
                    pila.push(c);
                } else if (c == ')') {
                    while (!pila.isEmpty() && pila.peek() != '(') {
                        sb.append(pila.pop()).append(' ');
                    }
                    pila.pop();
                } else {
                    while (!pila.isEmpty() && precedencia(c) <= precedencia(pila.peek())) {
                        sb.append(pila.pop()).append(' ');
                    }
                    pila.push(c);
                }
            }
        }

        if (ultimoEsNumero) {
            sb.append(' ');  // Añadir espacio si el último carácter es un número
        }
        
        while (!pila.isEmpty()) {
            sb.append(pila.pop()).append(' ');
        }

        return sb.toString().trim().split("\\s+");
    }

    /**
     * Verifica si un carácter es un operador.
     * @param c El carácter a verificar.
     * @return true si el carácter es un operador, false en caso contrario.
     */
    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /**
     * Determina la precedencia de un operador.
     * @param operador El operador cuya precedencia se desea determinar.
     * @return La precedencia del operador.
     */
    private int precedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    /**
     * Convierte una referencia de celda a un índice de fila.
     * @param referencia La referencia de celda (por ejemplo, "A1").
     * @return El índice de la fila (0-based).
     */
    private int parsearFila(String referencia) {
        return Integer.parseInt(referencia.replaceAll("[^\\d]", "")) - 1;
    }

    /**
     * Convierte una referencia de celda a un índice de columna.
     * @param referencia La referencia de celda (por ejemplo, "A1").
     * @return El índice de la columna (0-based).
     */
    private int parsearColumna(String referencia) {
        return referencia.replaceAll("[^A-Z]", "").charAt(0) - 'A';
    }
}