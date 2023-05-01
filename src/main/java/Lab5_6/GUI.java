package Lab5_6;
public class GUI {
    private String nickname;
    private Refresher refresher;
    private MainFrame mainFrame;
    public void process(String nick, String message, Target target, Operation operation) {
        if (target == Target.ONLINELIST) {
            mainFrame.updateOnlineList(nick, operation);
        }
        else if (target == Target.CHAT) {
            mainFrame.updateChat(nick, message);
        }
    }

    public String start() {
        Popup popup = new Popup();
        nickname = popup.askForNickname();
        System.out.println(nickname);
        popup = null;
        mainFrame = new MainFrame(nickname);
        mainFrame.start();
        refresher = new Refresher(nickname);
        //refresher.start();
        return nickname;
    }

    public void keepAlive() {
        while(true);
    }

    private void createRefresher() {
        refresher = new Refresher(nickname);
    }
}
