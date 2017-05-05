package com.stephancode.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseMotionListener, MouseWheelListener, MouseListener{

	public static int wheel = 0;
	
	public Mouse(Game game){
		game.addMouseMotionListener(this);
		game.addMouseWheelListener(this);
		game.addMouseListener(this);
	}

	public void mouseDragged(MouseEvent e) {
		int xx = e.getX() / Game.grid_size;
		int yy = e.getY() / Game.grid_size;
		
		Game.map[xx + yy * Game.w] = 1;
	}

	public void mouseMoved(MouseEvent e) {
		
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		wheel = e.getWheelRotation();
	}

	public void mouseClicked(MouseEvent e) {
		int xx = e.getX() / Game.grid_size;
		int yy = e.getY() / Game.grid_size;
		
		Game.map[xx + yy * Game.w] = 1;
		if(e.getButton() == e.BUTTON3){
			if(Game.can_update) Game.can_update = false;
			else Game.can_update = true;
		}
	}

	public void mouseEntered(MouseEvent arg0) {	}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}
	

}
