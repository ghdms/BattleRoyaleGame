package breaktime1.Wait;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import breaktime1.mainFrame;
import breaktime1.DB.DataUpdateRunnable;
import breaktime1.GameObject.Player;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Waiting_scene extends JPanel implements Runnable {
	private Statement stmt;
	private BufferedImage image;
	private int w, h;
	private mainFrame f;
	private JButton btnNewButton;
	private Thread t1;

	private DataUpdateRunnable DR;
	private String id;
	private JLabel readyarea;
	private JLabel load;
	private JLabel game = new JLabel();

	private Waiting wait;

	private Player player;

	public Waiting_scene(mainFrame F, Player plyr) {
		setSize(1200, 900);
		setLayout(null);
		f = F;
		stmt = f.getStmt();
		// 백그라운드 이미지 띄우는 부분
		try {
			image = ImageIO.read(getClass().getResource("/breakt/wait_bg.png"));
			w = image.getWidth();
			h = image.getHeight();

		} catch (IOException ioe) {
			System.out.println("Could not read in the pic");
			// System.exit(0);
		}
		// 이미지 띄우는 부분 종료

		// 초기화
		DR = f.getDR();
		player = plyr;

		readyarea = new JLabel();
		readyarea.setBounds(70, 120, 200, 50);
		readyarea.setOpaque(false);
		add(readyarea);

		Threadstart();

		btnNewButton = new JButton(new ImageIcon(getClass().getResource("/breakt/ready.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wait.setready(player); // 레디
			}
		});
		btnNewButton.setBounds(900, 100, 250, 155);
		add(btnNewButton);

		load = new JLabel(new ImageIcon(getClass().getResource("/breakt/load.png")));
		load.setBounds(406, 350, 388, 200);
		load.setVisible(false);
		add(load);

		setVisible(true);

	}

	// 로그인 성공or 겜 재시작시 실행되는 함수
	public void init(Player pl) {
		wait = new Waiting(stmt);
		player = pl;
		id = player.getNickname();
		int gameid = wait.insert_player(id); // 게임에 사람 집어넣기
		player.setGame_ID(gameid); // player gameid 업데이트

		game.setText("현재 방 ID : " + player.getGame_ID());
		game.setBounds(70, 50, 200, 50);
		add(game);
	}

	public void isQuefull() // 이후 서버단이랑 이어서 체킹~~~~~~~~~~~~~~~~
	{
		// 만약 대기 큐에 일정 인원수가 찬다면 실행시키면 됨

		load.setVisible(true);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String id = player.getNickname();
		wait.isQuefull(id, player.getGame_ID());
		
		f.initcard("place", player);
		f.GotoPage("place");

	}

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				while (!t1.isInterrupted()) {
					Thread.sleep(500);

					int Ready = DR.GetReadyNum(player.getGame_ID());
					String ReadyMsg = "READY " + Ready + "/5";

					readyarea.setText(ReadyMsg);

					if (Ready >= 5) { // 빠른 테스트를 위해 1인용으로 설정
						isQuefull();
						t1.interrupt();
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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

	public void Threadstart() {

		t1 = new Thread(this);
		t1.start();

	}
}

class Descending implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		return o2.compareTo(o1);
	}

}