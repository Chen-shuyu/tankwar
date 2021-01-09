import javax.swing.*;
import java.awt.*;

public class Tools {
    //讀取圖形的方法
    public static Image getImage(String fileName){
        return new ImageIcon("assets/images/"+fileName).getImage();
    }
}
