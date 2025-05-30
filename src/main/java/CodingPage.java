import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.imageio.ImageIO;
import java.io.PrintWriter;

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
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ScrollPaneLayout;

public class CodingPage extends JPanel
{
    private ArrayList<String> birdCode;
    private ArrayList<Blocks> blockCode;
    private CodePanel cp;
    private ModifyPanel modp;
    private boolean freeDraw;
    private CardLayout cards;
    public String lvl;
    public String directory;
    public String fileName;
    private JFrame frame;
    private RACHolder rach;
    public CodingPage(CardLayout cardsIn, boolean freeDrawIn, String lvlIn, String directoryIn, RACHolder rachIn)
    {
		frame = new JFrame();
		cards = cardsIn;
		fileName = "";
		freeDraw = freeDrawIn;
		lvl = lvlIn;
		directory = directoryIn;
        birdCode = new ArrayList();
        blockCode = new ArrayList();
        setSize(1400, 800);
        setLayout(null);
        setBackground(Color.BLACK);
        rach = rachIn;
       
		if(!freeDraw)
		{
			boolean empty = false;
			File inputFile = new File(directory + lvl + ".txt");
			Scanner inFile = null;
			try
			{
				inFile = new Scanner(inputFile); //try to open the file to read from using
			} catch(FileNotFoundException e)
			{
				System.err.printf("File %s could not be found.\n\n\n", directory + lvl + ".txt"); //throw an error if it can't find the file
				empty = true;
			}
			if(empty)
			{
				File outFile = new File(directory  + lvl + ".txt");
				PrintWriter pw = null;
				try
				{
					pw = new PrintWriter(outFile); //try to create PrintWriter
				}
				
				catch(IOException error)
				{
					System.err.print("Can't create/write to " + directory  + lvl + ".txt"); //throw an error if it's not possible
					System.exit(2);
				}
				
				pw.print("");
				pw.close();
			}
		}
    }
    
    public void paintComponent(Graphics g)
    {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1400,800);
		if(!freeDraw)
		{
			System.out.println("I draw");
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1400, 800);
			modp = new ModifyPanel(this);
			modp.setBounds(610, 0, 300, 800);
			add(modp);
			modp.repaint();

			ToolsPanel tp = new ToolsPanel(cards, this, lvl, rach);
			tp.setBounds(0, 0, 300, 800);
			add(tp);

			MenuPanel mp = new MenuPanel(modp);
			mp.setBounds(305, 0, 300, 800);
			add(mp);

			cp = new CodePanel(blockCode, this);
			cp.setBounds(915, 0, 485, 800);
			add(cp);
		}
		
		 else
        {
			frame.dispose();
			FreeDrawListener fdl = new FreeDrawListener();
			frame = new JFrame();
			frame.setSize(560, 300);
			frame.setLocation(500, 200);
			frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
			frame.setLayout(null);
			JLabel simLab = new JLabel("Enter the name of the file you want to use/create:");
			Font font = new Font("Arial", Font.BOLD, 20);
			simLab.setFont(font);
			simLab.setBounds(30, 50, 580, 20);
			frame.add(simLab);
			JTextField file = new JTextField("");
			file.setBounds(160, 100, 200, 30);
			file.setFont(font);
			file.addActionListener(fdl);
			frame.add(file);
			JButton useFile = new JButton("Use File");
			useFile.setBounds(160, 150, 200, 50);
			useFile.setBackground(new Color(255, 179, 0));
			useFile.setFont(font);
			useFile.setForeground(Color.WHITE);
			useFile.addActionListener(new UseFileListener());
			frame.add(useFile);
			frame.setVisible(true);
		}
	
	}
    
    public class FreeDrawListener implements ActionListener
    {
		public void actionPerformed(ActionEvent evt)
		{
			fileName = directory + "FreeDraw/" + evt.getActionCommand();
			boolean empty = false;
			File inputFile = new File(fileName + ".txt");
			Scanner inFile = null;
			try
			{
				inFile = new Scanner(inputFile); //try to open the file to read from using
			} catch(FileNotFoundException e)
			{
				System.err.printf("File %s could not be found.\n\n\n", directory + lvl + ".txt"); //throw an error if it can't find the file
				empty = true;
			}
			
			if(empty)
			{
				
				File outFile = new File(fileName + ".txt");
				PrintWriter pw = null;
				try
				{
					pw = new PrintWriter(outFile); //try to create PrintWriter
				}
				
				catch(IOException error)
				{
					System.err.print("Can't create/write to " + fileName + ".txt"); //throw an error if it's not possible
					System.exit(2);
				}
				
				pw.print("");
				pw.close();
			}
		}
	}
	
	public class UseFileListener implements ActionListener
    {
		public void actionPerformed(ActionEvent evt)
		{
			if(!fileName.equals(""))
			{
				frame.dispose();
				directory = fileName;
				freeDraw = false;
				repaint();
			}
		}
	}
    
    public boolean saveCode()
    {
		if(birdCode.size() != 0)
		{
		ArrayList<String> stringCode = new ArrayList();
		String line;
		String name;
		String input;
		boolean error = false;
		String errorMess = "";
		int space;
		int index = 1;
		int slCount = 0;
		int elCount = 0;
		for(int i = 0; i < birdCode.size(); i++)
		{
			line = birdCode.get(i);
			space = line.indexOf(" ");
			if(space == -1)
			{
				name = line.substring(0);
				input = "";
			}
			else
			{
				name = line.substring(0, space);
				input = line.substring(space);
			}
			if(name.equals("sl"))
			{
				name = name + index;
				slCount++;
				index++;
			}
			else if(name.equals("el"))
			{
				name = name + (index - 1);
				elCount++;
				index--;
			}
			if(index < 1)
			{
				error = true;
				errorMess = "'End Loop' without a 'Start Loop'";
				//System.out.println("I found error on line: " + (i +1));
			}
			stringCode.add(name + input);
		}
		if(slCount > elCount)
		{
			error = true;
			errorMess = "'Start Loop' without an 'End Loop'";
		}
		
		if(error)
		{
			frame = new JFrame();
			frame.setSize(450, 300);
			frame.setLocation(500, 300);
			frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
			frame.setLayout(null);
			JLabel err = new JLabel("Error: " + errorMess);
			Font font = new Font("Arial", Font.BOLD, 20);
			err.setFont(font);
			err.setBounds(30, 60, 420, 20);
			frame.add(err);
			JButton ok = new JButton("OK");
			ok.setFont(font);
			ok.setBounds(160, 150, 100, 50);
			ok.addActionListener(new OKListener());
			frame.add(ok);
			frame.setVisible(true);
		}
		else
		{
			File outFile = new File(directory + lvl + ".txt");
			PrintWriter pw = null;
			try
			{
				pw = new PrintWriter(outFile); //try to create PrintWriter
			}
			
			catch(IOException e)
			{
				System.err.print("Can't create/write to " + directory + lvl + ".txt"); //throw an error if it's not possible
				System.exit(2);
			}
			pw.print(stringCode.get(0));
			
			for(int i = 1; i < stringCode.size(); i++)
			{
				pw.print("\n" + stringCode.get(i));
			}
			pw.flush();
			pw.close();
			
		}
		return error;
	}
	else
		return false;
	}
	
	public class OKListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			frame.dispose();
		}
	}
    
    public int getCodeSize()
    {
		return blockCode.size();
	}

    public void updateSavedCode(ArrayList<String> birdCodeIn)
    {
        birdCode.clear();
        blockCode.clear();
        String line;
        String input;
        String name;
        for(int i = 0; i < birdCodeIn.size(); i++)
        {

            line = birdCodeIn.get(i);
            int space = line.indexOf(" ");
            if(space != -1)
            {
				input = line.substring(line.indexOf(" "));
				name = line.substring(0, space);
			}
			else
			{
				input = "";
				name = line.substring(0);
            }
            if(name.indexOf("el") != -1)
				name = "el";
			 if(name.indexOf("sl") != -1)
				name = "sl";
            birdCode.add(name + input);
            if(line.indexOf("fw") != -1)
                blockCode.add(new ForwardBlock(false, true, input, modp, null));

            else if(line.indexOf("bw") != -1)
                blockCode.add(new BackwardBlock(false, true, input, modp, null));

            else if(line.indexOf("sc") != -1)
            {
				if(input.equals(" Gray"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.GRAY));
				else if(input.equals(" Blue"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.BLUE));
				else if(input.equals(" Red"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.RED));
				else if(input.equals(" Black"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.BLACK));
				else if(input.equals(" White"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.BLACK));
				else if(input.equals(" Green"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.GREEN));
				else if(input.equals(" Orange"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.ORANGE));
				else if(input.equals(" Yellow"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.YELLOW));
				else if(input.equals(" Magenta"))
					blockCode.add(new SetColorBlock(false, true, input, modp, Color.MAGENTA));
            }

            else if(line.indexOf("tr") != -1)
                blockCode.add(new TurnRightBlock(false, true, input, modp, null));

            else if(line.indexOf("tl") != -1)
                blockCode.add(new TurnLeftBlock(false, true, input, modp, null));

            else if(line.indexOf("sl") != -1)
                blockCode.add(new StartLoopBlock(false, true, input, modp, null));

            else if(line.indexOf("el") != -1)
                blockCode.add(new EndLoopBlock(false, true, input, modp, null));

            else if(line.indexOf("jp") != -1)
                blockCode.add(new JumpBlock(false, true, input, modp, null));
				
        }
		updateCode("el", "", -1, null);
		deleteLine(blockCode.size());

    }

    public void updateCode(String name, String input, int pos, Color color)
    {
        Blocks block = null;
        if(name.equals("fw"))
            block = new ForwardBlock(false, true, input, modp, null);
        else if(name.equals("bw"))
            block = new BackwardBlock(false, true, input, modp, null);
        else if(name.equals("sc"))
            block = new SetColorBlock(false, true, input, modp, color);
        else if(name.equals("tr"))
            block = new TurnRightBlock(false, true, input, modp, null);
        else if(name.equals("tl"))
            block = new TurnLeftBlock(false, true, input, modp, null);
        else if(name.equals("sl"))
            block = new StartLoopBlock(false, true, input, modp, null);
        else if(name.equals("el"))
            block = new EndLoopBlock(false, true, input, modp, null);
        else if(name.equals("jp"))
            block = new JumpBlock(false, true, input, modp, null);
        //System.out.println("updateCode" + name + " " + input + " " + pos + " " + block);
        if(pos == -1)
        {
            birdCode.add(new String(name + " " + input));
            blockCode.add(block);
        }
        else
        {
            birdCode.add(pos - 1, new String(name + " " + input));
            blockCode.add(pos - 1, block);
        }
        remove(cp);
        cp = new CodePanel(blockCode, this);
        cp.setBounds(915, 0, 585, 800);
        add(cp);
        cp.revalidate();
//        cp.repaint();
//        repaint();
    }
    
    public void updateArray()
    {
		ArrayList<String> stringCode = new ArrayList();
        File inputFile = new File(directory + lvl + ".txt");
        Scanner inFile = null;
        try
        {
            inFile = new Scanner(inputFile); //try to open the file to read from using
        } catch(FileNotFoundException e)
        {
            System.err.printf("File %s could not be found.\n\n\n", directory + lvl + ".txt"); //throw an error if it can't find the file
            System.exit(1);
        }

        while(inFile.hasNext())
        {
            stringCode.add(inFile.nextLine());
        }

        updateSavedCode(stringCode);
    }

	
	public void deleteLine(int line)
	{
		if(line != -1)
		{ 
			birdCode.remove(line - 1);
			blockCode.remove(line - 1);
			remove(cp);
			cp = new CodePanel(blockCode, this);
			cp.setBounds(915, 0, 585, 800);
			add(cp);
			cp.revalidate();
		}
	}
}

class ToolsPanel extends JPanel
{
    private CardLayout cards;
    private CodingPage cp;
    private String lvl;
    private RACHolder rach;

    public ToolsPanel(CardLayout cardsIn, CodingPage cpIn, String lvlIn, RACHolder rachIn)
    {
		rach = rachIn;
		lvl = lvlIn;
        cards = cardsIn;
        cp = cpIn;
        setBackground(Color.WHITE);
        setLayout(null);
        ButtonListener bl = new ButtonListener();
        Font font = new Font("Arial", Font.BOLD, 20);

        JButton exit = new JButton("Save and Exit");
        exit.setFont(font);
        exit.setBackground(new Color(255, 179, 0));
        exit.setForeground(Color.WHITE);
        exit.setBounds(50, 300, 200, 70);
        exit.addActionListener(bl);
        add(exit);

        JButton useSaved = new JButton("Use Saved Code");
        useSaved.setFont(font);
        useSaved.setBackground(new Color(255, 179, 0));
        useSaved.setForeground(Color.WHITE);
        useSaved.setBounds(50, 420, 200, 70);
        useSaved.addActionListener(bl);
        add(useSaved);

        JButton show = new JButton("Run Code");
        show.setFont(font);
        show.setBackground(new Color(255, 179, 0));
        show.setForeground(Color.WHITE);
        show.setBounds(50, 540, 200, 70);
        show.addActionListener(bl);
        add(show);
        
        Delete delete = new Delete();
        delete.setBounds(50, 650, 200, 80);
        add(delete);
    }
    
    public class Delete extends JPanel implements MouseListener
    {
        private JTextField jt;
        private int pos = -1;

        public Delete()
        {
            addMouseListener(this);
            setSize(200, 80);
            setBackground(new Color(255, 179, 0));
            setLayout(null);


        }

        public void paintComponent(Graphics g)
        {
            g.setColor(new Color(255, 179, 0));
            g.fillRect(0, 0, 200, 80);
            JLabel lab = new JLabel("Delete Line:");
            Font font = new Font("Arial", Font.BOLD, 20);
            lab.setFont(font);
            lab.setForeground(Color.WHITE);
            lab.setBounds(50, 10, 150, 30);
            add(lab);

            jt = new JTextField();
            jt.setBounds(70, 40, 60, 30);
            jt.setFont(new Font("Arial", Font.BOLD, 16));
            jt.setBackground(Color.WHITE);
            jt.addActionListener(new DeleteListener());
            jt.revalidate();
            add(jt);
        }

        class DeleteListener implements ActionListener
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
                
                if(jt.getText().equals(""))
					clean = false;

                if(clean)
                    pos = Integer.parseInt(com);
                else
                {
                    jt.setText("");
                    pos = -1;
                }
                if(pos > cp.getCodeSize() || pos < 1)
                {
					jt.setText("");
					pos = -1;
				}
            }
        }

        public void mousePressed(MouseEvent evt)
        {
            requestFocusInWindow();
            if(pos != -1)
            {
                cp.deleteLine(pos);
                jt.setText("");
            }
        }
        public void mouseReleased(MouseEvent evt){}
        public void mouseClicked(MouseEvent evt){}
        public void mouseEntered(MouseEvent evt){}
        public void mouseExited(MouseEvent evt){}
    }
    
    public void paintComponent(Graphics g)
    {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 300, 800);
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("CharacterImg/bear-1.jpg"));
		}
		catch(IOException e)
		{
			System.err.println("CharacterImg/bear-1.jpg couldn't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 50, 50, 200, 200, this);
		
	}

    public class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            String act = evt.getActionCommand();

            if(act.equals("Save and Exit"))
            {
				cp.saveCode();
				cards.show(rach, "Menu");
            }

            if(act.equals("Use Saved Code"))
            {
                cp.updateArray();
            }

            if(act.equals("Run Code"))
            {
				PictureCompare pictComp;
				boolean error = cp.saveCode();
				if(!error)
					pictComp = new PictureCompare(cp.directory, cp.lvl, rach);
            }
        }
    }

    
}

class MenuPanel extends JPanel
{
    public MenuPanel(ModifyPanel modp)
    {
        setSize(300, 800);
        setBackground(Color.WHITE);
        setLayout(null);
        ForwardBlock fb = new ForwardBlock(true, false, "", modp, null);
        BackwardBlock bb = new BackwardBlock(true, false, "", modp, null);
        SetColorBlock scb = new SetColorBlock(true, false, "", modp, null);
        TurnRightBlock trb = new TurnRightBlock(true, false, "", modp, null);
        TurnLeftBlock tlb = new TurnLeftBlock(true, false, "", modp, null);
        StartLoopBlock slb = new StartLoopBlock(true, false, "", modp, null);
        EndLoopBlock elb = new EndLoopBlock(true, false, "", modp, null);
        JumpBlock jb = new JumpBlock(true, false, "", modp, null);
        ArrayList<Blocks> blocks = new ArrayList();
        blocks.add(fb);
        blocks.add(bb);
        blocks.add(scb);
        blocks.add(trb);
        blocks.add(tlb);
        blocks.add(slb);
        blocks.add(elb);
        blocks.add(jb);
        for(int i = 0; i < blocks.size(); i++)
        {
            blocks.get(i).setBounds(15, 100 + i*60, 270, 40);
            add(blocks.get(i));
        }

        JLabel title = new JLabel("Select block:");
        Font font = new Font("Arial", Font.BOLD, 20);
        title.setFont(font);
        title.setBounds(90, 30, 140, 30);
        add(title);
    }

}

class ModifyPanel extends JPanel
{
    public Blocks block;
    private CodingPage cp;

    public ModifyPanel(CodingPage cpIn)
    {
        cp = cpIn;
        setSize(300, 800);
        setBackground(Color.WHITE);
        setLayout(null);
        block = null;
    }



    public void paintComponent(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,300,800);
        removeAll();

        if(block != null)
        {
            block.setBounds(15, 100, 270, 40);
            block.revalidate();
            add(block);
            ButtonListener bl = new ButtonListener();
            Font font = new Font("Arial", Font.BOLD, 20);
            JButton add = new JButton("Add Block");
            add.addActionListener(bl);
            add.setFont(font);
            add.setBackground(new Color(255, 179, 0));
            add.setForeground(Color.WHITE);
            add.setBounds(50, 300, 200, 70);
            add(add);

            AddAt aa = new AddAt();
            aa.setBounds(50, 470, 200, 80);
            add(aa);

            JLabel title = new JLabel("Modify block:");
            font = new Font("Arial", Font.BOLD, 20);
            title.setFont(font);
            title.setBounds(90, 30, 140, 30);
            add(title);
        }
    }


    public class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            if(!block.input.equals(""))
                cp.updateCode(block.name, block.input, -1, block.color);
            if(block.name.equals("el"))
				cp.updateCode(block.name, block.input, -1, block.color);
        }
    }

    public class AddAt extends JPanel implements MouseListener
    {
        private JTextField jt;
        private int pos;

        public AddAt()
        {
            addMouseListener(this);
            setSize(200, 80);
            setBackground(new Color(255, 179, 0));
            setLayout(null);


        }

        public void paintComponent(Graphics g)
        {
            g.setColor(new Color(255, 179, 0));
            g.fillRect(0, 0, 200, 80);
            JLabel lab = new JLabel("Add at line");
            Font font = new Font("Arial", Font.BOLD, 20);
            lab.setFont(font);
            lab.setForeground(Color.WHITE);
            lab.setBounds(50, 10, 150, 30);
            add(lab);

            jt = new JTextField();
            jt.setBounds(70, 40, 60, 30);
            jt.setFont(new Font("Arial", Font.BOLD, 16));
            jt.setBackground(Color.WHITE);
            jt.addActionListener(new TextListener());
            add(jt);
        }

        class TextListener implements ActionListener
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
                    pos = Integer.parseInt(com);
                else
                {
                    jt.setText("");
                }
                if(pos == 0 || pos >cp.getCodeSize())
                {
                    jt.setText("");
                    pos = -1;
                }

            }
        }

        public void mousePressed(MouseEvent evt)
        {
            requestFocusInWindow();
            if(pos != -1 && !block.input.equals("") && !jt.getText().equals(""))
            {
                cp.updateCode(block.name, block.input, pos, block.color);
                jt.setText("");
            }
        }
        public void mouseReleased(MouseEvent evt){}
        public void mouseClicked(MouseEvent evt){}
        public void mouseEntered(MouseEvent evt){}
        public void mouseExited(MouseEvent evt){}
    }
}

class CodePanel extends JPanel
{
    private JPanel code;
    private ModifyPanel modp;
    public ArrayList<Blocks> blockCode;
    private CodingPage cp;

    public CodePanel(ArrayList<Blocks> blockCodeIn, CodingPage cpIn)
    {
		
		cp = cpIn;
        blockCode = blockCodeIn;
        setBackground(Color.WHITE);
        setSize(485, 800);
        setLayout(null);
        JLabel title = new JLabel("Your Code:");
        Font font = new Font("Arial", Font.BOLD, 20);
        title.setFont(font);
        title.setBounds(200, 30, 200, 40);
        add(title);

        JPanel code = new JPanel();
        code.setLayout(new GridLayout(500, 1));
        code.setBackground(Color.WHITE);
       
        Blocks block;
        String after;
        for(int i = 0; i < blockCode.size(); i++)
        {
			String num = String.format("%-5s", i+1 + ")");
            block = blockCode.get(i);
            if(block.name.equals("tr") || block.name.equals("tl"))
				after = " degrees";
			else if(block.name.equals("sl"))
				after = " times:";
			else
				after = "";
            JLabel label = new JLabel(num + block.labelName + " " + block.input + after);
            label.setFont(new Font("Arial", Font.BOLD, 25));
            label.setBackground(block.color);
            label.setForeground(block.color);
            if(block.color == Color.WHITE || block.color == Color.YELLOW)
				label.setForeground(Color.BLACK);
            code.add(label);
            revalidate();
        }
        JScrollPane codeHolder = new JScrollPane(code);
        codeHolder.revalidate();
        codeHolder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        codeHolder.setBounds(42, 100, 400, 550);
        add(codeHolder);
		
    }
    
    
}
