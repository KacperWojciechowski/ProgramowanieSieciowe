package Lab5_6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Semaphore;

public class Popup extends JFrame {

    private String nick = "default";
    private final Semaphore sem = new Semaphore(0);
    private final JPanel panel = new JPanel();
    private JButton sendButton = new JButton();
    private GridBagLayout mainLayout = new GridBagLayout();
    private JPanel inputPanel = new JPanel();
    private FlowLayout inputLayout = new FlowLayout();
    private JTextField input = new JTextField(40);

    public Popup()
    {
        super.setTitle("Podaj nick");
        panel.setPreferredSize(new Dimension(500, 150));
        panel.setLayout(mainLayout);
        sendButton.setEnabled(false);
        sendButton.setText("Send");
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        inputPanel.setPreferredSize(new Dimension(450, 50));
        inputPanel.setLayout(inputLayout);
        input.setText(null);
        inputPanel.add(input);
        inputPanel.add(sendButton);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(inputPanel, gbc);
        this.add(panel);
        this.setResizable(false);
        this.pack();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addListeners();
    }

    private void addListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sendButton.isEnabled())
                {
                    nick = input.getText();
                    sem.release();
                }
                super.mouseClicked(e);
            }
        });

        input.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (input.getText().equals("Podaj nick")) {
                    input.setText(null);
                    input.requestFocus();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (input.getText().equals("")) {
                    sendButton.setEnabled(false);
                    input.setText("Podaj nick");
                }
            }
        });
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e)
            {
                sendButton.setEnabled(!input.getText().equals(""));
                super.keyReleased(e);
            }
        });
    }

    public String askForNickname()
    {
        try {
            this.setVisible(true);
            sem.acquire();
            this.setVisible(false);
            return nick;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return nick;
        }
    }
}
