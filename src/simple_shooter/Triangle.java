/*-----------------------------------------------------------
 * University of Nebraska at Kearney 
 * CSIT 422 - Computer Graphics. Fall 2015
 * 
 * AUTHOR: 96740976 - Igor Felipe Ferreira Ceridorio
 * LAST MODIFIED: 9/30/2015
 * ARCHIVE: Triangle.java
 * OBJECTIVE: Defines a triangle object. This class takes
 * 			  care of move, rotate and check whether a pellet
 * 			  hit a triangle or not. It is also responsible
 * 			  for painting the triangle itself.
 *---------------------------------------------------------*/

package simple_shooter;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Triangle extends JPanel {

	ShooterDisplay display; // instance of display for communication
	double side, sideHalf, h; // side, side half and height of the triangle
	double triangleX, triangleY; // x and y location of the triangle
	double xA, yA, xB, yB, xC, yC; // points of the triangle
	double rotatedPoint[]; // array responsible for storing a rotated point
	int dir; // angle of travel
	int triStep = 15; // increment each frame for triangle
	int rotInclination; // degree inclination of the rotation

	// constructor
	public Triangle(ShooterDisplay display, double[] coordinates, int newDir){
		this.display = display;
		this.side = 90;
		this. dir = newDir;
		this.sideHalf = 0.5F * this.side;
		this.h = this.sideHalf * (double) Math.sqrt(3);
		this.triangleX = coordinates[0];
		this.triangleY = coordinates[1];
		this.rotInclination = 0;
	}
	
	// override constructor for the minor triangles
	public Triangle(ShooterDisplay display, double newSide, double[] coordinates, int newDir) {
		this.display = display;
		this.side = newSide;
		this.dir = newDir;
		this.sideHalf = 0.5F * this.side;
		this.h = this.sideHalf * (double) Math.sqrt(3);
		this.triangleX = coordinates[0];
		this.triangleY = coordinates[1];
		this.rotInclination = 0;
	}
	
	// method used to get the side of the current triangle
	double getTriSide(){
		return this.side;
	}
	
	// method used to get the direction of the current triangle
	int getTriDir(){
		return this.dir;
	}
	
	// method used to get the center point of the current triangle
	double[] getTriCoord(){
		double[] ret = {triangleX, triangleY};
		return ret;
	}
	
	// method responsible for converting degrees to radians
	private double radians(int r) {
		return r * Math.PI / 180;
	}

	// methods responsible for rounding x and y values
	int iX(double x) {
		return (int) Math.round(x);
	}

	int iY(double y) {
		return (int) Math.round(y);
	}

	// method responsible for moving the triangle
	public void moveTriangle() {
		triangleX = triangleX + triStep * Math.cos(radians(dir));
		if (triangleX > display.getWidth() - this.sideHalf || triangleX < 0) {
			dir = 180 - dir;
		} else if (triangleY > display.getHeight() - this.sideHalf
				|| triangleY < 0) {
			dir = -dir;
		}
		triangleY = triangleY - triStep * Math.sin(radians(dir));
	}

	// method responsible for rotating the triangle
	public double[] rotateTriPoint(double x, double y, double pivotX, double pivotY) {
		
		//converting the angle to radians
		double angle = radians(rotInclination);
		
		//calculating the new x and y points
		double newX = (int) (x * Math.cos(angle) - y * Math.sin(angle) - pivotX
				* Math.cos(angle) + pivotY * Math.sin(angle) + pivotX);

		double newY = (int) (x * Math.sin(angle) + y * Math.cos(angle) - pivotX
				* Math.sin(angle) - pivotY * Math.cos(angle) + pivotY);

		// incrementing the rotation angle for the next iteration
		if (rotInclination < 360)
			rotInclination += 1;
		else
			rotInclination = 0;
		
		double newPoint[] = { newX, newY };
		return newPoint;
	}
	
	// method responsible for checking whether a pellet hit the triangle or not
	public boolean hitTriangle(double pelX, double pelY){
		
		// barycentric coordinates of the fouth point given the three points of the triangle
		double alpha = ((yB - yC) * (pelX - xC) + (xC - xB) * (pelY - yC)) /
				((yB - yC) * (xA - xC) + (xC - xB) * (yA - yC));
		
		double beta = ((yC - yA) * (pelX - xC) + (xA - xC) * (pelY - yC)) /
				((yB - yC) * (xA - xC) + (xC - xB) * (yA - yC));
		
		double gamma = 1.0f - alpha - beta;
		
		// if all are positive, then the pellet hit the triangle
		if(alpha > 0 && beta > 0 && gamma > 0){
			return true;
		}
			
		return false;
	}

	// method responsible for drawing and painting the triangle
	public void paint(Graphics g) {
		super.paint(g);

		// defining the points where the triangle will be drawn
		xA = this.triangleX - this.sideHalf;
		yA = this.triangleY - 0.5F * this.h;
		xB = this.triangleX + this.sideHalf;
		yB = yA;
		xC = this.triangleX;
		yC = this.triangleY + 0.5F * this.h;
		
		// calling the rotation methods for the three points of the triangle
		rotatedPoint = rotateTriPoint(xA, yA, triangleX, triangleY);
		xA = rotatedPoint[0];
		yA = rotatedPoint[1];
		rotatedPoint = rotateTriPoint(xB, yB, triangleX, triangleY);
		xB = rotatedPoint[0];
		yB = rotatedPoint[1];
		rotatedPoint = rotateTriPoint(xC, yC, triangleX, triangleY);
		xC = rotatedPoint[0];
		yC = rotatedPoint[1];

		// drawing the triangle
		
		g.setColor(Color.BLUE);
		
		g.drawLine(iX(xA), iY(yA), iX(xB), iY(yB));
		g.drawLine(iX(xB), iY(yB), iX(xC), iY(yC));
		g.drawLine(iX(xC), iY(yC), iX(xA), iY(yA));
		
		// painting the triangle
		int xPoints[] = { iX(xA), iX(xB), iX(xC) };
		int yPoints[] = { iY(yA), iY(yB), iY(yC) };
		g.fillPolygon(xPoints, yPoints, 3);
		
	}

}
