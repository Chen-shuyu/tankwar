import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//主視窗顯示與案件控制
public class TankGame {
    public static GameClient gameClient;
    public static void main(String[] args) {
        // 創建一個JFrame實體物件
        JFrame frame = new JFrame();
        // 呼叫GameClient (視窗寬,高)
        gameClient = new GameClient(800, 650);
        frame.add(gameClient);  // 利用frame.add 裝載GameClient
        frame.setTitle("坦克大戰");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // 當關閉視窗時，程式停止
        gameClient.repaint();
        frame.pack();

        // 按鍵偵測 (進行坦克的移動)
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameClient.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                gameClient.keyReleased(e);
            }
        });
    }
}
