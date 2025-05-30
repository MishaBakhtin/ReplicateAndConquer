import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import javax.swing.*;
import java.util.Scanner;
import java.io.PrintWriter;

public class ReplicateAndConquer 
{
	public static void main(String[] args)
	{
		ReplicateAndConquer rac = new ReplicateAndConquer();
		rac.runIt();
	}
	
	public void runIt()
	{
		JFrame frame = new JFrame("Replicate & Conquer");
		frame.setSize(1400, 800);
		RACHolder rach = new RACHolder();
		frame.add(rach);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}

class RACHolder extends JPanel
{
	public String fileName;
	public double progress;
	private CardLayout cl;
	public info i;
	public JPanel codingPanel;
	private LogInPage lip;
	private SignUpPage sup;
	public SublevelPage sublev;
	public MenuPage mp;
	public StartPage sp;
	public TutPage tp;
	public CampaignPage cp;
	public AboutUsPage aup;
	public RACHolder()
	{
		codingPanel = new JPanel();
		codingPanel.setLayout(null);
		codingPanel.setSize(1400, 800);
		i = new info();
		cl = new CardLayout();
		setSize(1400, 800);
		setLayout(cl);
		
		sp = new StartPage(cl, this);
		add(sp, "StartPage");
		
		lip = new LogInPage(cl,this, i.getLoginInfo());
		add(lip, "LogIn");
		
		sup = new SignUpPage(cl,this, i.getLoginInfo(), i);
		add(sup,"Sign Up");
		
		mp = new MenuPage(cl, this);
		add(mp, "Menu");
		
		tp = new TutPage(cl, this);
		add(tp, "Tutorial");
		
		cp = new CampaignPage(cl, this);
		add(cp, "Campaign");
		
		sublev = new SublevelPage(cl, this);
		add(sublev, "Sublevel");
		
		aup = new AboutUsPage(cl, this);
		add(aup,"AboutUs");
		
		add(codingPanel, "CodingPage");
	}
	
	public void updateProgress()
	{
		File inputFile = new File(fileName + "progress.txt");
        Scanner inFile = null;
        try
        {
            inFile = new Scanner(inputFile); //try to open the file to read from using
        } catch(FileNotFoundException err)
        {
            System.err.printf("File %s could not be found.\n\n\n", fileName + "progress.txt"); //throw an error if it can't find the file
            System.exit(1);
        }
        String n = inFile.next();
        progress = Double.parseDouble(n);
	}
	public LogInPage getLogP()
	{
		return lip;
	}
	public SignUpPage getSignUpP()
	{
		return sup;
	}
}

class StartPage extends JPanel
{
	private CardLayout cl;
    private JButton login,signUp;
    private RACHolder rach;
    private Font f;
	public StartPage(CardLayout c, RACHolder rachIn) // Login panel
	{
		cl = c;
		rach = rachIn;
		f = new Font("Arial", Font.BOLD, 40);
		setLayout(null);

		login = new JButton("Log In");			// Jbuttons that let the user login and sign up
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE); 
        login.setFont(f);
        login.setBounds(450,500,500,75);
        login.addActionListener(new buttonHandler());
        add(login);
        
        signUp= new JButton("Sign Up");
        signUp.setBackground(Color.BLACK);
        signUp.setForeground(Color.WHITE); 
        signUp.setFont(f);
        signUp.setBounds(450,650,500,75);
        signUp.addActionListener(new buttonHandler());
        add(signUp);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1400,800);
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("Logo.jpg"));
		}
		catch(IOException e)
		{
			System.err.println("Logo.jpg couldn't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 400, 50, 600, 400, this);
	}
	class buttonHandler implements ActionListener // checks if user inputs login or sign up, then moves them to the corresponding panel
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

            if (command.equals("Log In"))
            {
                cl.show(rach,"LogIn");
            }
            if(command.equals("Sign Up"))
            {
                cl.show(rach,"Sign Up");
            }
		}
    }
}

class LogInPage extends JPanel implements ActionListener
{
	private CardLayout cl;
	private HashMap<String,String> loginInfo = new HashMap<String,String>(); // holds old and new passwords
	private RACHolder rach;
	private JButton login = new JButton("Login"); 						 // preset all the items so i dont have to declare them later
	private JButton reset= new JButton("Reset");
	private JButton back = new JButton("Back");
	private JTextField id = new JTextField();
	private JPasswordField pass= new JPasswordField();
	private JLabel idL= new JLabel("User ID:");
	private JLabel passL= new JLabel("Password:");
	private JLabel error= new JLabel();
	private Font f= new Font("Arial", Font.BOLD, 60);
	private Font f1= new Font("Arial", Font.BOLD, 35);
	private JButton exit= new JButton("Return");
	private boolean log= false;
	private String user,password;
	public LogInPage(CardLayout c, RACHolder rachIn, HashMap<String,String> loginIn) // lets the user login to their previous account if they have one
	{
		rach = rachIn;
		cl=c;
		loginInfo=loginIn;
		setLayout(null);
		setBackground(Color.WHITE);
		idL.setBounds(100,250,500,100);
		idL.setFont(f);
		passL.setBounds(100,350,500,100);
		passL.setFont(f);

		id.setBounds(550,250,600,100);
		id.setFont(f1);
		pass.setBounds(550,350,600,100);
		pass.setFont(f1);
		error.setBounds(850,650,800,100);
		error.setFont(f1);

		login.setBounds(550,450,300,100);
		login.setBackground(Color.BLACK);
		login.setForeground(Color.WHITE);
		login.setFont(f1);
		login.addActionListener(this);
		reset.setBounds(850,450,300,100);
		reset.setBackground(Color.BLACK);
		reset.setForeground(Color.WHITE);
		reset.setFont(f1);
		reset.addActionListener(this);
		back.setBounds(700,550,300,100);
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setFont(f1);
		back.addActionListener(this);
		add(back);
		
		exit.setBounds(550,600,600,100);
		exit.setFont(f);
		exit.addActionListener(this);

		add(idL);
		add(passL);
		add(id);
		add(pass);
		add(error);
		add(login);
		add(reset);
	}
	public void actionPerformed(ActionEvent e) // checks if the user wants to login or reset their inputs
	{
		if(e.getSource()==reset)
		{
			id.setText("");
			pass.setText("");
		}
		if(e.getSource()==login)	// sets the values to the user's inputs
		{
			user= id.getText().trim();
			password= String.valueOf(pass.getPassword());
			password= password.trim();

			if(loginInfo.containsKey(user))	//check for previous login info
			{
				if(loginInfo.get(user).equals(password))
				{
					cl.show(rach,"Menu");
					log=true;
					rach.fileName = "Users/" +user + password + "/";
					rach.updateProgress();
				}
				else
				{
					error.setForeground(Color.RED);
					error.setText("Incorrect Password");
				}
			}
			else
			{
				error.setForeground(Color.RED);
				error.setText("Username not found");
			}
		}
		
		if(e.getSource() == back)
		{
			cl.show(rach, "StartPage");
		}
	}
	public boolean checkLog()
	{
		return log;
	}
	public String getInfo()
	{
		return user+password;
	}
	public String getName()
	{
		return user;
	}
}


class SignUpPage extends JPanel implements ActionListener
{
	private JButton back = new JButton("Back");
	private CardLayout cl; 							// same as before declare and Initialize values here so i dont have to later
    private RACHolder rach;
	private HashMap<String,String> loginInfo = new HashMap<String,String>();
	private info i;
	private JButton login = new JButton("Sign Up");
	private JButton reset= new JButton("Reset");
	private JTextField id = new JTextField();
	private JPasswordField pass= new JPasswordField();
	private JLabel idL= new JLabel("Set User ID:");
	private JLabel passL= new JLabel("Set Password:");
	private JLabel error= new JLabel();
	private Font f= new Font("Arial", Font.BOLD, 60);
	private Font f1= new Font("Arial", Font.BOLD, 35);
	private boolean sign=false;
	private String newUser,newPass;
	public SignUpPage(CardLayout c, RACHolder rachIn, HashMap<String,String> loginIn, info iIn) // creates the fiels and other objects
	{
		rach = rachIn;
		setBackground(Color.WHITE);
		cl=c;
		loginInfo=loginIn;
		i=iIn;
		setLayout(null);
		idL.setBounds(100,250,500,100);
		idL.setFont(f);
		passL.setBounds(100,350,500,100);
		passL.setFont(f);


		id.setBounds(550,250,600,100);
		id.setFont(f1);
		pass.setBounds(550,350,600,100);
		pass.setFont(f1);
		error.setBounds(850,650,800,100);
		error.setFont(f1);

		login.setBounds(550,450,300,100);
		login.setBackground(Color.BLACK);
		login.setForeground(Color.WHITE);
		login.setFont(f1);
		login.addActionListener(this);
		reset.setBounds(850,450,300,100);
		reset.setBackground(Color.BLACK);
		reset.setForeground(Color.WHITE);
		reset.setFont(f1);
		reset.addActionListener(this);
		back.setBounds(700,550,300,100);
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setFont(f1);
		back.addActionListener(this);
		add(back);

		add(idL);
		add(passL);
		add(id);
		add(pass);
		add(error);
		add(login);
		add(reset);
	}
	public void actionPerformed(ActionEvent e) // checks if the username inputted is taken, checks for blanks, has the reset and signup functions
	{
		newUser = id.getText().trim();
        newPass = String.valueOf(pass.getPassword());
		newPass.trim();

        if (loginInfo.containsKey(newUser)&&e.getActionCommand().equals("Sign Up"))
        {
            error.setForeground(Color.RED);
            error.setText("Username already exists");
        }
        else if(e.getSource() == back)
		{
			cl.show(rach, "StartPage");
		}
		else if(e.getSource()==reset)
		{
			id.setText("");
			pass.setText("");
		}
        else if(newPass.isEmpty() && newUser.isEmpty())
        {
			error.setForeground(Color.RED);
            error.setText("Fields cannot be empty");
		}
        else if (newPass.isEmpty())
        {
            error.setForeground(Color.RED);
            error.setText("Password cannot be empty");
        }
        else if (newUser.isEmpty())
        {
			error.setForeground(Color.RED);
            error.setText("Username cannot be empty");
		}
        else
        {
			rach.fileName = "Users/" + newUser + newPass + "/";
            loginInfo.put(newUser, newPass);
            error.setForeground(Color.GREEN);
            i.makeFolderFD(rach.fileName);
			i.appendFile(newUser, newPass); // saves it to the hashmap, which is printed out to the loginData.txt which stores the hashmap
			
            error.setText("Account created!");
			sign=true;
			
			cl.show(rach,"Tutorial");
			
			File outFile = new File(rach.fileName + "progress.txt");
			PrintWriter pw = null;
			try
			{
				pw = new PrintWriter(outFile); //try to create PrintWriter
			}
			
			catch(IOException error)
			{
				System.err.print("Can't create/write to " + rach.fileName + "progress.txt"); //throw an error if it's not possible
				System.exit(2);
			}
			
			pw.print("1.1");
			pw.close();
			rach.updateProgress();
		}
		
	}
	public boolean checkSign()
	{
		return sign;
	}
	public String getInfo()
	{
		return newUser+newPass;
	}
	public String getName()
	{
		return newUser;
	}
}


class MenuPage extends JPanel
{
	private CardLayout cl;
	private RACHolder rach;
	public MenuPage(CardLayout c, RACHolder rachIn)
	{
		rach = rachIn;
		cl=c;
		setLayout(null);
		
		Font font = new Font("Arial", Font.BOLD, 30);
		JButton cam,free,tut,abt;
		cam= new JButton("Campaign");
		cam.setBackground(Color.BLACK);
		cam.setForeground(Color.WHITE);
		cam.setFont(font);
		cam.setBounds(800,50,350,100);
		cam.addActionListener(new hbuttonHandler());
		add(cam);
		
		free= new JButton("Free Draw");
		free.setBackground(Color.BLACK);
		free.setForeground(Color.WHITE);
		free.setFont(font);
		free.setBounds(800,220,350,100);
		free.addActionListener(new hbuttonHandler());
		add(free);
		
		tut= new JButton("Tutorial");
		tut.setBackground(Color.BLACK);
		tut.setForeground(Color.WHITE);
		tut.setFont(font);
		tut.setBounds(800,390,350,100);
		tut.addActionListener(new hbuttonHandler());
		add(tut);
		
		abt= new JButton("About Us");
		abt.setBackground(Color.BLACK);
		abt.setForeground(Color.WHITE);
		abt.setFont(font);
		abt.setBounds(800,560,350,100);
		abt.addActionListener(new hbuttonHandler());
		add(abt);

	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1400,800);
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("MenuBackground.jpg"));
		}
		catch(IOException e)
		{
			System.err.println("MenuBackground.jpg couldn't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 0, 0, 600, 800, this);
		
		try 
		{
			image = ImageIO.read(new File("Logo.jpg"));
		}
		catch(IOException e)
		{
			System.err.println("Logo.jpg couldn't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 60, 50, 450, 300, this);
	}
	class hbuttonHandler implements ActionListener // sends users to the panel they want to go to
	{
		public void actionPerformed(ActionEvent evt)
		{
			SignUpPage sup= rach.getSignUpP();
			LogInPage lp= rach.getLogP();
			String command = evt.getActionCommand();
			boolean logIn=false;
			logIn=lp.checkLog();
			
			boolean signIn=false;
			signIn=sup.checkSign();
			String fileName="";
			if(command.equals("Campaign"))
			{
				cl.show(rach,"Campaign");
				rach.cp.revalidate();
			}
			if(command.equals("Free Draw"))
			{
				cl.show(rach, "CodingPage");
				rach.codingPanel.removeAll();
				rach.codingPanel.setBackground(Color.BLACK);
				rach.codingPanel.add(new CodingPage(cl, true, "", rach.fileName, rach));
				
				rach.codingPanel.revalidate();
				
			}
			if(command.equals("Tutorial"))
			{
                cl.show(rach,"Tutorial");
                rach.tp.revalidate();
            }
			if(command.equals("About Us"))
			{
				cl.show(rach,"AboutUs");
			}
		}
	}
}

class TutPage extends JPanel
{
	private CardLayout cl;
	private RACHolder rach;
	private int slide;
	public TutPage(CardLayout clIn, RACHolder rachIn)
	{
		slide = 1;
		setLayout(null);
		cl = clIn;
		rach= rachIn;
		setSize(1400, 800);
		Font font = new Font("Arial", Font.BOLD, 30);
		JButton back= new JButton("Back");
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setFont(font);
		back.setBounds(20, 700, 120, 50);
		back.addActionListener(new hbuttonHandler());
		add(back);
		
		JButton next= new JButton("Next");
		next.setBackground(Color.BLACK);
		next.setForeground(Color.WHITE);
		next.setFont(font);
		next.setBounds(1250, 700, 120, 50);
		next.addActionListener(new hbuttonHandler());
		add(next);
	}
	
	public void paintComponent(Graphics g)
	{
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("TutorialPictures/tut" + slide + ".png"));
		}
		catch(IOException e)
		{
			System.err.println("TutorialPictures/tut" + slide + ".png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 0, 0, 1400, 800, this);
	}
	
	public class hbuttonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String com = evt.getActionCommand();
			if(com.equals("Back"))
			{
				slide--;
				if(slide == 0)
				{
					slide = 1;
					repaint();
				}
				else
					repaint();
			}
			else 
			{
				slide++;
				if(slide == 12)
				{
					slide = 1;
					cl.show(rach, "Menu");
				}
				else
					repaint();
			}
		}
	}
}

class CampaignPage extends JPanel
{
	private CardLayout cl;
	private RACHolder rach;
	public CampaignPage(CardLayout clIn, RACHolder rachIn)
	{
		cl = clIn;
		rach= rachIn;
		setLayout(null);
		
	}
	
	public void paintComponent(Graphics g)
	{
		removeAll();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1400,800);
		Font font = new Font("Arial", Font.BOLD, 40);
		
		JLabel choose = new JLabel("Which boss are you going to fight?");
		choose.setFont(new Font("Arial", Font.BOLD, 30));
		choose.setBounds(440, 20, 600, 40);
		choose.setForeground(Color.BLACK);
		add(choose);
		
		JButton first = new JButton("1");
		if(rach.progress > 1)
			first.setBackground(Color.BLACK);
		else
			first.setBackground(Color.RED);
		first.setForeground(Color.WHITE);
		first.setFont(font);
		first.setBounds(225, 400, 200, 200);
		first.addActionListener(new hbuttonHandler());
		add(first);
		
		JButton second = new JButton("2");
		if(rach.progress > 2)
			second.setBackground(Color.BLACK);
		else
			second.setBackground(Color.RED);
		second.setForeground(Color.WHITE);
		second.setFont(font);
		second.setBounds(475, 400, 200, 200);
		second.addActionListener(new hbuttonHandler());
		add(second);
		
		JButton third = new JButton("3");
		if(rach.progress > 3)
			third.setBackground(Color.BLACK);
		else
			third.setBackground(Color.RED);
		third.setForeground(Color.WHITE);
		third.setFont(font);
		third.setBounds(725, 400, 200, 200);
		third.addActionListener(new hbuttonHandler());
		add(third);
		
		JButton fourth = new JButton("4");
		if(rach.progress > 4)
			fourth.setBackground(Color.BLACK);
		else
			fourth.setBackground(Color.RED);
		fourth.setForeground(Color.WHITE);
		fourth.setFont(font);
		fourth.setBounds(975, 400, 200, 200);
		fourth.addActionListener(new hbuttonHandler());
		add(fourth);
		
		JButton back= new JButton("Back");
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setFont(font);
		back.setBounds(600, 600, 200, 100);
		back.addActionListener(new hbuttonHandler());
		add(back);
		
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("MapPictures/Jensen.png"));
		}
		catch(IOException e)
		{
			System.err.println("MapPictures/Jensen.png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 225, 100, 200, 300, this);
		
		image = null;
		try 
		{
			image = ImageIO.read(new File("MapPictures/Jordan.png"));
		}
		catch(IOException e)
		{
			System.err.println("MapPictures/Jordan.png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 475, 100, 200, 300, this);
		
		image = null;
		try 
		{
			image = ImageIO.read(new File("MapPictures/JE.png"));
		}
		catch(IOException e)
		{
			System.err.println("MapPictures/JE.png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 725, 100, 200, 300, this);
		
		image = null;
		try 
		{
			image = ImageIO.read(new File("MapPictures/Dante.png"));
		}
		catch(IOException e)
		{
			System.err.println("MapPictures/Dante.png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 975, 100, 200, 300, this);
	}
	
	public class hbuttonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if(evt.getActionCommand().equals("Back"))
				cl.show(rach, "Menu");
			else if(rach.progress > Integer.parseInt(evt.getActionCommand()))
			{
				rach.sublev.lvl = Integer.parseInt(evt.getActionCommand());
				cl.show(rach, "Sublevel");
				rach.sublev.repaint();
				rach.sublev.revalidate();
			}
		}
	}
}

class SublevelPage extends JPanel
{
	private CardLayout cl;
	private RACHolder rach;
	public int lvl;
	public SublevelPage(CardLayout clIn, RACHolder rachIn)
	{
		cl = clIn;
		rach= rachIn;
		setLayout(null);
		
	}
	
	public void paintComponent(Graphics g)
	{
		removeAll();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,1400,800);
		Font font = new Font("Arial", Font.BOLD, 40);
		
		JLabel choose = new JLabel("Choose the sublevel:");
		choose.setFont(new Font("Arial", Font.BOLD, 35));
		choose.setBounds(550, 100, 600, 40);
		choose.setForeground(Color.BLACK);
		add(choose);
		
		JButton first = new JButton(lvl + ".1");
		if(rach.progress >= Double.parseDouble(lvl + ".1"))
			first.setBackground(Color.BLACK);
		else
			first.setBackground(Color.RED);
		first.setForeground(Color.WHITE);
		first.setFont(font);
		first.setBounds(100, 300, 200, 200);
		first.addActionListener(new hbuttonHandler());
		add(first);
		
		JButton second = new JButton(lvl + ".2");
		if(rach.progress >= Double.parseDouble(lvl + ".2"))
			second.setBackground(Color.BLACK);
		else
			second.setBackground(Color.RED);
		second.setForeground(Color.WHITE);
		second.setFont(font);
		second.setBounds(350, 300, 200, 200);
		second.addActionListener(new hbuttonHandler());
		add(second);
		
		JButton third = new JButton(lvl + ".3");
		if(rach.progress >= Double.parseDouble(lvl + ".3"))
			third.setBackground(Color.BLACK);
		else
			third.setBackground(Color.RED);
		third.setForeground(Color.WHITE);
		third.setFont(font);
		third.setBounds(600, 300, 200, 200);
		third.addActionListener(new hbuttonHandler());
		add(third);
		
		JButton fourth = new JButton(lvl + ".4");
		if(rach.progress > Double.parseDouble(lvl + ".4"))
			fourth.setBackground(Color.BLACK);
		else
			fourth.setBackground(Color.RED);
		fourth.setForeground(Color.WHITE);
		fourth.setFont(font);
		fourth.setBounds(850, 300, 200, 200);
		fourth.addActionListener(new hbuttonHandler());
		add(fourth);
		
		JButton fifth = new JButton(lvl + ".5");
		if(rach.progress > Double.parseDouble(lvl + ".5"))
			fifth.setBackground(Color.BLACK);
		else
			fifth.setBackground(Color.RED);
		fifth.setForeground(Color.WHITE);
		fifth.setFont(font);
		fifth.setBounds(1100, 300, 200, 200);
		fifth.addActionListener(new hbuttonHandler());
		add(fifth);
		
		JButton back= new JButton("Back");
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setFont(font);
		back.setBounds(600, 600, 200, 100);
		back.addActionListener(new hbuttonHandler());
		add(back);
	}
	
	public class hbuttonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			SignUpPage sup= rach.getSignUpP();
			LogInPage lp= rach.getLogP();
			String com = evt.getActionCommand();
			boolean logIn=false;
			logIn=lp.checkLog();
			
			boolean signIn=false;
			signIn=sup.checkSign();
			
			String fileName=("");
			if(com.equals("Back"))
			{
				cl.show(rach, "Campaign");
				rach.cp.revalidate();
				rach.cp.repaint();
			}
			else if(Double.parseDouble(com) <= rach.progress)
			{
				if(signIn==true)
				{
					fileName=sup.getInfo();
					
				}
				else if (logIn==true)
				{
					fileName=lp.getInfo();
				}
				cl.show(rach, "CodingPage");
				rach.codingPanel.removeAll();
				rach.codingPanel.setBackground(Color.BLACK);
				rach.codingPanel.add(new CodingPage(cl, false, lvl + "-" + com.substring(2), rach.fileName, rach));
				
				rach.codingPanel.revalidate();
			}
		}
	}
}

class AboutUsPage extends JPanel implements ActionListener
{
	private CardLayout cl;
	private RACHolder rach;
	public AboutUsPage(CardLayout clIn, RACHolder rachIn)
	{
		setLayout(null);
		cl = clIn;
		rach = rachIn;
		setSize(1400, 800);
		Font font = new Font("Arial", Font.BOLD, 20);
		JButton back= new JButton("Back");
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setFont(font);
		back.setBounds(650, 700, 100, 50);
		back.addActionListener(this);
		add(back);
	}
	
	public void paintComponent(Graphics g)
	{
		Image image = null;
		try 
		{
			image = ImageIO.read(new File("TutorialPictures/tut0.png"));
		}
		catch(IOException e)
		{
			System.err.println("TutorialPictures/tut0.png can't be found");
			System.exit(1);
		}
		
		g.drawImage(image, 0, 0, 1400, 800, this);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		cl.show(rach, "Menu");
	}
}
