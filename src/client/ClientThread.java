/*
	������ �޼����� �ǽð� û���ϱ� ���ؼ��� while������ readLine()�� �����ؾ� �Ѵ�.
	���� ���ξ����嵵 �� �۾��� �õ��ϸ� ���ξ������ while������ ���������� ���ϹǷ� ��� UI�� �����ְ� �ȴ�.
	�׷��� �����带 ������ ���� ó������.
*/
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientThread extends Thread{
	Socket socket;
	BufferedReader buffr;		//���ϱ� ��� �ϱ� ���� �޼���
	BufferedWriter buffw;
	boolean flag = true;
	JTextArea area;
	
	public ClientThread(Socket socket, JTextArea area) {		//�����ϴ� ���� Ŭ���̾�Ʈ�̱� ������ ���;��Ѵ�.
		this.socket = socket;
		this.area = area;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//���
	public void listen(){
		String msg = null;	
		try {
			msg = buffr.readLine();
			area.append(msg+"\n");
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
