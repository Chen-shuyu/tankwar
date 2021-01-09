import object.GameObject;

import javax.swing.*;
import java.awt.*;

public class Wall extends GameObject {


    //水平或垂直
    private boolean horizontal;
    private int bricks;


    Wall(int x, int y, boolean horizontal, int bricks, Image[] image) {
        super(x, y, image);
        this.horizontal = horizontal;
        this.bricks = bricks;
    }


    public void draw(Graphics g){
        // 判斷要畫垂直的還是水平的牆
        if(horizontal){
            for (int i =0; i<bricks; i++){
                g.drawImage(image[0], x+i* width, y, null);
            }
        } else {
            for (int i =0; i<bricks; i++){
                g.drawImage(image[0], x, y+i* height, null);
            }
        }
    }

    // 覆寫 Rectangle
    // 讓每一個牆面都會被碰撞到
    @Override
    public Rectangle getRectangle() {
        // 如果是水平的牆面，就把寬度乘上牆面的數量
        // 如果是垂直了話，就把高度乘上牆面的數量
        return horizontal ? new Rectangle(x, y, bricks*width, height):
            new Rectangle(x, y, width, bricks*height);
    }
}
