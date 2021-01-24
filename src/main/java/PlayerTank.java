import object.Direction;

import java.awt.*;

public class PlayerTank extends Tank implements SuperFire{

    public PlayerTank(int x, int y, Direction direction, Image[] image) {
        super(x, y, direction, image);
        hp=3;
    }

    public void superFire(){
        for (Direction direction : Direction.values()){
            Bullet bullet = new Bullet(x + (width-GameClient.getBulletImage()[0].getWidth(null))/2,
                    y+ (height - GameClient.getBulletImage()[0].getHeight(null))/2,direction,
                    enemy,GameClient.getBulletImage()
                    );
            TankGame.getGameClient().addGameObject(bullet);
        }
    }


}
