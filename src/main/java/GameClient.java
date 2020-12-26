import object.Direction;
import object.Tank;
import object.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

//主遊戲邏輯與圖形顯示
public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;
    private Tank playerTank;
    private List<Tank> enemyTanks = new ArrayList<Tank>();  //敵方坦克
    private List<Wall> walls = new ArrayList<Wall>();  //敵方坦克
    private Image background;
    private boolean stop;

    //預設遊戲畫面
    GameClient() {
        this(800, 800);
    }

    //設定畫面的大小
    public GameClient(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        init(); // 呼叫坦克

        // 執行續處理
        new Thread(() -> {
            while (!stop) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 建立一個坦克，設定位置和方向
    public void init() {
        background= new ImageIcon("assets/images/background.jpg").getImage();
        playerTank = new Tank(400, 90, Direction.DOWN);

        // 敵方坦克
        for (int i =0; i<3; i++){
            for (int j=0; j<4; j++){
                enemyTanks.add(new Tank(250+j*100,300+i*100,Direction.UP, true));
            }
        }

        walls.add(new Wall(180, 150, true, 15));
        walls.add(new Wall(100, 150, false, 14));
        walls.add(new Wall(700, 150, false, 14));
    }

    @Override
    // 將坦克畫出在GUI介面中
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);    // 黑色
        g.fillRect(0, 0, screenWidth, screenHeight);  // 填滿整個視窗
        g.drawImage(background,0 ,0,null);
        // 取用playerTank的圖片，位置並畫在視窗上
        //g.drawImage(playerTank.getImage(), playerTank.getX(), playerTank.getY(), null); // 圖片X軸的位置，y軸的位置
        //取代上面的方法
        playerTank.draw(g);

        // 畫出敵方坦克
        for(Tank tank:enemyTanks){
            tank.draw(g);
        }

        for (Wall wall:walls){
            wall.draw(g);
        }


    }


    // 判斷按下的按鈕，並移動坦克
    public void keyPressed(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        // 判斷按下按鍵的值
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    //利用KeyEvent的VK_UP判斷為上
                dirs[0] = true;
                //playerTank.setDirection(Direction.UP);      // 更改圖形方向 (因為無法偵測八方向，所以要改成組合)
                //playerTank.setY(playerTank.getY()-playerTank.getSpeed());   //移動:往上為Y軸的減項(將移動的方式新增至Tank中)
                break;
            case KeyEvent.VK_DOWN:
                dirs[1] = true;
                //playerTank.setDirection(Direction.DOWN);
                //playerTank.setY(playerTank.getY()+playerTank.getSpeed());
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = true;
                //playerTank.setDirection(Direction.LEFT);
                // playerTank.setX(playerTank.getX()+playerTank.getSpeed());
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = true;
                //playerTank.setDirection(Direction.RIGHT);
                //playerTank.setX(playerTank.getX()-playerTank.getSpeed());
                break;
        }
        //playerTank.move();      //將移動的增減新增到Tank中
    }

    public void keyReleased(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        // 判斷按下按鍵的值
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    //利用KeyEvent的VK_UP判斷為上
                dirs[0] = false;
                break;
            case KeyEvent.VK_DOWN:
                dirs[1] = false;
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = false;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = false;
                break;
        }
    }
    //repaint();  // 重新畫一次圖片，會自動呼叫paintComponent，最後因為使用執行續所以刪除
}
