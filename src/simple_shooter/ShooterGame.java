/*-----------------------------------------------------------
 * University of Nebraska at Kearney 
 * CSIT 422 - Computer Graphics. Fall 2015
 * 
 * AUTHOR: 96740976 - Igor Felipe Ferreira Ceridorio
 * LAST MODIFIED: 9/30/2015
 * ARCHIVE: ShooterGame.java
 * OBJECTIVE: This class has the game logic. It creates the
 * 			  first triangles of the game, verifies if
 * 			  a pellet hit any triangle, and produces the
 * 			  minor triangles when a bigger one is hit.
 *---------------------------------------------------------*/

package simple_shooter;

import java.util.ArrayList;
import java.util.Random;

public class ShooterGame {

	ShooterDisplay display; // instance of display for communication
	ArrayList<Triangle> triangles = new ArrayList<Triangle>(); // array of triangles
	Cannon cannon; // the cannon of the game
	int minTriSide; // minimum triangle side size allowed

	// constructor
	public ShooterGame(ShooterDisplay display) {
		this.display = display;
		this.minTriSide = 20;

		// creating the cannon of the game
		cannon = new Cannon(display);

		// creating the first triangle(s) of the game
		genInitTriangles(6);
	}

	// method responsible for knowing whether a pellet hit or not one of the triangles
	public boolean pelHitTriangle(int pelIndex) {

		int triToRemove = -1;

		for (int i = 0; i < triangles.size(); i++) {
			// check if the pellet hit the triangle
			if (triangles.get(i).hitTriangle(cannon.pellets.get(pelIndex).pelX,
					cannon.pellets.get(pelIndex).pelY))
				triToRemove = i;
		}

		if (triToRemove != -1) {
			remGenTriangle(triToRemove);
			return true;
		}

		return false;

	}

	// method responsible for generating the first triangles
	public void genInitTriangles(int qt) {

		for (int i = 0; i < qt; i++) {
			// generates the random coordinates for the triangle
			Random generator = new Random();
			double coord[] = { 0, 0 };

			// generates a direction for the triangle
			int dir = genDirection();

			coord[0] = generator.nextInt(((display.getWidth() - 100) + 1) + 100);
			coord[1] = generator.nextInt(((display.getHeight() - 100) + 1) + 100);

			// adds the triangle to the Array List
			Triangle triangle = new Triangle(display, coord, dir);
			triangles.add(triangle);
		}

	}

	// method responsible for generating the direction for new triangles
	public int genDirection() {
		Random generator = new Random();
		int newDirection = 0;

		int number = generator.nextInt(2);

		// generates a positive new direction
		if (number == 0)
			newDirection = generator.nextInt(165) + 1;
		// generates a negative new direction
		else
			newDirection = (generator.nextInt(165) + 1) * (-1);

		return newDirection;
	}

	// method to remove a hit triangle and create the minor ones
	public void remGenTriangle(int triIndex) {
		// get the side size of the current triangle
		double newSize = triangles.get(triIndex).getTriSide() / 2;
		// get the middle point of the current triangle
		double[] coordinates = triangles.get(triIndex).getTriCoord();

		// removes the hit triangle
		triangles.remove(triIndex);

		// creates the new triangles with the half size of the previous one
		if ((newSize) > minTriSide) {
			for (int i = 0; i < 4; i++) {
				Triangle newTriangle = new Triangle(display, newSize, coordinates, genDirection());
				triangles.add(newTriangle);
			}
		}
	}
	
	// method responsible for removing triangles out of window range
	public void removeTriangle() {
		for (int i = 0; i < triangles.size(); i++) {
			if ((triangles.get(i).triangleX > display.getWidth() + 50)
					|| (triangles.get(i).triangleX < 0 - 50) 
					|| (triangles.get(i).triangleY < 0 - 50) 
					|| (triangles.get(i).triangleY > display.getHeight() + 50))
				triangles.remove(i);
		}
	}
	
}
