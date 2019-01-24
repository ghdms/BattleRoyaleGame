package breaktime1.Place;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import breaktime1.mainFrame;
import breaktime1.GameObject.Fighting;
import breaktime1.GameObject.Player;

public class Fightrst_UI extends JPanel
{
	JLabel enescore;
	JLabel myscore;
	JLabel enecrown;
	JLabel mycrown;
	JLabel wint;
	JLabel loset;
	JLabel drawp;
	JLabel result;
	Fighting fsys;
	Place_scene place;
	Player player;
	Fight_UI f;
	Thread rst;
	
	int mysc;
	int yoursc;
	
	public Fightrst_UI(Fighting fs, Place_scene p) 
	{
		
		setSize(1200, 900);
		setLayout(null);
		
		fsys = fs;
		player = p.getCur_pl();
		place = p;	
		
		enescore = enescore();
		myscore = myscore();
		enecrown = enecrown();
		mycrown = mycrown();
		wint = wint();
		loset = loset();
		drawp = drawp();
		result = result();
		
		add(drawp);
		drawp.setVisible(false);
		add(enescore);
		add(myscore);
		add(enecrown);
		enecrown.setVisible(false);
		add(mycrown);
		mycrown.setVisible(false);
		add(wint);
		wint.setVisible(false);
		add(loset);
		loset.setVisible(false);
		add(result);

		setOpaque(false);
		setVisible(true);
		
	}
	public void init() 
	{
		drawp.setVisible(false);
		enecrown.setVisible(false);
		mycrown.setVisible(false);
		wint.setVisible(false);
		loset.setVisible(false);
		
		revalidate();
		repaint();
		
		System.out.print("결과창 정보받는시점????");
		
		mysc = fsys.getscore(player.getNickname());
		String myscoret = Integer.toString(mysc);
		myscore.setText(myscoret);

		yoursc = fsys.getscore(fsys.getEnename());
		String yourscoret = Integer.toString(yoursc);
		enescore.setText(yourscoret);
		
		if (mysc > yoursc) {
			int damage = fsys.getEnedamage();
			System.out.print("적뎀"+damage);
			int hp = player.getHp();
			hp = (int) (hp - (yoursc * 0.25)); //수정함
			player.setHp(hp);
			
			// ★★★★★★★★★★★★★★★★사용한 아이템 처리하기
			// 최고기록 갱신
			if (mysc > player.getShake()) {
				player.setShake(mysc);
				// db 플레이어 테이블 shake 갱신
			}

		} else if (yoursc > mysc) 
		{
			int damage = fsys.getEnedamage();
			int hp = player.getHp();
			hp = (int) (hp - (yoursc * 0.5)); //수정함
			System.out.print("적뎀"+damage);
			player.setHp(hp);
			// ★★★★★★★★★★★★★★★★사용한 아이템 처리하기
			// 최고기록 갱신	
			if (mysc > player.getShake()) {
				player.setShake(mysc);
				// db 플레이어 테이블 shake 갱신
				
			}
		} 
		else {
			// 최고기록 갱신
			if (mysc > player.getShake()) {
				player.setShake(mysc);
				// db 플레이어 테이블 shake 갱신
			}

		}
		fsys.sethpf(player.getHp(), player.getNickname());
        player.setFight(0);
        
        rst = new Thread(new resultshow());
        rst.start();
	}
	public class resultshow implements Runnable {
		public void run() {
			System.out.print("결과창 스레드!!!!!!!!!!!!!!!!!!!");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(mysc >yoursc)
			{
				mycrown.setVisible(true);
				wint.setVisible(true);
				wint.setBounds(367, 542, 84, 83);
				loset.setVisible(true);
				loset.setBounds(765, 235, 1103, 96);
			}
			else if(yoursc > mysc)
			{
				enecrown.setVisible(true);
				wint.setBounds(786, 239, 84, 83);
				wint.setVisible(true);
				loset.setBounds(363, 530, 103, 96);
				loset.setVisible(true);
			}
			else if(yoursc == mysc)
			{
				drawp.setVisible(true);
			}
			// 전투종료
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(player.getHp()<=0)
			{
				place.initui("gameover");
				place.uichange("gameover");	
			
			}
			else {
				
				place.uichange("normal");
				place.initui("normal");
					
			}
		}
	}

	/////////////////////////////////////////////////////////////컴포넌트///////////////////////////////////////////////////////////////////
	
	public JLabel result() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/score.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(-6, 102, w, h);

		return label;
	}

	public JLabel enecrown() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/eneking.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(829, 102, w, h);

		return label;
	}

	public JLabel mycrown() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/myking.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(267, 323, w, h);

		return label;
	}

	public JLabel wint() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/win_t.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		// label.setBounds(786,239,w,h);
		label.setBounds(367, 542, w, h);

		return label;
	}

	public JLabel loset() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/lose_t.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		// label.setBounds(363,530,w,h);
		label.setBounds(765, 235, w, h);

		return label;
	}

	public JLabel enescore() {
		JLabel Hud = new JLabel("100");
		Hud.setFont(new Font("sserife", Font.ITALIC + Font.BOLD, 80));
		Hud.setBounds(84, 287, 313, 111);
		Hud.setForeground(Color.WHITE);
		return Hud;
	}

	public JLabel myscore() {
		JLabel Hud = new JLabel("100");
		Hud.setFont(new Font("sserife", Font.ITALIC + Font.BOLD, 80));
		Hud.setBounds(884, 498, 313, 111);
		Hud.setForeground(Color.WHITE);
		return Hud;
	}

	public JLabel drawp() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/draw.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		// label.setBounds(363,530,w,h);
		label.setBounds(-6, 102, w, h);

		return label;
	}
}
