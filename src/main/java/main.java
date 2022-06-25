
public class main {

    public static void main(String[] arg) {
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("innitTankCount"));
        TankFrame frame = new TankFrame();
        //初始化敌人坦克
        for (int i = 0; i < initTankCount; i++) {
            frame.tanks.add(new Tank(50 + i * 80, 200, Dir.DOWN, frame, Group.BAD));
        }

        //播放炮弹音乐
        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        try {
            while (true) {
                Thread.sleep(25);
                frame.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
