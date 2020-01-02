package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JApplet implements ActionListener {

    private Timer timer;
    private Game game;

    @Override
    public void init() {
        timer = new Timer(25, this);
        game = new Game();

        setSize(1000,1000);
        setLayout(new BorderLayout());

        add(game, BorderLayout.CENTER);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {
        timer.start();

    }

    @Override
    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
    }

}
