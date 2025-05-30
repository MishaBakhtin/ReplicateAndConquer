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
import java.io.PrintWriter;


public class PictureCompare
{	
	private String fileName;
	private FirstImageTest fit;
	private SecondImageTest sit;
	private RACHolder rach;
	private String lvl;
	
	public PictureCompare(String fileNameIn, String lvlIn, RACHolder rachIn)
	{
		lvl = lvlIn;
		rach = rachIn;
		fileName = fileNameIn;
		fit = new FirstImageTest(lvlIn, fileNameIn);
		sit = new SecondImageTest(fileNameIn, lvlIn);
		run();
	}
	
	public void run()
	{
		JFrame frame = new JFrame("PictureCompareTest");
		frame.setSize( 1450, 850);	
		frame.setLayout(new GridLayout(1, 2));			
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE); 
		frame.setLocation(0,50);
		frame.setResizable(false);
		frame.add(fit);
		frame.add(sit);
		frame.setVisible(true);	
		if(fileName.indexOf("FreeDraw") == -1)	
			compare();
	}
	
	public void compare()
	{
		double sim;
		int match = 0;
		int mismatch = 0;
		Color color1;
		Color color2;
		
		System.out.println(fit.picture.getHeight());
		System.out.println(fit.picture.getWidth());
		
		int height = fit.picture.getHeight();
		int width = fit.picture.getWidth();
		boolean white = false;
		
		for(int i = 0; i < height; i++)
		{
			for(int n = 0; n < width; n++)
			{
				color1 = new Color(fit.picture.getRGB(n, i));
				color2 = new Color(sit.picture.getRGB(n, i));
				if(color1.equals(Color.WHITE) && color2.equals(Color.WHITE))
					white = true;
				else
					white = false;
					
				if(!white)
				{
					if(color1.equals(color2))
						match++;
					else
						mismatch++;
				}
			}
		}
		
		sim = match*1.0 / (match + mismatch) * 100;
		if(sim >= 90.0 && !lvl.equals(""))
		{
			rach.progress = Double.parseDouble(lvl.substring(0, 1) + "." + lvl.substring(2)) + 0.1;
			if(rach.progress == 1.6)
				rach.progress = 2.1;
			if(rach.progress == 2.6)
				rach.progress = 3.1;
			if(rach.progress == 3.6)
				rach.progress = 4.1;
			File outFile = new File(rach.fileName + "/progress.txt");
			PrintWriter pw = null;
			try
			{
				pw = new PrintWriter(outFile); //try to create PrintWriter
			}
			
			catch(IOException e)
			{
				System.err.print("Can't create/write to " + rach.fileName + "/progress.txt"); //throw an error if it's not possible
				System.exit(2);
			}
			
			pw.print("" + rach.progress);
			pw.close();
			
		}
		String similarity = String.format("The similarity is %.2f%s", sim, "%");
		
		JFrame frame = new JFrame();
		frame.setSize(400, 200);
		frame.setLocation(300, 300);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		JLabel simLab = new JLabel(similarity);
		Font font = new Font("Arial", Font.BOLD, 20);
		simLab.setFont(font);
		simLab.setBounds(60, 50, 250, 20);
		frame.add(simLab);
		frame.setVisible(true);
		if(sim >= 90)
		{
			JFrame yay = new JFrame();
			yay.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			yay.setSize(500, 500);
			yay.setLocation(900, 200);
			yay.add(new YayPanel("1"));
			yay.setVisible(true);
		}
		else if(sim < 90 && sim >= 75)
		{
			JFrame yay = new JFrame();
			yay.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			yay.setSize(500, 500);
			yay.setLocation(900, 200);
			yay.add(new YayPanel("2"));
			yay.setVisible(true);
		}
		else
		{
			JFrame yay = new JFrame();
			yay.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			yay.setSize(500, 500);
			yay.setLocation(900, 200);
			yay.add(new YayPanel("3"));
			yay.setVisible(true);
		}
	}
	
	
}

class YayPanel extends JPanel
{
	private String prog;
	public YayPanel(String progIn)
	{
		setSize(500, 500);
		setLayout(null);
		prog = progIn;
	}
	
	public void paintComponent(Graphics g)
	{
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("YayFolder/" + prog + ".png"));
		}
		catch(IOException e)
		{
			System.err.println("YayFolder/" + prog + ".png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 0, 0, 480, 450, this);
	}
}

class FirstImageTest extends JPanel
{
	public BufferedImage picture;
	public Image image;
	private String lvl;
	private JSlider hor;
	private JSlider vert;
	private JTextField xVal;
	private JTextField yVal;
	private int x;
	private int y;
	
	public FirstImageTest(String lvlIn, String fileName)
	{
		x = 300;
		y = 350;
		setSize(700, 800);
		setLayout(null);
		lvl = lvlIn;
		Font font = new Font("Arial", Font.BOLD, 20);
		HorizontalListener hl = new HorizontalListener();
		VerticalListener vl = new VerticalListener();
		hor = new JSlider(JSlider.HORIZONTAL, 0, 600, 300);
		hor.setMajorTickSpacing(50);
		hor.setMinorTickSpacing(25);
		hor.addChangeListener(hl);
		hor.setBounds(100, 0, 600, 100);
		add(hor);
		
		vert = new JSlider(JSlider.VERTICAL, 0, 700, 350);
		vert.setMajorTickSpacing(50);
		vert.setMinorTickSpacing(25);
		vert.addChangeListener(vl);
		vert.setBounds(0, 100, 100, 699);
		add(vert);
		
		JLabel xCord = new JLabel("X:");
		xCord.setFont(font);
		xCord.setBounds(10, 30, 30, 20);
		add(xCord);
		JLabel yCord = new JLabel("Y:");
		yCord.setFont(font);
		yCord.setBounds(10, 70, 30, 20);
		add(yCord);
		
		XValueListener xvl = new XValueListener();
		xVal = new JTextField(x + "");
		xVal.addActionListener(xvl);
		xVal.setBounds(50, 30, 40, 20);
		xVal.setFont(font);
		add(xVal);
		
		YValueListener yvl = new YValueListener();
		yVal = new JTextField(y + "");
		yVal.addActionListener(yvl);
		yVal.setBounds(50, 70, 40, 20);
		yVal.setFont(font);
		add(yVal);
		
		picture = new BufferedImage(600, 700, BufferedImage.TYPE_INT_ARGB);
		image = null;
		if(fileName.indexOf("FreeDraw") == -1)
		{
			try 
			{
				picture = ImageIO.read(new File("LevelImages/lvl" + lvl  + ".png"));
			}
			catch(IOException e)
			{
				System.err.println("\n\nLevelPictures/lvl" + lvl + ".png can't be found.\n\n");
				System.exit(1);
			}
			
			try 
			{
				image = ImageIO.read(new File("LevelImages/lvl" + lvl  + ".png"));
			}
			catch(IOException e)
			{
				System.err.println("\n\nLevelImages/lvl" + lvl + ".png can't be found.\n\n");
				System.exit(1);
			}
		}
		else
		{
			try 
			{
				picture = ImageIO.read(new File("LevelImages/lvl1.jpg"));
			}
			catch(IOException e)
			{
				System.err.println("\n\nLevelImages/lvl1.jpg can't be found.\n\n");
				System.exit(1);
			}
			
			try 
			{
				image = ImageIO.read(new File("LevelImages/lvl1.jpg"));
			}
			catch(IOException e)
			{
				System.err.println("\n\nLevelImages/lvl1.jpg can't be found.\n\n");
				System.exit(1);
			}
		}
	}
	
	public class XValueListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890";
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
                {
                    x = Integer.parseInt(com);
                    if(x < 0)
                    {
						xVal.setText("0");
						x = 0;
					}
					else if(x > 600)
					{
						xVal.setText("600");
						x = 600;
					}
					hor.setValue(x);
                    repaint();
                }
                else
                {
                    xVal.setText("");
                }
               
		}
	}
	
	public class YValueListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890";
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
                {
                    y = Integer.parseInt(com);
                    if(x < 0)
                    {
						yVal.setText("0");
						y = 0;
					}
					else if(x > 700)
					{
						yVal.setText("700");
						y = 600;
					}
					vert.setValue(y);
                    repaint();
                }
                else
                {
                    yVal.setText("");
                }
               
		}
	}
	
	public class HorizontalListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			x = hor.getValue();
			xVal.setText("" + x);
			repaint();
		}
	}
	
	public class VerticalListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			y = vert.getValue();
			yVal.setText("" + y);
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 100, 100, 600, 700, this);
		g.drawLine(x + 100, 100, x + 100, 800);
		g.drawLine(100, 800 - y, 700, 800 - y);
	}
}

class SecondImageTest extends JPanel
{
	public BufferedImage picture;
	public Image image;
	private String lvl;
	private String fileName;
	private JSlider hor;
	private JSlider vert;
	private JTextField xVal;
	private JTextField yVal;
	private JPanel vertLine;
	private JPanel horLine;
	private int x;
	private int y;
	
	public SecondImageTest(String fileNameIn, String lvlIn)
	{
		
		
		lvl = lvlIn;
		fileName = fileNameIn;
		x = 300;
		y = 350;
		setSize(700, 800);
		setLayout(null);
		lvl = lvlIn;
		Font font = new Font("Arial", Font.BOLD, 20);
		HorizontalListener hl = new HorizontalListener();
		VerticalListener vl = new VerticalListener();
		hor = new JSlider(JSlider.HORIZONTAL, 0, 600, 300);
		hor.setMajorTickSpacing(50);
		hor.setMinorTickSpacing(25);
		hor.addChangeListener(hl);
		hor.setBounds(100, 0, 600, 100);
		add(hor);
		
		vert = new JSlider(JSlider.VERTICAL, 0, 700, 350);
		vert.setMajorTickSpacing(50);
		vert.setMinorTickSpacing(25);
		vert.addChangeListener(vl);
		vert.setBounds(0, 100, 100, 699);
		add(vert);
		
		JLabel xCord = new JLabel("X:");
		xCord.setFont(font);
		xCord.setBounds(10, 30, 30, 20);
		add(xCord);
		JLabel yCord = new JLabel("Y:");
		yCord.setFont(font);
		yCord.setBounds(10, 70, 30, 20);
		add(yCord);
		
		XValueListener xvl = new XValueListener();
		xVal = new JTextField(x + "");
		xVal.addActionListener(xvl);
		xVal.setBounds(50, 30, 40, 20);
		xVal.setFont(font);
		add(xVal);
		
		YValueListener yvl = new YValueListener();
		yVal = new JTextField(x + "");
		yVal.addActionListener(yvl);
		yVal.setBounds(50, 70, 40, 20);
		yVal.setFont(font);
		add(yVal);
		
		BirdCodeReader bcr = new BirdCodeReader(fileName, lvl);
		bcr.setBounds(100, 100, 600, 700);
		add(bcr);
		picture = createImage(bcr);
		vertLine = new JPanel();
		vertLine.setSize(5, 700);
		vertLine.setBackground(Color.BLACK);
		vertLine.setBounds(400, 100, 5, 700);
		add(vertLine);
		
		horLine = new JPanel();
		horLine.setSize(600, 5);
		horLine.setBackground(Color.BLACK);
		horLine.setBounds(100, 450, 600, 5);
		add(horLine);
	}
	
	public class XValueListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890";
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
                {
                    x = Integer.parseInt(com);
                    if(x < 0)
                    {
						xVal.setText("0");
						x = 0;
					}
					else if(x > 600)
					{
						xVal.setText("600");
						x = 600;
					}
					hor.setValue(x);
                    repaint();
                }
                else
                {
                    xVal.setText("");
                }
               
		}
	}
	
	public class YValueListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String check = "1234567890";
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
                {
                    y = Integer.parseInt(com);
                    if(y < 0)
                    {
						yVal.setText("0");
						y = 0;
					}
					else if(y > 700)
					{
						yVal.setText("700");
						y = 700;
					}
					vert.setValue(y);
                    repaint();
                }
                else
                {
                    yVal.setText("");
                }
               
		}
	}
	
	public class HorizontalListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			x = hor.getValue();
			xVal.setText("" + x);
			repaint();
		}
	}
	
	public class VerticalListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			y = vert.getValue();
			yVal.setText("" + y);
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		remove(vertLine);
		vertLine.setBounds(100 + x, 100, 5, 700);
		add(vertLine);
		remove(horLine);
		horLine.setBounds(100, 100 + y, 600, 5);
		add(horLine);
	}
	
	public BufferedImage createImage(JPanel panel) 
	{
		int w = panel.getWidth();
		int h = panel.getHeight();
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		panel.paint(g);
		g.dispose();
		return bi;
	}
	
	public static void savePNG(BufferedImage image, String filePath) 
	{
        try 
        {
            File outputFile = new File(filePath);
            ImageIO.write(image, "png", outputFile);
            System.out.println("PNG file created successfully at: " + filePath);
        } 
        catch (IOException e) 
        {
            System.err.println("Error creating PNG file: " + e.getMessage());
        }
    }
}
