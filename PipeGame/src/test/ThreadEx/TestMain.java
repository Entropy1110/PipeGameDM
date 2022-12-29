package test.ThreadEx;

import javax.swing.*;
import java.awt.*;

public class TestMain {
    static int[][] pipeArr = {{1, 0, 0}, {1, 0, 0}, {0, 0, 1}};
    public static void main(String[] args){
        for (int i = 0; i < 3; i++){
            for (int k = 0; k < 3; k++){
                System.out.print(pipeArr[i][k]+ " ");
            }
            System.out.println();
        }
        rotateArr();
        for (int i = 0; i < 3; i++){
            for (int k = 0; k < 3; k++){
                System.out.print(pipeArr[i][k]+ " ");
            }
            System.out.println();
        }
    }
    private static void rotateArr() {
        int arr[][] = pipeArr.clone();
        for (int i = 0; i < 3; i++){
            pipeArr[i] = new int[]{arr[2][i], arr[1][i], arr[0][i]};
        }
    }
}
class MyFrame extends JFrame implements Runnable{
    JButton btn;
    static boolean flag = true;
    int count = 0;
    int sum = 0;
    Container c = this.getContentPane();
    JLabel label, label2;

    JPanel panel1;
    public MyFrame(){
        super("hi");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        init();
        this.setVisible(true);

    }

    private void init() {
        panel1 = new JPanel(new GridLayout(2, 2, 10, 10));
        label = new JLabel();
        label2 = new JLabel();
        panel1.add(label);
        panel1.add(label2);
        c.add(panel1);

        btn = new JButton("STOP");
        btn.addActionListener(e->{
            flag = false;
            MyThread.flag = false;
            btn.setEnabled(false);
        });
        panel1.add(btn);
    }

    @Override
    public void run(){

        while (flag) {
            label.setText("count = " + count);
            label2.setText("sum = " + sum);
        }

    }
}

class MyThread implements Runnable {
    MyFrame frame;
    static Boolean flag = true;
    public MyThread(MyFrame frame){
        this.frame = frame;
    }

    @Override
    public void run() {
        while(flag) {
            frame.count++;
            frame.sum += frame.count;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}