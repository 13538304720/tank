
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    static final int GAME_WIDTH = 1000, GAME_HIGHT = 1000;
    Tank myTank = new Tank(400, 800, Dir.UP, this, Group.GOOD);
    public static List<Bullet> bullets = new ArrayList<Bullet>();
    public static List<Tank> tanks = new ArrayList<Tank>();
    public static List<Explode> explodes = new ArrayList<Explode>();


    public TankFrame() {
        setTitle("坦克大战");
        Dimension d = new Dimension();
        d.setSize(GAME_WIDTH, GAME_HIGHT);
        setSize(d);
        setResizable(true);
        setVisible(true);

        myTank.setMoving(false);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    //解决闪烁问题，原因，画笔重刷时间过快，导致坦克和子弹没画完。
    // 解决：截获update，进行画制图片，进行缓存，先在内存画完整张图在调用paint方法整体刷新（将内存图片复制到显存）
    //先调用update再调用paint
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color c = goffScreen.getColor();
        goffScreen.setColor(Color.black);
        goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGHT);
        goffScreen.setColor(c);
        paint(goffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量" + bullets.size(), 20, 80);
        g.drawString("敌人数量" + tanks.size(), 20, 100);
        g.drawString("爆炸数量" + explodes.size(), 20, 120);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
        //碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }
        }
    }


    class MyKeyListener extends KeyAdapter {

        boolean bl = false;
        boolean bu = false;
        boolean br = false;
        boolean bd = false;

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bl = true;
                    break;
                case KeyEvent.VK_UP:
                    bu = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    break;
                default:
                    break;
            }
            //执行移动
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bl = false;
                    break;
                case KeyEvent.VK_UP:
                    bu = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    break;
                case KeyEvent.VK_NUMPAD0:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bl && !bu && !br && !bd) {
                myTank.setMoving(false);
            }else {
                myTank.setMoving(true);
                if (bl)
                    myTank.setDir(Dir.LEFT);
                if (bu)
                    myTank.setDir(Dir.UP);
                if (br)
                    myTank.setDir(Dir.RIGHT);
                if (bd)
                    myTank.setDir(Dir.DOWN);
            }
        }


    }


}
