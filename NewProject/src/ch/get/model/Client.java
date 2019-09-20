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
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import ch.get.util.ShowAlertWindow;
import ch.get.view.ClientLayoutController;
import javafx.scene.control.Alert.AlertType;

public class Client{

	private ClientLayoutController cont = null;
	private final String SERVER_IP = "192.168.100.134";
	private final int SERVER_PORT = 8000;
	private Socket socket;
	
	private PrintWriter pw;
	
	public Client() {
		socket = new Socket();
		try {			
			socket.connect(
					new InetSocketAddress(SERVER_IP, SERVER_PORT), 10000); //���� ����, Ÿ�Ӿƿ� 10��
			
			//Stream ����
			pw = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(),
									StandardCharsets.UTF_8)),
									true); // AutoFlush
			pw.write(Inet4Address.getLocalHost()+" ���� ��û");
			
			 //Ŭ���̾�Ʈ ����
			cont = ClientLayoutController.cliContInstance;
			new StartClientProcessor(socket).start();
			cont.inputDataListView("Ŭ���̾�Ʈ ���� ���� �Ϸ�!");
		} catch (UnknownHostException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "������ ã���� �����ϴ�.", "IP�� PORT�� �߸� �Ǿ����ϴ�.");
		} catch (IOException e) {
			new ShowAlertWindow(AlertType.INFORMATION, "������ ã���� �����ϴ�.", "IP�� PORT�� �߸� �Ǿ����ϴ�.");
		} catch (Exception e) {
			
		}
	}
	
	class StartClientProcessor extends Thread{
		//initClient
		private Socket socket;
		private String msg;
		private Thread receiveThread;
		
		public StartClientProcessor(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			// receiveThread
			receiveThread = new Thread( () -> 
			{
				BufferedReader br;
				try {
					br = new BufferedReader( //�޽��� �޳�.
							new InputStreamReader(socket.getInputStream()));	
					while(true) {
						msg = br.readLine();
						cont.inputDataListView(msg);
						
						if(msg == null) {
							cont.inputDataListView("���� ���� ����...");
							break;
						}
					}
				} catch (IOException e) {
				} catch (Exception e) {
				}
			});
			receiveThread.setDaemon(true);
			receiveThread.start();
			System.out.println("cc");
			//File
		}
		
		/*
		 * getter/setter
		 */
		public Socket getSocket() {
			return socket;
		}
		public void setSocket(Socket socket) {
			this.socket = socket;
		}
	}
}
