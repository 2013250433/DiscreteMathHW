package ho.jin.game;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	private final int WIDTH = 640;
	private final int HEIGHT = 480;
	
	public Game(){
		JFrame frame = new JFrame("MiniCraft");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	private void start(){
		new Thread(this).start();
	}
	
	public void run(){
		render();
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
			
	}
	
	public static void main(String args[]){
		new Game().render();
		
	}
}