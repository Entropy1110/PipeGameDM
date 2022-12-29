package nyc.pipegame;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;


public class MainMenuFrame extends JFrame {
    Container c = this.getContentPane();
    CardLayout buttonsCard;
    private JPanel StartExit;
    private MainGUI mainGUI;
    private JButton startButton;
    private JButton exitButton;
    private JButton levelsButton[];
    private static final String[] LEVELS = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    public MainMenuFrame(MainGUI mainGUI) {
        buttonsCard = new CardLayout();
        setLayout(buttonsCard);
        this.mainGUI = mainGUI;
        setTitle("Main Menu");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }



    private void init() {
        initButtons();
        initLevelButtons();
    }

    private void initButtons() {

        StartExit = new JPanel(new GridLayout(2, 1));
        startButton = new JButton("Start Game");
        StartExit.add(startButton);
        startButton.addActionListener(e -> buttonsCard.next(c));

        exitButton = new JButton("Exit");
        StartExit.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));
        add(StartExit, "First");
    }

    private void initLevelButtons() {
        JPanel levelsPanel = new JPanel(new GridLayout(6, 1, 15, 0));
        levelsButton = new JButton[LEVELS.length];
        for (int i = 0; i < LEVELS.length; i++){
            levelsButton[i] = new JButton(LEVELS[i]);
            levelsButton[i].setActionCommand(String.valueOf(i+1));
            levelsButton[i].setBackground(SystemColor.ORANGE);
            levelsButton[i].setBorder(new BevelBorder(BevelBorder.RAISED));
            levelsPanel.add(levelsButton[i]);
            levelsButton[i].addActionListener(e -> {
                int selectedLevel = Integer.parseInt(e.getActionCommand());
                mainGUI.initLevel(selectedLevel);
                this.setVisible(false);
                mainGUI.setVisible(true);
                mainGUI.setTitle("파이프 연결하기 - Level."  + (selectedLevel));
            });
        }
        JButton back = new JButton("뒤로가기");
        back.setBackground(Color.ORANGE);
        back.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        back.addActionListener(e->{
            buttonsCard.previous(c);
        });
        levelsPanel.add(back);
        add(levelsPanel);
    }





}

