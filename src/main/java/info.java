import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class info
{
    private HashMap<String,String> loginInfo = new HashMap<String,String>();
    private File f= new File("loginData.txt");
    private File folder,directory;
    
    protected void addUser(String username,String password)
    {
        loginInfo.put(username,password);
    }
    protected void appendFile(String username, String password)
    {
        PrintWriter pw;
        try
        {
            pw= new PrintWriter(new FileWriter(f,true));
            pw.println(username+":"+password);
            pw.close();    
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Couldnt write to file");
        }
    }
    public HashMap<String, String> getLoginInfo() 
    {
        HashMap<String, String> map = new HashMap<>();

        if (!f.exists()) 
        {
            return map; // return empty map if file doesn't exist
        }
        try 
        {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                String[] parts = line.split(":"); // split the array object at the : , which gives the username at array 0, and password array 1
                if (parts.length == 2) 
                {
                    map.put(parts[0], parts[1]);
                }
            }
            scanner.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            System.err.println("Could not read loginData.txt");
        }
        return map;
    }
    public void makeFolder(String username) // used the code from Geeks for Geeks, cited it 
    {
        // Sprecify the Directory Name 
        String directoryName = username;
        
        // Address of Current Directory
        String currentDirectory = System.getProperty("user.dir");

        // Specify the path of the directory to be created
        String directoryPath = currentDirectory + File.separator + directoryName;

        // Create a File object representing the directory
        directory = new File(directoryPath);

        // Attempt to create the directory
        boolean directoryCreated = directory.mkdir();

        if (directoryCreated) 
        {
            System.out.println("Directory created successfully at: " + directoryPath);
        } else 
        {
            System.out.println("Failed to create directory. It may already exist at: " + directoryPath);
        }
	}
    public void makeFolderFD(String username)
    {
		makeDirs md= new makeDirs(username);
	}
    
}
	class makeDirs
	{
		String user;
		public makeDirs(String user)
		{
		File directory = new File(user);
        String childDir ="FreeDraw";


        if (directory.exists() || directory.mkdirs()) {
            File childDirectory = new File(directory + File.separator + childDir);
            if (childDirectory.exists() || childDirectory.mkdirs()) {
                System.out.println("Nested directories created successfully.");
            } else {
                 System.err.println("Failed to create child directory.");
            }
        } else {
            System.err.println("Failed to create parent directory.");
        }
		}
	}
