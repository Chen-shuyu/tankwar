import javax.swing.*;
import java.awt.*;

public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;

    GameClient(){
        this(800, 600);
    }

    public GameClient(int screenWidth, int screenHeight){
        this.screenWidth= screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

    }

}
