package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {
	
	private int x, y;
	private int widht, height;
		
	public Player(int x, int y, int widht, int height) {
		this.setX(x);
		this.setY(y);
		this.widht = widht;
		this.height = height;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(this.getX(), this.getY(), widht, height);
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
