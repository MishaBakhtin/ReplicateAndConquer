


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.util.Scanner;
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

abstract class Primitive
{
	public void drawShape(Graphics2D g){}
}

class LineShape extends Primitive
{
	private double val;
	public LineShape(double valIn)
	{
		val = valIn;
	}
	@Override
	public void drawShape(Graphics2D g)
	{
		Line2D.Double line;
		line = new Line2D.Double(0, 0, val, 0);
		g.draw(line);
		line = new Line2D.Double(0, -1, val, -1);
		g.draw(line);
		line = new Line2D.Double(0, -2, val, -2);
		g.draw(line);
		line = new Line2D.Double(0, 1, val, 1);
		g.draw(line);
		line = new Line2D.Double(0, 2, val, 2);
		g.draw(line);
		g.translate(val, 0);
	}
}

class Turn extends Primitive
{
	private double val;
	public Turn(double valIn)
	{
		val = valIn;
	}
	@Override
	public void drawShape(Graphics2D g)
	{
		g.rotate(val/180*Math.PI);
	}
}

class Jump extends Primitive
{
	private double val;
	public Jump(double valIn)
	{
		val = valIn;
	}
	@Override
	public void drawShape(Graphics2D g)
	{
		g.translate(val, 0);
	}
}

class SelectColor extends Primitive
{
	private String val;
	public SelectColor(String valIn)
	{
		val = valIn;
	}
	
	@Override
	public void drawShape(Graphics2D g)
	{
		if(val.equals("Gray"))
			g.setColor(Color.GRAY);
				
		if(val.equals("Blue"))
			g.setColor(Color.BLUE);
					
		if(val.equals("Red"))
			g.setColor(Color.RED);
					
		if(val.equals("Black"))
			g.setColor(Color.BLACK);
			
		if(val.equals("White"))
			g.setColor(Color.WHITE);
			
		if(val.equals("Green"))
			g.setColor(Color.GREEN);
			
		if(val.equals("Orange"))
			g.setColor(Color.ORANGE);
			
		if(val.equals("Yellow"))
			g.setColor(Color.YELLOW);
			
		if(val.equals("Magenta"))
			g.setColor(Color.MAGENTA);
	}
}
