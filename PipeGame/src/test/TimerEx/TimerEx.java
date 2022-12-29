package test.TimerEx;


import javax.swing.JFrame;
import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerEx extends JFrame {
    Container c = this.getContentPane();
    public TimerEx(){
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.fillRect(10, 10, 30, 30);
    }

    public static void main(String args[]){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date() + " : Executing the task from "
                        + Thread.currentThread().getName());
            }
        };
        long delay = 3000L;
        long period = 1000L;
        Timer timer = new Timer("timer");

        System.out.println(new Date() + " : Scheduling....");
        timer.schedule(task, delay, period);
    }

}
