import java.awt.TextArea;

import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.format.AudioFormat;

class TransmitThread extends Thread       //调用之前 先设置IP地址和端口号
{
	//用一个字符串数组传送IP和端口号
	private String[] DestIP = {"",""};
	private TextArea ta;
	private MediaTransmit at;
	public TransmitThread(String IP, String Port,TextArea ta)
	{
		DestIP[0] = IP;
		DestIP[1] = Port;
		this.ta = ta;
	}
	
	public MediaTransmit getMediaTransmit()
	{
		return at;
	}
	public void run()
	{
		// 找可用的媒体硬件
		CaptureAudio ca = new CaptureAudio();
		MediaLocator medialocator = ca.initialCaptureStream(new AudioFormat(
				AudioFormat.LINEAR, 44100, 16, 2),ta);

		Format fmt = null;
		//String DestIP = "192.168.0.101"; //输入目的地IP
		//String DestPortBase = "22222";//输入目的地端口号
		at = new MediaTransmit(medialocator,DestIP[0], DestIP[1], fmt,ta);
		// Start the transmission
		String result = at.start();

		// result will be non-null if there was an error. The return
		// value is a String describing the possible error. Print it.
		if (result != null)
		{
			System.err.println("Error : " + result);
			ta.append("  < Error : " + result);
			System.exit(0);
		}

		System.err.println("Transmission Started");
		ta.append("  < Transmission Started\n");

		// Transmit for 60 seconds and then close the processor
		// This is a safeguard when using a capture data source
		// so that the capture device will be properly released
		// before quitting.
		// The right thing to do would be to have a GUI with a
		// "Stop" button that would call stop on AVTransmit2
		try
		{
			Thread.currentThread().sleep(6000000);
		} catch (InterruptedException ie)
		{
		}

		// Stop the transmission
		at.stop();

		System.err.println("Reach to MAX time");
		ta.append("  < Reach to MAX time");
		System.exit(0);
	}
}