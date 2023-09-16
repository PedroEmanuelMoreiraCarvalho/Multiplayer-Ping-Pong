package server;

import duo_pong.Game;

public class GameStats {
	
	private static int speed = 5;
	private int player1_x, player2_x, player1_y,player2_y,player1_score,player2_score;
	private int ball_x, ball_y;
	private int ball_dir_x = speed, ball_dir_y = speed;
	
	public GameStats() {
		setPlayer1_score(0);
		setPlayer2_score(0);
		setPlayer1_x(0);
		setPlayer2_x(Game.WIDHT - ( 2 * Game.PlayerW));
		setPlayer1_y(0);
		setPlayer2_y(0);
		Restart();
	}
	
	public void Restart() {
		ball_dir_x*= -1;
		setBall_x(375);
		setBall_y(50 + (int) Math.floor(Math.random() * (Game.HEIGHT / 2)));
	}
	
	public void update_ball() {
		setBall_x(getBall_x() + ball_dir_x);
		setBall_y(getBall_y() + ball_dir_y);
		
		if(getBall_y() + ball_dir_y >= getPlayer1_y() &&
		   getBall_y() < getPlayer1_y() + Game.PlayerH &&
		   getBall_x() + ball_dir_x <= getPlayer1_x() + Game.PlayerW ) {
			setBall_x(getPlayer1_x() + Game.PlayerW);
			ball_dir_x *= -1;
		}
		else if(getBall_y() + ball_dir_y >= getPlayer2_y() && 
		   getBall_y() < getPlayer2_y() + Game.PlayerH &&
		   getBall_x() + Game.BALL_SIZE + ball_dir_x >= getPlayer2_x()) {
			setBall_x(getPlayer2_x() - Game.BALL_SIZE);
			ball_dir_x *= -1;
		}
		
		if(getBall_y() < 0 || 
		   getBall_y() > Game.HEIGHT - Game.BALL_SIZE) ball_dir_y *= -1;
		
		if(getBall_x() < 0 - Game.BALL_SIZE) {
			setPlayer2_score(getPlayer2_score() + 1);
			Restart();
			return;
		}else if(getBall_x() > Game.WIDHT) {
			setPlayer1_score(getPlayer1_score() + 1);
			Restart();
			return;
		}
	}
	
	public void UpP1() {
		setPlayer1_y(getPlayer1_y() - getSpeed());
	}
	
	public void UpP2() {
		setPlayer2_y(getPlayer2_y() - getSpeed());
	}
	
	public void DownP1() {
		setPlayer1_y(getPlayer1_y() + getSpeed());
	}
	
	public void DownP2() {
		setPlayer2_y(getPlayer2_y() + getSpeed());
	}

	public int getPlayer1_x() {
		return player1_x;
	}

	public void setPlayer1_x(int player1_x) {
		this.player1_x = player1_x;
	}

	public int getPlayer2_x() {
		return player2_x;
	}

	public void setPlayer2_x(int player2_x) {
		this.player2_x = player2_x;
	}

	public int getPlayer1_y() {
		return player1_y;
	}

	public void setPlayer1_y(int player1_y) {
		this.player1_y = player1_y;
	}

	public int getPlayer2_y() {
		return player2_y;
	}

	public void setPlayer2_y(int player2_y) {
		this.player2_y = player2_y;
	}

	public int getPlayer1_score() {
		return player1_score;
	}

	public void setPlayer1_score(int player1_score) {
		this.player1_score = player1_score;
	}

	public int getPlayer2_score() {
		return player2_score;
	}

	public void setPlayer2_score(int player2_score) {
		this.player2_score = player2_score;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		GameStats.speed = speed;
	}

	public int getBall_x() {
		return ball_x;
	}

	public void setBall_x(int ball_x) {
		this.ball_x = ball_x;
	}

	public int getBall_y() {
		return ball_y;
	}

	public void setBall_y(int ball_y) {
		this.ball_y = ball_y;
	}
	
}
