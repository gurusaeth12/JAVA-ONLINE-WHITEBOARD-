import java.awt.*;
import java.io.Serializable;

public class Message implements Serializable {
    public int x1, y1, x2, y2, size;
    public Color color;
    public boolean clear;

    public Message(int x1, int y1, int x2, int y2, Color color, int size, boolean clear) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.size = size;
        this.clear = clear;
    }
}
