/*-----------------------------------------------------------
 * University of Nebraska at Kearney 
 * CSIT 422 - Computer Graphics. Fall 2015
 * 
 * AUTHOR: 96740976 - Igor Felipe Ferreira Ceridorio
 * LAST MODIFIED: 9/30/2015
 * ARCHIVE: ShooterDisplay.java
 * OBJECTIVE: This class creates the display of the game.
 *            It is also responsible for the timer, as well 
 *            the key pressure events and it calls the other
 *            classes paint method, to show the objects of 
 *            the game on screen.
 *---------------------------------------------------------*/

package simple_shooter;

import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ShooterDisplay extends JPanel {

	ShooterGame game;
	Timer timer;

	// constructor
	public ShooterDisplay(int x, int y) {

		// setting the size of the window
		this.setSize(x, y);

		// adding the listener for the window resizing
		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				// updates the cannon position on the screen
				game.cannon.updateCannon();
			}

			public void componentHidden(ComponentEvent arg0) {
			}

			public void componentMoved(ComponentEvent arg0) {
			}

			public void componentShown(ComponentEvent arg0) {
			}
		});

		// adding the listener for the cannon movements
		this.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent ke) {
				int code = ke.getKeyCode();
				if (code == KeyEvent.VK_UP) {
					game.cannon.newPellet();
				} else if (code == KeyEvent.VK_RIGHT) {
					game.cannon.rotateRight();
					game.display.repaint();
				} else if (code == KeyEvent.VK_LEFT) {
					game.cannon.rotateLeft();
					game.display.repaint();
				}

			}

			public void keyReleased(KeyEvent arg0) {
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		// activation of the timer
		timer = new Timer(15, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				// this variable stores a pellet index in case it hit a triangle
				int pelIndex = -1;
				
				// moving all the pellets present in the game
				for (int i = 0; i < game.cannon.pellets.size(); i++) {
					game.cannon.pellets.get(i).movePellet();
					
					// removing triangles out of range
					game.removeTriangle();
					
					// if the pellet hit a triangle stores its index
					if (game.pelHitTriangle(i))
						pelIndex = i;
				}
			
				// moving all the triangles present in the game
				for (int i = 0; i < game.triangles.size(); i++) {
					game.triangles.get(i).moveTriangle();
				}
	
				// remove the pellet that hit the triangle from its Array List
				if(pelIndex != -1)
					game.cannon.removePellet(pelIndex);
				
				// removing pellets out of range
				game.cannon.removePellet();
				
				// repainting the display
				game.display.repaint();
				
				// if the game is over pops a message indicating it and ends the process
				if(game.triangles.size() == 0){
					 JOptionPane.showMessageDialog(null, "Game Over!");
					 System.exit(0);
				}
				
			}
		});
		timer.start();
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);

		game = new ShooterGame(this);

	}

	public void paint(Graphics g) {
		super.paint(g);

		// paint method for the cannon
		game.cannon.paint(g);

		// paint method for all the triangles present in the array list
		for (int i = 0; i < game.triangles.size(); i++) {
			game.triangles.get(i).paint(g);
		}

		// paint method for the pellets present in the array list
		for (int i = 0; i < game.cannon.pellets.size(); i++) {
			game.cannon.pellets.get(i).paint(g);
		}

	}

}
