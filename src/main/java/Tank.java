import object.GameObject;

import javax.swing.*;
import java.awt.*;

public class Tank extends GameObject {
    protected Direction direction;
    protected int speed;
    protected boolean enemy;  //


    public Tank(int x, int y, Direction direction, Image[] image) {
        this(x, y, direction, false, image);
    }

    public Tank(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, image);
        this.direction = direction;
        speed = 10;
        this.enemy = enemy;
    }


    public int getSpeed() {
        return speed;
    }


    // 新增dirs boolean陣列，為了判斷上下左右四個方向
    private boolean[] dirs = new boolean[4];

    public boolean[] getDirs() {
        return dirs;
    }

    // 新增坦克move的方法
    public void move() {
        // 記錄移動前的位置
        oldX = x;
        oldY = y;

        switch (direction) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT_UP:
                x += speed;
                y -= speed;
                break;
            case RIGHT_DOWN:
                x += speed;
                y += speed;
                break;
            case LEFT_up:
                x -= speed;
                y -= speed;
                break;
            case LEFT_DOWN:
                x -= speed;
                y += speed;
                break;
        }


    }

    // 將原本放在move裡面的 碰撞邊界and碰撞到物件拿出來
    // 因為不需要每次移動都同時做兩個偵測，所以為了偵測是碰撞到邊界or碰撞到物件
    // 新增isCollisionBound 偵測碰裝邊界
    // 新增isCollisionObject 偵測碰裝物件
    // 新增collision 判斷是碰撞邊界還是物件
    public boolean collision() {
        boolean isCollision = false;
        isCollision = isCollisionBound();
        if (!isCollision) {
            isCollision = isCollisionObject();
        }
        return isCollision;
    }

    // 邊界偵測
    public boolean isCollisionBound() {
        boolean isCollision = false;
        if (x < 0) {
            x = 0;
            isCollision = true;
        } else if (x > TankGame.gameClient.getScreenWidth() - width) {
            x = TankGame.gameClient.getScreenWidth() - width;
            isCollision = true;
        }
        if (y < 0) {
            y = 0;
            isCollision = true;
        } else if (y > TankGame.gameClient.getScreenHeight() - height) {
            y = TankGame.gameClient.getScreenHeight() - height;
            isCollision = true;
        }
        return isCollision;
    }

    //  物件的碰撞
    public boolean isCollisionObject() {
        boolean isCollision = false;
        for (GameObject gameObject : TankGame.gameClient.getGameObjects()) {
            if (gameObject != this && getRectangle().intersects(gameObject.getRectangle())) {
                // 碰撞後更新成上一次的位置
                x = oldX;
                y = oldY;
                isCollision = true;
                break;
            }
        }
        return isCollision;
    }


    // 利用dirs陣列填入的True和False，來判斷坦克的方向
    private void determineDirection() {
        //上下右左
        if (dirs[0] && !dirs[1] && dirs[2] && !dirs[3]) direction = Direction.LEFT_up;
        else if (dirs[0] && !dirs[1] && !dirs[2] && dirs[3]) direction = Direction.RIGHT_UP;
        else if (!dirs[0] && dirs[1] && dirs[2] && !dirs[3]) direction = Direction.LEFT_DOWN;
        else if (!dirs[0] && dirs[1] && !dirs[2] && dirs[3]) direction = Direction.RIGHT_DOWN;
        else if (dirs[0] && !dirs[1] && !dirs[2] && !dirs[3]) direction = Direction.UP;
        else if (!dirs[0] && dirs[1] && !dirs[2] && !dirs[3]) direction = Direction.DOWN;
        else if (!dirs[0] && !dirs[1] && dirs[2] && !dirs[3]) direction = Direction.LEFT;
        else if (!dirs[0] && !dirs[1] && !dirs[2] && dirs[3]) direction = Direction.RIGHT;
    }

    //射出砲彈
    public void fire(){
        //Bullet bullet = new Bullet(x, y, direction, enemy,GameClient.bulletImage);
        TankGame.gameClient.addGameObject(new Bullet(x+(width-GameClient.bulletImage[0].getWidth(null))/2,
                y+(height-GameClient.bulletImage[0].getHeight(null))/2, direction, enemy,GameClient.bulletImage));
    }


    public void draw(Graphics g) {
        if (!alive){
            return;
        }
        // 如果有移動，就判斷方向，然後移動
        if (isRunning()) {
            determineDirection();
            move();
            collision();
        }
        g.drawImage(image[direction.ordinal()], x, y, null);
    }

    // 增加停止的方法
    // 如果dirs陣列裡面都不是四個false時，就會移動
    public boolean isRunning() {
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i]) {
                return true;
            }
        }
        return false;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
