package breaktime1.Place;
//���� �г�

/*
���� �ν��Ͻ� ������ ������ ����� ����
�ƴ� �ù� �׷� ī�带 �������ܾ�
��ư�� �ǳ� �����ٰ� ���� �׸��°͵� ���� �����ѰŰ�����
�׳� �� �гη� �����ϰ� �̹����� �ٲٴ°� ������ -> �̹��� �ٲٴ� �Լ� ����� (�̹����� ����?���� �޾ƿ�)
*/
/* ���� �϶�
* �ϴ� �� ����... ���� = classroom / ���� = aisle / ��� = stair 
* ���������� ��������ϴ� �� �� ���� ����� ������ ���ٴ�.............
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
		// ��׶��� �̹��� ���� �κ�
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
		// �̹��� ���� �κ� ����
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
			// eneui.init()<-���ӵ��ҰԿ�
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

		game = new Game(stmt, cur_pl); // ���� ��ü ���� ->UI�ּ� �ʿ��ϸ� �̰� �޾ƴ� ���ϴ�
		// UI ���� & ����ֱ�
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

		// ó���� ����
		nui.start();
	}

	public Game getGame() {
		return game;
	}

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	public void paintComponent(Graphics g) {
		// System.out.println("�̹��� �ҷ����� ��");
		g.drawImage(image, 0, 0, w, h, this);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	public void fight_flag(boolean tf)
	{
		nui.setflag(tf);
	}
}