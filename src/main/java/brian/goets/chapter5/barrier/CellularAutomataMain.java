package brian.goets.chapter5.barrier;

import java.util.concurrent.TimeUnit;

import brian.goets.chapter5.barrier.CellularAutomata.Board;

public class CellularAutomataMain {

    public static void main(String[] args) {
        CellularAutomata.Board board = new BoardImpl();
        CellularAutomata cellularAutomata = new CellularAutomata(board);
        cellularAutomata.start();
    }

    static class BoardImpl implements CellularAutomata.Board {

        @Override
        public int getMaxX() {
            return 0;
        }

        @Override
        public int getMaxY() {
            return 0;
        }

        @Override
        public int getValue(int x, int y) {
            return 0;
        }

        @Override
        public int setNewValue(int x, int y, int value) {
            return 0;
        }

        @Override
        public void commitNewValues() {
            System.out.println(String.format("[%s] main board sleep 3 sec", Thread.currentThread().getName()));
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean hasConverged() {
            return false;
        }

        @Override
        public void waitForConvergence() {

        }

        @Override
        public Board getSubBoard(int numPartitions, int index) {
            return new BoardImpl();
        }
    }
}
