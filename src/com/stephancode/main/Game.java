package com.stephancode.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;

	public static int grid_size = 20;
	public static int w = 512;
	public static int h = 512;
	public static boolean can_update = false;
	
	private int WIDTH = 1280;
	private int HEIGHT = WIDTH * 9 / 16;
	
	private boolean running = false;
	
	public static byte[] map = new byte[w * h];
	public static byte[] next = new byte[w * h];
	
	JFrame jf;
	Dimension d;
	
	public Game(){
		jf = new JFrame("GOLAD");
		d = new Dimension(WIDTH, HEIGHT);
		
		jf.setPreferredSize(d);
		jf.pack();
		jf.add(this);
		jf.setVisible(true);
		jf.setResizable(true);
		jf.setDefaultCloseOperation(3);
		new Mouse(this);
		Random r = new Random();
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				if(r.nextInt(3) == 0)
					map[x + y * w] = 1;
				else
					map[x + y * w] = 0;
				
				
				
				next[x + y * w] = 0;
			}
		}
		
	}
	
	private void start(){
		running = true;
		new Thread(this).start();
	}
	
	public void run() {
		int fps = 0;
		int ups = 0;
		
		double then = System.nanoTime();
		double fpsTimer = System.currentTimeMillis();
		double delta = 0;
		double nspt = 1000000000 / 30d;
		
		while(running){
			double now = System.nanoTime();
			delta += (now - then) / nspt;
			then = now;
			while(delta >= 1){
				ups++;
				update();
				delta--;
			}
		
			fps++;
			render();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			if(System.currentTimeMillis() - fpsTimer >= 1000){
				fps = ups = 0;
				fpsTimer += 1000;
			}
		}
	}
	
	private void update(){
		if(Mouse.wheel > 0) grid_size--;
		if(Mouse.wheel < 0) grid_size++;
		Mouse.wheel = 0;
		if(can_update){
			for(int y = 0; y < h; y++){
				for(int x = 0; x < w; x++){
					if(map[x + y * w] == 1 && getNeighbours(x,  y) < 2)
						next[x + y * w] = 0;
					
					if(map[x + y * w] == 1 && (getNeighbours(x,  y) == 2 || getNeighbours(x,  y) == 3))
						next[x + y * w] = 1;
					
					if(map[x + y * w] == 1 && getNeighbours(x,  y) > 3)
						next[x + y * w] = 0;
					
					if(map[x + y * w] == 0 && getNeighbours(x,  y) == 3)
						next[x + y * w] = 1;
					
				}
			}
			
			for(int y = 0; y < h; y++){
				for(int x = 0; x < w; x++){
					map[x + y * w] = next[x + y * w];
				}
			}
		}
		
	}
	
	private int getNeighbours(int x, int y){
		int neighbours = 0;
		
		if(x + 1 > 0 && x + 1 < w)
		if(map[x + 1 + y * w] == 1) neighbours++;
		
		if(x - 1 > 0 && x + 1 < w)
		if(map[x - 1 + y * w] == 1) neighbours++;

		if(y - 1 > 0 && y - 1 < h)
		if(map[x + (y - 1) * w] == 1) neighbours++;
		
		if(y + 1 > 0 && y + 1 < h)
		if(map[x + (y + 1) * w] == 1) neighbours++;

		if(y - 1 > 0 && y - 1 < h && x + 1 > 0 && x + 1 < w)
		if(map[x + 1 + (y - 1) * w] == 1) neighbours++;

		if(y + 1 > 0 && y + 1 < h && x + 1 > 0 && x + 1 < w)
		if(map[x + 1 + (y + 1) * w] == 1) neighbours++;

		if(y - 1 > 0 && y - 1 < h && x - 1 > 0 && x - 1 < w)
		if(map[x - 1 + (y - 1) * w] == 1) neighbours++;

		if(y + 1 > 0 && y + 1 < h && x - 1 > 0 && x - 1 < w)
		if(map[x - 1 + (y + 1) * w] == 1) neighbours++;
		
		return neighbours;
	}
	
	private void render(){
		BufferStrategy bs = getBufferStrategy();
		if(bs == null){
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.BLACK);
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				if(map[x + y * w] == 0)
					g.drawRect(x * grid_size, y * grid_size,  0, 0);
				else g.fillRect(x * grid_size, y * grid_size,  grid_size, grid_size);
			}
		}
		

		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args){
		new Game().start();
	}
	
}


