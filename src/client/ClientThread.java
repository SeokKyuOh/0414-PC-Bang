/*
	서버의 메세지를 실시간 청취하기 위해서는 while문으로 readLine()을 실행해야 한다.
	따라서 메인쓰레드도 이 작업을 시도하면 메인쓰레드기 while문에서 빠져나가지 못하므로 모든 UI가 멈춰있게 된다.
	그래서 쓰레드를 별도로 만들어서 처리하자.
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
	BufferedReader buffr;		//말하기 듣기 하기 위한 메서드
	BufferedWriter buffw;
	boolean flag = true;
	JTextArea area;
	
	public ClientThread(Socket socket, JTextArea area) {		//접속하는 것은 클라이언트이기 때문에 얻어와야한다.
		this.socket = socket;
		this.area = area;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//듣기
	public void listen(){
		String msg = null;	
		try {
			msg = buffr.readLine();
			area.append(msg+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//말하기
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
