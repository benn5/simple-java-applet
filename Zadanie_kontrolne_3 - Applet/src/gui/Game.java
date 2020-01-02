package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Game extends JComponent {

    private Ellipse2D.Double ball = new Ellipse2D.Double(100, 150, 15, 15);
    private RoundRectangle2D.Double bat =new RoundRectangle2D.Double(200,200,200,20,20,20);

    private double speed = 10.0;
    private double batSpeed = 7.0;
    private int xDirectionBall = 1;
    private int yDirectionBall = 1;

    private BufferedImage buffer;
    private boolean checkIntersection = true;



    public Game(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                bat.x = e.getX() - bat.getWidth() / 2;
                bat.y = e .getY() - bat.getHeight() / 2;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ball.x = e.getX();
                ball.y = e.getY();
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {

                int key = e.getKeyCode();

                switch (key){
                    case KeyEvent.VK_UP:
                        bat.y -= batSpeed;
                        break;
                    case KeyEvent.VK_DOWN:
                        bat.y += batSpeed;
                        break;
                    case KeyEvent.VK_LEFT:
                        bat.x -= batSpeed;
                        break;
                    case KeyEvent.VK_RIGHT:
                        bat.x += batSpeed;
                        break;
                }

                return false;
            }
        });


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buffer = null;
            }
        });


        Cursor hiddenCursor = getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(1,1), "");

        setCursor(hiddenCursor);

    }

    @Override
    protected void paintComponent(Graphics g) {

        if(buffer == null){
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }

        Graphics2D g2 = (Graphics2D)buffer.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0,0,getWidth(), getHeight());

        g2.setColor(Color.RED);
        g2.fill(ball);

        g2.setColor(Color.BLUE);
        g2.fill(bat);

        g.drawImage(buffer, 0,0, null);

    }


    public void update(){

        ball.x += xDirectionBall * speed;
        ball.y += yDirectionBall * speed;

        if(ball.x < 0){
            xDirectionBall = 1;
            ball.x = 0;

        }
        else if(ball.x > getWidth() - ball.getBounds().width) {
            xDirectionBall = -1;
            ball.x = getWidth() - ball.getBounds().width;
        }


        if(ball.y < 0){
            yDirectionBall = 1;
            ball.y = 0;

        }
        else if(ball.y > getHeight() - ball.getBounds().height) {
            yDirectionBall = -1;
            ball.y = getHeight() - ball.getBounds().height;
        }

        if(ball.intersects(bat.getBounds2D())){
            if(checkIntersection) {
                yDirectionBall = -yDirectionBall;
                checkIntersection = false;
            }
        }
        else {
            checkIntersection = true;
        }

        repaint();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }



}
