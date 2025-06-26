/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu.estructura.de.datos;

/**
 *
 * @author kamus
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;

public class EjercicioMatricesDinamicas extends JFrame {
    private static final int SIZE = 4;
    private JButton[][] botones = new JButton[SIZE][SIZE];
    private Color[][] colores = new Color[SIZE][SIZE];
    private boolean[][] encontrados = new boolean[SIZE][SIZE];

    private int intentos = 20;
    private JLabel intentosLabel;

    private JButton primero = null;
    private JButton segundo = null;

    private Timer ocultarTimer;

    private final Color COLOR_OCULTO = Color.LIGHT_GRAY;

    public EjercicioMatricesDinamicas() {
        setTitle("Juego de Memoria con Colores");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        generarColores();
        initUI();

        setVisible(true);
    }

    private void generarColores() {
        Color[] posibles = {
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE,
            Color.MAGENTA, Color.YELLOW, Color.CYAN, Color.PINK
        };

        List<Color> lista = new ArrayList<>();
        for (Color c : posibles) {
            lista.add(c);
            lista.add(c);
        }
        Collections.shuffle(lista);
        Iterator<Color> it = lista.iterator();

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                colores[i][j] = it.next();
    }

    private void initUI() {
        JPanel matrizPanel = new JPanel(new GridLayout(SIZE, SIZE, 10, 10));
        matrizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        matrizPanel.setBackground(new Color(220, 235, 250));

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton btn = new JButton();
                btn.setBackground(COLOR_OCULTO);
                btn.setOpaque(true);
                btn.setBorderPainted(false);
                int fila = i, col = j;

                btn.addActionListener(e -> manejarClic(btn, fila, col));
                botones[i][j] = btn;
                matrizPanel.add(btn);
            }
        }

        intentosLabel = new JLabel("Intentos restantes: " + intentos, SwingConstants.CENTER);
        intentosLabel.setFont(new Font("Arial", Font.BOLD, 18));
        intentosLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(matrizPanel, BorderLayout.CENTER);
        initBottomPanel();
    }
    private void initBottomPanel() {
     JPanel bottomPanel = new JPanel(new BorderLayout());
     bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

     // Etiqueta de intentos
     intentosLabel = new JLabel("Intentos restantes: " + intentos, SwingConstants.CENTER);
     intentosLabel.setFont(new Font("Arial", Font.BOLD, 18));
     bottomPanel.add(intentosLabel, BorderLayout.CENTER);

     // Botón de reinicio
     JButton resetButton = new JButton("Reiniciar");
     resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
     resetButton.addActionListener(e -> reiniciarJuego());
     bottomPanel.add(resetButton, BorderLayout.EAST);

     add(bottomPanel, BorderLayout.SOUTH);
    }
    private void reiniciarJuego() {
     dispose(); // Cierra esta ventana
     SwingUtilities.invokeLater(EjercicioMatricesDinamicas::new); // Lanza una nueva
    }

    private void manejarClic(JButton btn, int fila, int col) {
        if (encontrados[fila][col]) return;
        if (btn == primero || btn == segundo) return;
        if (ocultarTimer != null && ocultarTimer.isRunning()) return;

        btn.setBackground(colores[fila][col]);

        if (primero == null) {
            primero = btn;
        } else {
            segundo = btn;
            intentos--;
            intentosLabel.setText("Intentos restantes: " + intentos);

            Color c1 = obtenerColor(primero);
            Color c2 = obtenerColor(segundo);

            if (c1.equals(c2)) {
                marcarEncontrado(primero);
                marcarEncontrado(segundo);
                primero = null;
                segundo = null;

                if (juegoCompletado()) {
                    mostrarMensaje("¡Ganaste! 🎉");
                }
            } else {
                ocultarTimer = new Timer(700, e -> {
                    primero.setBackground(COLOR_OCULTO);
                    segundo.setBackground(COLOR_OCULTO);
                    primero = null;
                    segundo = null;
                    ocultarTimer.stop();
                });
                ocultarTimer.setRepeats(false);
                ocultarTimer.start();
            }

            if (intentos == 0 && !juegoCompletado()) {
                mostrarMensaje("¡Te quedaste sin intentos! 😢");
            }
        }
    }

    private Color obtenerColor(JButton btn) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (botones[i][j] == btn)
                    return colores[i][j];
        return null;
    }

    private void marcarEncontrado(JButton btn) {
        btn.setEnabled(false);
        btn.setVisible(false);

        // también actualiza la lógica
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (botones[i][j] == btn)
                    encontrados[i][j] = true;
    }

    private boolean juegoCompletado() {
        for (boolean[] fila : encontrados)
            for (boolean b : fila)
                if (!b) return false;
        return true;
    }

    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
        int opt = JOptionPane.showConfirmDialog(this, "¿Jugar otra vez?", "Reiniciar", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(EjercicioMatricesDinamicas::new);
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EjercicioMatricesDinamicas::new);
    }
}
