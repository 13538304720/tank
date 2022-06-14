import java.awt.*;

public class Tank {
    private int x, y;
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 10;
    private TankFrame tankFrame = null;

    private boolean moving = false;

    public boolean isMoving() {
        return moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Tank(int x, int y, Dir dir, TankFrame tankFrame) {
        super();
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        g.fillRect(x, y, 50, 50);
        g.setColor(c);
        move();

    }

    public void move() {
        if (!moving) return;
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
    }

    public void fire() {
        tankFrame.bullets.add(new Bullet(this.x, this.y, this.dir, this.tankFrame));
    }
}
