// FroggerFrame.java
/*
Implements the Frogger window.
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class FroggerFrame extends JFrame implements KeyListener, ActionListener
{
	public static final int DELAY = 400; // milliseconds
	public static final String WORLD = "world.txt";
	private javax.swing.Timer myTimer;
	private FroggerComponent frogger;
 	JButton new_game;
 	JLabel label;
 	int level = 1;
 	int round = 0;
 	public FroggerFrame( )
 	{
 		setTitle("Frogger");
 		
 		Container container = getContentPane();
 		container.setLayout(new BorderLayout());
 		
 		frogger = new FroggerComponent(WORLD);
 		container.add(frogger, BorderLayout.CENTER);
 		frogger.addKeyListener(this); // adding the KeyListener
 		frogger.setFocusable(true); // setting focusable to true
 		JPanel panel = new JPanel( );
 		container.add(panel, BorderLayout.SOUTH); 
 			
 		new_game = new JButton("New Game");
 		new_game.addActionListener(this);
 		new_game.setFocusable(false);
 		panel.add(new_game);
 		
 		myTimer = new javax.swing.Timer(DELAY, this);
 		myTimer.start( ); 
 		
 		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frogger.requestFocusInWindow( );
 		pack( );
 		setVisible(true); 

 				
 		
 	}
 	public void keyTyped(KeyEvent e) 
 	{ }
	public void keyReleased(KeyEvent e)
	{ }
 	public void keyPressed(KeyEvent e)
 	{
 		frogger.key(e.getKeyCode( ));
 	}
 	public void actionPerformed(ActionEvent e)
 	{

 	}
 	public static void main(String[ ] args)
 	{ 
 		FroggerFrame frame = new FroggerFrame( );
 	}
}