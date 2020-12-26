import javax.swing.*;
import java.awt.*;

public class Tool {
    public static Image getImage(String fileName) {
        return new ImageIcon("assets/images/" + fileName).getImage();
    }
}
