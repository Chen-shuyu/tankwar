import object.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//主遊戲邏輯與圖形顯示
public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;
    private Image background;

    private Tank playerTank;
    private List<GameObject> gameObjects = new ArrayList<>(); //使用父類別進行集合類型宣告
    private boolean stop;
    public static Image[] bulletImage= new Image[8];


    //預設遊戲畫面
    GameClient() {
        this(900, 900);
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

    // 回傳所有物件
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    // 建立一個坦克，設定位置和方向
    public void init() {
        background= Tool.getImage("background.jpg");
        Image[] brickImage = {Tool.getImage("brick.png")};
        Image[] iTankImage = new Image[8];
        Image[] eTankImage = new Image[8];

        String[] sub={"U", "D", "L", "R", "RU", "RD", "LU", "LD"};

        for (int i=0; i<iTankImage.length; i++){
            iTankImage[i] = Tool.getImage("itank"+sub[i]+".png");
            eTankImage[i] = Tool.getImage("etank"+sub[i]+".png");
            bulletImage[i]=Tool.getImage("missile"+sub[i]+".png");
        }

        playerTank = new Tank(400, 90, Direction.DOWN,iTankImage);
        gameObjects.add(playerTank);
        // 敵方坦克
        for (int i =0; i<3; i++){
            for (int j=0; j<4; j++){
                gameObjects.add(new Tank(250+j*100,300+i*100,Direction.UP, true, eTankImage));
            }
        }
        //objects.addAll(enemyTanks);

        gameObjects.add(new Wall(250, 150, true, 15,brickImage));
        gameObjects.add(new Wall(150, 150, false, 14,brickImage));
        gameObjects.add(new Wall(800, 150, false, 14,brickImage));
        //objects.addAll(walls);
    }

    @Override
    // 將坦克畫出在GUI介面中
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);    // 黑色
        g.fillRect(0, 0, screenWidth, screenHeight);  // 填滿整個視窗
        g.drawImage(background,0 ,0,null);

        for (GameObject object: gameObjects){
            object.draw(g);
        }

        // 使用迭代器進行移除
        Iterator<GameObject> iterator = gameObjects.iterator();
        while (iterator.hasNext()){
            if(!(iterator.next()).isAlive()){
                iterator.remove();
            }
        }
        System.out.println(gameObjects.size());
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
                break;
            case KeyEvent.VK_LEFT:
                dirs[2] = true;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3] = true;
                break;
            case KeyEvent.VK_A:
                playerTank.fire();
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

    public void addGameObject(GameObject object) {
        gameObjects.add(object);
    }
    //repaint();  // 重新畫一次圖片，會自動呼叫paintComponent，最後因為使用執行續所以刪除
}
