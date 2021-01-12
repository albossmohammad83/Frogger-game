// FroggerComponent.java
/*
Program describtion: The main class for Frogger.
 Stores the state of the world, draws it,
 handles the tick() and key() logic.
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Color;

public class FroggerComponent extends JComponent 
{
 	// Size of the game grid
 	public static final int WIDTH = 20;
 	public static final int HEIGHT = 7;
 	// Initial pixel size for each grid square
 	public static final int PIXELS = 50;
	private Image frogImage;
	private Image lilyImage;
	private Image carImage;
 	// Codes to store what is in each square in the grid public static final int EMPTY = 0;
 	public static final int EMPTY = 0;
 	public static final int CAR = 1; 
 	public static final int LILY = 2;
 	private int [][] grid = new int [WIDTH][HEIGHT];
 	Row [] rows = new Row[HEIGHT];
	private int x = 0;
	private int y = 0;
 	private boolean dead;
 	/*
 	Provided utility method to read in an Image object.
 	If the image cannot load,
 	prints error output and returns null.
	Uses Java standard ImageIO.read() method.
 	*/
 	private Image readImage(String filename)
 	{
 		 Image image = null;
 		try
 		{
 			image = ImageIO.read(new File(filename));
 		}
 		catch (IOException e)
 		{
 			System.out.println("Failed to load image '" + filename + "'");
 			e.printStackTrace();
 		}
 		return(image);
 	}
	private void readRow(String file)
 	{ 
 		try
 		{
 		FileReader fr = new FileReader(file);
 		BufferedReader br = new BufferedReader(fr); String line = br.readLine();
 		int count = 1;
 		Row DIRT_ROW =  new Row("dirt 0 0.0");
 		while ((line!=null) && (line!="\n") && (line!="\r"))
 		{
			Row r = new Row(line);
 			rows [count] = r;
 			line = br.readLine();
 			count++;
 			rows[0] = DIRT_ROW;
 			rows[6] = DIRT_ROW; 
 		}
 		}
 		catch (IOException ex) 
 		{
 		System.out.println("File not found!"); }

 	}
 	public FroggerComponent(String filename)
 	{
 		setPreferredSize(new Dimension(WIDTH * PIXELS,
		HEIGHT * PIXELS) );
 		readRow(filename);
 		frogImage = readImage("frog.png");

		lilyImage = readImage("lily.png");

		carImage = readImage("car-pink.png");
 	}
 	public void reset( )
 	{
 		for (int x = 0; x < grid.length; x++)
 		{
 			for (int y= 0; y < grid[x].length; y++)
 			{
 				grid[x][y] = EMPTY;	
 			}
 		}
 		dead = false;
 		
 		x = 0;
 		y = 6;
 		repaint();
	}
	private void moveBy(int dx, int dy)
 	{
 		 if ( (x + dx >= 0 && x + dx < WIDTH) && (y + dy >= 0 && y + dy < HEIGHT) )
 		 {
		 x += dx;
		 y += dy;
 		 }
 	}
 	public boolean isWin()
 	{
 	return (y == 0); 
 	}
 	public void key(int code) throws ArrayIndexOutOfBoundsException
 	{
 		if (code == KeyEvent.VK_UP)
 		{
 			if(y != 0)
 			{
 				moveBy(0, -1);
 			}
 		}
 		if (code == KeyEvent.VK_DOWN)
 		{
 			if (y != HEIGHT)
 			{
 				moveBy(0, 1);
 			}
 		}
 		if (code == KeyEvent.VK_LEFT)
 		{
 			if (x != 0)
 			{
 			moveBy(-1, 0);
 			}
 		}
 		if (code == KeyEvent.VK_RIGHT)
 		{
 			if (x != WIDTH)
 			{
 			moveBy(1, 0);
 			}
 		}
 		if ( grid[x][y] == CAR)
 		{
 			reset();
 		}
 		for (int i = 0; i < rows.length; i++)
 		{
 			if (grid[x][y] != LILY && y == i)
 			{
 				reset();	
 			}
 		}
 		repaint();
 	}

 	public void paintComponent(Graphics g)
 	{
		// Draw road, river, and dirt
 		int squareWidth = WIDTH*PIXELS/ grid.length;
		int squareHeight = HEIGHT*PIXELS/ grid[x].length;
		for (int i = 0; i < rows.length ;i++)
		{
			for (int j = 0; j < grid.length;j++)
			{
				switch (rows[i].getType())
				{
				case Row.WATER:
					g.setColor(Color.BLUE);
					break;
				case Row.ROAD:
					g.setColor(Color.BLACK);
					break;
				default:
					g.setColor(Color.GRAY);
					break;
				}
			g.fillRect(j*PIXELS,i*PIXELS,squareWidth,squareHeight);
			}
		}
		reset();
		// draw frog,lily, and car
		g.drawImage(frogImage, x *PIXELS, y *PIXELS, squareWidth, squareHeight, null);
		
		for (int i = 0; i < rows.length ;i++)				
		{
 			for (int h = 0; h < grid.length; h++)
 			{
 				if (rows[i].getType() == 1 && rows[i].isAdd())
 				{	
 					g.drawImage(lilyImage, h * PIXELS, i * PIXELS, squareWidth, squareHeight, null);
 					grid[h][i] = LILY;
 				}	
				if (rows[i].getType() == 0 && rows[i].isAdd())
				{
					g.drawImage(carImage, h * PIXELS, i * PIXELS, squareWidth, squareHeight, null);
					grid[h][i] = CAR;	
				}
			}
		}
		repaint();
 	}
 
 
 	public void tick(int round)
 	{
		round = 0;
 		while (round == 1000000)
 		{
 			round++;
 			for (int i = 0; i < rows.length;i++)
 			{
 				for (int j = 0; j < grid.length; j++) 
				{
 					if (grid[j][i] == LILY )
 					{
 						if ( rows[i].isTurn( round ))
 						{
 							j++;
 							
 						if (j > WIDTH)
 						{
 							grid[i][j] = EMPTY;
 						}
 						repaint();
 						}
 					}
 					if (grid[j][i] == CAR )
 					{
 						if ( rows[i].isTurn( round ))
 						{
 							j++;
 							
 						if (j > WIDTH)
 						{
 							grid[i][j] = EMPTY;
 						}
 						repaint();
 						}			
 					} 				 		
 				}
 			}
 			if (grid[x][y] == LILY)
 			{
 				moveBy(1,0);
 				repaint();
 			}
 			
 		}
 	}
 	
}
