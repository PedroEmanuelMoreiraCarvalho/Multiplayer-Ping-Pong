package duo_pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Ball;
import entities.Player;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener {
	
	public static final int WIDHT = 900, HEIGHT = 500, BALL_SIZE = 20;
	
	public static boolean up = false, down = false;
	public static Player player1, player2;
	public static Ball ball;

	public static Client client;
	
	public static int PlayerW = 15, PlayerH = 100;
	public static int player1_score = 0, player2_score = 0;
	
	public static void main(String[] args) {
		player1 = new Player(10,10,PlayerW,PlayerH);
		player2 = new Player(40,10,PlayerW,PlayerH);
		ball = new Ball(375,225);

		client = new Client(8000);
		
		JFrame frame = new JFrame("Pong");
		
		//39 = without header 
		frame.setSize(WIDHT, HEIGHT + 39);
		
		Game game = new Game();

		frame.add(game);
		frame.addKeyListener(game);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//game_loop
		GameLoop(game);
	}
	
	public static void GameLoop(Game game) {
		try {
			synchronized (game) {					
				while(true) {
					if(up) client.emit("UP");
					else if(down) client.emit("DOWN");
					game.repaint();
					game.wait(10);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, WIDHT, HEIGHT);
		g2d.setColor(Color.YELLOW);
		g2d.setFont(new Font("Courier", Font.BOLD,25));
		g2d.drawString(String.valueOf(player1_score), 40, 40);
		g2d.setFont(new Font("Courier", Font.BOLD,25));
		g2d.drawString(String.valueOf(player2_score), WIDHT-70, 40);
		player1.render(g2d);
		player2.render(g2d);
		ball.render(g2d);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_W) {
			up = true;
		}else if (keyCode == KeyEvent.VK_S) {
			down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_W) {
			up = false;
		}else if (keyCode == KeyEvent.VK_S) {
			down = false;
		}
	}
}
