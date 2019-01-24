package breaktime1.Place;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import breaktime1.GameObject.Fighting;

public class Fightready_UI extends JPanel
{
	JLabel ready;
	JLabel readychk1;
	JLabel readychk2;
	JLabel shkplz;
	Thread tm;
	Fight_UI f;
	
	JLabel readytimer;
	int t=3;
	
	public Fightready_UI(Fighting fsys,Fight_UI fui) 
	{
		setSize(1200, 900);
		setLayout(null);
		
		shkplz = shkplz();		
		readytimer = readycount();
		ready = ready();
		readychk1 = readychk1();
		readychk2 = readychk2();
		f = fui;
		
		add(shkplz);
		shkplz.setVisible(false);
		
		add(readytimer);
		add(readychk1);
		add(readychk2);
		add(ready);
		
		setOpaque(false);
		setVisible(true);
		
	}
	public void init()
	{
		shkplz.setVisible(false);
		tm = new Thread(new readyc());
		tm.start();
		readytimer.setVisible(false);
		t=3;
		readytimer.setVisible(true);
	}
	public class readyc implements Runnable {
		public void run() {
			while (t != -1) 
			{
				try {
					Thread.sleep(1000);
					t -= 1;
					String text = Integer.toString(t);
					readytimer.setText(text);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				revalidate();
				repaint();
			}
			t=3;
			shkplz.setVisible(true);
			try {
				Thread.sleep(12000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			f.changeui(2);
		}
	}	
	public JLabel readycount() {
		JLabel Hud = new JLabel("3");
		Hud.setFont(new Font("sserife", Font.BOLD, 45));
		Hud.setBounds(680, 387, 280, 60);
		Hud.setForeground(Color.WHITE);
		return Hud;
	}
	public JLabel ready() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/ready.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(323, 363, w, h);

		return label;
	}

	public JLabel readychk1() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/enecan.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(361, 391, w, h);

		return label;
	}

	public JLabel readychk2() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/mycan.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(747, 390, w, h);

		return label;
	}

	public JLabel shkplz() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/shakeplz.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(-6, 304, w, h);

		return label;
	}

}
