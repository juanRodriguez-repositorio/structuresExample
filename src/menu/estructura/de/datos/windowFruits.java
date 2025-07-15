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
import java.net.URL;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack; 

public class windowFruits extends JFrame {

    // Paneles principales
    private JPanel topPanel;     // Zona 1: Secuencia objetivo
    private JPanel middlePanel;  // Zona 2: Frutas dispersas
    private JPanel bottomPanel;  // Zona 3: Pila visual y botones
    
    private Stack<String> pilaFrutas;      // Lógica LIFO
    private JPanel pilaVisual;             // Panel donde se ve la pila de frutas
    private JButton verificarBtn=new JButton("Verificar");
    private JButton reiniciarBtn=new JButton("Reiniciar");
    private JLabel resultadoLabel;
    private JPanel resultadoVisualPanel;
    private List<JButton> botonesFrutas = new ArrayList<>();
    private  Color backgroundColor=new Color(0, 180, 230);
    private Color hoverColor=new Color(0, 160, 200);
    private Color pressedColor=new Color(0, 130, 170);
    
    private List<String> fruitsInOrder = Arrays.asList(
     "manzana.png", "fresa.png", "naranja.png",
     "uva.png", "piña.png", "arandano.png"
    );
    // Constructor
    public windowFruits() {
        setTitle("Ordena las Frutas");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initTopPanel();      // Secuencia original
        initMiddlePanel();   // Zona de frutas dispersas
        initBottomPanel();   // Pila y botones
        verificarBtn.addActionListener(e -> {
         resultadoVisualPanel.removeAll(); // limpiar imágenes anteriores

         if (pilaFrutas.isEmpty()) {
          resultadoLabel.setText("Primero selecciona las frutas.");
          resultadoLabel.setForeground(Color.RED);
          resultadoVisualPanel.revalidate();
          resultadoVisualPanel.repaint();
          return;
         }

         if (pilaFrutas.size() < fruitsInOrder.size()) {
          resultadoLabel.setText("Te faltan frutas por seleccionar.");
          resultadoLabel.setForeground(Color.RED);
          resultadoVisualPanel.revalidate();
          resultadoVisualPanel.repaint();
          return;
         }

         boolean correcto = true;
         Stack<String> copia = new Stack<>();
         copia.addAll(pilaFrutas); // no modificamos la pila original

         for (int i = 0; i < fruitsInOrder.size(); i++) {
          String esperado = fruitsInOrder.get(i);
          String obtenido = copia.pop();
          
 
          // Mostrar imagen en orden desapilado
          URL url = getClass().getResource("/images/" + obtenido);
          if (url != null) {
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            resultadoVisualPanel.add(new JLabel(new ImageIcon(img)));
          }

          if (!esperado.equals(obtenido)) {
            correcto = false;
          }
         }

         resultadoVisualPanel.revalidate();
         resultadoVisualPanel.repaint();

         if (correcto) {
          resultadoLabel.setText("¡Ganaste!Orden correcto.");
          resultadoLabel.setForeground(new Color(0, 128, 0)); // verde
         } else {
          resultadoLabel.setText("Perdiste. El orden no coincide.");
          resultadoLabel.setForeground(Color.RED);
         }
        });
        
        reiniciarBtn.addActionListener(e -> {
          // 1. Limpiar la pila lógica y visual
          pilaFrutas.clear();
          pilaVisual.removeAll();
          pilaVisual.revalidate();
          pilaVisual.repaint();

          // 2. Reactivar todos los botones
          for (JButton boton : botonesFrutas) {
           boton.setEnabled(true);
          }

          // 3. Borrar mensaje y resultado visual
          resultadoLabel.setText(" ");
          resultadoVisualPanel.removeAll();
          resultadoVisualPanel.revalidate();
          resultadoVisualPanel.repaint();

        });

        setVisible(true);
    }

    // ===============================
    // 🟧 Zona 1: Secuencia a lograr
    // ===============================
    private void initTopPanel() {
        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600, 100));
        topPanel.setBackground(new Color(255, 245, 230)); // color suave
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        // Aquí luego se agregan las imágenes de frutas en orden
        topPanel.add(new JLabel("Secuencia a lograr:"));
        for (String fileName : fruitsInOrder) {
          ImageIcon icon = new ImageIcon(getClass().getResource("/images/"+fileName));
          // Escalar imagen a 64x64
          Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
          JLabel fruitLabel = new JLabel(new ImageIcon(img));
    
          topPanel.add(fruitLabel);
        }

        add(topPanel, BorderLayout.NORTH);
    }

    // ===============================
    // 🟨 Zona 2: Frutas en desorden
    // ===============================
   private void initMiddlePanel() {
    middlePanel = new JPanel(null);
    middlePanel.setPreferredSize(new Dimension(600, 350));
    middlePanel.setBackground(new Color(240, 255, 240));

    // Lista de frutas
    List<String> fruitsShuffled = new ArrayList<>(Arrays.asList(
        "manzana.png", "fresa.png", "uva.png",
        "naranja.png", "piña.png", "arandano.png"
    ));
    Collections.shuffle(fruitsShuffled); // desordena las frutas

    // 6 posiciones bien distribuidas
    Point[] posiciones = {
        new Point(50, 30),
        new Point(200, 30),
        new Point(350, 30),
        new Point(100, 150),
        new Point(250, 150),
        new Point(400, 150)
    };
    List<Point> posicionesDisponibles = new ArrayList<>(Arrays.asList(posiciones));
    Collections.shuffle(posicionesDisponibles); // para no tener patrones fijos

    for (int i = 0; i < fruitsShuffled.size(); i++) {
        String fileName = fruitsShuffled.get(i);
        Point p = posicionesDisponibles.get(i);

        URL url = getClass().getResource("/images/" + fileName);
        if (url == null) continue;

        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);

        JButton button = new JButton(new ImageIcon(img));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setName(fileName);
           // 🎯 Evento clic: agregar a la pila
        button.addActionListener(e -> {
         pilaFrutas.push(fileName);

         // Mostrar imagen en la pila visual
         JLabel label = new JLabel(new ImageIcon(img));
         pilaVisual.add(label);
         pilaVisual.revalidate();
         pilaVisual.repaint();

         // Evitar que se seleccione dos veces
         button.setEnabled(false);
        });
        button.setBounds(p.x, p.y, 64, 64);
        botonesFrutas.add(button); // 👈 agrega cada botón a la lista
        middlePanel.add(button);
     }

     add(middlePanel, BorderLayout.CENTER);
    }

    // ===============================
    // 🟩 Zona 3: Pila visual y botones
    // ===============================
    private void initBottomPanel() {
     bottomPanel = new JPanel();
     bottomPanel.setPreferredSize(new Dimension(600, 200));
     bottomPanel.setLayout(new BorderLayout());
     bottomPanel.setBackground(new Color(230, 240, 255)); // azul claro
 
     // 🥞 Pila lógica
     pilaFrutas = new Stack<>();

     // 🧱 Panel donde se apilan visualmente las frutas
     pilaVisual = new JPanel();
     pilaVisual.setLayout(new BoxLayout(pilaVisual, BoxLayout.Y_AXIS));
     pilaVisual.setBackground(Color.WHITE);

     // 🎯 Panel contenedor de la pila visual
     JPanel stackPanel = new JPanel(new BorderLayout());
     stackPanel.setPreferredSize(new Dimension(250, 200));
     stackPanel.setBorder(BorderFactory.createTitledBorder("Tu pila"));
     stackPanel.add(new JScrollPane(pilaVisual), BorderLayout.CENTER);

     bottomPanel.add(stackPanel, BorderLayout.WEST);
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 80));
     buttonPanel.add(verificarBtn);
     buttonPanel.add(reiniciarBtn);
     
     // Mensaje de resultado
     
     resultadoLabel = new JLabel(" ");
     resultadoLabel.setFont(new Font("Arial", Font.BOLD, 14));
     resultadoLabel.setHorizontalAlignment(SwingConstants.CENTER);
     resultadoLabel.setForeground(Color.DARK_GRAY);
     
     // 🖼️ Panel para mostrar la secuencia desapilada
     resultadoVisualPanel = new JPanel();
     resultadoVisualPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
     resultadoVisualPanel.setBackground(new Color(245, 245, 245));
     resultadoVisualPanel.setPreferredSize(new Dimension(300, 80));

     // Agrupamos mensaje + secuencia en un solo panel
     JPanel resultadoContainer = new JPanel();
     resultadoContainer.setLayout(new BorderLayout());
     resultadoContainer.add(resultadoLabel, BorderLayout.NORTH);
     resultadoContainer.add(resultadoVisualPanel, BorderLayout.CENTER);
     
     JPanel rightPanel = new JPanel();
     rightPanel.setLayout(new BorderLayout());
     rightPanel.add(resultadoContainer, BorderLayout.NORTH);
     rightPanel.add(buttonPanel, BorderLayout.CENTER);

     bottomPanel.add(rightPanel, BorderLayout.CENTER);
     add(bottomPanel, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(windowFruits::new);
    }
}

   

