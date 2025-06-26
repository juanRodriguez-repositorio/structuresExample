/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu.estructura.de.datos;

/**
 *
 * @author kamus
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class EjercicioListasEnlazadas extends JFrame {
    private LinkedList<String> lista;
    private JPanel visualPanel;
    private JTextField inputField;
    private JTextField posField; 

    public EjercicioListasEnlazadas() {
        setTitle("Lista Enlazada Visual");
        setSize(800, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        lista = new LinkedList<>();

        initTopPanel();
        initBottomPanel();

        setVisible(true);
    }

    private void initTopPanel() {
        visualPanel = new JPanel();
        visualPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setBorder(BorderFactory.createTitledBorder("Visualización de la lista"));

        add(visualPanel, BorderLayout.CENTER);
    }

    private void initBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout());

        inputField = new JTextField(10);

        JButton addFirstBtn = new JButton("Agregar al inicio");
        JButton addLastBtn = new JButton("Agregar al final");
        JButton removeFirstBtn = new JButton("Eliminar del inicio");
        JButton removeLastBtn = new JButton("Eliminar del final");
        posField = new JTextField(3);
        JButton addAtPosBtn = new JButton("Agregar en posición");
        JButton removeByValueBtn = new JButton("Eliminar por valor");
        
        // Lógica de agregar en posición
        addAtPosBtn.addActionListener(e -> {
          String value = inputField.getText().trim();
          String posText = posField.getText().trim();

          if (!value.isEmpty() && !posText.isEmpty()) {
            try {
              int index = Integer.parseInt(posText);
              if (index < 0 || index > lista.size()) {
                JOptionPane.showMessageDialog(this, "Índice fuera de rango.");
                return;
              }
              lista.add(index, value);
              inputField.setText("");
              posField.setText("");
              actualizarVisualizacion();
            } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(this, "Índice inválido.");
            }
           }
        });

        // Lógica de eliminar por valor
        removeByValueBtn.addActionListener(e -> {
          String value = inputField.getText().trim();
          if (!value.isEmpty()) {
           boolean eliminado = lista.remove(value);
           if (!eliminado) {
            JOptionPane.showMessageDialog(this, "Valor no encontrado.");
           } else {
            actualizarVisualizacion();
           }
           inputField.setText("");
          }
        });

        addFirstBtn.addActionListener(e -> {
            String value = inputField.getText().trim();
            if (!value.isEmpty()) {
                lista.addFirst(value);
                inputField.setText("");
                actualizarVisualizacion();
            }
        });

        addLastBtn.addActionListener(e -> {
            String value = inputField.getText().trim();
            if (!value.isEmpty()) {
                lista.addLast(value);
                inputField.setText("");
                actualizarVisualizacion();
            }
        });

        removeFirstBtn.addActionListener(e -> {
            if (!lista.isEmpty()) {
                lista.removeFirst();
                actualizarVisualizacion();
            }
        });

        removeLastBtn.addActionListener(e -> {
            if (!lista.isEmpty()) {
                lista.removeLast();
                actualizarVisualizacion();
            }
        });
        
        bottomPanel.add(new JLabel("Pos:"));
        bottomPanel.add(posField);
        bottomPanel.add(addAtPosBtn);
        bottomPanel.add(removeByValueBtn);
        bottomPanel.add(new JLabel("Valor:"));
        bottomPanel.add(inputField);
        bottomPanel.add(addFirstBtn);
        bottomPanel.add(addLastBtn);
        bottomPanel.add(removeFirstBtn);
        bottomPanel.add(removeLastBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void actualizarVisualizacion() {
        visualPanel.removeAll();

        for (String elemento : lista) {
            JLabel nodo = new JLabel("[" + elemento + "]");
            nodo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            nodo.setOpaque(true);
            nodo.setBackground(new Color(200, 230, 255));
            nodo.setPreferredSize(new Dimension(60, 30));
            nodo.setHorizontalAlignment(SwingConstants.CENTER);
            visualPanel.add(nodo);

            JLabel flecha = new JLabel("→");
            visualPanel.add(flecha);
        }

        // Agrega "null" al final para mostrar el fin de la lista
        visualPanel.add(new JLabel("null"));

        visualPanel.revalidate();
        visualPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EjercicioListasEnlazadas::new);
    }
}
