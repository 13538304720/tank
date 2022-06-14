

import java.awt.*;

public class Bullet {
    private static final int SPEED = 5;
    //坐标
    private int x, y;
    //方向
    private Dir dir;
    //子弹宽高
    private int w = 30, h = 30;

    private boolean live = true;
    TankFrame tankFrame = null;

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        if(!live){
            tankFrame.bullets.remove(this);
        }
        Color oldColor = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, w, h);
        g.setColor(oldColor);
        move();
    }

    private void move() {
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        if (x < 0 || y < 0 || x > tankFrame.GAME_WIDTH || y > tankFrame.GAME_HIGHT) {
            live = false;
        }
    }
}
