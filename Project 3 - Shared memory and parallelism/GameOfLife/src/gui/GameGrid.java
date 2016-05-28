package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ciprian on 27-May-16.
 */
public class GameGrid extends JPanel {

    private JButton[][] buttons;

    public GameGrid(int n, int m) {
        setLayout(new java.awt.GridLayout(n, m));
        buttons = new JButton[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                JButton b = new JButton();
                b.setBackground(Color.black);
                b.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        //...
                    }
                });
                add(b);
                buttons[i][j] = b;
            }
        }
    }

    public void updateCell(int i, int j, boolean flag) {
        if (flag)
            buttons[i][j].setBackground(Color.white);
        else
            buttons[i][j].setBackground(Color.black);
    }
}
