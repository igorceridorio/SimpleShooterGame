/*-----------------------------------------------------------
 * University of Nebraska at Kearney 
 * CSIT 422 - Computer Graphics. Fall 2015
 * 
 * AUTHOR: 96740976 - Igor Felipe Ferreira Ceridorio
 * LAST MODIFIED: 9/30/2015
 * ARCHIVE: Cannon.java
 * OBJECTIVE: Defines a pellet object. This class takes 
 * 			  care of moving and printing the pellets.
 *---------------------------------------------------------*/

package simple_shooter;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Pellet extends JPanel {
	ShooterDisplay display; // instance of display for communication
	double pelX, pelY; // x and y location of the pellet
	int pelSize = 3; // pellet's diameter
	int pelStep = 15; // increment each frame for pellet
	int angle = 0; // angle of travel for pellet

	// constructor
	public Pellet(ShooterDisplay display, double pelX, double pelY, int angle) {
		this.display = display;
		this.pelX = pelX;
		this.pelY = pelY;
		this.angle = angle;
	}

	// method responsible for converting degrees to radians
	private double radians(int r) {
		return r * Math.PI / 180;
	}

	// method responsible for moving a pellet
	public void movePellet() {
		
		if(angle == 90){
			pelY = pelY - pelStep * Math.sin(radians(angle));
		} else if(angle < 0) {
			pelX = pelX - pelStep * Math.cos(radians(angle));
			pelY = pelY - pelStep * Math.sin(radians(angle));
		} else {	
			pelX = pelX + pelStep * Math.cos(radians(angle));
			pelY = pelY - pelStep * Math.sin(radians(angle));
		}
		
	}

	// method responsible for drawing and painting the pellet
	public void paint(Graphics g) {
		super.paint(g);
		
		// drawing the pellet
		g.setColor(Color.GREEN);
		g.drawOval((int) pelX, (int) pelY, pelSize, pelSize);
		g.fillOval((int) pelX, (int) pelY, pelSize, pelSize);
	}
}
