package nyc.pipegame;

import javax.swing.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyTimer {
    public static boolean pause = false;
    double count = 0.0;
    MainGUI mainGUI;
    ScheduledThreadPoolExecutor executor;
    public MyTimer(MainGUI mainGUI){
        this.mainGUI = mainGUI;
    }

    public void startTimer() {
        count = 0.0;
        executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> {
            if (pause)
                return;
            upCount();
            SwingUtilities.invokeLater(() -> mainGUI.refreshTime(count));
        }, 0, 100, TimeUnit.MILLISECONDS);
    }
    public void stopTimer() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }


    public void pauseTimer(){
        pause = true;
        mainGUI.refreshTime(count);
    }
    public void continueTimer(){
        pause = false;
    }

    private void upCount() {
        count += 0.1;
    }
}
