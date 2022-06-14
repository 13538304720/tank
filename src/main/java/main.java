
public class main {

    public static void main (String[] arg) {
        TankFrame frame = new TankFrame();
        try{
            while(true){
                Thread.sleep(50);
                frame.repaint();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
