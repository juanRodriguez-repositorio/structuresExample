/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu.estructura.de.datos;

/**
 *
 * @author kamus
 */
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TreePanel extends JPanel {
    private BinarySearchTree tree;
    private final int NODE_RADIUS = 25;
    private final int X_GAP = 30;
    private final int Y_GAP = 60;

    public TreePanel(BinarySearchTree tree) {
        this.tree = tree;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree.getRoot() != null) {
            drawTree((Graphics2D) g, tree.getRoot(), getWidth() / 2, 40, getWidth() / 4);
        }
    }

    private void drawTree(Graphics2D g, TreeNode node, int x, int y, int xOffset) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        // Dibujar nodo
        g.setColor(Color.CYAN);
        g.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        String val = String.valueOf(node.value);
        int strWidth = g.getFontMetrics().stringWidth(val);
        g.drawString(val, x - strWidth / 2, y + 5);

        // Dibujar líneas e hijos
        if (node.left != null) {
            int childX = x - xOffset;
            int childY = y + Y_GAP;
            g.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawTree(g, node.left, childX, childY, xOffset / 2);
        }

        if (node.right != null) {
            int childX = x + xOffset;
            int childY = y + Y_GAP;
            g.drawLine(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            drawTree(g, node.right, childX, childY, xOffset / 2);
        }
    }
}
