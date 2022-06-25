package com.tankMain;

import com.tankMain.observer.TankFireEvent;
import com.tankMain.observer.TankFireHandler;
import com.tankMain.observer.TankFireObserver;
import com.tankMain.strategy.DefaultFireStrategy;
import com.tankMain.strategy.FireStrategy;
import lombok.Data;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class Tank extends GameObject{

    public static int w = ResourceMgr.goodTankD.getWidth();
    public static int h = ResourceMgr.goodTankD.getHeight();

    public Dir dir = Dir.DOWN;
    int oldX, oldY;
    private static final int SPEED = 4;

    private TankFrame tankFrame = null;

    private boolean living = true;

    private boolean moving = true;

    public int x, y;

    private Random random = new Random();

    public Group group = Group.BAD;

    public Rectangle rect = new Rectangle();

    FireStrategy fs;


    public Tank(int x, int y, Dir dir, Group group) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;


        rect.x = this.x;
        rect.y = this.y;
        rect.width = w;
        rect.height = h;

        if (group == Group.GOOD) {
            String goodFSName = (String) PropertyMgr.get("goodFS");

            try {
                fs = (FireStrategy) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            fs = new DefaultFireStrategy();
        }

        GameModel.getInstance().add(this);
    }

    private void boundsCheck() {
        if (this.x < 2)
            x = 2;
        if (this.y < 28)
            y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.w - 2)
            x = TankFrame.GAME_WIDTH - Tank.w - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.h - 2)
            y = TankFrame.GAME_HEIGHT - Tank.h - 2;
    }

    public void die() {
        this.living = false;
    }



    public void fire() {
        fs.fire(this);
    }

    public Dir getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void back() {
        x = oldX;
        y = oldY;
    }

    private void move() {
        //记录移动之前的位置
        oldX = x;
        oldY = y;

        if (!moving)
            return;

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

        if (this.group == Group.BAD && random.nextInt(100) > 95)
            this.fire();

        if (this.group == Group.BAD && random.nextInt(100) > 95)
            randomDir();

        boundsCheck();
        // update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    public void paint(Graphics g) {
        if (!living)
            GameModel.getInstance().remove(this);

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

    private void randomDir() {

        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void stop() {
        moving = false;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    private List<TankFireObserver> fireObservers = Arrays.asList(new TankFireHandler());
    public void handleFireKey() {
        TankFireEvent event = new TankFireEvent(this);
        for(TankFireObserver o : fireObservers) {
            o.actionOnFire(event);
        }
    }
}
