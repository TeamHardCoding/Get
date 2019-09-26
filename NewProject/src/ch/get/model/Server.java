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
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import ch.get.util.Protocol;
import ch.get.view.RootLayoutController;

public class Server extends Thread {
	
	private Socket socket;
	private List<PrintWriter> listWriters;
	private String request;
	private String name;
	private String endUserIp;
	
	//��Ʈ��
	private PrintWriter pw;
	
	public Server(Socket socket, List<PrintWriter> temp, List<String> clientIpLists) {
		this.socket = socket;
		listWriters = temp;
		endUserIp = socket.getInetAddress().getHostAddress();
		
		clientIpLists.add(endUserIp);
	}
	
	@Override
	public void run() {
		request = null;
		
		try {
			BufferedReader br = 
					new BufferedReader(
							new InputStreamReader(
									socket.getInputStream(), StandardCharsets.UTF_8));
			
			pw = new PrintWriter
					(new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream(), StandardCharsets.UTF_8)), true);
			
			while(true) {
				request = br.readLine();
				System.out.println(request);
				
				if(request == null) {
					printText("Ŭ���̾�Ʈ ���� ����.");
					break;
				}
				
				String protocol[] = request.split(":");
				String proTemp = protocol[0].toUpperCase();	
				String msg = protocol[1];
				
				if(proTemp.equals(Protocol.JOIN.name())) {
					printText(endUserIp+" : "+msg);
					procJoin(msg, pw); //���� ������ ��ε� ĳ��Ʈ ��.
				} else if(proTemp.equals(Protocol.QUIT.name())) {
					printText(endUserIp+" : "+msg+" ���� ����");
					procQuit(pw);
				} else if(proTemp.equals(Protocol.MSG.name())) {
					System.out.println("chk");
					procSendMsg(msg);
				} else {
					if(proTemp.equals(Protocol.FILE.name())) {
						
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	/*���� ���*/
	public void procQuitFromServer() { //���� ���
		printText(endUserIp+" : "+"���� ���� - ���� ���");
		pw.println("�������� ������ ���� ���׽��ϴ�.");
		
		String data = this.name+"���� �����߽��ϴ�.";
		procQuit(getPw()); //procQuit �� ��ε� ĳ��Ʈ ����
	}
	
	/*Ŭ�� ���*/
	private void procSendMsg(String msg) {
		printText(endUserIp+" : "+msg);
		broadCaster(this.name+" : "+msg);
	}
	
	private void procJoin(String nickname, PrintWriter pw) {
		this.name = nickname;
		addWriter(pw);
		broadCaster(this.name+" ����.");
	}
	
	private void procQuit(PrintWriter pw) { // ��ε� ĳ��Ʈ ����
		removeWriter(pw); //��Ʈ�� ����
		
		String data = this.name+"���� �����߽��ϴ�.";
		broadCaster(data);
	}
	
	private void broadCaster(String temp) { //Ŭ���̾�Ʈ ��ε� ĳ��Ʈ
		pw.println("����");
		synchronized (listWriters) {
			for (PrintWriter printWriter : listWriters) {
				printWriter.println(temp);
				printWriter.flush();
			}
		}
	}
	
	private void addWriter(PrintWriter pw) { //Ŭ���̾�Ʈ ���� �Ҵ� ��Ʈ�� �߰�
		synchronized (listWriters) {
			listWriters.add(pw);
		}
	}
	
	private void removeWriter(PrintWriter pw) { //Ŭ���̾�Ʈ ���� ���� �Ҷ� ��Ʈ�� ����
		synchronized (listWriters) {
			listWriters.remove(pw);
		}
	}

	private void printText(String msg) { //���� �ܼ� â�� ���� �޽��� ���
		RootLayoutController.rcl.printText(msg);
	}
	
	public PrintWriter getPw() {
		return pw;
	}
}
