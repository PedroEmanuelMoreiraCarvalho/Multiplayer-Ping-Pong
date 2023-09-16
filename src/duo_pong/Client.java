package duo_pong;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	private static Socket socket = null;
	private static InputStreamReader inSR = null;
	private static OutputStreamWriter outSW = null;
	private static BufferedReader bin = null;
	private static BufferedWriter bout = null;
	public static boolean signal = false;
	public static String signal_message = "";
	
	public Client(int port){
		
		try {
			//put server address here
			socket = new Socket("localhost", port);
			
			inSR = new InputStreamReader(socket.getInputStream());
			outSW = new OutputStreamWriter(socket.getOutputStream());
			
			bin = new BufferedReader(inSR);
			bout = new BufferedWriter(outSW);
			
			Listenner listenner = new Listenner(bin);
			new Thread(listenner).start();
			
		}catch(IOException e) {
			e.getStackTrace();
		}
	}
	
	public void emit(String message) {
		try {
			bout.write(message);
			bout.newLine();
			bout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Analyze(String message) {
		String[] values = message.split(":");
		int[] converted_values = new int[values.length];
		
		for(int i = 0; i < values.length; i++) {
			converted_values[i] = Integer.parseInt(values[i]);
		}
		
		//set values in game

		Game.player1.setX(converted_values[0]);
		Game.player1.setY(converted_values[1]);
		Game.player2.setX(converted_values[2]);
		Game.player2.setY(converted_values[3]);
		Game.ball.setX(converted_values[4]);
		Game.ball.setY(converted_values[5]);
		Game.ball.setY(converted_values[5]);
		Game.player1_score = converted_values[6];
		Game.player2_score = converted_values[7];
	}
	
	private static class Listenner implements Runnable{
		
		private BufferedReader bin;
		
		public Listenner(BufferedReader socket_reader) {
			this.bin = socket_reader;
		}
		
		public void run() {
			try {
				while(true) {
					Analyze(bin.readLine());
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
