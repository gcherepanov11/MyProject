package loginingfilegui;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


// Так реализуется прозрачное окно

public class Translucent extends JFrame {
    public Translucent() {
        super("Translucent JFrame");
        setLayout(new GridLayout());

        setBounds(400, 300, 300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new JLabel("Translucent Window Demo"));
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Translucent window = new Translucent();

                if (translucencySupported())
                    window.setOpacity(0.1f);

                window.setVisible(true);
            }
        });
    }

    public static boolean translucencySupported() {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        return gd.isWindowTranslucencySupported(
                GraphicsDevice.WindowTranslucency.TRANSLUCENT);
    }
}