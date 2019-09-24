/*
 * Author ch.Get
 */
package ch.get.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import ch.get.util.ShowAlertWindow;
import ch.get.view.ClientLayoutController;
import javafx.scene.control.Alert.AlertType;

public class Client{

	private Object lock;
	private ClientLayoutController cont = null;
	private int serverPort;
	private String serverIp;
	private Socket socket;
	
	//sendMsg
	private PrintWriter pw;
	String msg;
	
	public Client(Object lock) 
	{
		//����ȭ Mutex
		this.lock = lock;
		//���� ���� ����
		cont = ClientLayoutController.cliContInstance;
	}
	
	public void initClient() {
		try {		
			socket = new Socket();
			serverIp = cont.getServerIp();
			serverPort = cont.getServerPort();
			
			socket.connect(
					new InetSocketAddress(serverIp, serverPort), 3000); //���� ����, Ÿ�Ӿƿ� 10��
			
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),
									StandardCharsets.UTF_8)),
									true);
			//������ ��� ������ ����
			new ChatClientReceiveThread(socket).start(); //���ú�
			
			new ChatClientWritingThread(socket).start(); //����
			
			
		} catch (UnknownHostException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "ȣ��Ʈ�� ã���� �����ϴ�.", "���������� ���� ���� �ϼ���.");
		} catch (IOException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "������ ã���� �����ϴ�.", "IP�� PORT�� �߸� �Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "�Է°�\n"+"IP : "+serverIp+"\nPORT : "+serverPort;
			new ShowAlertWindow(AlertType.ERROR, "Setting�� ���� IP ����", msg);
		}
	}
	
	public void closeClient() {
		PrintWriter pw;
		
		synchronized (lock) {
			if(socket != null) {
				try {
					pw = new PrintWriter(
							new BufferedWriter(
									new OutputStreamWriter(
											socket.getOutputStream(),
											StandardCharsets.UTF_8)),
											true);
					
					pw.println("quit");
					pw.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	public void sendMsgToServer(String msg) {
		this.msg = socket.getLocalAddress().getHostAddress()
				+"("
				+socket.getLocalAddress().getHostName()
				+")"
				+" : "
				+msg;
		
		pw.println(this.msg);
	}
	
	//������ �ۼ��� ��Ʈ��
	class ChatClientWritingThread extends Thread {
	
		private Socket socket = null;
		private String requestFromServer;
		
		public ChatClientWritingThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true) {
				
			}
		}
	}
	
	//�޽��� �ޱ�
	class ChatClientReceiveThread extends Thread {
		//initClient
		private Socket socket;
		private String msg;
		
		public ChatClientReceiveThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			BufferedReader br;
			
			try { 
				br = new BufferedReader( //�޽��� �޴� ������
						new InputStreamReader(socket.getInputStream()));	
				
				while(true) {
					msg = br.readLine();
					if(msg.equalsIgnoreCase("quit")) { //�������� ���� �޽����� �´ٸ� �����Ŵ
						closeClient();
					}
					cont.inputDataListView(msg);	
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		}

	}
}