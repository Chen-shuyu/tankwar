import object.GameObject;

import java.awt.*;

public class Bullet extends Tank {
    public Bullet(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction, enemy, image);
        speed = 15;
    }

    @Override
    public void draw(Graphics g) {
        if (!alive) {
            return;
        }
        move();
        collision();
        g.drawImage(image[direction.ordinal()], x, y, null);
    }

    @Override
    public boolean isCollisionBound() {
        if (super.isCollisionBound()) {
            alive = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean collision() {
        if (isCollisionBound()) {
            alive = false;
            return true;
        }

        boolean isCollision = false;
        for (GameObject gameObject : TankGame.gameClient.getGameObjects()) {
            // 判斷是否為子彈本身
            if (gameObject == this) {
                continue;
            }
            if (gameObject instanceof Tank ) {
                if (enemy == ((Tank) gameObject).enemy) {
                    continue;
                }
            }

            // 判斷是否碰撞到物件
            if (getRectangle().intersects(gameObject.getRectangle())) {
                // 是否碰撞到坦克
                if (gameObject instanceof Tank ) {
                    ((Tank) gameObject).setAlive(false);
                }
                isCollision = true;
                alive = false;
                break;
            }
        }
        return isCollision;
    }
}
