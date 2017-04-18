/*
	상품 1건의 디스플레이를 표현한 클래스
	사진과 버튼으로 이루어져 있다.
*/
package client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Product extends JPanel implements ActionListener{
	ClientMain clientMain;
	Canvas can;
	JButton bt_buy;
	BufferedImage image;
	URL url;	//자원의 위치에 대한 객체
	
	//캔버스의 크기
	int width = 120;
	int height = 150;
	
	public Product(URL url, ClientMain clientMain) {	//넘겨받자
		this.url = url;
		this.clientMain = clientMain;		//ClientThread를 사용해야하는데 이를 알고 있는 clientMain을 생성자로 받아서 활용하자
		//이미지 생성. 이미지를 그리기 전에 가져와야하기 때문에.
		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can = new Canvas(){
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, width, height, this);
			}
		};
		bt_buy = new JButton("구매");
		
		
		setLayout(new BorderLayout());
		add(can);
		add(bt_buy, BorderLayout.SOUTH);
		
		bt_buy.addActionListener(this);
				
		//현재 패널의 사이즈 지정해주자
		this.setPreferredSize(new Dimension(width+4, height+45));
		
	}
	
	public void buy(){
		//나 이거 살래요~
		//String msg = "나의정체+뭘 먹을지..";
		String msg = "requestType =buy&product_id=87&id=batman";
		clientMain.ct.send(msg);
		
		//JavaSrcript Object Notation
		//JSON 표기법으로 하면 공식적이기 때문에 모두가 알아보기 편하고
		//표기법 자체도 직관적이면서 알아보기 편리하다.
		//JSON 표기법으로 생각한다면 { }만으로도 자료를 담을 수 있다.
		{
			"requestType":"chat",
			"msg": "하이요",
			"id":"batman",

		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		buy();
		
	}
	
}
