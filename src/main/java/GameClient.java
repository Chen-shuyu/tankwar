import object.Direction;
import object.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//主遊戲邏輯與圖形顯示
public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;
    private PlayerTank playerTank;
    private Image[] eTankImage=new Image[8];
    private CopyOnWriteArrayList<GameObject>gameObjects= new CopyOnWriteArrayList<>();
    public static Image[] bulletImage= new Image[8];

    public CopyOnWriteArrayList<GameObject> getObjects() { return gameObjects; }
    public static Image[] getBulletImage() {return bulletImage; }
    public Image background;
    //為了避免同時間要刪除跟新增物件到GameObject同步問題


    //private List<GameObject> gameObjects = new ArrayList<>(); //使用父類別進行集合類型宣告
    //private boolean stop;



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
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    // 回傳所有物件
//    public List<GameObject> getGameObjects() {
//        return gameObjects;
//    }



    // 建立一個坦克，設定位置和方向
    public void init() {
       // background= Tools.getImage("background.jpg");
        Image[] brickImage = {Tools.getImage("brick.png")};
        Image[] iTankImage = new Image[8];
        //Image[] eTankImage = new Image[8];

        String[] sub={"U", "D", "L", "R", "RU", "RD", "LU", "LD"};

        for (int i=0; i<iTankImage.length; i++){
            iTankImage[i] = Tools.getImage("itank"+sub[i]+".png");
            eTankImage[i] = Tools.getImage("etank"+sub[i]+".png");
            bulletImage[i]=Tools.getImage("missile"+sub[i]+".png");
        }

        playerTank = new PlayerTank(400, 90, Direction.DOWN,iTankImage);
        gameObjects.add(playerTank);
        // 敵方坦克
        for (int i =0; i<3; i++){
            for (int j=0; j<4; j++){
                gameObjects.add(new EnemyTank(350 + j * 100, 500 + 80 * i, Direction.UP, true, eTankImage));
            }
        }

        gameObjects.addAll(Arrays.asList(new Wall[]{
                new Wall(250, 150, true, 15,brickImage),
                new Wall(150, 150, false, 14,brickImage),
                new Wall(800, 150, false, 14,brickImage),
        }));

    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    // 將坦克畫出在GUI介面中
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.setColor(Color.BLACK);    // 黑色
        g.fillRect(0, 0, screenWidth, screenHeight);  // 填滿整個視窗
        g.drawImage(background,0 ,0,null);

        for (GameObject object: gameObjects){
            object.draw(g);
        }
        //如果物件已經不在存活則移除
        for (GameObject object: gameObjects){
            if (!object.isAlive()){
                gameObjects.remove(object);
            }
        }
        checkGameStatus();
        System.out.println(gameObjects.size());
    }




    // 判斷按下的按鈕，並移動坦克
    public void keyPressed(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        // 判斷按下按鍵的值
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    //利用KeyEvent的VK_UP判斷為上
                dirs[0] = true;
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
            case KeyEvent.VK_S:
                playerTank.superFire();
                break;
        }
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
    public void checkGameStatus(){
        boolean gameWin = true;
        for (GameObject object:gameObjects){
            if (object instanceof EnemyTank){
                gameWin = false;
                break;
            }
        }
        if (gameWin){
            for (int i=0; i<3; i++){
                for (int j =0; j<4;j++){
                    gameObjects.add(new EnemyTank(350 + j * 100, 500 + 80 * i, Direction.UP, true, eTankImage));
                }
            }
        }
    }




    //repaint();  // 重新畫一次圖片，會自動呼叫paintComponent，最後因為使用執行續所以刪除
}
