import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @author Laurie
 */

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int length;//蛇的长度
    int[] snakeX = new int[500];//随便定义长度,反之不出意外是不会超过的
    int[] snakeY = new int[500];
    String direction;//蛇的方向,来根据这个改变蛇头的图片
    Timer timer = new Timer(60, this);//计时器,来触发蛇移动的事件
    int score;//分数
    Boolean isFail = false;//是否失败判断
    Boolean isStart = false;//是否启动判断
    int foodX;//食物坐标
    int foodY;
    Random random = new Random();//用来产生随机数的Random类的实例化


    public void init(){
        length = 3;//一开始蛇的三节
        snakeX[0] = 100;//蛇头为下标0
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
        direction = "R";//初始化方向朝右边
        score = 0;
        foodX = getFoodX();//通过方法获取食物的坐标
        foodY = getFoodY();
    }
    public GamePanel(){
        //构造函数
        init();//进行初始化
        this.setFocusable(true);//设置可以被赋予焦点
        this.setForeground(Color.pink);//设置为粉色
        this.addKeyListener(this);//添加键盘的监听
        timer.start();//开始计时器
        JMenuBar menuBar = new JMenuBar();//选项栏
        JButton jb1 = new JButton("退出游戏");
        JButton jb3 = new JButton("新手难度");
        JButton jb4 = new JButton("高手难度");
        JButton jb5 = new JButton("地狱难度");
        menuBar.add(jb1);
        menuBar.add(jb3);
        menuBar.add(jb4);
        menuBar.add(jb5);
        jb1.addActionListener(e -> {//添加jb1的监听
            System.exit(0);//退出
        });
        jb3.addActionListener(e -> {
            this.requestFocus();//不获取焦点后面操作键盘都会在菜单栏里,就无法操作蛇了
            timer.stop();//把原来的timer停止
            timer = new Timer(80, this);//使蛇的速度变慢
            timer.start();//重新开始timer
        });
        jb4.addActionListener(e -> {
            this.requestFocus();
            timer.stop();
            timer = new Timer(40, this);
            timer.start();
        });
        jb5.addActionListener(e -> {
            this.requestFocus();
            timer.stop();
            timer = new Timer(20, this);
            timer.start();
        });
        this.add(menuBar);
        JButton jb2 =  new JButton("重新开始");
        jb2.addActionListener(e -> {
            init();//重新开始所以重新初始化一下
            repaint();//重画
            this.requestFocus();
        });
        this.add(jb2);
    }
    public int getFoodX(){
        //如果没有判断的话会出现bug,也就是食物可能出现在蛇的身体里,所以要进行判断优化
        boolean flag = true;
        int x = 25 + 25 * random.nextInt(34);//因为一格为25,所以要乘一个25
        while(flag){
            for(int i = 0; i < length; i++){
                if(snakeX[i] == x) {
                    x = 25 + 25 * random.nextInt(34);//如果和身体重合就重新随机一个数
                }else{
                    flag = false;
                }
            }
        }
        return x;
    }
    public int getFoodY(){
        boolean flag = true;
        int y = 75 + 25 * random.nextInt(24);//24是计算出来的,总panel的大小除以25
        while(flag){
            for(int i = 0; i < length; i++){
                if(snakeY[i] == y) {
                    y = 75 + 25 * random.nextInt(34);
                }else{
                    flag = false;
                }
            }
        }
        return y;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);//这个不加可能会出问题
        this.setBackground(Color.white);
        g.fillRect(0,0,90,30);
        g.setColor(Color.black);
        g.drawString("贪吃蛇大挑战",10,20);

        g.setColor(Color.LIGHT_GRAY);//分数提示框
        g.fillRect( 25, 36, 850, 40);

        g.setColor(Color.pink);//游戏主界面
        g.fillRect(25,75, 850, 600);

        if ("R".equals(direction)) {//判断是什么蛇头朝向,选择不同的蛇头图片
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if ("L".equals(direction)) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if ("U".equals(direction)) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if ("D".equals(direction)) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }

        for (int i = 1; i < length; i++) {//通过for循环绘制蛇的身体
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度："+length,650,54);
        g.drawString("分数："+score,750,54);//分数长度等的绘制
        Data.food.paintIcon(this, g, foodX, foodY);//绘制食物
        if (!isStart) {//如果还没开始就提示按下空格开始
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格来开始游戏", 300, 300);
        }

        if (isFail) {//如果失败了就提示按下空格重新开始
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("蛇死了哦，按下空格重新开始吧", 200, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && !isFail) {//如果已经开始并且没有失败
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                //就把每节身体的位置都变成上一个身体的位置来模拟移动
                snakeY[i] = snakeY[i - 1];
            }

            if ("R".equals(direction)) {
                //动完了身体,这里动蛇头,根据不同的方向来动到位置
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if ("L".equals(direction)) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if ("U".equals(direction)) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            } else if ("D".equals(direction)) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) {
                    snakeY[0] = 75;
                }
            }

            if (snakeX[0] == foodX && snakeY[0] == foodY) {//判断是否吃到食物
                //如果两者的xy坐标重合了就说明吃到了
                length++;//吃到了长度加一
                snakeX[length - 1] = foodX - 1;//这里是避免出现一个bug,没有这两行就会在吃吃到以后左上角出现一个绿方块
                //这是因为新增加的身体没有赋值坐标,所以就默认在00的位置
                snakeY[length - 1] = foodY - 1;
                score = score + 10;
                foodX = getFoodX();//重新生成新的食物
                foodY = getFoodY();
            }
            for(int i = 1; i < length; i++){//进行判断是否死亡,当蛇头和身体重合就是死亡
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                    break;
                }
            }
            repaint();//蛇移动了所以需要重新画一遍
        }
        timer.start();//计时器重新开始,蛇再进行下一步移动
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();//这个为接受的键盘的按键对应的代码
        if(keyCode == KeyEvent.VK_SPACE){//如果是空格的话激素hi进行判断
            if(!isFail){//如果没有失败就进行暂停与重新游戏的功能实现
                isStart = !isStart;
            }else{//如果已经死了就重新开始
                isFail = false;
                init();
            }
            repaint();//重新绘制
        }
        if (keyCode == KeyEvent.VK_LEFT && !"R".equals(direction)) {//检查获取的是上下左右的哪个进行蛇头转向
            direction = "L";
        } else if (keyCode == KeyEvent.VK_RIGHT && !"L".equals(direction)) {
            direction = "R";
        } else if (keyCode == KeyEvent.VK_UP && !"D".equals(direction)) {
            direction = "U";
        } else if (keyCode == KeyEvent.VK_DOWN && !"U".equals(direction)) {
            direction = "D";
        }
        if (keyCode == KeyEvent.VK_A && !"R".equals(direction)) {//检查获取的是wasd的哪个进行蛇头转向
            //注意当是与蛇头反方向的不可以进行转动,毕竟也不能180度转脖子把
            direction = "L";
        } else if (keyCode == KeyEvent.VK_D && !"L".equals(direction)) {
            direction = "R";
        } else if (keyCode == KeyEvent.VK_W && !"D".equals(direction)) {
            direction = "U";
        } else if (keyCode == KeyEvent.VK_S && !"U".equals(direction)) {
            direction = "D";
        }
    }




    //这两个没有用,所以不用写了
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
