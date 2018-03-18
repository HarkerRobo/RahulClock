package rahulclock;

import java.util.function.Consumer;

public class Timer extends Thread {

    private int displayseconds = 0;
    private int secondssofar = 0;
    private Consumer<String> timeAcceptor;
    private long startTime;
    private boolean running = true;

    public Timer(int minutes, Consumer<String> acceptor) {
        displayseconds = minutes * 60;
        timeAcceptor = acceptor;
    }

    @Override
    public void run() {
        try {
            startTime = System.currentTimeMillis();
            while (running && displayseconds > 0) {
                timeAcceptor.accept(formatTime(displayseconds));
                secondssofar += 1;
                displayseconds -= 1;
                Thread.sleep(startTime + secondssofar * 1000 - System.currentTimeMillis());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void end() {
        running = false;
    }

    private static String formatTime(int totalsecs) {
        int mins = totalsecs / 60;
        int secs = totalsecs % 60;
        return String.format("%d:%02d", mins, secs);
    }

}
