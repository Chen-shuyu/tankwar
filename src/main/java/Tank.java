import object.GameObject;

import javax.swing.*;
import java.awt.*;

public class Tank extends GameObject {
    private Direction direction;
    private int speed;
    private boolean enemy;  //


    public Tank(int x, int y, Direction direction, Image[] image) {
        this(x,y,direction, false,image);
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
        // 邊界偵測
        if (x<0){
            x=0;
        }else if (x>TankGame.gameClient.getScreenWidth()-width){
            x=TankGame.gameClient.getScreenWidth()-width;
        }
        if (y<0){
            y=0;
        }else if (y>TankGame.gameClient.getScreenHeight()-height){
            y=TankGame.gameClient.getScreenHeight()-height;
        }
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

    public void draw(Graphics g) {
        // 如果有移動，就判斷方向，然後移動
        if (isRunning()) {
            determineDirection();
            move();
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
