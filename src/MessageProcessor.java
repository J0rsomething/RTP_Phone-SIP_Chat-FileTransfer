/**
 * 
 * @author MATT
 * 关于回调接口：
 *     创建一个指向接口AAA的变量（假设它叫做aaa），现在有两个类实现了AAA接口
 *     分别叫做BBB和CCC，可以用aaa = new BBB() aaa = new CCC()
 *     上两个new语句后面如果用aaa.method()调用方法，是各自调用BBB和CCC里面不的method实现。
 *     
 *
 */
public interface MessageProcessor
{
	public void processMessage(String sender, String message);

	public void processError(String errorMessage);

	public void processInfo(String infoMessage);
}
