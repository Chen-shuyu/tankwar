package object;

import javax.swing.*;
import java.awt.*;

public class Tank {
    int x;
    int y;
    private Direction direction;
    private int speed;


    public Tank(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        speed=5;
    }

    public int getSpeed() {
        return speed;
    }

    // 按照不同方向，更換坦克的圖片
    public Image getImage(){
        if (direction==Direction.UP)
            return new ImageIcon("assets/images/itankU.png").getImage();
        if (direction==Direction.DOWN)
            return new ImageIcon("assets/images/itankD.png").getImage();
        if (direction==Direction.RIGHT)
            return new ImageIcon("assets/images/itankR.png").getImage();
        if (direction==Direction.LEFT)
            return new ImageIcon("assets/images/itankL.png").getImage();
        if (direction==Direction.RIGHT_UP)
            return new ImageIcon("assets/images/itankRU.png").getImage();
        if (direction==Direction.RIGHT_DOWN)
            return new ImageIcon("assets/images/itankRD.png").getImage();
        if (direction==Direction.LEFT_up)
            return new ImageIcon("assets/images/itankLU.png").getImage();
        if (direction==Direction.LEFT_DOWN)
            return new ImageIcon("assets/images/itankLD.png").getImage();

        return null;
    }

    // 上下左右四個方向
    private boolean[] dirs=new boolean[4];

    public boolean[] getDirs() {
        return dirs;
    }

    // 新增坦克move的方法
    public void move(){
        switch (direction){
            case UP:
                y-=speed;
                break;
            case DOWN:
                y+=speed;
                break;
            case RIGHT:
                x+=speed;
                break;
            case LEFT:
                x-=speed;
                break;
            case RIGHT_UP:
                x+=speed;
                y-=speed;
                break;
            case RIGHT_DOWN:
                x+=speed;
                y+=speed;
                break;
            case LEFT_up:
                x-=speed;
                y-=speed;
                break;
            case LEFT_DOWN:
                x-=speed;
                y+=speed;
                break;
        }
    }

    private void determineDirection(){
        //上下右左
        if (dirs[0] && dirs[2] && !dirs[1] && !dirs[3]) direction=Direction.RIGHT_UP;

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
