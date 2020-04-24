package lab10;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DrawPanel extends JPanel {
    private List<XmasShape> shapes;

    public DrawPanel(List<XmasShape> shapes) {
        this.shapes = shapes;
        if (this.shapes == null)
            this.shapes = new ArrayList<>();
    }
    public DrawPanel addBackground()
    {
        this.shapes.add(0,new Background(this));
        return this;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;
        for (XmasShape s : shapes) {
            s.draw(g2d);
        }
    }

    public static void main(String[] args) {
        // write your code here
        JFrame frame = new JFrame("Choinka");
        frame.setContentPane(new DrawPanel(
                Stream.of(Stream.of(new Tree(250, 100, 1.2, 1.2)),
                        getBaubles(),
                        getGifts(),
                        Stream.of(new Star(415,53,3.0)),
                        Stream.of(new Caption("Merry Christmas",550,45,4,Math.PI/4,
                                new Color(0xAF060B))))
                    .flatMap(x->x)
                    .collect(Collectors.toList()))
                .addBackground());
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
    private static Stream<XmasShape> getBaubles()
    {
        return Stream.of(new Bauble(420, 195, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(525, 195, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(400, 295, 0.3, new Color(0xB32D1D), new Color(0xB31540)),
                new Bauble(560, 295, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(350, 385, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(600, 385, 0.3, new Color(0xB32D1D), new Color(0xB31540)),
                new Bauble(300, 485, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(650, 485, 0.3, new Color(0xB32D1D), new Color(0xB31540)),
                new Bauble(370, 485, 0.3, new Color(0xB32D1D), new Color(0xB31540)),
                new Bauble(580, 485, 0.3, new Color(0xA400B3), new Color(0x9D00B3)),
                new Bauble(420, 415, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(530, 415, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(420, 345, 0.3, new Color(0x2A63B3), new Color(0x2A63B3)),
                new Bauble(530, 345, 0.3, new Color(0xB32D1D), new Color(0xB31540)),
                new Bauble(440, 245, 0.3, new Color(0xB32D1D), new Color(0xB31540)),
                new Bauble(510, 245, 0.3, new Color(0xB32D1D), new Color(0xB31540)));
    }
    private static Stream<XmasShape> getGifts()
    {
        return Stream.of(
                new Gift(130,260,80,80,new Color(0x793474)),
                new Gift(175,270,100,100,new Color(0x796A17)),
                new Gift(275,270,100,100,new Color(0x2362A8)),
                new Gift(345,260,70,70,new Color(0xBB0297))
        );
    }
}
