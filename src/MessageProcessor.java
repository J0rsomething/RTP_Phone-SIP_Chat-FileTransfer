/**
 * 
 * @author MATT
 * ���ڻص��ӿڣ�
 *     ����һ��ָ��ӿ�AAA�ı���������������aaa����������������ʵ����AAA�ӿ�
 *     �ֱ����BBB��CCC��������aaa = new BBB() aaa = new CCC()
 *     ������new�����������aaa.method()���÷������Ǹ��Ե���BBB��CCC���治��methodʵ�֡�
 *     
 *
 */
public interface MessageProcessor
{
	public void processMessage(String sender, String message);

	public void processError(String errorMessage);

	public void processInfo(String infoMessage);
}
