import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class WhiteboardClient extends JFrame {
    private final DrawingPanel panel;
    private final ObjectOutputStream out;

    public WhiteboardClient(String serverAddress) throws Exception {
        Socket socket = new Socket(serverAddress, 5000);
        out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        panel = new DrawingPanel(out);
        add(panel, BorderLayout.CENTER);

        setTitle("🎨 Java Online Whiteboard (Client)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Thread for listening to updates
        new Thread(() -> {
            try {
                while (true) {
                    Message msg = (Message) in.readObject();
                    if (msg.clear) panel.clearLocal();
                    else panel.addRemoteLine(msg);
                }
            } catch (Exception e) {
                System.out.println("❌ Disconnected from server.");
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        String ip = JOptionPane.showInputDialog("Enter Server IP (use localhost for same PC):");
        new WhiteboardClient(ip);
    }
}

class DrawingPanel extends JPanel {
    private final List<Message> lines = new ArrayList<>();
    private final ObjectOutputStream out;
    private Color currentColor = Color.BLACK;
    private int brushSize = 3;
    private int prevX, prevY;
    private boolean dragging = false;

    public DrawingPanel(ObjectOutputStream out) {
        this.out = out;
        setBackground(Color.WHITE);

        // Toolbar
        JToolBar toolbar = new JToolBar();
        JButton clearBtn = new JButton("Clear");
        JButton colorBtn = new JButton("Color");
        JButton eraserBtn = new JButton("Eraser");

        JLabel sizeLabel = new JLabel(" Brush size: ");
        JSlider sizeSlider = new JSlider(1, 20, 3);
        sizeSlider.addChangeListener(e -> brushSize = sizeSlider.getValue());

        clearBtn.addActionListener(e -> sendClear());
        colorBtn.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Color", currentColor);
            if (newColor != null) currentColor = newColor;
        });
        eraserBtn.addActionListener(e -> currentColor = Color.WHITE);

        toolbar.add(colorBtn);
        toolbar.add(eraserBtn);
        toolbar.add(clearBtn);
        toolbar.add(sizeLabel);
        toolbar.add(sizeSlider);

        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.NORTH);

        // Mouse drawing
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
                dragging = true;
            }

            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragging) {
                    int x = e.getX();
                    int y = e.getY();
                    Message msg = new Message(prevX, prevY, x, y, currentColor, brushSize, false);
                    lines.add(msg);
                    repaint();
                    sendMessage(msg);
                    prevX = x;
                    prevY = y;
                }
            }
        });
    }

    private void sendMessage(Message msg) {
        try {
            out.writeObject(msg);
            out.flush();
        } catch (IOException ignored) {}
    }

    private void sendClear() {
        lines.clear();
        repaint();
        try {
            out.writeObject(new Message(0, 0, 0, 0, Color.WHITE, 0, true));
            out.flush();
        } catch (IOException ignored) {}
    }

    public void addRemoteLine(Message msg) {
        lines.add(msg);
        repaint();
    }

    public void clearLocal() {
        lines.clear();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Message line : lines) {
            g.setColor(line.color);
            ((Graphics2D) g).setStroke(new BasicStroke(line.size));
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }
}
