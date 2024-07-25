package MySweep;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class OpeningWindow extends JFrame implements DarkModeToggleable {//<-- its a JFrame
    private JTextField WidthField;
    private JTextField HeightField;
    private JTextField BombNumber;
    private JTextField LivesNumber;
    private JButton Start = new JButton("Start!");
    private JButton ScoreBoard = new JButton();
    private JButton HelpWindow = new JButton();
    private JLabel LifeFieldLabel = new JLabel();
    private JLabel WidthFieldLabel = new JLabel();
    private JLabel HeightFieldLabel = new JLabel();
    private JLabel BombFieldLabel = new JLabel();
    private JLabel TitleLabel = new JLabel();
    private JLabel AuthorLabel = new JLabel();
    private final Color PURPLE = new Color(58, 0, 82);
    private final Color LIGHTPRPL = new Color(215, 196, 255);
    private static final Icon DefaultButtonIcon = (new JButton()).getIcon();

    public OpeningWindow(String initialx, String initialy, String initialbombno, String initiallives) {
        WidthField = new JTextField(initialx);
        HeightField = new JTextField(initialy);
        BombNumber = new JTextField(initialbombno);
        LivesNumber = new JTextField(initiallives);
        initComponents();
    }
    public OpeningWindow() {
        WidthField = new JTextField();
        HeightField = new JTextField();
        BombNumber = new JTextField();
        LivesNumber = new JTextField();
        initComponents();
    }
    private void StartActionPerformed() {
        try{
            int width =(int)(Integer.parseInt(WidthField.getText()));
            int height =(int)(Integer.parseInt(HeightField.getText()));
            int bombCount = (int)(Integer.parseInt(BombNumber.getText()));
            int lives = (int)(Integer.parseInt(LivesNumber.getText()));
            if(width*height<=bombCount||bombCount<0||lives<1||width<1||height<1){
                if(lives<1)LifeFieldLabel.setText("no life");
                if(width<1)WidthFieldLabel.setText("invalid width");
                if(height<1)HeightFieldLabel.setText("invalid height");
                if(width*height<=bombCount)BombFieldLabel.setText("Space<Bombs");
                if(bombCount<0)BombFieldLabel.setText("Bombs<0");
                return;
            }
            EventQueue.invokeLater(new Runnable() {
                public void run() {new MainGameWindow(width,height,bombCount,lives).setVisible(true);}
            });
            OpeningWindow.this.dispose();
        }catch(NumberFormatException e){TitleLabel.setText("Invalid field(s)");}
    } 
    public void toggleDarkMode(){//<-- MineSweeper.toggleDarkMode() calls this function
        setDarkMode();
        repaint();
    }
    private void initComponents() {
        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                StartActionPerformed();
            }
        });
        ScoreBoard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try{
                            int width =(int)(Integer.parseInt(WidthField.getText()));
                            int height =(int)(Integer.parseInt(HeightField.getText()));
                            int bombCount = (int)(Integer.parseInt(BombNumber.getText()));
                            int lives = (int)(Integer.parseInt(LivesNumber.getText()));
                            new ScoresWindow(width,height,bombCount,lives,OpeningWindow.this).setVisible(true);
                        }catch(NumberFormatException e){
                            new ScoresWindow(OpeningWindow.this).setVisible(true);
                        }
                    }
                });
            }
        });
        HelpWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new InstructionsWindow().setVisible(true);
                    }
                });//<-- see?
            }
        });//<-- 2 of them!
        KeyAdapter keyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    Component CurrComp = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                    if(!(CurrComp instanceof JButton)){
                        StartActionPerformed();
                    }else ((JButton)CurrComp).doClick();
                }
            }
        };
        Start.addKeyListener(keyAdapter);
        ScoreBoard.addKeyListener(keyAdapter);
        WidthField.addKeyListener(keyAdapter);
        HeightField.addKeyListener(keyAdapter);
        BombNumber.addKeyListener(keyAdapter);
        LivesNumber.addKeyListener(keyAdapter);
        HelpWindow.addKeyListener(keyAdapter);

        //set font and initial text
        ScoreBoard.setFont(new Font(MineSweeper.MAIN_FONT, 0, 12));
        ScoreBoard.setText("HiSc");
        HelpWindow.setFont(new Font(MineSweeper.MAIN_FONT, 0, 12));
        HelpWindow.setText("Help");
        LifeFieldLabel.setFont(new Font(MineSweeper.MAIN_FONT, 0, 14));
        LifeFieldLabel.setText("#ofLives:");
        LifeFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        WidthFieldLabel.setFont(new Font(MineSweeper.MAIN_FONT, 0, 14));
        WidthFieldLabel.setText("Width(in Tiles):");
        WidthFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        HeightFieldLabel.setFont(new Font(MineSweeper.MAIN_FONT, 0, 14));
        HeightFieldLabel.setText("Height(in Tiles):");
        HeightFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        BombFieldLabel.setFont(new Font(MineSweeper.MAIN_FONT, 0, 14));
        BombFieldLabel.setText("#ofBombs");
        BombFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TitleLabel.setFont(new Font(MineSweeper.MAIN_FONT, 0, 36));
        TitleLabel.setText("Mine Sweeper");
        AuthorLabel.setFont(new Font(MineSweeper.MAIN_FONT, 0, 12));
        AuthorLabel.setText("-Birdee");
        AuthorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        setIconImage(MineSweeper.MineIcon);

        setDarkMode();
        JPanel backgroundPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor((MineSweeper.isDarkMode())?PURPLE:LIGHTPRPL);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(300, 200));
        getContentPane().add(backgroundPanel);
        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints containerConstraints = new GridBagConstraints();

        containerConstraints.gridx =2;
        containerConstraints.gridy =0;
        containerConstraints.gridwidth =3;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(TitleLabel, containerConstraints);

        containerConstraints.gridx =4;
        containerConstraints.gridy =1;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(AuthorLabel, containerConstraints);

        containerConstraints.gridx =2;
        containerConstraints.gridy =2;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(WidthFieldLabel, containerConstraints);

        containerConstraints.gridx =4;
        containerConstraints.gridy =2;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(HeightFieldLabel, containerConstraints);

        containerConstraints.gridx =2;
        containerConstraints.gridy =3;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(WidthField, containerConstraints);

        containerConstraints.gridx =4;
        containerConstraints.gridy =3;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(HeightField, containerConstraints);

        containerConstraints.gridx =2;
        containerConstraints.gridy =4;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(BombFieldLabel, containerConstraints);

        containerConstraints.gridx =4;
        containerConstraints.gridy =4;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(LifeFieldLabel, containerConstraints);

        containerConstraints.gridx =2;
        containerConstraints.gridy =5;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(BombNumber, containerConstraints);

        containerConstraints.gridx =4;
        containerConstraints.gridy =5;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(LivesNumber, containerConstraints);

        containerConstraints.gridx =2;
        containerConstraints.gridy =6;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(ScoreBoard, containerConstraints);

        containerConstraints.gridx =2;
        containerConstraints.gridy =7;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =1;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(HelpWindow, containerConstraints);

        containerConstraints.gridx =4;
        containerConstraints.gridy =6;
        containerConstraints.gridwidth =1;
        containerConstraints.gridheight =2;
        containerConstraints.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(Start, containerConstraints);

        pack();
        getContentPane().setVisible(true);
    }
    private void setDarkMode(){
        if(MineSweeper.isDarkMode()){
            Start.setForeground(Color.WHITE);
            ScoreBoard.setForeground(Color.WHITE);
            HelpWindow.setForeground(Color.WHITE);
            Start.setBackground(Color.BLACK);
            ScoreBoard.setBackground(Color.BLACK);
            HelpWindow.setBackground(Color.BLACK);
            LifeFieldLabel.setForeground(Color.WHITE);
            WidthFieldLabel.setForeground(Color.WHITE);
            HeightFieldLabel.setForeground(Color.WHITE);
            BombFieldLabel.setForeground(Color.WHITE);
            TitleLabel.setForeground(Color.GREEN);
            AuthorLabel.setForeground(Color.GREEN);
        }else{
            Start.setBackground(null);
            Start.setIcon(DefaultButtonIcon);
            Start.setForeground(Color.BLACK);
            ScoreBoard.setBackground(null);
            ScoreBoard.setIcon(DefaultButtonIcon);
            ScoreBoard.setForeground(Color.BLACK);
            HelpWindow.setBackground(null);
            HelpWindow.setIcon(DefaultButtonIcon);
            HelpWindow.setForeground(Color.BLACK);
            LifeFieldLabel.setForeground(Color.BLACK);
            WidthFieldLabel.setForeground(Color.BLACK);
            HeightFieldLabel.setForeground(Color.BLACK);
            BombFieldLabel.setForeground(Color.BLACK);
            TitleLabel.setForeground(Color.BLACK);
            AuthorLabel.setForeground(Color.BLACK);
        }
    }
}
