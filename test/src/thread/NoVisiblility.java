package thread;

public class NoVisiblility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() { //读线程
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) { //主线程 
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}