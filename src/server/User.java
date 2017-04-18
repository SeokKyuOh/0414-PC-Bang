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
	BufferedReader buffr;		//말하기 듣기 하기 위한 메서드
	BufferedWriter buffw;
	boolean flag = true;
	Thread thread;
	
	public User(Socket socket) {		//생성되고 사라지면 안되니까 생성자에 넣는다.
		this.socket = socket;
		
		try {
			buffr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//스트림 생성 후 대화가 가능하기 때문에 위치를 여기로 한다.
		thread = new Thread(this);
		thread.start();
		
		la = new JLabel("123번 pc");
		add(la);
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.CYAN);
	}
	
	//듣기
	public void listen(){
		String msg = null;
		try {
			msg = buffr.readLine();
			//대화,구매,가입,탈퇴..id=batman@product_id=87  대화인지 주문인지 구분할줄 알아야한다.
			
			//요청 분석 시작
			//requestType = chat   chat이면 돌려보내고 아니면 나만 듣는다.
			//msg = 클라이언트말
			//id = 클라이언트 id
			
			String[] data = msg.split("&");
			String[] requestType = data[0].split("=");
			if(requestType[1].equals("chat")){
				String[] str = data[1].split("=");
				send(str[1]);		//클라이언트에 메세지 보내기  msg = 클라이언트의 말.  여기서 msg부분이 str[0]/ 말 부분ㅌ이 str[1]
			} else if(requestType[1].equals("buy")){
				System.out.println("구매하고 싶어?");
			}
			
			
			
			
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
