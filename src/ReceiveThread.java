import java.awt.TextArea;

class ReceiveThread extends Thread           //调用之前 先设置IP地址和端口号
{
	
	private TextArea ta;
	private String[] DestIP = {"",""};
	private MediaReceive avReceive;
	public ReceiveThread(String IP, String Port,TextArea ta)
	{
		DestIP[0] = IP;
		DestIP[1] = Port;
		this.ta = ta;
	}
	
	public MediaReceive getMediaReceive()
	{
		return avReceive;
	}
	
	public void run()
	{

		//***********************************
		//Receive code
		//***********************************
		String argv[] = {DestIP[0] +"/" + DestIP[1] + "/32"};
		
		avReceive = new MediaReceive(argv,ta);
		if (!avReceive.initialize()) {
		    //System.err.println("Failed to initialize the sessions.");
			ta.append("Failed to initialize the sessions.");
		    System.exit(-1);
		}

		// Check to see if AVReceive2 is done.
		try {
		    while (!avReceive.isDone())
			Thread.sleep(1000);
		} catch (Exception e) {}

		//System.err.println("Exiting AVReceive2");
		ta.append("ERROR, stop to receive");
	    }

	    static void prUsage() {
		System.err.println("Usage: AVReceive2 <session> <session> ...");
		System.err.println("     <session>: <address>/<port>/<ttl>");
		System.exit(0);
	}
}