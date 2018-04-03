import java.awt.TextArea;
import java.io.*;
import java.net.*;

public class FileSender extends Thread
{
	private String destIP;
	private String destPort;
	private TextArea ta;
	private File file;
	private FileInputStream fis;
	private DatagramSocket ds;
	private String fileURL;

	public FileSender(String IP, String Port, String URL, TextArea t)
	{

		destIP = IP;
		destPort = Port;
		fileURL = URL;
		ta = t;
	}

	public void run()
	{
		
		sendFile();
	}

	
	/*
	 * 
	 * double click send button to send a file 
	 * file name via UDP send abnormal
	 * 
	 * name need to be send first 
	 * 
	 * :write new class to send name first by the action of button "load"
	 * 
	 */
	
	

	public void sendFile()
	{
		
		FileInputStream fis = null;
		OutputStream os = null;
		Socket s = null;
		try
		{
			s = new Socket(InetAddress.getByName(destIP),
					Integer.parseInt(destPort));
			os = s.getOutputStream();
			// fileURL could be like C:\Users\MATT\Desktop\1.doc
			fis = new FileInputStream(new File(fileURL));
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] b = new byte[1024];
		int len;
		try
		{
			while ((len = fis.read(b)) != -1)
			{
				os.write(b, 0, len);
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			os.close();
			fis.close();
			s.close();
			ta.append("File sent\n");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
