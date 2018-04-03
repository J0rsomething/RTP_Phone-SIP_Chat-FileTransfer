import java.awt.TextArea;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Control;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoPlayerException;
import javax.media.NoProcessorException;
import javax.media.NotRealizedError;
import javax.media.Processor;
import javax.media.Player;
import javax.media.ProcessorModel;
import javax.media.control.FormatControl;
import javax.media.control.StreamWriterControl;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;

public class CaptureAudio
{

	
	MediaLocator medialocator = null;

	public  MediaLocator initialCaptureStream(AudioFormat audioformat,TextArea ta)
	{
		CaptureDeviceInfo di = null;
		

		AudioFormat audioform = audioformat;

		// 查询CaptureDeviceManager，来定位你需要使用的媒体采集设备。
		Vector<?> deviceList = CaptureDeviceManager.getDeviceList(audioform);

		if (deviceList.size() > 0)
		{
			// 得到此设备的CaptureDeviceInfo实例。
			di = (CaptureDeviceInfo) deviceList.firstElement();
			// di = (CaptureDeviceInfo) deviceList.lastElement();
			System.out.println("Device Found:" + di.getName());// 显示使用的设备名称
			ta.append("Device Found:" + di.getName() + "\n");
			//gui.display("Device Found:" + di.getName());
		} else
		{
			// 找不到满足（linear, 44100Hz, 16 bit,stereo audio.）音频设备，退出。
			System.out.println("ERROR: No Device Found");
			System.exit(-1);
		}
		
		
		return di.getLocator();	
		
	}
	public static void main(String[] args)
	{

	}
}
