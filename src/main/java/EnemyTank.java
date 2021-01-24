import object.Direction;

import java.awt.*;
import java.util.Random;

public class EnemyTank extends Tank{

    public EnemyTank(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction, enemy, image);
    }

    //ai方法:進行亂數移動
    public void  ai(){
        Random rand =new Random();
        if(rand.nextInt(20)==1){
            //dirs = new boolean[4];
            // 1/2機率停止
            if (rand.nextBoolean()){
                return;
            }
            getNewDirection();
        }
        // 1/50機率開火
        if (rand.nextInt(50)==1){
            fire();
        }
    }

    @Override
    public void draw(Graphics g){
        ai();
        super.draw(g);
    }

    public boolean collision(){
        if(super.collision()){
            getNewDirection();
            return true;
        }
        return false;
    }



    // 取得新方向
    public void getNewDirection(){
        dirs = new boolean[4];
        Random rand = new Random();
        //方向(上下左右)
        int dir= rand.nextInt(Direction.values().length);

        if (dir <= Direction.RIGHT.ordinal()){
            dirs[dir]=true;
        } else {
            if (dir ==Direction.LEFT_UP.ordinal()){
                dirs[0]=true;
                dirs[2]=true;
            }else if (dir ==Direction.RIGHT_UP.ordinal()){
                dirs[0]=true;
                dirs[3]=true;
            }else if (dir ==Direction.LEFT_DOWN.ordinal()){
                dirs[1]=true;
                dirs[2]=true;
            }else if (dir ==Direction.RIGHT_DOWN.ordinal()){
                dirs[1]=true;
                dirs[3]=true;
            }
        }
    }


}
