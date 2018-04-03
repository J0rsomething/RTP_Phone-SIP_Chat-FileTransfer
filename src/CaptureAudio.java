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

		// ��ѯCaptureDeviceManager������λ����Ҫʹ�õ�ý��ɼ��豸��
		Vector<?> deviceList = CaptureDeviceManager.getDeviceList(audioform);

		if (deviceList.size() > 0)
		{
			// �õ����豸��CaptureDeviceInfoʵ����
			di = (CaptureDeviceInfo) deviceList.firstElement();
			// di = (CaptureDeviceInfo) deviceList.lastElement();
			System.out.println("Device Found:" + di.getName());// ��ʾʹ�õ��豸����
			ta.append("Device Found:" + di.getName() + "\n");
			//gui.display("Device Found:" + di.getName());
		} else
		{
			// �Ҳ������㣨linear, 44100Hz, 16 bit,stereo audio.����Ƶ�豸���˳���
			System.out.println("ERROR: No Device Found");
			System.exit(-1);
		}
		
		
		return di.getLocator();	
		
	}
	public static void main(String[] args)
	{

	}
}
