/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package menu.estructura.de.datos;

/**
 *
 * @author kamus
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Menú de Ejercicios Estructuras de Datos");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Agrega botones de ejercicios y sus PDF
        addExercise("Juego de Pila (Frutas)", "ejercicioPilas.pdf", () -> new windowFruits());
        addExercise("Juego de Cola (Colores)", "ejercicioColas.pdf", () -> new ejercicioColas());
        addExercise("Lista Enlazada Visual", "ejercicioDeListasEnlazadas.pdf", () -> new EjercicioListasEnlazadas());
        addExercise("Memoria con Matriz", "ejercicioMatricesDinamicas.pdf", () -> new EjercicioMatricesDinamicas());
        addExercise("Árbol Binario de Búsqueda", "ejercicioArbolesBinarios.pdf", () -> new EjercicioArbolesBinarios());

        setVisible(true);
    }

    private void addExercise(String title, String pdfFileName, Runnable launchExercise) {
        JButton launchButton = new JButton(title);
        JButton pdfButton = new JButton("Ver PDF");

        launchButton.addActionListener(e -> launchExercise.run());

        pdfButton.addActionListener(e -> {
            try {
                File pdfFile = new File("pdfs/" + pdfFileName);
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Archivo no encontrado: " + pdfFileName);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al abrir el PDF.");
            }
        });

        add(launchButton);
        add(pdfButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
