/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.TablaHash;
import Vista.HojaCalculo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para manejar la interacción entre la vista HojaCalculo y el modelo TablaHash.
 */
public class ControladorHashTable {
    private HojaCalculo vista;
    private TablaHash tablaHash;
    private int tamañoTabla;
    private int cantidadClaves;

    /**
     * Constructor del controlador que inicializa la vista.
     * @param vista La vista de la hoja de cálculo.
     */
    public ControladorHashTable(HojaCalculo vista) {
        this.vista = vista;
        inicializar();
    }

    /**
     * Inicializa los componentes de la vista y añade los listeners necesarios.
     */
    private void inicializar() {
        vista.getMenuTablaHash().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solicitarDatosIniciales();
            }
        });
    }

    /**
     * Solicita los datos iniciales (tamaño de la tabla hash y cantidad de claves) al usuario.
     */
    private void solicitarDatosIniciales() {
        String tamañoInput = JOptionPane.showInputDialog(vista, "Ingrese la cantidad de claves a utilizar:");
        if (tamañoInput != null && !tamañoInput.trim().isEmpty()) {
            try {
                tamañoTabla = Integer.parseInt(tamañoInput.trim());
                if (tamañoTabla > 0) {
                    tablaHash = new TablaHash(tamañoTabla);
                    String cantidadClavesInput = JOptionPane.showInputDialog(vista, "Ingrese el tamaño del cuadro:");
                    if (cantidadClavesInput != null && !cantidadClavesInput.trim().isEmpty()) {
                        try {
                            cantidadClaves = Integer.parseInt(cantidadClavesInput.trim());
                            if (cantidadClaves > 0) {
                                mostrarTablaHash();
                            } else {
                                JOptionPane.showMessageDialog(vista, "La cantidad de claves debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(vista, "Ingrese un número válido para la cantidad de claves.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "El tamaño de la tabla hash debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Ingrese un número válido para el tamaño de la tabla hash.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Muestra la interfaz para ingresar las claves y valores de la tabla hash.
     */
    private void mostrarTablaHash() {
        JFrame frame = new JFrame("Tabla Hash");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(cantidadClaves + 1, 2, 5, 5)); // cantidadClaves filas + encabezado, con márgenes
        JTextField[] camposClave = new JTextField[cantidadClaves];
        JTextField[] camposValor = new JTextField[cantidadClaves];

        // Encabezados
        panel.add(new JLabel("Clave"));
        panel.add(new JLabel("Valor"));

        for (int i = 0; i < cantidadClaves; i++) {
            camposClave[i] = new JTextField();
            camposValor[i] = new JTextField();
            panel.add(camposClave[i]);
            panel.add(camposValor[i]);
        }

        JButton botonInsertar = new JButton("Insertar");
        botonInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarClavesYValores(frame, camposClave, camposValor);
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(botonInsertar, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Inserta las claves y valores ingresados en la tabla hash.
     * @param frame El frame de la interfaz.
     * @param camposClave Los campos de texto para las claves.
     * @param camposValor Los campos de texto para los valores.
     */
    private void insertarClavesYValores(JFrame frame, JTextField[] camposClave, JTextField[] camposValor) {
        for (int i = 0; i < cantidadClaves; i++) {
            String claveText = camposClave[i].getText().trim();
            String valorText = camposValor[i].getText().trim();

            if (!claveText.isEmpty() && !valorText.isEmpty()) {
                try {
                    int clave = Integer.parseInt(claveText);
                    tablaHash.insertar(clave, valorText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Clave debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Salir del bucle si hay un error
                }
            }
        }
        tablaHash.mostrarTabla();
    }
}