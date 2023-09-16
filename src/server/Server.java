package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	static ServerSocket server;
	static ArrayList<ClientHandler> sockets_connecteds = new ArrayList<ClientHandler>();
	public static GameStats game_stats = new GameStats();
	public static boolean GAME_START = false;
	
	public Server(int port) throws IOException {
		server = new ServerSocket(port);
		game_stats.Restart();
		
		while(true) {
			Socket client = server.accept();
			
			ClientHandler client_handler = new ClientHandler(client);
			sockets_connecteds.add(client_handler);
			new Thread(client_handler).start();
			
			if(sockets_connecteds.size() == 2) {
				GameLoop game_loop = new GameLoop();
				new Thread(game_loop).start();
			}
		}
	}
	
	public static void SendToClient(String message) throws IOException {
		for(int i = 0; i < sockets_connecteds.size(); i++) {
			ClientHandler client_hundler = sockets_connecteds.get(i);
			client_hundler.SendMessage(message);
		}
	}
	
	public static void Analyze(String message, ClientHandler author) throws IOException {
			
		if(sockets_connecteds.get(0).equals(author)) { //player1
			if(message.equals("UP")) game_stats.UpP1();
			else if(message.equals("DOWN")) game_stats.DownP1();
		}else if(sockets_connecteds.get(1).equals(author)){ //player2
			if(message.equals("UP")) game_stats.UpP2();
			else if(message.equals("DOWN")) game_stats.DownP2();
		}
	}
	
	public static void SendStats() throws IOException {
		String gamedata = game_stats.getPlayer1_x()+":"+game_stats.getPlayer1_y()+":"+
						  game_stats.getPlayer2_x()+":"+game_stats.getPlayer2_y()+":"+
						  game_stats.getBall_x()+":"+game_stats.getBall_y()+":"+
						  game_stats.getPlayer1_score()+":"+game_stats.getPlayer2_score();
		SendToClient(gamedata);
	}
	
	public static class GameLoop implements Runnable{
		
		public void run() {			
			try {
				synchronized (this) {					
					while(true) {
						game_stats.update_ball();
						SendStats();
						this.wait(10);
					}
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		try {
			@SuppressWarnings("unused")
			Server serv = new Server(8000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class ClientHandler implements Runnable{
		private final Socket client_socket;
		private InputStreamReader inSR = null;
		private OutputStreamWriter outSW = null;
		private BufferedReader bin = null;
		private BufferedWriter bout = null;
		
		public ClientHandler(Socket client_socket) throws IOException{
			this.client_socket = client_socket;
			this.inSR = new InputStreamReader(client_socket.getInputStream());
			this.outSW = new OutputStreamWriter(client_socket.getOutputStream());
			
			this.bin = new BufferedReader(inSR);
			this.bout = new BufferedWriter(outSW);
		}
		
		public void SendMessage(String message) throws IOException{
			bout.write(message);
			bout.newLine();
			bout.flush();
		}
		
		public void run() {
			try {
				String line;
				
				while((line = bin.readLine()) != null) {
//					SendToClient(line,this);
					Analyze(line,this);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(bout == null) {
						bout.close();
					}
					if(bin == null) {
						bin.close();
						client_socket.close();
					}
				}
				catch (IOException e) {
                    e.printStackTrace();
                }
			}
		}
	}
}
