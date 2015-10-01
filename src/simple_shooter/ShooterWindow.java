/*-----------------------------------------------------------
 * University of Nebraska at Kearney 
 * CSIT 422 - Computer Graphics. Fall 2015
 * 
 * AUTHOR: 96740976 - Igor Felipe Ferreira Ceridorio
 * LAST MODIFIED: 9/30/2015
 * ARCHIVE: ShooterWindow.java
 * OBJECTIVE: This class creates the window of the game.
 *---------------------------------------------------------*/

package simple_shooter;

import javax.swing.*;

@SuppressWarnings("serial")
public class ShooterWindow extends JFrame{

	private int startX = 1300;
	private int startY = 800;
	
	public ShooterWindow(){
		super("Simple Shooter Game!");
		ShooterDisplay sd = new ShooterDisplay(startX, startY);
		add(sd);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(startX, startY);
	}
	
	public static void main(String[] args){
		new ShooterWindow();
	}
	
}