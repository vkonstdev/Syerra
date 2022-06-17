import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MusicTest1 {
    static JFrame f = new JFrame("Мой первый музыкальный клип");
    static MyDrawPanel dp;
    public static void main(String[] args) {
        MusicTest1 mt = new MusicTest1();
        mt.go();
    }
    private void setUpGUI() {
        dp = new MyDrawPanel();
        f.setContentPane(dp);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
    }
    private void go() {
        setUpGUI();
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(dp, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();
            //int r = 0;
            for (int i = 0; i < 100; i += 4) {
                //r = (int) ((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, i, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, i, 100, i + 2));
            }
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }
}

class MyDrawPanel extends JPanel implements ControllerEventListener {
    boolean msg = false;
    public void paintComponent(Graphics g) {
        if (msg) {
            Graphics2D g2 = (Graphics2D) g;
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            Color randomColor = new Color(red, green, blue);
            g2.setColor(randomColor);
            int ht = (int) ((Math.random() * 120) + 10);
            int width = (int) ((Math.random() * 120) + 10);
            int x = (int) ((Math.random() * 40) + 10);
            int y = (int) ((Math.random() * 40) + 10);
            g2.fillRect(x, y, ht, width);
            msg = false;
        }
    }
    @Override
    public void controlChange(ShortMessage event) {
        msg = true;
        repaint();
    }
}
