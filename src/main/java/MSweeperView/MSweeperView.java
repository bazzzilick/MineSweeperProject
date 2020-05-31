package MSweeperView;

import Interface.ControllerInterface;
import Interface.ModelInterface;
import Interface.ModelObserver;
import MSweeperModel.Box;
import MSweeperModel.GameState;
import MSweeperModel.Coord;
import MSweeperModel.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MSweeperView extends JFrame implements ModelObserver, ActionListener
{
    private final int IMAGE_SIZE = 50;

    private ModelInterface model;
    private ControllerInterface controller;

    private JPanel panel;
    //private JLabel label;

    private JMenuBar bar;
    private JMenu mainMenu;
    private JMenu infoMenu;
    private JMenu levelMenu;

    private JMenuItem newGame;
    private JMenuItem exitGame;
    private JMenuItem mEasy;
    private JMenuItem mMed;
    private JMenuItem mHard;
    private JMenuItem score;
    private JMenuItem about;

    private GameAdapter gameAdapter;

    public MSweeperView(ModelInterface model, ControllerInterface controller) {

        this.model = model;
        this.model.registerObserver(this);
        this.controller = controller;

        setImages();

        //initMenu();
        //initLable();
        //initPanel();
        //initFrame();
        //gameAdapter = new GameAdapter();
        //panel.addMouseListener(gameAdapter);
    }

    private void setImages() {
        for(Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        System.out.println(name);
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource(filename));
        return icon.getImage();
    }

    /*private Image getImage(String name) {
        String filename = "D:\\MyProjects\\LabsJava\\MineSweeper\\res\\img\\" + name.toLowerCase() + ".png";
        //ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        ImageIcon icon = new ImageIcon(filename);
        Image img = icon.getImage(); //.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
        return img;
    }*/

    public void createTextBox() {
        //ImageIcon icon = new ImageIcon("img/icon.png");
        String result = JOptionPane.showInputDialog(null,
                "Enter your name, please", "anonymous");
        controller.setNewName(result);

    }

    @Override
    public void update(GameState state) {
        panel.repaint();
        if (state == GameState.BOMBED) {
            showGameOverMessage();
        }
    }

    private void showGameOverMessage() {
        JOptionPane.showMessageDialog(null, "Unfortunately you lose."
                        + "\nIn order to start again, select New Game in Menu", "Game Over :(",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWinMessage(long time) {
        JOptionPane.showMessageDialog(null, "You won!!!\n"
                        + "Your time: " + time + " sec"
                        + "\nIn order to start again, select New Game in Menu", "Congratulation! ;)",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(newGame)) {
            controller.startNewGame();
        } else if (event.getSource().equals(exitGame)) {
            controller.exitGame();
        } else if (event.getSource().equals(mEasy)) {
            controller.setDifficultLevel("easy");
        } else if (event.getSource().equals(mMed)) {
            controller.setDifficultLevel("medium");
        } else if (event.getSource().equals(mHard)) {
            controller.setDifficultLevel("hard");
        } else if (event.getSource().equals(score)) {
            controller.showScoreTable();
        } else if (event.getSource().equals(about)) {
            controller.showAboutMessage();
        }
    }

    public void initPanel() {

        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                /*for(Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) getImage(model.getBox(coord).name().toLowerCase()),
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);*/

                /*for(Coord coord : Ranges.getAllCoords()){
                    g.drawImage((Image) Box.values()[(coord.x + coord.y) % Box.values().length].image,
                                coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }*/

                for(Coord coord : Ranges.getAllCoords())
                    g.drawImage((Image) model.getBox(coord).image,
                            coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);

                /*for(Box box : Box.values()) {
                    Coord coord = new Coord( box.ordinal() * IMAGE_SIZE, 0);
                    g.drawImage((Image) box.image, box.ordinal() * IMAGE_SIZE, 0, this);
                }*/
            }
        };

        gameAdapter = new GameAdapter();

        panel.addMouseListener(gameAdapter);
        panel.setPreferredSize(new Dimension(
                model.getCols() * IMAGE_SIZE,
                model.getRows() * IMAGE_SIZE));
        add(panel);
    }

    /*public void initLable() {
        label = new JLabel("Welcome!");
        add (label, BorderLayout.NORTH);
    }*/

    public void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineSweeper");
        setResizable(true);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    public void initMenu() {

        infoMenu = new JMenu("Info");
        //infoMenu.addActionListener(this);
        score = new JMenuItem("Score");
        score.addActionListener(this);
        about = new JMenuItem("About");
        about.addActionListener(this);

        infoMenu.add(score);
        infoMenu.add(about);
        infoMenu.addSeparator();

        levelMenu = new JMenu("Level");
        //levelMenu.addActionListener(this);
        mEasy = new JMenuItem("Easy");
        mEasy.addActionListener(this);
        mMed = new JMenuItem("Medium");
        mMed.addActionListener(this);
        mHard = new JMenuItem("Hard");
        mHard.addActionListener(this);

        levelMenu.add(mEasy);
        levelMenu.add(mMed);
        levelMenu.add(mHard);
        levelMenu.addSeparator();

        mainMenu = new JMenu("Game");
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);
        exitGame = new JMenuItem("Exit Game");
        exitGame.addActionListener(this);

        mainMenu.add(newGame);
        mainMenu.add(levelMenu);
        mainMenu.add(exitGame);
        mainMenu.addSeparator();

        bar = new JMenuBar();
        bar.add(mainMenu);
        bar.add(infoMenu);
        setJMenuBar(bar);
    }

    public void showAboutMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void showScoreTable(StringBuilder str) {
        JOptionPane.showMessageDialog(null, str, "High Scores",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setNewDifficultLevel() {
        panel = null;
        gameAdapter = null;
        initPanel();
        pack();
        //TODO resizeIconForLevel();
    }

    private class GameAdapter extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent event) {
            int x = event.getX() / IMAGE_SIZE;
            int y = event.getY() / IMAGE_SIZE;
            Coord coord = new Coord(x, y);
            controller.gameControl(event, coord);
            //label.setText(getMessage());
            //panel.repaint();
        }
    }
}

