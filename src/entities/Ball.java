package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import duo_pong.Game;

public class Ball {
	
	private int x, y;
	private int widht = Game.BALL_SIZE, height = Game.BALL_SIZE;
		
	public Ball(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillOval(this.getX(), this.getY(), widht, height);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
}
