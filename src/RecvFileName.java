import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class RecvFileName
{
	private String port;
	private String name;
	public RecvFileName(String Port)
	{
		port = Port;
	}
	
	public String getFileName()
	{
		DatagramSocket ds = null;
		
		DatagramPacket packet = null;
		try
		{
			ds = new DatagramSocket(Integer.parseInt(port));
			byte[] b = new byte[100];
			packet = new DatagramPacket(b,0,b.length);
			ds.receive(packet);
			name = new String(packet.getData(),0,packet.getLength());
		} catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			if(ds != null)
			{
				ds.close();
			}
		}
		
		return name;
	}
	
	
	
}
