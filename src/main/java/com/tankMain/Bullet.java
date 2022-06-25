package com.tankMain;

import java.awt.*;

public class Bullet extends GameObject{
    private static final int SPEED = 5;
    //坐标
    private int x, y;
    //方向
    private Dir dir;
    //子弹宽高
    public static int w = ResourceMgr.bulletL.getWidth();
    public static int h = ResourceMgr.bulletL.getHeight();
    public Group group = Group.BAD;
    private boolean living = true;


    public GameModel gm = null;
    public Rectangle rect = new Rectangle();


    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;


        rect.x = this.x;
        rect.y = this.y;
        rect.width = w;
        rect.height = h;

        GameModel.getInstance().add(this);

    }

    @Override
    public void paint(Graphics g) {
        if(!living) {
            GameModel.getInstance().remove(this);
        }

        switch(dir) {
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

        //update rect
        rect.x = this.x;
        rect.y = this.y;

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }


    public void die() {
        this.living = false;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }
}
