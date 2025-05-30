import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;	
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;
import java.awt.RenderingHints;

class BirdCodeReader extends JPanel
{
	private String fileName;
	private String lvl;
	public BirdCodeReader(String fileNameIn, String lvlIn)
	{
		lvl = lvlIn;
		fileName = fileNameIn;
		setSize(600, 700);
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,600, 700);
		String line;
		Line2D.Double comp;
		Primitive pr;
		LoopPrimitive lp = new LoopPrimitive();
		ArrayList<Primitive> code = new ArrayList();
		ArrayList<String> foundLoop = new ArrayList();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		File inFile = new File(fileName + lvl + ".txt");
		Scanner n = null;
		
		try
		{
			n = new Scanner(inFile);
		}
		catch(IOException e)
		{
			System.out.println("\n\n\n" + fileName + lvl + ".txt can't be found\n\n\n");
			System.exit(1);
		}
		
		g2d.translate(300, 350);
		g2d.rotate(1.5*Math.PI);
		
		while(n.hasNextLine())
		{
			line = n.nextLine();
			foundLoop.add(line);
		}
		
		code = lp.openLoop(foundLoop, 1);
		
		
		for(int i = 0; i < code.size(); i++)
		{
			code.get(i).drawShape(g2d);
		}
	}
}
