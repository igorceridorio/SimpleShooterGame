/*-----------------------------------------------------------
 * University of Nebraska at Kearney 
 * CSIT 422 - Computer Graphics. Fall 2015
 * 
 * AUTHOR: 96740976 - Igor Felipe Ferreira Ceridorio
 * LAST MODIFIED: 9/30/2015
 * ARCHIVE: Cannon.java
 * OBJECTIVE: Defines a cannon object. This class takes
 * 			  care of rotate left, rotate right, update 
 * 			  cannon coordinates when necessary as well as
 * 			  creating and removing new pellets, and 
 * 			  printing the cannon itself. 
 *---------------------------------------------------------*/

package simple_shooter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Cannon extends JPanel {

	ShooterDisplay display; // instance of display for communication
	ArrayList<Pellet> pellets = new ArrayList<Pellet>(); // ArrayList of pellets
	double basisX, basisY; // x and y location of the cannon's basis
	double pivotX, pivotY; // pivots for the cannon's aim
	double aimX, aimY; // x and y location of the cannon's aim
	int basisSize; // basis' diameter
	int rotDirection; // positive indicates right, negative indicates left
	int angle; // tracks the angle of the aim
	int rotMovement; // tracks the movement of the aim
	int rotMin; // minimum number of movements allowed to the left
	int rotMax; // maximum number of movements allowed to the right

	// constructor
	public Cannon(ShooterDisplay display) {
		this.display = display;
		this.basisSize = 50;
		this.rotDirection = 0;
		this.rotMovement = 0;
		this.rotMin = -13;
		this.rotMax = 13;
		this.pivotX = display.getWidth() / 2;
		this.pivotY = display.getHeight() - (this.basisSize / 2);
		this.aimX = pivotX;
		this.aimY = pivotY - (this.basisSize / 2);
		this.angle = 90;

	}

	// method responsible for rotating the aim to the right
	public void rotateRight() {

		if (rotMovement < rotMax) {
			rotMovement += 1;

			// changing the direction of the rotation
			if (rotDirection <= 0)
				rotDirection = 6;
			
			// recalculating the angle of the movement
			angle -= rotDirection;

			// converting the angle to radians
			double angle = radians(rotDirection);

			// calculating the new x and y points
			double newX = (aimX * Math.cos(angle) - aimY * Math.sin(angle)
					- pivotX * Math.cos(angle) + pivotY * Math.sin(angle) + pivotX);

			double newY = (aimX * Math.sin(angle) + aimY * Math.cos(angle)
					- pivotX * Math.sin(angle) - pivotY * Math.cos(angle) + pivotY);

			// the points receive the new position
			aimX = newX;
			aimY = newY;
		}
	}

	// method responsible for rotating the aim to the left
	public void rotateLeft() {

		if (rotMovement > rotMin) {
			rotMovement -= 1;

			// changing the direction of the rotation
			if (rotDirection >= 0)
				rotDirection = -6;
			
			// recalculating the angle of the movement
			angle -= rotDirection;

			// converting the angle to radians
			double angle = radians(rotDirection);

			// calculating the new x and y points
			double newX = (aimX * Math.cos(angle) - aimY * Math.sin(angle)
					- pivotX * Math.cos(angle) + pivotY * Math.sin(angle) + pivotX);

			double newY = (aimX * Math.sin(angle) + aimY * Math.cos(angle)
					- pivotX * Math.sin(angle) - pivotY * Math.cos(angle) + pivotY);

			aimX = newX;
			aimY = newY;
		}
	}

	// method responsible for updating the position of the cannon
	public void updateCannon() {
		this.basisX = (display.getWidth() - this.basisSize) / 2;
		this.basisY = display.getHeight() - this.basisSize;
		this.pivotX = display.getWidth() / 2;
		this.pivotY = display.getHeight() - (this.basisSize / 2);
		this.aimX = pivotX;
		this.aimY = pivotY - (this.basisSize / 2);
		this.rotMovement = 0;
		this.angle = 90;
	}

	// method responsible for converting degrees to radians
	private double radians(int r) {
		return r * Math.PI / 180;
	}

	// method responsible for drawing and painting the cannon
	public void paint(Graphics g) {
		super.paint(g);

		// drawing the cannon's basis
		g.setColor(Color.RED);
		g.drawOval((int) basisX, ((int) basisY + this.basisSize / 2),
				basisSize, basisSize);
		g.fillOval((int) basisX, ((int) basisY + this.basisSize / 2),
				basisSize, basisSize);

		// drawing the cannon's aim
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(6));
		g2.drawLine((int) pivotX, (int) pivotY, (int) aimX, (int) aimY);

	}

	// method responsible for creating a new pellet
	public void newPellet() {
		// creating the new pellet
		Pellet pellet = new Pellet(display, aimX, aimY, angle);
		pellets.add(pellet);
	}

	// method responsible for removing pellets out of window range
	public void removePellet() {
		for (int i = 0; i < pellets.size(); i++) {
			if ((pellets.get(i).pelX > display.getWidth())
					|| (pellets.get(i).pelX < 0) || (pellets.get(i).pelY < 0))
				pellets.remove(i);
		}
	}
	
	// override method responsible for removing a specific pellet
	public void removePellet(int pelIndex){
		pellets.remove(pelIndex);
	}

}
