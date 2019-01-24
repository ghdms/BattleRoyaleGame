package breaktime1;

import java.awt.*;
import javax.swing.*;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import breaktime1.DB.DataUpdateRunnable;
import breaktime1.GameObject.Player;
import breaktime1.Login.Login_scene;
import breaktime1.Place.Place_scene;
import breaktime1.Wait.Waiting_scene;

import java.sql.*;

//�г��� ǥ���ϴ� �������Դϴ�.
//�г� ��ȯ �Լ��� ���� ����

public class mainFrame extends JFrame {
	private Statement stmt;
	private DataUpdateRunnable DR;
	private CardLayout cards = new CardLayout();

	private Login_scene login;
	private Waiting_scene waiting;
	private Place_scene place;
	private Player player;

	public mainFrame(Statement stmt, Player plyr) {
		// TODO Auto-generated constructor stub

		player = plyr;
		this.stmt = stmt;
		DR = new DataUpdateRunnable(stmt);
		setTitle("BreakTime");
		setSize(1200, 900);
		getContentPane().setLayout(cards);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pressx();

		login = new Login_scene(this, player);
		waiting = new Waiting_scene(this, player);
		place = new Place_scene(this, player);

		getContentPane().add("login", login);
		getContentPane().add("waiting", waiting);
		getContentPane().add("place", place);

		setVisible(true);

	}

	public void pressx() {
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("���۽� �̺�Ʈ!");
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("����� �̺�Ʈ!");
				if (player != null) {
					try {
						String qr1 = "delete from player where nickname = '" + player.getNickname() + "'";
						int r1 = stmt.executeUpdate(qr1);

						String qr2 = "update Game set pl_num = pl_num - 1 where id = " + player.getGame_ID();
						int r2 = stmt.executeUpdate(qr2);

						String qr3 = "delete from fight where pl_name = '" + player.getNickname() + "'";
						int r3 = stmt.executeUpdate(qr3);

						System.out.println(r1 + " " + r2 + " " + r3);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else 
				{
					System.exit(100);
				}
			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public Statement getStmt() {
		return stmt;
	}

	public void GotoPage(String pagename) {
		cards.show(getContentPane(), pagename);
	}

	public DataUpdateRunnable getDR() {
		return DR;
	}

	// ī�� �ʱ�ȭ �Լ�
	public void initcard(String cardname, Player pl) {
		player = pl;
		switch (cardname) {
		case "waiting":
			waiting.init(pl);
			break;
		case "place":
			place.init(this,pl);
			break;
			// ���� ī�庰 �ʱ�ȭ�� �ʿ��ϴٸ� �����Ŵ
		}

	}

}
