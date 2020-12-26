package object;

import javax.swing.*;
import java.awt.*;

public class Tank {
    int x;
    int y;
    private Direction direction;
    private int speed;
    private boolean enemy;  //


    public Tank(int x, int y, Direction direction) {
        this(x,y,direction, false);
    }
    public Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        speed = 5;
        this.enemy = enemy;
    }



    public int getSpeed() {
        return speed;
    }

    // 按照不同方向，更換坦克的圖片
    public Image getImage() {
        String name = enemy ? "etank":"itank";
        if (direction == Direction.UP)
            return new ImageIcon("assets/images/"+name+"U.png").getImage();
        if (direction == Direction.DOWN)
            return new ImageIcon("assets/images/"+name+"D.png").getImage();
        if (direction == Direction.RIGHT)
            return new ImageIcon("assets/images/"+name+"R.png").getImage();
        if (direction == Direction.LEFT)
            return new ImageIcon("assets/images/"+name+"L.png").getImage();
        if (direction == Direction.RIGHT_UP)
            return new ImageIcon("assets/images/"+name+"RU.png").getImage();
        if (direction == Direction.RIGHT_DOWN)
            return new ImageIcon("assets/images/"+name+"RD.png").getImage();
        if (direction == Direction.LEFT_up)
            return new ImageIcon("assets/images/"+name+"LU.png").getImage();
        if (direction == Direction.LEFT_DOWN)
            return new ImageIcon("assets/images/"+name+"LD.png").getImage();

        return null;
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
        g.drawImage(getImage(), x, y, null);
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
