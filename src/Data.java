import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author Laurie
 */
public class Data {
    //读取statics文件夹里的图片,加载成图片
    public static URL bodyURL = Data.class.getResource("/statics/body.png");
    public static URL foodURL = Data.class.getResource("/statics/food.png");
    public static URL downURL = Data.class.getResource("/statics/down.png");
    public static URL upURL = Data.class.getResource("/statics/up.png");
    public static URL rightURL = Data.class.getResource("/statics/right.png");
    public static URL leftURL = Data.class.getResource("/statics/left.png");
    public static ImageIcon body = new ImageIcon(bodyURL);
    public static ImageIcon food = new ImageIcon(foodURL);
    public static ImageIcon down = new ImageIcon(downURL);
    public static ImageIcon up = new ImageIcon(upURL);
    public static ImageIcon right = new ImageIcon(rightURL);
    public static ImageIcon left = new ImageIcon(leftURL);
}
