package nyc.pipegame;

import javax.swing.*;
import java.util.ArrayList;

public class GameEngine implements Runnable {
    private MainGUI mainGUI;
    private int[][] gameImageGrid = new int[12][12];
    private int level;
    private PipeNode[][] nodes = new PipeNode[4][4];
    public ArrayList<PipeNode> connectedPipes;

    public boolean gameOver;

    public GameEngine(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.level = mainGUI.level;
    }


    @Override
    public void run() {

            setNode();
            while (!gameOver) {
                try {
                    //refresh game status
                    for (int i = 0; i < 4; i++) {
                        for (int k = 0; k < 4; k++) {
                            GameImage pipe = mainGUI.pipeImages[i][k];
                            int[][] pipeArr = pipe.getPipeArr();
                            for (int p = 0; p < 3; p++) {
                                for (int q = 0; q < 3; q++) {
                                    gameImageGrid[3 * i + p][3 * k + q] = pipeArr[p][q];
                                }
                            }

                        }
                        //end
                    }
                    generateTree();
                    if (connectedPipes.size() == 16) {
                        gameOver = true;
                        mainGUI.timer.stopTimer();
                        String totalTime = mainGUI.timerLabel.getText();
                        JOptionPane.showMessageDialog(null, "게임 종료\n총 소요시간 : " + totalTime, "스테이지 클리어!", JOptionPane.INFORMATION_MESSAGE);
                        mainGUI.mainMenuFrame.setVisible(true);
                        mainGUI.mainMenuFrame.requestFocus();
                        mainGUI.setVisible(false);
                        return;
                    }

                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("종료");
                }
        }
    }


    private void setNode() {
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                GameImage pipe = mainGUI.pipeImages[i][k];
                int idx = pipe.getIdx();
                if (idx <= 3) {
                    nodes[i][k] = new PipeNode(k, i, pipe, 1);  //middle, not route
                } else if (idx <= 7) {
                    nodes[i][k] = new PipeNode(k, i, pipe, -1); //leaf
                } else {
                    nodes[i][k] = new PipeNode(k, i, pipe, 2);  //route
                }
            }
        }
    }

    public void generateTree() {


        PipeNode route = nodes[2][2];
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++){
                nodes[i][k].discolor();
            }

        }
        connectedPipes = new ArrayList<>();
        checkConnected();
        colorPipe(route);

        for (PipeNode pn : connectedPipes){
            pn.color();

        }
        for (int i = 0; i < 4; i++){
            for (int k = 0; k < 4; k++)
                mainGUI.pipeButton[i][k].setIcon(nodes[i][k].pipe.getImageIcon());
        }

        mainGUI.centerPanel.repaint();
    }

    private  void colorPipe(PipeNode route) {

        for (PipeNode pn : route.nodeList) {
            if (!connectedPipes.contains(pn)) {
                connectedPipes.add(pn);
                colorPipe(pn);
            }
        }

    }

    private  void checkConnected() {
        for (int i = 0; i < 4; i++){
            for (int k = 0; k < 4; k++){
                nodes[i][k].nodeList = new ArrayList<>();
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 3; k++) {

                if (gameImageGrid[3 * i + 1][3 * k + 2] * gameImageGrid[3 * i + 1][3 * k + 3] == 1) {
                    nodes[i][k].connectNode(nodes[i][k + 1]);
                    nodes[i][k + 1].connectNode(nodes[i][k]);
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 4; k++) {
                if (gameImageGrid[3 * i + 2][3 * k + 1] * gameImageGrid[3 * i + 3][3 * k + 1] == 1) {
                    nodes[i][k].connectNode(nodes[i + 1][k]);
                    nodes[i + 1][k].connectNode(nodes[i][k]);
                }
            }
        }
    }



}