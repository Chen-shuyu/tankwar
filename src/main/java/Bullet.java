import object.Direction;
import object.GameObject;

import java.awt.*;

public class Bullet extends Tank {
    public Bullet(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction, enemy, image);
        setSpeed(15);
    }

    @Override
    public void draw(Graphics g) {
        if (!alive) {
            return;
        }
        move();
        collision();
        g.drawImage(image[getDirection().ordinal()], x, y, null);
    }


    @Override
    public boolean CollisionObject() {
        boolean isCollision =false;
        for (GameObject object: TankGame.getGameClient().getObjects()){
            if (object instanceof Tank){
                if (((Tank) object).isEnemy()==isEnemy()){
                    continue;
                }
            }

            if (object!= this && getRectangle().intersects(object.getRectangle())){
                if (object instanceof Tank){
                    ((Tank) object).getHurt(1);  //讓坦克消失
                }
                isCollision=true;
            }
        }
        return isCollision;
    }

    public boolean collision(){
        boolean isCollision = CollisionBound();

        if (!isCollision){
            isCollision= CollisionObject();
        }
        if (isCollision){
            alive = false;  //將子彈本身設定為False
            return true;
        }
        return false;
    }
}
