import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;	
import javax.swing.JPanel;

import javax.swing.JComponent;
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



public class Blocks extends JPanel
{
	public String name;
	public String labelName;
	public String input;
	public boolean inMenu;
	public boolean inCode;
	public boolean delete;
	public Color color;
	private CardLayout cards;
	public JLabel inL1;
	public Font font;
	ModifyPanel modp;
	
	
	public Blocks(String nameIn, String labelNameIn, Color colorIn, CardLayout cardsIn, boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn)
	{
		labelName = labelNameIn;
		modp = modpIn;
		name = nameIn;
		cards = cardsIn;
		input = inputIn;
		font = new Font("Arial", Font.BOLD, 20);
		setLayout(null);
		setSize(270, 40);
		inMenu = inMenuIn;
		inCode = inCodeIn;
		color = colorIn;
		setBackground(color);
		inL1 = new JLabel(labelName);
		inL1.setFont(font);
		inL1.setForeground(Color.WHITE);
		inL1.setBounds(5,5,260, 30);
		add(inL1);
	}
	
}

class ForwardBlock extends Blocks implements MouseListener
{
	private JTextField text;
	public ForwardBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("fw", "Move forward", new Color(0, 230, 255), new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		addMouseListener(this);
		text = new JTextField(input);
		text.setFont(new Font("Arial", Font.BOLD, 16));
		text.addActionListener(new InputListener());
		if(inCode || inMenu)
			text.setEditable(false);
		text.setBounds(150, 5, 70, 30);
		add(text);
	}
	
	public class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890.";
			boolean clean = true;
			char let;
			String com = evt.getActionCommand();
			for(int i = 0; i < com.length(); i++)
			{
				let = com.charAt(i);
				if(check.indexOf(let) == -1)
					clean = false;
			}
				
			if(clean)
				input = com;
			else
				text.setText("");
		}
	}
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = new ForwardBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class BackwardBlock extends Blocks implements MouseListener
{
	private JTextField text;
	
	public BackwardBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("bw", "Move backward", new Color(0, 230, 255), new CardLayout(), inMenuIn, inCodeIn,  inputIn, modpIn);
		addMouseListener(this);
		text = new JTextField(input);
		text.setFont(new Font("Arial", Font.BOLD, 16));
		text.addActionListener(new InputListener());
		if(inCode || inMenu)
			text.setEditable(false);
		text.setBounds(180, 5, 70, 30);
		add(text);
	}
	
	public class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890.";
			boolean clean = true;
			char let;
			String com = evt.getActionCommand();
			for(int i = 0; i < com.length(); i++)
			{
				let = com.charAt(i);
				if(check.indexOf(let) == -1)
					clean = false;
			}
				
			if(clean)
				input = com;
			else
				text.setText("");
		}
	}

	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = new BackwardBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class SetColorBlock extends Blocks implements MouseListener
{
	private JMenuItem black;
	private JTextField select;
	public SetColorBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("sc", "Set color to:", Color.BLACK, new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		if(colorIn != null && colorIn != Color.WHITE)
			color = colorIn;
		addMouseListener(this);
		if(inMenu)
		{
			JMenuBar jmbF = makeMenu();
			jmbF.setBounds(130, 5, 100, 30);
			add(jmbF);
		}
		else
		{
			TextListener tl = new TextListener();
			select = new JTextField(input);
			if(inCode)
				select.setEditable(false);
			select.setFont(font);
			select.setBounds(130, 5, 100, 30);
			select.addActionListener(tl);
			add(select);
		}
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(input.equals("Gray"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.GRAY;
			setBackground(Color.GRAY);
		}
				
		if(input.equals("Blue"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.BLUE;
			setBackground(Color.BLUE);
		}
					
		if(input.equals("Red"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.RED;
			setBackground(Color.RED);
		}
					
		if(input.equals("Black"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.BLACK;
			setBackground(Color.BLACK);
		}
			
		if(input.equals("White"))
		{
			g.drawRect(0,0,269, 39);
			inL1.setForeground(Color.BLACK);
			color = Color.WHITE;
			setBackground(Color.WHITE);
		}
			
		if(input.equals("Green"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.GREEN;
			setBackground(Color.GREEN);
		}
			
		if(input.equals("Orange"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.ORANGE;
			setBackground(Color.ORANGE);
		}
			
		if(input.equals("Yellow"))
		{
			inL1.setForeground(Color.BLACK);
			color = Color.YELLOW;
			setBackground(Color.YELLOW);
		}
			
		if(input.equals("Magenta"))
		{
			inL1.setForeground(Color.WHITE);
			color = Color.MAGENTA;
			setBackground(Color.MAGENTA);
		}

		
	}
	
		public JMenuBar makeMenu()
		{
			InputListener ml = new InputListener();
			JMenuBar jmb = new JMenuBar();
			JMenu menu = new JMenu("Color Choices:");
			JMenuItem gray = new JMenuItem("Gray");
			
			JMenuItem blue = new JMenuItem("Blue");
			
			JMenuItem red = new JMenuItem("Red");
			
			black = new JMenuItem("Black");
			
			JMenuItem white= new JMenuItem("White");
			
			JMenuItem green = new JMenuItem("Green");
			
			JMenuItem orange = new JMenuItem("Orange");
			
			JMenuItem yellow = new JMenuItem("Yellow");
			
			JMenuItem magenta = new JMenuItem("Magenta");
			
				gray.addActionListener(ml);
				blue.addActionListener(ml);
				red.addActionListener(ml);
				black.addActionListener(ml);
				white.addActionListener(ml);
				green.addActionListener(ml);
				orange.addActionListener(ml);
				yellow.addActionListener(ml);
				magenta.addActionListener(ml);
			
			
			menu.add(gray);
			menu.add(blue);
			menu.add(red);
			menu.add(black);
			menu.add(white);
			menu.add(green);
			menu.add(orange);
			menu.add(yellow);
			menu.add(magenta);
			jmb.add(menu);
			
			return jmb;
		}
		
		public class InputListener implements ActionListener
		{
			public void actionPerformed(ActionEvent evt)
			{
				System.out.println("menu thingie works");
				if(!inMenu && !inCode)
				{
					input = evt.getActionCommand();
					repaint();
				}
			}
		}
		
	public class TextListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "GrayBlueRedBlackWhiteGreenOrangeYellowMagenta";
			input = evt.getActionCommand();
			if(check.indexOf(input) != -1)
				repaint();
			else
			{
				input = "";
				select.setText("");
			}
		}
	} 
		
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = null;
			modp.repaint();
			modp.block = new SetColorBlock(false, false, "", modp, null);
			modp.block.revalidate();
			modp.repaint();
			black.setSelected(true);
			modp.block.revalidate();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}


class TurnRightBlock extends Blocks implements MouseListener
{
	private JTextField text;
	public TurnRightBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("tr", "Turn right", new Color(225,126,232), new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		addMouseListener(this);
		text = new JTextField(input);
		text.setFont(new Font("Arial", Font.BOLD, 16));
		text.addActionListener(new InputListener());
		if(inCode || inMenu)
			text.setEditable(false);
		text.setBounds(105, 5, 70, 30);
		JLabel lab = new JLabel("degrees");
		lab.setForeground(Color.WHITE);
		lab.setFont(font);
		lab.setBounds(180, 5, 85, 30);
		add(lab);
		add(text);
	}
	
	public class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890.";
			boolean clean = true;
			char let;
			String com = evt.getActionCommand();
			for(int i = 0; i < com.length(); i++)
			{
				let = com.charAt(i);
				if(check.indexOf(let) == -1)
					clean = false;
			}
				
			if(clean)
				input = com;
			else
				text.setText("");
		}
	}
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.block = new TurnRightBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}


class TurnLeftBlock extends Blocks implements MouseListener
{
	private JTextField text;
	public TurnLeftBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("tl", "Turn left", new Color(225,126,232), new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		addMouseListener(this);
		text = new JTextField(input);
		text.addActionListener(new InputListener());
		text.setFont(new Font("Arial", Font.BOLD, 16));
		if(inCode || inMenu)
			text.setEditable(false);
		text.setBounds(100, 5, 70, 30);
		add(text);
		JLabel lab = new JLabel("degrees");
		lab.setForeground(Color.WHITE);
		lab.setFont(font);
		lab.setBounds(180, 5, 85, 30);
		add(lab);
	}
	
	public class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890.";
			boolean clean = true;
			char let;
			String com = evt.getActionCommand();
			for(int i = 0; i < com.length(); i++)
			{
				let = com.charAt(i);
				if(check.indexOf(let) == -1)
					clean = false;
			}
				
			if(clean)
				input = com;
			else
				text.setText("");
		}
	}
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = new TurnLeftBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class StartLoopBlock extends Blocks implements MouseListener
{
	private JTextField text;
	public StartLoopBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("sl", "Loop", new Color(232,103,3), new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		addMouseListener(this);
		text = new JTextField(input);
		text.addActionListener(new InputListener());
		if(inCode || inMenu)
			text.setEditable(false);
		text.setBounds(60, 5, 70, 30);
		text.setFont(new Font("Arial", Font.BOLD, 16));
		JLabel lab = new JLabel("times");
		lab.setForeground(Color.WHITE);
		lab.setFont(font);
		lab.setBounds(140, 5, 85, 30);
		add(lab);
		add(text);
	}
	
	public class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890.";
			boolean clean = true;
			char let;
			String com = evt.getActionCommand();
			for(int i = 0; i < com.length(); i++)
			{
				let = com.charAt(i);
				if(check.indexOf(let) == -1)
					clean = false;
			}
				
			if(clean)
				input = com;
			else
				text.setText("");
		}
	}
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = new StartLoopBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}


class EndLoopBlock extends Blocks implements MouseListener
{
	
	public EndLoopBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("el", "End loop", new Color(232,103,3), new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		addMouseListener(this);
	}
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = new EndLoopBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}

class JumpBlock extends Blocks implements MouseListener
{
	private JTextField text;
	public JumpBlock(boolean inMenuIn, boolean inCodeIn, String inputIn, ModifyPanel modpIn, Color colorIn)
	{
		super("jp", "Jump forward", new Color(255, 102, 153), new CardLayout(), inMenuIn, inCodeIn, inputIn, modpIn);
		addMouseListener(this);
		text = new JTextField(input);
		text.setFont(new Font("Arial", Font.BOLD, 16));
		text.addActionListener(new InputListener());
		if(inCode || inMenu)
			text.setEditable(false);
		text.setBounds(150, 5, 70, 30);
		add(text);
	}
	
	public class InputListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890.";
			boolean clean = true;
			char let;
			String com = evt.getActionCommand();
			for(int i = 0; i < com.length(); i++)
			{
				let = com.charAt(i);
				if(check.indexOf(let) == -1)
					clean = false;
			}
				
			if(clean)
				input = com;
			else
				text.setText("");
		}
	}
	
	public void mousePressed(MouseEvent evt)
	{
		requestFocusInWindow();
		if(inMenu)
		{
			modp.removeAll();
			modp.block = new JumpBlock(false, false, "", modp, null);
			modp.repaint();
		}
	}
	public void mouseReleased(MouseEvent evt){}
	public void mouseClicked(MouseEvent evt){}
	public void mouseEntered(MouseEvent evt){}
	public void mouseExited(MouseEvent evt){}
}
