package breaktime1.Place;

/*
전투 클래스UI - 메인프레임, 시스템 포함
레디 창 띄우기
타이머 표시
5초간 레디 체크  레디하면 그림 띄워주기
둘다 on이거나 5초가 지났을 경우 -> shake 띄우기
일정 시간 후 종료 -> 시스템에서 흔든 횟수 받아 표시
승패 UI 표시하기
데미지 표시(적과 본인), HP까기

if) 둘 다 살았다면 -> 탐색 화면 갱신(갱신함수 쓰기) 후 패널전환
if) 내가 죽었다면 -> 사망패널 
if) 쟤가 죽었다면 -> 시스템에서 가방 불러와 가방 띄워주기
 */

/*
전투 클래스UI - 메인프레임, 시스템 포함
레디 창 띄우기
타이머 표시
5초간 레디 체크  레디하면 그림 띄워주기
둘다 on이거나 5초가 지났을 경우 -> shake 띄우기
일정 시간 후 종료 -> 시스템에서 흔든 횟수 받아 표시
승패 UI 표시하기
데미지 표시(적과 본인), HP까기

if) 둘 다 살았다면 -> 탐색 화면 갱신(갱신함수 쓰기) 후 패널전환
if) 내가 죽었다면 -> 사망패널 
if) 쟤가 죽었다면 -> 시스템에서 가방 불러와 가방 띄워주기
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import breaktime1.mainFrame;
import breaktime1.GameObject.Fighting;
import breaktime1.GameObject.Player;

public class Fight_UI extends JPanel {
	Font font = new Font("sserife", Font.BOLD, 17);
	Font hudfont = new Font("sserife", Font.BOLD, 22);
	Font namefont = new Font("sserife", Font.BOLD, 27);

	// 시스템
	Fighting fsys;
	Player player;
	Place_scene place;

	JLabel ene_prof;
	JLabel my_prof;

	JLabel eneHP_label;
	JPanel eneHP;

	JLabel myHP_label;
	JPanel myHP;

	JLabel info;
	JLabel myhp;
	JLabel2D ene_name;
	JLabel2D atkdef;

	JLabel enebg;
	
	JLabel kill = new JLabel();
	JLabel remain = new JLabel();
	JLabel2D my_name;
	JLabel2D wpname;
	JButton pauseb;
	
	// 더하는 ui들
	Fightrst_UI rstui;
	Fightready_UI redui;
	
	int t = 3;

	Thread readygo;
	// Thread readyok = new Thread(new readyok());
	Thread shakeplz;
	Thread rstshow;

	public Fight_UI(mainFrame f, Place_scene p) {
		setSize(1200, 900);
		setLayout(null);

		// 시스템 인스턴스 만들기
		fsys = new Fighting(f.getStmt());
		place = p;
		
		rstui = new Fightrst_UI(fsys,p);
		redui = new Fightready_UI(fsys,this);
		
		/////////////////////////////////////////////////// 라벨 및 컴포넌트 초기화
		my_prof = my_prof();
		ene_prof = ene_prof();
		atkdef = atkdef(0, 0);
		info = info();
		pauseb = pauseb();

		ene_name = ene_name("초깃값");
		my_name = my_name("초깃값");
		wpname = wp(100);
		eneHP = eneHP(100);
		myHP = myHP(100);
		kill = Kill(0);
		remain = Remain(3);

		enebg = enebg();
		

		//////////////////////////////////////////////// add
		
		// 결과표시시 필요
		add(redui);
		redui.setVisible(false);
		add(rstui);
		rstui.setVisible(false);
		
		add(atkdef);
		add(eneHP_label);
		add(eneHP);
		add(myHP_label);
		add(myHP);

		add(wpname);
		add(ene_name);
		add(my_name);

		add(my_prof);
		add(ene_prof);

		add(kill);
		add(remain);
		add(pauseb);
		add(info);

		add(enebg);
		

		
		setOpaque(false);
		setVisible(true);

	}

	public void initF() 
	
	{
		System.out.print("전투 init");
		// 정보 초기화
		player = place.getCur_pl();

		eneHP = eneHP(fsys.getEneHP());
		myHP = myHP(player.getHp());
		wpname = wp(fsys.getbest());
		
		atkdef = atkdef(player.getDamage(), player.getDefense());
		
		this.revalidate();
		this.repaint();
		
		// 레디 더하기
		changeui(1);
		// 레디 시작
		readygo = new Thread(new readygo());
		readygo.start();
	}
	public void changeui(int i)
	{
		if (i ==1)
		{
			redui.setVisible(true);
			rstui.setVisible(false);
			redui.init();
		}
		else if(i == 2)
		{
			rstui.setVisible(true);
			redui.setVisible(false);
			rstui.init();
		}
	}
	public class readygo implements Runnable
	{
		public void run() 
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fsys.Setname((player.getNickname()));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ene_name.setText(fsys.getEnename());
			my_name.setText(player.getNickname());
			
			redui.setVisible(true);
			redui.init();
		}
	}

	//////////////////////////////////////////////////////////////////////////// 아래로
	//////////////////////////////////////////////////////////////////////////// 인스턴스


	public JLabel my_prof() {
		BufferedImage us_image = null;
		try {
			us_image = ImageIO.read(getClass().getResource("/breakt/UI/fpof.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = us_image.getWidth();
		int h = us_image.getHeight();
		JLabel usericon = new JLabel(new ImageIcon(us_image));
		usericon.setSize(w, h);
		usericon.setBounds(480, 603, w, h);

		return usericon;
	}

	public JLabel ene_prof() {
		BufferedImage us_image = null;
		try {
			us_image = ImageIO.read(getClass().getResource("/breakt/UI/ene_prof.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = us_image.getWidth();
		int h = us_image.getHeight();
		JLabel usericon = new JLabel(new ImageIcon(us_image));
		usericon.setSize(w, h);
		usericon.setBounds(0, 0, w, h);

		return usericon;
	}

	public JLabel2D ene_name(String Username) {
		JLabel2D UsernameHud = new JLabel2D("\n" + Username);
		UsernameHud.setText(Username);
		UsernameHud.setFont(namefont);
		UsernameHud.setBounds(355, 16, 281, 30);
		UsernameHud.setOutlineColor(Color.white);
		UsernameHud.setStroke(new BasicStroke(5f));

		return UsernameHud;
	}

	public JLabel2D my_name(String Username) {
		JLabel2D UsernameHud = new JLabel2D("\n" + Username);
		UsernameHud.setText(Username);
		UsernameHud.setFont(namefont);
		UsernameHud.setBounds(590, 822, 281, 30);
		UsernameHud.setOutlineColor(Color.white);
		UsernameHud.setStroke(new BasicStroke(5f));

		return UsernameHud;
	}

	public JButton pauseb() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/stop.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JButton button = new JButton(new ImageIcon(image));
		button.setSize(w, h);
		button.setBounds(1059, 0, w, h);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("일시정지");
			}
		});

		return button;
	}

	public JLabel info() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/info.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(790, 0, w, h);

		return label;
	}

	public JLabel2D wp(int best) {
		JLabel2D label2D = new JLabel2D();
		String b = Integer.toString(best);
		label2D.setText("BEST : " + b);
		label2D.setOutlineColor(Color.WHITE);
		label2D.setFont(new Font("Dialog", Font.BOLD, 22));
		label2D.setBounds(277, 129, 223, 46);

		return label2D;
	}

	public JLabel seteneHP_label(int hp_point) {
		String hp_string = Integer.toString(hp_point);
		JLabel2D nowhp = new JLabel2D(hp_string);

		nowhp.setFont(font);
		nowhp.setBounds(190 + 355, 68, 60, 20);
		nowhp.setOutlineColor(Color.white);
		nowhp.setStroke(new BasicStroke(3f));

		return nowhp;
	}

	public JPanel eneHP(int hp_point) {
		int pos_w = 310;
		int pos_h = 70;

		// 패널 설정
		JPanel hp_panel = new JPanel();
		hp_panel.setLayout(null);
		hp_panel.setSize(1200, 900);
		hp_panel.setOpaque(false);

		// 라벨
		eneHP_label = seteneHP_label(hp_point);

		// 베이스 설정
		BufferedImage hpbase_image = null;
		try {
			hpbase_image = ImageIO.read(getClass().getResource("/breakt/UI/hp_base.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = hpbase_image.getWidth();
		int h = hpbase_image.getHeight();
		JLabel HP_base = new JLabel(new ImageIcon(hpbase_image));
		HP_base.setSize(w, h);
		HP_base.setBounds(pos_w, pos_h, w, h);
		hp_panel.add(HP_base);

		// 바 설정

		BufferedImage hpbar_image = null;
		try {
			hpbar_image = ImageIO.read(getClass().getResource("/breakt/UI/hp.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int bar_w = hpbar_image.getWidth();
		int bar_h = hpbar_image.getHeight();
		JLabel HP_bar = new JLabel(new ImageIcon(hpbar_image));
		HP_bar.setSize(bar_w, bar_h);

		int length = (int) (bar_w * (hp_point / 100.));
		HP_bar.setBounds(pos_w + 8, pos_h, length, bar_h);
		hp_panel.add(HP_bar);

		// 탑 설정
		BufferedImage hptop_image = null;
		try {
			hptop_image = ImageIO.read(getClass().getResource("/breakt/UI/hp_top.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int top_w = hptop_image.getWidth();
		int top_h = hptop_image.getHeight();
		JLabel HP_top = new JLabel(new ImageIcon(hptop_image));
		HP_top.setSize(top_w, top_h);
		HP_top.setBounds(pos_w + length + 8, pos_h, top_w, top_h);
		hp_panel.add(HP_top);

		hp_panel.setVisible(true);
		return hp_panel;
	}

	public JLabel setmyHP_label(int hp_point) {
		String hp_string = Integer.toString(hp_point);
		JLabel2D nowhp = new JLabel2D(hp_string);

		nowhp.setFont(font);
		nowhp.setBounds(610, 786, 60, 20);
		nowhp.setOutlineColor(Color.white);
		nowhp.setStroke(new BasicStroke(3f));

		return nowhp;
	}

	public JPanel myHP(int hp_point) {
		int pos_w = 863;
		int pos_h = 788;

		// 패널 설정
		JPanel hp_panel = new JPanel();
		hp_panel.setLayout(null);
		hp_panel.setSize(1200, 900);
		hp_panel.setOpaque(false);

		// 라벨
		myHP_label = setmyHP_label(hp_point);

		// 바 설정
		BufferedImage hpbar_image = null;
		try {
			hpbar_image = ImageIO.read(getClass().getResource("/breakt/UI/hp.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int bar_w = hpbar_image.getWidth();
		int bar_h = hpbar_image.getHeight();
		JLabel HP_bar = new JLabel(new ImageIcon(hpbar_image));
		HP_bar.setSize(bar_w, bar_h);

		int length = (int) (bar_w * (hp_point / 100.));
		HP_bar.setBounds(pos_w - length, pos_h, length, bar_h);
		hp_panel.add(HP_bar);

		// 베이스 설정
		BufferedImage hpbase_image = null;
		try {
			hpbase_image = ImageIO.read(getClass().getResource("/breakt/UI/hp_base_L.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = hpbase_image.getWidth();
		int h = hpbase_image.getHeight();
		JLabel HP_base = new JLabel(new ImageIcon(hpbase_image));
		HP_base.setSize(w, h);
		HP_base.setBounds(pos_w, pos_h, w, h);
		hp_panel.add(HP_base);

		// 탑 설정
		BufferedImage hptop_image = null;
		try {
			hptop_image = ImageIO.read(getClass().getResource("/breakt/UI/hp_top_L.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int top_w = hptop_image.getWidth();
		int top_h = hptop_image.getHeight();
		JLabel HP_top = new JLabel(new ImageIcon(hptop_image));
		HP_top.setSize(top_w, top_h);
		HP_top.setBounds(pos_w - length - 7, pos_h, top_w, top_h);
		hp_panel.add(HP_top);

		hp_panel.setVisible(true);
		return hp_panel;
	}

	public JLabel2D atkdef(int atk, int def) {
		String str = Integer.toString(atk) + "                 " + Integer.toString(def);
		JLabel2D Hud = new JLabel2D(str);

		Hud.setFont(hudfont);
		Hud.setBounds(740, 685, 280, 30);
		Hud.setOutlineColor(Color.white);
		Hud.setStroke(new BasicStroke(3f));

		return Hud;
	}

	public JLabel2D Remain(int RemainP) {
		String Remainstr = Integer.toString(RemainP);
		JLabel2D RemainHud = new JLabel2D(Remainstr);

		RemainHud.setFont(hudfont);
		RemainHud.setBounds(860, 4, 280, 30);
		RemainHud.setOutlineColor(Color.white);
		RemainHud.setStroke(new BasicStroke(3f));

		return RemainHud;
	}

	public JLabel2D Kill(int killP) {
		String Killstr = Integer.toString(killP);
		JLabel2D KillHud = new JLabel2D(Killstr);

		KillHud.setFont(hudfont);
		KillHud.setBounds(1000, 4, 280, 30);
		KillHud.setOutlineColor(Color.white);
		KillHud.setStroke(new BasicStroke(3f));

		return KillHud;
	}



	public JLabel enebg() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/ene_fight.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JLabel label = new JLabel(new ImageIcon(image));
		label.setSize(w, h);
		label.setBounds(104, 287, w, h);

		return label;
	}

}