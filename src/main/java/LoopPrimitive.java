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

public class LoopPrimitive
{
	public ArrayList openLoop(ArrayList<String> commands, double rep)
	{
		ArrayList<Primitive> outArr = new ArrayList();
		
		String line;
		String val;
		double num;
		Primitive pr;
		LoopPrimitive lp = new LoopPrimitive();
		ArrayList<String> foundLoop = new ArrayList();
		ArrayList<Primitive> inLoop = new ArrayList();
		
		for(int i = 0; i < commands.size(); i++)
		{
			line = commands.get(i);
			val = line.substring(line.indexOf(" ") + 1);
			if(line.indexOf("fw") != -1)
			{
				
				num = Double.parseDouble(val);
				pr = new LineShape(num);
				outArr.add(pr);
			}
			
			if(line.indexOf("bw") != -1)
			{
				
				num = 0 - Double.parseDouble(val);
				pr = new LineShape(num);
				outArr.add(pr);
			}	
			
			if(line.indexOf("tr") != -1)
			{
				
				num = Double.parseDouble(val);
				pr = new Turn(num);
				outArr.add(pr);
			}
			
			if(line.indexOf("tl") != -1)
			{
				
				num = 0 - Double.parseDouble(val);
				pr = new Turn(num);
				outArr.add(pr);
			}
			
			if(line.indexOf("jp") != -1)
			{
				
				num = Double.parseDouble(val);
				pr = new Jump(num);
				outArr.add(pr);
			}
			
			if(line.indexOf("sc") != -1)
			{
				pr = new SelectColor(val);
				outArr.add(pr);
			}
			
			if(line.indexOf("sl") != -1)
			{
				int loopIndex = Integer.parseInt(line.substring(line.indexOf("sl") + 2, line.indexOf(" ")));
				num = Double.parseDouble(val);
				
				i++;
				line = commands.get(i);
				while(line.indexOf("el" + loopIndex) == -1)
				{
					foundLoop.add(line);
					i++;
					line = commands.get(i);
				}
				
				inLoop = lp.openLoop(foundLoop, num);
				
				for(int n = 0; n< inLoop.size(); n++)
				{
					outArr.add(inLoop.get(n));
				}
				inLoop.clear();
				foundLoop.clear();
			}
		}
		
		int size = outArr.size();
		for(int i = 1; i < rep; i++)
		{
			for(int n = 0; n < size; n++)
			{
				outArr.add(outArr.get(n));
			}
		}
		
		return outArr;
	}
}
