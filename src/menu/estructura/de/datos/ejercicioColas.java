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
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ejercicioColas extends JFrame {
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;

    private List<Color> colorSequence;     // Secuencia original (FIFO)
    private Queue<Color> userQueue;        // Respuesta del usuario

    private JLabel resultadoLabel;
    private JButton verificarBtn, reiniciarBtn;

    private List<JButton> colorButtons;    // Botones de selección
    private JPanel colaVisualPanel;
    private boolean puedeSeleccionar = false;

    public ejercicioColas() {
        setTitle("Memoria de Colores - Cola FIFO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        colorSequence = new ArrayList<>();
        userQueue = new LinkedList<>();
        colorButtons = new ArrayList<>();

        initTopPanel();
        initMiddlePanel();
        initBottomPanel();

        setVisible(true);

        mostrarSecuenciaPorTiempo(); // ⏱️ Mostrar colores y ocultar después
    }

    // 🟥 Parte superior: muestra la secuencia original por unos segundos
    private void initTopPanel() {
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(700, 100));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createTitledBorder("Secuencia"));

        // Generar secuencia de 6 colores aleatorios
        List<Color> coloresDisponibles = Arrays.asList(
            Color.RED, Color.BLUE, Color.GREEN,
            Color.ORANGE, Color.MAGENTA, Color.YELLOW
        );
        Collections.shuffle(coloresDisponibles);
        colorSequence.clear();
        colorSequence.addAll(coloresDisponibles.subList(0, 6));

        for (Color c : colorSequence) {
            JPanel colorBox = new JPanel();
            colorBox.setPreferredSize(new Dimension(40, 40));
            colorBox.setBackground(c);
            colorBox.setBorder(new LineBorder(Color.DARK_GRAY));
            topPanel.add(colorBox);
        }

        add(topPanel, BorderLayout.NORTH);
    }

    // 🎯 Parte intermedia: botones para que el usuario seleccione colores
    private void initMiddlePanel() {
     middlePanel = new JPanel();
     middlePanel.setPreferredSize(new Dimension(700, 180));
     middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
     middlePanel.setBackground(new Color(240, 240, 255));

     // ⚠️ NO usar directamente colorSequence
     List<Color> coloresDesordenados = new ArrayList<>(colorSequence);
     Collections.shuffle(coloresDesordenados); // 🎲 los desordena

     for (Color c : coloresDesordenados) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.setBackground(c);
        button.setBorder(new LineBorder(Color.GRAY));
        button.addActionListener(e -> {
            if (!puedeSeleccionar) return;

            userQueue.add(c);
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY); 
            JPanel cuadro = new JPanel();
            cuadro.setPreferredSize(new Dimension(40, 40));
            cuadro.setBackground(c);
            cuadro.setBorder(new LineBorder(Color.DARK_GRAY));
            colaVisualPanel.add(cuadro);
            colaVisualPanel.revalidate();
            colaVisualPanel.repaint();
        });
        colorButtons.add(button);
        middlePanel.add(button);
     }

     add(middlePanel, BorderLayout.CENTER);
    }

    // ✅ Parte inferior: botón y resultado
    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(700, 100));
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        // Mensaje
        resultadoLabel = new JLabel(" ");
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultadoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(resultadoLabel, BorderLayout.NORTH);
        
        // 👁️ Cola visual del usuario
        colaVisualPanel = new JPanel();
        colaVisualPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        colaVisualPanel.setBackground(new Color(245, 245, 245));
        colaVisualPanel.setPreferredSize(new Dimension(700, 60));

        // Botones
        verificarBtn = new JButton("Verificar");
        reiniciarBtn = new JButton("Reiniciar");
        
        //eventos
        
        verificarBtn.addActionListener(e -> {
          if (userQueue.isEmpty()) {
           resultadoLabel.setText("Primero selecciona los colores.");
           resultadoLabel.setForeground(Color.RED);
           return;
          }

          if (userQueue.size() < colorSequence.size()) {
            resultadoLabel.setText("Te faltan colores por seleccionar.");
            resultadoLabel.setForeground(Color.RED);
            return;
          }

          boolean correcto = true;

          Iterator<Color> secuenciaOriginal = colorSequence.iterator();
          Iterator<Color> respuestaUsuario = userQueue.iterator();

          while (secuenciaOriginal.hasNext() && respuestaUsuario.hasNext()) {
           Color esperado = secuenciaOriginal.next();
           Color dado = respuestaUsuario.next();

            if (!esperado.equals(dado)) {
             correcto = false;
             break;
            }
          }

          if (correcto) {
            resultadoLabel.setText("¡Correcto! 🎉 Memoria impecable.");
            resultadoLabel.setForeground(new Color(0, 128, 0)); // verde oscuro
          } else {
            resultadoLabel.setText("Incorrecto 😢 Intenta de nuevo.");
            resultadoLabel.setForeground(Color.RED);
          }

          puedeSeleccionar = false; // ❌ Bloquea más clics después de verificar
        });
        
        reiniciarBtn.addActionListener(e -> {
          // 1. Limpiar estructuras lógicas
          userQueue.clear();
          colorButtons.clear(); // lista auxiliar para los botones

          // 2. Limpiar visualmente los paneles existentes
          remove(topPanel);
          remove(middlePanel);
          colaVisualPanel.removeAll();
          colaVisualPanel.revalidate();
          colaVisualPanel.repaint();

          resultadoLabel.setText(" ");
          puedeSeleccionar = false;

          // 3. Generar nueva secuencia
          List<Color> coloresDisponibles = Arrays.asList(
           Color.RED, Color.BLUE, Color.GREEN,
           Color.ORANGE, Color.MAGENTA, Color.YELLOW
          );
          Collections.shuffle(coloresDisponibles);
           colorSequence.clear();
           colorSequence.addAll(coloresDisponibles.subList(0, 6));

          // 4. Recrear paneles
          initTopPanel();     // nueva secuencia visible
          initMiddlePanel();  // nuevos botones desordenados

          revalidate();
          repaint();

          // 5. Mostrar por unos segundos y ocultar
         mostrarSecuenciaPorTiempo();
    });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(verificarBtn);
        buttonPanel.add(reiniciarBtn);
        
        // Agrupamos todo
        JPanel resultadoContainer = new JPanel(new BorderLayout());
        resultadoContainer.add(resultadoLabel, BorderLayout.NORTH);
        resultadoContainer.add(colaVisualPanel, BorderLayout.CENTER);
        resultadoContainer.add(buttonPanel, BorderLayout.SOUTH);
        
        bottomPanel.add(resultadoContainer, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ⏱️ Mostrar los colores por 4 segundos y luego ocultarlos
    private void mostrarSecuenciaPorTiempo() {
        puedeSeleccionar = false; // ❌ Bloquea interacciones

        Timer timer = new Timer(4000, e -> {
         topPanel.setVisible(false);   // Oculta la secuencia original
         puedeSeleccionar = true;      // ✅ Ahora sí puede hacer clic
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ejercicioColas::new);
    }
}
