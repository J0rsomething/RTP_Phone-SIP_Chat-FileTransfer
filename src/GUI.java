import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TooManyListenersException;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.format.AudioFormat;
import javax.sip.InvalidArgumentException;
import javax.sip.ObjectInUseException;
import javax.sip.PeerUnavailableException;
import javax.sip.TransportNotSupportedException;
import javax.sound.sampled.*;
import javax.swing.*;

public class GUI
{

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { PhoneFrame frame = new PhoneFrame();
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * frame.setVisible(true); frame.setResizable(false); } }); }
	 */
	public GUI()
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				PhoneFrame frame = new PhoneFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});

	}
}

class PhoneFrame extends JFrame
{

	private JLabel sendIP;
	private JTextField sendIPText;
	private JLabel sendPort;
	private JTextField sendPortText;
	private JLabel recvPort;
	private JTextField recvPortText;
	private JButton testCapture;
	private JButton start;
	private JButton stop;
	private JLabel hostIP;
	private TextArea ta;
	private boolean isSending = false;
	private boolean isTesting = false;
	private MediaReceive mediareceive;
	private MediaTransmit mediatransmit;

	private JButton recv;
	private JButton send;
	private JButton load;
	private JTextField sendFileURL;
	private JTextField recvFileURL;
	private JLabel sendURL;
	private JLabel recvURL;
	private JLabel fileIP;
	private JLabel filePort;
	private JTextField fileIPText;
	private JTextField filePortText;

	private JTextField sendMessageText;
	private JLabel sendMessage;
	private JLabel localSIP;
	private JLabel destSIP;
	private JTextField destSIPText;
	private JButton sendSMS;

	public PhoneFrame()
	{
		setTitle("Phone");
		int height = 500;
		int width = 500;
		setSize(width, height);
		setLocationByPlatform(true);
		setLayout(null);

		// set a lovely icon
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("icon.png");
		setIconImage(img);

		ta = new TextArea();// �ϰ벿�ֵ���������ʾ��
		ta.setBounds(0, 0, 500, 200);
		add(ta);

		// RTP�绰����
		sendIP = new JLabel("        SendIP:");
		sendIP.setBounds(0, 0, 100, 50);

		sendIPText = new JTextField();
		sendIPText.setBounds(110, 10, 200, 30);

		sendPort = new JLabel("        SendPort:");
		sendPort.setBounds(0, 60, 100, 50);

		sendPortText = new JTextField("22222");
		sendPortText.setBounds(110, 70, 50, 30);

		recvPort = new JLabel("   RecvPort:");
		recvPort.setBounds(170, 60, 100, 50);

		recvPortText = new JTextField("33333");
		recvPortText.setBounds(260, 70, 50, 30);

		testCapture = new JButton("AV Test");
		testCapture.setBounds(20, 170, 100, 30);

		start = new JButton("Start");
		start.setBounds(140, 170, 100, 30);

		stop = new JButton("Stop");
		stop.setBounds(260, 170, 100, 30);

		JLabel hostIP = new JLabel();
		hostIP.setBounds(0, 110, 250, 50);

		/*
		 * filePanel component
		 */

		recv = new JButton("Receive");
		recv.setBounds(20, 170, 100, 30);
		recv.setToolTipText("Press to Receive");

		send = new JButton("Send");
		send.setBounds(250, 170, 100, 30);

		load = new JButton("Load");
		load.setToolTipText("wait destination start receive and then load a file first and then send");
		load.setBounds(130, 170, 100, 30);

		sendURL = new JLabel("  Send File URL:");
		sendURL.setBounds(0, 0, 100, 50);

		recvURL = new JLabel("  File Save To:");
		recvURL.setBounds(0, 60, 100, 50);

		sendFileURL = new JTextField();
		sendFileURL.setBounds(110, 10, 330, 30);

		recvFileURL = new JTextField();
		recvFileURL.setBounds(110, 70, 330, 30);

		fileIP = new JLabel("    Dest IP:");
		fileIP.setBounds(0, 110, 100, 50);

		fileIPText = new JTextField();
		fileIPText.setBounds(110, 120, 130, 30);

		filePort = new JLabel("     Port:");
		filePort.setBounds(250, 110, 100, 50);

		filePortText = new JTextField("44444");
		filePortText.setBounds(330, 120, 100, 30);

		/*
		 * SMS Panel
		 */
		sendMessage = new JLabel("    Message: ");
		sendMessage.setBounds(0, 0, 100, 50);

		sendMessageText = new JTextField();
		sendMessageText.setBounds(100, 10, 350, 30);

		try
		{
			localSIP = new JLabel("    From:                   sip:" + InetAddress.getLocalHost().getHostName()
					+ "@" + InetAddress.getLocalHost().getHostAddress() + ":"
					+ 5060); // display local sipURI set later
		} catch (UnknownHostException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		localSIP.setBounds(0, 60, 300, 50);

		destSIP = new JLabel("    To URI:  ");
		destSIP.setBounds(0, 110, 100, 50);

		destSIPText = new JTextField();
		destSIPText.setBounds(100, 120, 350, 30);

		sendSMS = new JButton("Send");
		sendSMS.setBounds(190, 160, 100, 50);

		InetAddress addr = null;
		try
		{
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hostIP.setText("        Local Host IP:    " + addr.getHostAddress());// ��ñ���IP

		JTabbedPane jtp = new JTabbedPane();

		JPanel rtpPanel = new JPanel();
		JPanel filePanel = new JPanel();
		JPanel smsPanel = new JPanel();
		rtpPanel.setLayout(null);
		filePanel.setLayout(null);
		smsPanel.setLayout(null);

		rtpPanel.add(sendIP);
		rtpPanel.add(sendIPText);
		rtpPanel.add(sendPort);
		rtpPanel.add(sendPortText);
		rtpPanel.add(recvPort);
		rtpPanel.add(recvPortText);
		rtpPanel.add(testCapture);
		rtpPanel.add(start);
		rtpPanel.add(stop);
		rtpPanel.add(hostIP);

		filePanel.add(send);
		filePanel.add(recv);
		filePanel.add(sendURL);
		filePanel.add(recvURL);
		filePanel.add(sendFileURL);
		filePanel.add(recvFileURL);
		filePanel.add(fileIP);
		filePanel.add(fileIPText);
		filePanel.add(filePort);
		filePanel.add(filePortText);
		filePanel.add(load);

		smsPanel.add(sendMessage);
		smsPanel.add(sendMessageText);
		smsPanel.add(localSIP);
		smsPanel.add(destSIP);
		smsPanel.add(destSIPText);
		smsPanel.add(sendSMS);
		/***********
			 * 
			 * 
			 * 
			 */

		// ********************
		jtp.setBounds(10, 210, 480, 280);
		jtp.add(rtpPanel, "RTP Dialog");
		jtp.add(filePanel, "File Transmitter");
		jtp.add(smsPanel, "SIP SMS");

		add(jtp);

		CaptureAction captureAction = new CaptureAction();
		testCapture.addActionListener(captureAction);
		stop.addActionListener(captureAction);
		start.addActionListener(captureAction);

		FileAction fileAction = new FileAction();
		send.addActionListener(fileAction);
		recv.addActionListener(fileAction);
		load.addActionListener(fileAction);

		SMSAction smsAction = new SMSAction();
		sendSMS.addActionListener(smsAction);
	}

	private class CaptureAction implements ActionListener
	{

		Player p = null;

		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == testCapture && p == null)
			{
				CaptureAudio ca = new CaptureAudio();
				MediaLocator medialocator = ca
						.initialCaptureStream(new AudioFormat(
								AudioFormat.LINEAR, 44100, 16, 2),ta);
				// p = null;
				try
				{
					p = Manager.createRealizedPlayer(medialocator);
				} catch (NoPlayerException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotRealizeException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isTesting = true;
				p.start();
				
			} else if (event.getSource() == start && p == null)
			{
				String DestinationIP = sendIPText.getText();
				String SendPort = sendPortText.getText();
				String RecvPort = recvPortText.getText();

				ReceiveThread receive = new ReceiveThread(DestinationIP,
						RecvPort, ta);
				TransmitThread transmit = new TransmitThread(DestinationIP,
						SendPort, ta);
				mediatransmit = transmit.getMediaTransmit();
				mediareceive = receive.getMediaReceive();

				receive.start();
				transmit.start();
				isSending = true;
			} else if (event.getSource() == stop)
			{
				if (p != null && p.getState() == 600 && isTesting == true)
				{
					p.stop();
					p.close();
					p = null;
					isTesting = false;
				} else if (isSending == true)
				{
					// �����ֹ���͡ￄ1�7
					/*
					 * **************************************
					 * **************************************
					 * **************************************
					 * **************************************
					 */
					mediatransmit.stop();
					mediareceive.close();

				}
			}

		}

		public CaptureAction()
		{

		}

	}

	private class FileAction implements ActionListener
	{
		private FileReceiver filerecv = null;
		private FileSender filesender = null;
		private String recvFileName;

		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == recv)
			{
				RecvFileName recvfilename = new RecvFileName(
						filePortText.getText());
				recvFileName = recvfilename.getFileName();
				filerecv = new FileReceiver(filePortText.getText(),
						recvFileURL.getText(), recvFileName, ta);
				filerecv.start();
			} else if (event.getSource() == send)
			{
				filesender = new FileSender(fileIPText.getText(),
						filePortText.getText(), sendFileURL.getText(), ta);
				filesender.start();
			} else if (event.getSource() == load)
			{
				// �����ڷ��Ͷ���detect UDP�����ļ���
				// ���ն�����һ�����������ļ��� �ٰ��ļ�����Ϊ��������recv�С�

				DetectFile detectfile = new DetectFile(fileIPText.getText(),
						filePortText.getText(), sendFileURL.getText(), ta);
				if (detectfile.detect())
				{
					ta.append("File found\n");
				} else
				{
					ta.append("File not found\n");
				}

			}

		}
	}

	private class SMSAction implements MessageProcessor, ActionListener
	{

		private SipLayer sipLayer = null;

		public SMSAction()
		{
			try
			{
				String localname = InetAddress.getLocalHost().getHostName();
				String localip = InetAddress.getLocalHost().getHostAddress();
				sipLayer = new SipLayer(localname, localip, 5060);
				sipLayer.setMessageProcessor(this);
			} catch (UnknownHostException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PeerUnavailableException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransportNotSupportedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectInUseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TooManyListenersException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void sendBtnActionPerformed(ActionEvent evt)
		{

			try
			{
				String to = destSIPText.getText();
				String message = sendMessageText.getText();
				sipLayer.sendMessage(to, message);
			} catch (Throwable e)
			{
				e.printStackTrace();
				ta.append("ERROR sending message: " + e.getMessage() + "\n");
			}

		}

		public void actionPerformed(ActionEvent evt)
		{

			sendBtnActionPerformed(evt);
		}

		public void processMessage(String sender, String message)
		{

			ta.append("From " + sender + ":\n " + message + "\n");
		}

		public void processError(String errorMessage)
		{
			ta.append("ERROR: " + errorMessage + "\n");
		}

		public void processInfo(String infoMessage)
		{
			ta.append(infoMessage + "\n");
		}

	}

}
