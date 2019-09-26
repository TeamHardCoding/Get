/*
 * Author ch.Get
 * 
 * protocol lists / JOIN, QUIT, MSG, FILE
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

import ch.get.util.Protocol;
import ch.get.util.ShowAlertWindow;
import ch.get.view.ClientLayoutController;
import javafx.scene.control.Alert.AlertType;

public class Client{

	private Object lock;
	private ClientLayoutController cont = null;
	private int serverPort;
	private String serverIp;
	private Socket socket;
	private ChatClientReceiveThread chatReceiveThread;
//	private ChatClientWritingThread chatWritingThread;
	
	//sendMsg
	private PrintWriter pw;
	
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
	
			//�޽��� �Է��� ���� ��Ʈ�� ����
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),StandardCharsets.UTF_8)),true);
			
			//������ ��� ������ ����
			chatReceiveThread = new ChatClientReceiveThread(socket); //���ú�
//			chatWritingThread = new ChatClientWritingThread(socket); //����
			
			//������ ��ŸƮ
			chatReceiveThread.start();
//			chatWritingThread.start();
			joinToServer("���� �ϼ̽��ϴ�.");
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
	
	//Ŭ���̾�Ʈ ����
	public void closeClient() {
		PrintWriter pw;
		
		synchronized (lock) {
			if(socket != null) {
				try {
					pw = new PrintWriter(
							new BufferedWriter(
									new OutputStreamWriter(socket.getOutputStream(),StandardCharsets.UTF_8)),true);
					
					pw.write("quit: ");
					pw.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMsgToServer(String msg) { //�޽��� ������
		String temp = Protocol.MSG.name() //�������� ���
				+":" //�������� ��ū
				+msg; //�޽��� ���
		
		pw.println(temp);
	}
	
	public void joinToServer(String msg) {
		String temp = Protocol.JOIN.name()
				+":"
				+msg;
		
		pw.println(temp);
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	
//	//������ �ۼ��� ��Ʈ��
//	class ChatClientWritingThread extends Thread {
//	
//		private Socket socket = null;
//		
//		public ChatClientWritingThread(Socket socket) {
//			this.socket = socket;
//		}
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			
//			try {
//
//				
//				while(true) {
//					
//				}	
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
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