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

public class Server implements Runnable {
	
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
			
			pw = new PrintWriter(
							new OutputStreamWriter(
									socket.getOutputStream(), StandardCharsets.UTF_8));
			
			while(true) {
				request = br.readLine();
//				System.out.println(request);
				
				if(request == null) {
					printText("Ŭ���̾�Ʈ ���� ����.");
					procQuit(pw);
					break;
				}
				
				String[] protocol = request.split(":");
//				System.out.println(request);
				
				if(protocol[0].equals(Protocol.JOIN.name())) {
					printText(endUserIp+":"+protocol[1]); // �г���
					procJoin(protocol[1], pw); //���� ������ ��ε� ĳ��Ʈ ��.
				} else if(protocol[0].equals(Protocol.QUIT.name())) {
					printText(endUserIp+":"+" ���� ����");
					procQuit(pw);
				} else if(protocol[0].equals(Protocol.MSG.name())) {
					procSendMsg(protocol[1]);
				} else {
					if(protocol[0].equals(Protocol.FILE.name())) {
						
					}
				}
			}
		} catch (IOException e) {
			printText(this.name+" ���� ����");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	
	/*���� ���*/
	public void procQuitFromServer() { //���� ���
		PrintWriter pw;
		
		printText(endUserIp+":"+"���� ���� - ���� ���");
		
		try {
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
			pw.println("�������� ������ ���� ���׽��ϴ�.\r\n");
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		String data = this.name+"���� �����߽��ϴ�.";
		procQuit(getPw()); //procQuit �� ��ε� ĳ��Ʈ ����
	}
	
	/*Ŭ�� ���*/
	private void procSendMsg(String msg) {
		printText(endUserIp+":"+msg);
		broadCaster(this.name+":"+msg);
	}
	
	private void procJoin(String nickname, PrintWriter pw) {
		this.name = nickname;
		broadCaster(this.name+" ����.");
		addWriter(pw);
	}
	
	private void procQuit(PrintWriter pw) { // ��ε� ĳ��Ʈ ����
		removeWriter(pw); //��Ʈ�� ����
		
		String data = this.name+"���� �����߽��ϴ�.";
		broadCaster(data);
	}
	
	private void broadCaster(String temp) { //Ŭ���̾�Ʈ ��ε� ĳ��Ʈ
		
		System.out.println(listWriters.size()+" left pW Size");
		
		for (PrintWriter printWriter : listWriters) {
			printWriter.println(temp);
			printWriter.flush();
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
