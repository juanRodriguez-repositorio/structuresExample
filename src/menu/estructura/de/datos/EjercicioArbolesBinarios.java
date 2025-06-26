/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package menu.estructura.de.datos;

/**
 *
 * @author kamus
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EjercicioArbolesBinarios extends JFrame {
    private BinarySearchTree tree = new BinarySearchTree();
    private JTextField inputField;
    private JLabel outputLabel;
    private TreePanel treePanel;

    public EjercicioArbolesBinarios() {
        setTitle("Árbol Binario de Búsqueda");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initControls();
        initTreePanel();

        setVisible(true);
    }

    private void initControls() {
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);
        JButton insertButton = new JButton("Insertar");
        JButton searchButton = new JButton("Buscar");
        JButton deleteButton = new JButton("Eliminar");

        inputPanel.add(new JLabel("Valor:"));
        inputPanel.add(inputField);
        inputPanel.add(insertButton);
        inputPanel.add(searchButton);
        inputPanel.add(deleteButton);

        JPanel recorridoPanel = new JPanel();
        JButton inOrderButton = new JButton("InOrden");
        JButton preOrderButton = new JButton("PreOrden");
        JButton postOrderButton = new JButton("PostOrden");

        recorridoPanel.add(inOrderButton);
        recorridoPanel.add(preOrderButton);
        recorridoPanel.add(postOrderButton);

        topPanel.add(inputPanel);
        topPanel.add(recorridoPanel);
        add(topPanel, BorderLayout.NORTH);

        // Etiqueta para mostrar mensajes o recorridos
        outputLabel = new JLabel(" ");
        outputLabel.setFont(new Font("Arial", Font.BOLD, 14));
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        add(outputLabel, BorderLayout.SOUTH);

        // Listeners
        insertButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                tree.insert(value);
                outputLabel.setText("Insertado: " + value);
                treePanel.repaint();
            } catch (NumberFormatException ex) {
                outputLabel.setText("Entrada inválida.");
            }
        });

        searchButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                boolean found = tree.contains(value);
                outputLabel.setText(found ? "Encontrado: " + value : "No encontrado.");
            } catch (NumberFormatException ex) {
                outputLabel.setText("Entrada inválida.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(inputField.getText());
                tree.delete(value);
                outputLabel.setText("Eliminado: " + value);
                treePanel.repaint();
            } catch (NumberFormatException ex) {
                outputLabel.setText("Entrada inválida.");
            }
        });

        inOrderButton.addActionListener(e ->
            outputLabel.setText("InOrden: " + tree.inOrder().toString())
        );

        preOrderButton.addActionListener(e ->
            outputLabel.setText("PreOrden: " + tree.preOrder().toString())
        );

        postOrderButton.addActionListener(e ->
            outputLabel.setText("PostOrden: " + tree.postOrder().toString())
        );
    }

    private void initTreePanel() {
        treePanel = new TreePanel(tree);
        add(treePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EjercicioArbolesBinarios::new);
    }
}
