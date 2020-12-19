import javax.swing.*;

public class TankGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GameClient gameClient = new GameClient(800, 600);
        frame.add(gameClient);
        frame.setTitle("坦克大戰");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
}
