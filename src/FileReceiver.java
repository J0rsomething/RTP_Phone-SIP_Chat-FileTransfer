import java.awt.TextArea;
import java.io.*;
import java.net.*;

/*
 * parameter:  <Port> <Save URL>   <TextArea>
 * 
 * 
 * 
 * 
 */
public class FileReceiver extends Thread
{
	private TextArea ta;
	private String port;
	private String fileURL;
	private String fileName;
	
	
	public FileReceiver(String p,String URL,String Name,TextArea t)
	{
		ta = t;
		port = p;
		fileURL = URL;
		fileName = Name;
	}
	
	public void run()
	{
		startReceive();
	}
	
	private void startReceive()
	{
		ServerSocket ss = null;
		Socket s = null;
		InputStream is = null;
		FileOutputStream fos = null;
		
		try
		{
			ss = new ServerSocket(Integer.parseInt(port));
			s = ss.accept();
			is = s.getInputStream();
			//Example:  "C:\Users\MATT\Desktop" + "\\" + "1.doc"
			fos = new FileOutputStream(new File(fileURL + "\\"+ fileName));
			
			ta.append("Saving file to " + fileURL + "\\"+ fileName + "\n");
			byte[] b = new byte[1024];
			int len;
			while((len = is.read(b)) != -1)
			{
				fos.write(b, 0, len);
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			fos.close();
			is.close();
			s.close();
			ss.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
}
