package main;

import gui.GameGrid;
import util.Game;
import util.Worker;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Ciprian on 27-May-16.
 */
public class GameOfLife {

    private static final int windowSize = 600;
    private static int nrWorkers = 10;
    private static Integer n;
    private static Integer m;
    private static GameGrid gameGrid;
    private static Game game;
    private static Worker[] workers;

    public static void main(String[] args) {
        while(n == null) {
            try {
                n = new Integer(JOptionPane.showInputDialog("Number of lines:"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        while(m == null) {
            try {
                m = new Integer(JOptionPane.showInputDialog("Number of columns:"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        nrWorkers = Math.min(10, n * m);

        drawGUI();
        game = new Game(n, m, gameGrid);
        try {
            createWorkers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (n <= m)
            frame.setSize(new Dimension(windowSize, (int) (windowSize * (1.0 * n / m))));
        else
            frame.setSize(new Dimension((int) (windowSize * (1.0 * m / n)), windowSize));
        gameGrid = new GameGrid(n, m);
        frame.add(gameGrid);
        frame.setVisible(true);
    }

    private static void createWorkers() throws InterruptedException {
        workers = new Worker[nrWorkers];
        for (int i = 0; i < nrWorkers; ++i)
            workers[i] = new Worker(i, n, m, game, nrWorkers);
        for (int i = 0; i < nrWorkers; ++i)
            workers[i].start();
        for (int i = 0; i < nrWorkers; ++i)
            workers[i].join();
    }
}
