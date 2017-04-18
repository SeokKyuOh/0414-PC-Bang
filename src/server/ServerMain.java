package server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerMain extends JFrame implements Runnable{
	JPanel p_center;	//전체화면용
	Thread thread;		//접속자 감지용 쓰레드
	ServerSocket server;
	int port = 7878;		//서버는 ip가 따로 필요없기 때문에 포트만 만들자
	
	public ServerMain() {
		try {
			server = new ServerSocket(port);
			System.out.println("서버 생성");
			thread = new Thread(this);		//runnable 하는 주체가 나니까 this.
			
			//이 시점엔 메인쓰레드와 개발자정의 쓰레드는 각자 자신의 코드를 수행하게 되므로
			//메인쓰레드에 의해 프레임이 화면에 등장함과 동시에 서버의 accept()도 동시에 동작하게 된다.
			//즉 별도의 실행을 하며 독립적으로 가동된다.
			thread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p_center = new JPanel();
		//p_center.setBackground(Color.WHITE);
		add(p_center);
		
		setLayout(new FlowLayout());
		setSize(800,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	public void run() {
		//접속자를 무한으로 받는다
		while(true){
			try {
				Socket socket = server.accept();
				//접속자가 발견되면, pc메인 카운터에 접속자를 화면에 등장시킨다.
				System.out.println("유저 접속");
				User user = new User(socket);		//시각화된 아바타이다. 패널이면서 쓰레드임.
				
				p_center.add(user);
				p_center.updateUI();
							
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new ServerMain();
	}



}
