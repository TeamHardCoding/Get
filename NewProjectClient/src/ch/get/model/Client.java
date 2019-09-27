/*
 * Author ch.Get
 * 
 * protocol lists / JOIN, QUIT, MSG, FILE
 */
package ch.get.model;

import java.io.BufferedReader;
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
			
			//Ű���� �Է�
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			//������ ��� ������ ����
			chatReceiveThread = new ChatClientReceiveThread(socket); //���ú�
//			chatWritingThread = new ChatClientWritingThread(socket); //����
			
			//������ ��ŸƮ
			chatReceiveThread.start();
//			chatWritingThread.start();
			String nickName = "Ŭ��";
			joinToServer(nickName);
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
							new OutputStreamWriter(
									socket.getOutputStream(), StandardCharsets.UTF_8));
					
					pw.println(Protocol.QUIT.name()+":"+"\r\n");
					pw.flush();
//					System.out.println("closeClient");
					socket.close();
				} catch (Exception e) {
					System.out.println("Exception");
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMsgToServer(String msg) { //�޽��� ������
		PrintWriter pw;
		
		try {
			String temp = Protocol.MSG.name() //�������� ���
					+":" //�������� ��ū
					+msg+"\r\n"; //�޽��� ���
			
			ClientLayoutController.cliContInstance.inputDataListView(msg);
			
			pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8)); 
			
			pw.println(temp);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void joinToServer(String msg) {
		String temp = Protocol.JOIN.name()
				+":"
				+msg+"\r\n";
		
		//sendMsg
		PrintWriter pw;
		
		try {
			pw = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println(temp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//�޽��� �Է��� ���� ��Ʈ�� ����

		
	}
	
	//�޽��� �ޱ�
	class ChatClientReceiveThread extends Thread {
		//initClient
		private Socket socket;
		private String request;
		
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
					request = br.readLine();
					System.out.println(request);
					cont.inputDataListView(request);
				}
			} catch (IOException e) {
			} catch (Exception e) {
			}
		}

	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
}