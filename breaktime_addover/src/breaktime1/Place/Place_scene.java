package breaktime1.Place;
//지역 패널

/*
지역 인스턴스 생성시 딸려서 생기게 하자
아니 시발 그럼 카드를 못만들잔아
버튼을 맨날 지웠다가 새로 그리는것도 존나 끔찍한거같은데
그냥 한 패널로 통일하고 이미지만 바꾸는게 나을듯 -> 이미지 바꾸는 함수 줘야함 (이미지는 유저?에서 받아옴)
*/
/* 지역 일람
* 일단 세 개만... 교실 = classroom / 복도 = aisle / 계단 = stair 
* 열거형에서 랜덤출력하는 좀 더 좋은 방법이 있으면 좋겟다.............
*/

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import breaktime1.mainFrame;
import breaktime1.DB.DataUpdateRunnable;
import breaktime1.GameObject.Game;
import breaktime1.GameObject.Player;
import breaktime1.Gameflow;
import breaktime1.Gameflow.place;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;

public class Place_scene extends JPanel {
	private BufferedImage image;
	private int w, h;
	private JButton search;
	private Player cur_pl;
	private Statement stmt;
	private Game game;
	private ene_findUI eneui;
	private Fight_UI fui;
	private Normal_UI nui;
	private mainFrame F;
	private gameover_UI goui;
	
	public Player getCur_pl() {
		return cur_pl;
	}

	private DataUpdateRunnable DR;

	public Place_scene(mainFrame f, Player plyr) {
		F = f;
		stmt = f.getStmt();
		cur_pl = plyr;

		DR = new DataUpdateRunnable(stmt);
		this.setLayout(new OverlayLayout(this));

		this.setLayout(new OverlayLayout(this));

		setSize(1200, 900);
		setLayout(null);
		setVisible(true);

	}

	/*
	 * classroom(0), aisle(1), restaurant(2), art(3), gym(4), kitchen(5), music(6),
	 * science(7), nurse(8);
	 */
	
	public void imagechange(Gameflow.place Placename) {
		// 백그라운드 이미지 띄우는 부분
		try {
			String filename = null;
			switch (Placename) {
			//img = ImageIO.read(getClass().getResource("/breakt/sign.png"));
			case classroom:
				filename = "/breakt/BG_img/bg_classroom.png";
				break;
			case aisle:
				filename = "/breakt/BG_img/bg_aisle.png";
				break;
			case restaurant:
				filename = "/breakt/BG_img/bg_restaurant.png";
				break;
			case art:
				filename = "/breakt/BG_img/bg_art.png";
				break;
			case gym:
				filename = "/breakt/BG_img/bg_gym.png";
				break;
			case kitchen:
				filename = "/breakt/BG_img/bg_kitchen.png";
				break;
			case music:
				filename = "/breakt/BG_img/bg_music.png";
				break;
			case science:
				filename = "/breakt/BG_img/bg_science.png";
				break;
			case nurse:
				filename = "/breakt/BG_img/bg_nurse.png";
				break;

			}
			image = ImageIO.read(getClass().getResource(filename));
			w = image.getWidth();
			h = image.getHeight();

			DR.UpdateZone(Placename.getValue(), cur_pl);

			repaint();

		} catch (IOException ioe) {
			System.out.println("Could not read in the pic");
			ioe.printStackTrace();
			// System.exit(0);
		}
		// 이미지 띄우는 부분 종료
	}

	public void uichange(String uiname) {
		switch (uiname) {
		case "normal":
			nui.setVisible(true);
			fui.setVisible(false);
			eneui.setVisible(false);
			goui.setVisible(false);
			break;
		case "ene_find":
			nui.setVisible(false);
			fui.setVisible(false);
			eneui.setVisible(true);
			goui.setVisible(false);
			break;
		case "fight":
			nui.setVisible(false);
			fui.setVisible(true);
			eneui.setVisible(false);
			goui.setVisible(false);
			break;
		case "gameover":
			nui.setVisible(false);
			fui.setVisible(false);
			eneui.setVisible(false);
			goui.setVisible(true);
			break;
		}
		this.revalidate();
		this.repaint();
	}

	public void ene_find_init(String ene) {
		eneui.init(ene);
	}

	public void initui(String uiname) {
		switch (uiname) {
		case "normal":
			nui.init();
			break;
		case "ene_find":
			// eneui.init()<-ㅇㅣ따할게요
			break;
		case "fight":
			fui.initF();
			break;
		case "gameover":
			goui.init(false);
			break;
		case "win":
			goui.init(true);
			break;
		}
	}

	public void init(mainFrame f, Player pl) {
		Random random = new Random();
		int randint = random.nextInt(9);
		place[] placelist = place.values();
		place fstP = placelist[randint];
		imagechange(fstP);
		cur_pl = pl;
		cur_pl.setZone_ID(randint);

		game = new Game(stmt, cur_pl); // 게임 객체 생성 ->UI애서 필요하면 이거 받아다 씁니당
		// UI 생성 & 집어넣기
		nui = new Normal_UI(f, cur_pl, this);
		fui = new Fight_UI(f, this);
		eneui = new ene_findUI(f, this);
		goui = new gameover_UI(f, this);
		
		nui.setVisible(true);
		fui.setVisible(false);
		eneui.setVisible(false);
		goui.setVisible(false);
		game.setUIinInventory(nui);
		
		add(goui);
		add(nui);
		add(fui);
		add(eneui);

		// 처음엔 랜덤
		nui.start();
	}

	public Game getGame() {
		return game;
	}

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	public void paintComponent(Graphics g) {
		// System.out.println("이미지 불러오긴 함");
		g.drawImage(image, 0, 0, w, h, this);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	public void fight_flag(boolean tf)
	{
		nui.setflag(tf);
	}
}