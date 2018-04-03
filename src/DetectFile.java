import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DetectFile
{
	private String destIP;
	private String destPort;
	private String fileURL;
	private TextArea ta;

	public DetectFile(String IP, String Port, String URL, TextArea textarea)
	{
		destIP = IP;
		destPort = Port;
		fileURL = URL;
		ta = textarea;
	}

	// detect if the file exist and send the file name via UDP
	public boolean detect()
	{
		DatagramSocket ds = null;
		File file = null; 
		try
		{
			file = new File(fileURL);
			ds = new DatagramSocket();
			if (file.exists())
			{
				byte[] b = file.getName().getBytes();
				DatagramPacket packet = new DatagramPacket(b, 0, b.length,
						InetAddress.getByName(destIP), Integer.parseInt(destPort));
				ds.send(packet);
				
			} else
			{
				return false;
			} 
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(ds != null)
			{
				ds.close();
				
			}
		}
		return true;
 	}

}
