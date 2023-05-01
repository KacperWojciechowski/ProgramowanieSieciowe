package Lab5_6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.jar.JarEntry;

public class MainFrame extends JFrame {

    private JButton sendButton = new JButton("Send");
    private JLabel onlineLabel = new JLabel("Online");
    private JPanel panel = new JPanel();
    private GridBagLayout mainLayout = new GridBagLayout();
    private JPanel onlinePanel = new JPanel();
    private BoxLayout onlineLayout = new BoxLayout(onlinePanel, BoxLayout.Y_AXIS);
    private JPanel inputPanel = new JPanel();
    private FlowLayout inputLayout = new FlowLayout();
    private JTextArea onlineArea = new JTextArea();
    private JScrollPane onlineScroll = new JScrollPane(onlineArea);
    private JTextArea chat = new JTextArea();
    private JScrollPane chatScroll = new JScrollPane(chat);
    private GridLayout chatLayout = new GridLayout();
    private JPanel chatPanel = new JPanel();
    private JTextField input = new JTextField(50);
    private String nick;
    public MainFrame(String nick)
    {
        this.nick = nick;
        super.setTitle("Podaj nick");
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(mainLayout);

        configureSendButton();
        configureInputPanel();
        configureOnlinePanel();
        configureChatPanel();

        GridBagConstraints gbc = new GridBagConstraints();

        addChatPanel(gbc);
        addOnlinePanel(gbc);
        addInputPanel(gbc);

        this.add(panel);
        this.setResizable(false);
        this.pack();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addListeners();
    }

    private void addInputPanel(GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(inputPanel, gbc);
    }

    private void addOnlinePanel(GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(onlinePanel, gbc);
    }

    private void addChatPanel(GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(chatPanel, gbc);
    }

    private void configureChatPanel() {
        chatPanel.setPreferredSize(new Dimension(550, 400));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(5 ,5, 5, 5));
        chatPanel.setLayout(chatLayout);
        chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatPanel.add(chatScroll);
    }

    private void configureOnlinePanel() {
        onlinePanel.setPreferredSize(new Dimension(200, 400));
        onlinePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        onlinePanel.setLayout(onlineLayout);
        onlineScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        onlineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        onlinePanel.add(onlineLabel);
        onlinePanel.add(onlineScroll);
    }

    private void configureInputPanel() {
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        inputPanel.setPreferredSize(new Dimension(550, 50));
        inputPanel.setLayout(inputLayout);
        input.setText(null);
        inputPanel.add(input);
        inputPanel.add(sendButton);
    }

    private void configureSendButton() {
        sendButton.setEnabled(false);
        sendButton.setText("Send");
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
                String msg = input.getText();
                Client.send("[" + nick + "]" + "{Message}(" + msg + ")");
            }
            super.mouseClicked(e);
            }
        });

        input.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (input.getText().equals("Wpisz wiadomosc")) {
                    input.setText(null);
                    input.requestFocus();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (input.getText().equals("")) {
                    sendButton.setEnabled(false);
                    input.setText("Wpisz wiadomosc");
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

    void updateOnlineList(String nick, Operation operation)
    {
        if (operation == Operation.CLEAR)
        {
            onlineArea.setText(null);
        }
        else
        {
            if (!onlineArea.getText().contains(nick))
            {
                onlineArea.append(nick + "\n");
            }
        }
    }

    void updateChat(String nick, String message)
    {
        System.out.println("Append to chat: [" + nick + "] " + message);
        chat.append("[" + nick + "] " + message + "\n");
    }

    public void start() {
        this.setVisible(true);
    }
}
