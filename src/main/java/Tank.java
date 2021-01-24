import object.Direction;
import object.GameObject;

//import javax.swing.*;
import java.awt.*;

public class Tank extends GameObject {
    private Direction direction;
    private int speed;
    // 新增dirs boolean陣列，為了判斷上下左右四個方向
    protected boolean[] dirs = new boolean[4];


    protected boolean enemy;  //
    protected int hp;
    public boolean isEnemy() {
        return enemy;
    }
    public Tank(int x, int y, Direction direction, Image[] image) {
        this(x, y, direction, false, image);
    }

    public Tank(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, image);
        this.direction = direction;
        speed = 10;
        this.enemy = enemy;
        hp=1;
    }
    public void getHurt(int damage){
        if(--hp<=0){
            alive=false;
        }
    }





    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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


    public int getSpeed() { return speed;}
    public void setSpeed(int speed) {this.speed = speed;}


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
            case LEFT_UP:
                x -= speed;
                y -= speed;
                break;
            case LEFT_DOWN:
                x -= speed;
                y += speed;
                break;
        }


    }

    public boolean[] getDirs() {
        return dirs;
    }
    // 利用dirs陣列填入的True和False，來判斷坦克的方向
    private void determineDirection() {
        //上下右左
        if (dirs[0] && !dirs[1] && dirs[2] && !dirs[3]) direction = Direction.LEFT_UP;
        else if (dirs[0] && !dirs[1] && !dirs[2] && dirs[3]) direction = Direction.RIGHT_UP;
        else if (!dirs[0] && dirs[1] && dirs[2] && !dirs[3]) direction = Direction.LEFT_DOWN;
        else if (!dirs[0] && dirs[1] && !dirs[2] && dirs[3]) direction = Direction.RIGHT_DOWN;
        else if (dirs[0] && !dirs[1] && !dirs[2] && !dirs[3]) direction = Direction.UP;
        else if (!dirs[0] && dirs[1] && !dirs[2] && !dirs[3]) direction = Direction.DOWN;
        else if (!dirs[0] && !dirs[1] && dirs[2] && !dirs[3]) direction = Direction.LEFT;
        else if (!dirs[0] && !dirs[1] && !dirs[2] && dirs[3]) direction = Direction.RIGHT;
    }
    public void draw(Graphics g) {
        if (!alive){
            return;
        }
        // 如果有移動，就判斷方向，然後移動
        if (!isStop()) {
            determineDirection();
            move();
            collision();
        }
        g.drawImage(image[direction.ordinal()], x, y, null);
    }

    public boolean isStop() {
        for (boolean dir : dirs) {
            if (dir) {
                return false;
            }
        }

        return true;
    }


    //射出砲彈
    public void fire(){
        //Bullet bullet = new Bullet(x, y, direction, enemy,GameClient.bulletImage);
        TankGame.getGameClient().addGameObject(new Bullet(x+(width-GameClient.getBulletImage()[0].getWidth(null))/2,
                y+(height-GameClient.getBulletImage()[0].getHeight(null))/2, direction, enemy,GameClient.getBulletImage()));
    }


    //  物件的碰撞
    public boolean CollisionObject() {
        boolean isCollision = false;
        for (GameObject object : TankGame.getGameClient().getObjects()) {
            // 如果碰撞到的東西不等於自己、不等於子彈、
            if (object != this  && getRectangle().intersects(object.getRectangle())) {
                // 碰撞後更新成上一次的位置
                x = oldX;
                y = oldY;
                isCollision = true;
            }
        }
        return isCollision;
    }


    // 邊界偵測
    public boolean CollisionBound() {
        boolean isCollision = false;
        //寬度偵測
        //如果x<0，代表超出左邊界
        if (x < 0) {
            x = 0;  //就把x值設為零
            isCollision = true;
        } else if (x > TankGame.getGameClient().getScreenWidth() - width) {   //如果X大於邊界-坦克寬度，就代表超過右邊界
            x = TankGame.getGameClient().getScreenWidth() - width;
            isCollision = true;
        }

        //高度偵測
        if (y < 0) {
            y = 0;
            isCollision = true;
        } else if (y > TankGame.getGameClient().getScreenHeight() - height) {
            y = TankGame.getGameClient().getScreenHeight() - height;
            isCollision = true;
        }
        return isCollision;
    }


    // 將原本放在move裡面的 碰撞邊界and碰撞到物件拿出來
    // 因為不需要每次移動都同時做兩個偵測，所以為了偵測是碰撞到邊界or碰撞到物件
    // 新增isCollisionBound 偵測碰裝邊界
    // 新增isCollisionObject 偵測碰裝物件
    // 新增collision 判斷是碰撞邊界還是物件
    public boolean collision() {
        boolean isCollision = CollisionBound();
        //若不是碰撞四周則判斷，是否碰撞物件
        if (!isCollision) {
            isCollision = CollisionObject();
        }
        return isCollision;
    }





}
