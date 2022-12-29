package nyc.pipegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.Font.BOLD;

public class MainGUI extends JFrame {
    static enum Pipe {
        PIPE_1,
        PIPE_2,
        PIPE_3,
        PIPE_4,
        PIPE_1_COLORED,
        PIPE_2_COLORED,
        PIPE_3_COLORED,
        PIPE_4_COLORED,
        START_1,
        START_2,
        START_3
    }

    JButton[][] pipeButton;
    GameImage[][] pipeImages;
    Container c = getContentPane();
    JButton pauseBtn;
    MyTimer timer;
    JLabel timerLabel;
    public CardLayout cardLayout = new CardLayout();
    private JPanel gamePanel = new JPanel(new BorderLayout());
    public JPanel mainPanel = new JPanel(cardLayout);
    public int level;
    MainMenuFrame mainMenuFrame;
    JPanel centerPanel;
    GameEngine engine;
    Thread engineThread;

    final int[][] level1_pipes = {
            {0, 0, 3, 0},
            {2, 3, 2, 3},
            {0, 3, 9, 0},
            {0, 1, 2, 3}
    };

    final int[][] level2_pipes = {
            {3, 0, 0, 0},
            {3, 2, 2, 3},
            {0, 2, 9, 3},
            {0, 1, 3, 0}
    };

    final int[][] level3_pipes = {
            {0, 0, 2, 3},
            {2, 0, 0, 1},
            {3, 1, 9, 2},
            {0, 1, 3, 0}
    };

    final int[][] level4_pipes = {
            {3, 1, 2, 0},
            {0, 0, 2, 3},
            {0, 2, 9, 0},
            {3, 3, 3, 0}
    };

    final int[][] level5_pipes = {
            {0, 3, 3, 0},
            {3, 2, 2, 0},
            {3, 0, 9, 2},
            {0, 1, 3, 0}
    };

    public MainGUI() {
        super();
        mainMenuFrame = new MainMenuFrame(this);
        GameImageManager.mainGUI = this;

        setLayout(cardLayout);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);

        init();
        PauseMenuDialog pauseMenuDialog = new PauseMenuDialog(this);

        mainPanel.add(pauseMenuDialog, "pauseMenu");
        c.add(mainPanel);
        this.setVisible(false);
    }

    public void init() {
        gamePanel = new JPanel(new BorderLayout());
        mainPanel.add(gamePanel, "GameScreen");

        cardLayout.show(mainPanel, "GameScreen");
        engine = new GameEngine(this);
        engineThread = new Thread(engine);
        timer = new MyTimer(this);
        initNorth();
        initCenter();
    }

    public void initLevel(int level) {
        this.level = level;

        init();
        switch (level) {
            case 1:
                levelStart(level1_pipes);
                break;
            case 2:
                levelStart(level2_pipes);
                break;
            case 3:
                levelStart(level3_pipes);
                break;
            case 4:
                levelStart(level4_pipes);
                break;
            case 5:
                levelStart(level5_pipes);
                break;
        }
    }

    private void levelStart(int[][] levelPipes) {



        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < 4; k++) {
                pipeButton[i][k] = new JButton();
                GameImage gameImage = GameImageManager.getGameImage(levelPipes[i][k]);
                pipeImages[i][k] = gameImage;
                JButton button = pipeButton[i][k];
                button.setIcon(gameImage.getImageIcon());
                pipeButton[i][k].addActionListener(e -> {
                    gameImage.rotate();
                    gameImage.rotateArr();
                    button.setIcon(gameImage.getImageIcon());
                    centerPanel.repaint();


                });
                centerPanel.add(button);
            }

        }
        gamePanel.add(centerPanel, BorderLayout.CENTER);
        engineThread.start();
        timer.startTimer();
        timer.continueTimer();
    }




    private void initCenter() { //게임 진행 화면
        pipeButton = new JButton[4][4];
        pipeImages = new GameImage[4][4];
        centerPanel = new JPanel(new GridLayout(4, 4, 0, 0));

    }

    private void initNorth() {  //소요시간, 메뉴로 돌아가기, 일시정지
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pauseBtn = new JButton("일시정지");
        timerLabel = new JLabel("");


        pauseBtn.addActionListener(e -> {
            timer.pauseTimer();
            cardLayout.show(mainPanel, "pauseMenu");
        });
        northPanel.add(pauseBtn);
        northPanel.add(timerLabel);
        this.gamePanel.add(northPanel, BorderLayout.NORTH);
    }



    public void refreshTime(double count) {
        String countStr = String.format("%.1f", count);
        timerLabel.setText(countStr + "초");
    }


}

class PauseMenuDialog extends JPanel {
    MainGUI mainGUI;
    JLabel label = new JLabel("일시정지");
    JButton backBtn = new JButton("메뉴화면");
    JButton continueBtn = new JButton("계속하기");

    public PauseMenuDialog(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.setSize(800, 800);
        this.label.setFont(new Font(null, BOLD, 30));
        label.setSize(300, 75);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setLocation(mainGUI.getWidth() / 2 - 150, mainGUI.getHeight() / 2 - 150);
        backBtn.setBounds(mainGUI.getWidth() / 2 - 100, mainGUI.getHeight() / 2 + 100, 200, 50);
        continueBtn.setBounds(mainGUI.getWidth() / 2 - 100, mainGUI.getHeight() / 2, 200, 50);
        init();
        this.setLayout(null);
        this.setVisible(true);

    }

    private void init() {
        backBtn.addActionListener(e -> {

            mainGUI.engineThread.interrupt();
            mainGUI.timer.stopTimer();
            mainGUI.timer = null;
            mainGUI.engine.gameOver = true;

            mainGUI.dispose();
            mainGUI.mainMenuFrame.setVisible(true);
            mainGUI.mainMenuFrame.requestFocus();

        });
        continueBtn.addActionListener(e -> {
            mainGUI.cardLayout.show(mainGUI.mainPanel, "GameScreen");
            mainGUI.timer.continueTimer();
            mainGUI.pauseBtn.setEnabled(true);
        });

        this.add(continueBtn);
        this.add(backBtn);
        this.add(label);
    }
}
