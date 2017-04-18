package server;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class User extends JPanel implements Runnable{
	JLabel la;
	Socket socket;
	BufferedReader buffr;		//���ϱ� ��� �ϱ� ���� �޼���
	BufferedWriter buffw;
	boolean flag = true;
	Thread thread;
	
	public User(Socket socket) {		//�����ǰ� ������� �ȵǴϱ� �����ڿ� �ִ´�.
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//��Ʈ�� ���� �� ��ȭ�� �����ϱ� ������ ��ġ�� ����� �Ѵ�.
		thread = new Thread(this);
		thread.start();
		
		la = new JLabel("123�� pc");
		add(la);
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.CYAN);
	}
	
	//���
	public void listen(){
		String msg = null;
		try {
			msg = buffr.readLine();
			//��ȭ,����,����,Ż��..id=batman@product_id=87  ��ȭ���� �ֹ����� �������� �˾ƾ��Ѵ�.
			
			//��û �м� ����
			//requestType = chat   chat�̸� ���������� �ƴϸ� ���� ��´�.
			//msg = Ŭ���̾�Ʈ��
			//id = Ŭ���̾�Ʈ id
			
			String[] data = msg.split("&");
			String[] requestType = data[0].split("=");
			if(requestType[1].equals("chat")){
				String[] str = data[1].split("=");
				send(str[1]);		//Ŭ���̾�Ʈ�� �޼��� ������  msg = Ŭ���̾�Ʈ�� ��.  ���⼭ msg�κ��� str[0]/ �� �κФ��� str[1]
			} else if(requestType[1].equals("buy")){
				System.out.println("�����ϰ� �;�?");
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���ϱ�
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void run() {
		while(flag){
			listen();
		}
	}
}
