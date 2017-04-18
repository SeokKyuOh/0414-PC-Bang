/*
	��ǰ 1���� ���÷��̸� ǥ���� Ŭ����
	������ ��ư���� �̷���� �ִ�.
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
	URL url;	//�ڿ��� ��ġ�� ���� ��ü
	
	//ĵ������ ũ��
	int width = 120;
	int height = 150;
	
	public Product(URL url, ClientMain clientMain) {	//�Ѱܹ���
		this.url = url;
		this.clientMain = clientMain;		//ClientThread�� ����ؾ��ϴµ� �̸� �˰� �ִ� clientMain�� �����ڷ� �޾Ƽ� Ȱ������
		//�̹��� ����. �̹����� �׸��� ���� �����;��ϱ� ������.
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
		bt_buy = new JButton("����");
		
		
		setLayout(new BorderLayout());
		add(can);
		add(bt_buy, BorderLayout.SOUTH);
		
		bt_buy.addActionListener(this);
				
		//���� �г��� ������ ����������
		this.setPreferredSize(new Dimension(width+4, height+45));
		
	}
	
	public void buy(){
		//�� �̰� �췡��~
		//String msg = "������ü+�� ������..";
		String msg = "requestType =buy&product_id=87&id=batman";
		clientMain.ct.send(msg);
		
		//JavaSrcript Object Notation
		//JSON ǥ������� �ϸ� �������̱� ������ ��ΰ� �˾ƺ��� ���ϰ�
		//ǥ��� ��ü�� �������̸鼭 �˾ƺ��� ���ϴ�.
		//JSON ǥ������� �����Ѵٸ� { }�����ε� �ڷḦ ���� �� �ִ�.
		{
			"requestType":"chat",
			"msg": "���̿�",
			"id":"batman",

		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		buy();
		
	}
	
}
