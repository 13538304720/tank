

import java.awt.*;

public class Bullet {
    private static final int SPEED = 5;
    //坐标
    private int x, y;
    //方向
    private Dir dir;
    //子弹宽高
    public static int w = ResourceMgr.bulletL.getWidth();
    public static int h = ResourceMgr.bulletL.getHeight();

    private boolean living = true;

    private Group group = Group.BAD;

    TankFrame tankFrame = null;

    Rectangle rect = new Rectangle();

    public Bullet(int x, int y, Dir dir, TankFrame tankFrame,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = w;
        rect.height = this.h;
    }

    public void paint(Graphics g) {
        if (!living) {
            tankFrame.bullets.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
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

        rect.x = this.x;
        rect.y = this.y;
        if (x < 0 || y < 0 || x > tankFrame.GAME_WIDTH || y > tankFrame.GAME_HIGHT) {
            living = false;
        }
    }

    public void collideWith(Tank tank) {
        if(this.group == tank.getGroup()) return;
        //用一个rect来记录子弹位置
        Rectangle rect2 = new Rectangle(tank.getX(), tank.getY(), tank.w, tank.h);
        if (rect.intersects(rect2)) {
            this.die();
            tank.die();
            int ex = tank.getX() + Tank.w / 2 - Explode.WIDTH / 2;
            int ey = tank.getY() + Tank.h / 2 - Explode.HIGHT / 2;
            tankFrame.explodes.add(new Explode(ex, ey, this.tankFrame));
        }
    }

    public void die() {
        this.living = false;
    }
}
