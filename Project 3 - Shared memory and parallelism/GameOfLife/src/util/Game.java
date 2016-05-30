package util;

import gui.GameGrid;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ciprian on 27-May-16.
 */
public class Game {
    private int n, m, currentFrame, nextFrame;
    private boolean[][][] matrix;
    private GameGrid gameGrid;
    private AtomicInteger workingPainters;

    public Game(int n, int m, GameGrid gameGrid) {
        this.n = n;
        this.m = m;
        this.gameGrid = gameGrid;
        initializeMatrix();
        currentFrame = 0;
        nextFrame = 1;
        workingPainters = new AtomicInteger(0);
    }

    private void initializeMatrix() {
        Random random = new Random();
        matrix = new boolean[2][n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                matrix[0][i][j] = matrix[1][i][j] = random.nextBoolean();
                drawCell(i, j);
            }
        }
    }

    public int countNeighbours(int x, int y) {
        int neighbours = 0;
        for (int i = x - 1; i <= x + 1; ++i) {
            for (int j = y - 1; j <= y + 1; ++j) {
                if (i < 0 || i >= n || j < 0 || j >= m)
                    continue;
                if (i == x && j == y)
                    continue;
                if (matrix[currentFrame][i][j])
                    neighbours++;
            }
        }
        return neighbours;
    }

    public boolean isAlive(int i, int j) {
        return matrix[currentFrame][i][j];
    }

    public void kill(int i, int j) {
        matrix[nextFrame][i][j] = false;
    }

    public void create(int i, int j) {
        matrix[nextFrame][i][j] = true;
    }

    public void drawCell(int i, int j) {
        gameGrid.updateCell(i, j, matrix[currentFrame][i][j]);
        matrix[nextFrame][i][j] = matrix[currentFrame][i][j];
    }

    public void startedFramePainting()
    {
        workingPainters.incrementAndGet();
    }

    public void finishedFramePainting()
    {
        int value = workingPainters.decrementAndGet();
        if(value == 0)
        {
            int aux = currentFrame;
            currentFrame = nextFrame;
            nextFrame = aux;
        }
    }
}
