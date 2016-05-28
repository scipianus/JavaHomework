package util;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Ciprian on 27-May-16.
 */

public class Worker extends Thread {

    private static CyclicBarrier barrier;
    private int id, n, m, start, end;
    private Game game;

    public Worker(int id, int n, int m, Game game, int nrWorkers) {
        this.id = id;
        this.n = n;
        this.m = m;
        this.game = game;

        this.start = ((n * m) / nrWorkers) * id;
        this.end = ((n * m) / nrWorkers) * (id + 1);
        if(id == nrWorkers - 1)
            this.end = n * m;

        if (barrier == null)
            barrier = new CyclicBarrier(nrWorkers);
    }

    public void run() {
        while(true) {
            try {
                game.startedFramePainting();
                for (int cell = start; cell < end; ++cell) {
                    int i = cell / m;
                    int j = cell % m;
                    int neighbours = game.countNeighbours(i, j);
                    if (game.isAlive(i, j)) {
                        if (neighbours <= 1 || neighbours >= 4) {
                            game.kill(i, j);
                        }
                    } else {
                        if(neighbours == 3)
                            game.create(i, j);
                    }
                }
                game.finishedFramePainting();
                barrier.await();
                for (int cell = start; cell < end; ++cell) {
                    int i = cell / m;
                    int j = cell % m;
                    game.drawCell(i, j);
                }
                barrier.await();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
