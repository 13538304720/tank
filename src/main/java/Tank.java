import lombok.Data;

import java.awt.*;
import java.util.Random;

@Data
public class Tank {

    public static int w = ResourceMgr.goodTankD.getWidth();
    public static int h = ResourceMgr.goodTankD.getHeight();

    private Dir dir = Dir.DOWN;

    private static final int SPEED = 4;

    private TankFrame tankFrame = null;

    private boolean living = true;

    private boolean moving = true;

    private int x, y;

    private Random random = new Random();

    private Group group = Group.BAD;

    Rectangle rect = new Rectangle();


    public Tank(int x, int y, Dir dir, TankFrame tankFrame, Group group) {
        super();
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
        this.group = group;
    }

    public void fire() {
        int bx = this.x + Tank.w / 2 - Bullet.w / 2;
        int by = this.y + Tank.h / 2 - Bullet.h / 2;
        tankFrame.bullets.add(new Bullet(bx, by, this.dir, this.tankFrame,this.group));
        if(this.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
    }

    private void move() {
        if(!living) return;

        if(!moving) return ;

        //save the oldDir for TankDirChangedMsg
        //oldDir = dir;

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
        if(this.group == Group.BAD && random.nextInt(100) > 95)
            this.fire();

        if(this.group == Group.BAD && random.nextInt(100) > 95)
            randomDir();

        boundsCheck();
        //update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    private void boundsCheck() {
        if (this.x < 0) {
            x = 0;
        } else if (this.y < 28) {
            y = 28;
        } else if (this.x > TankFrame.GAME_WIDTH - Tank.w - 13) {
            x = TankFrame.GAME_WIDTH - Tank.w - 13;
        } else if (this.y > TankFrame.GAME_HIGHT - Tank.h - 15) {
            y = TankFrame.GAME_HIGHT - Tank.h - 15;
        }
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }


    public void paint(Graphics g) {
        if (!living) {
            tankFrame.tanks.remove(this);
        }
        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }
        move();

    }


    public void die() {
        this.living = false;
    }
}
