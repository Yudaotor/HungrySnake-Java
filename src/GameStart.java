import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Laurie
 */
public class GameStart {
    public static void main(String[] args) {
        JFrame jframe = new JFrame("贪吃蛇");
        jframe.setBounds(10, 10, 900, 720);//绘制游戏主界面的范围
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);//不可扩大缩小窗口
        GamePanel gamePanel = new GamePanel();
        jframe.add(gamePanel);
        jframe.setVisible(true);//显形
        gamePanel.setLayout(new BorderLayout());
    }
}