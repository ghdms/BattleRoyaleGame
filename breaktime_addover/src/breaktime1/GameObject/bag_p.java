package breaktime1.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Statement;

import javax.swing.*;

import breaktime1.GameUI.Inventory;
import breaktime1.Input.MouseManager;
import breaktime1.Place.Normal_UI;

/* �ؾ� �� ����
 * ���� ��� �˾� / ������ ����
 * ���� ��ư ����� ������ �ޱ� -> ����, ���, ������(�̰� �����Ұ�?) & ���� �ٱ��� ������ ������
 * �� ������ �� ���� ��������
 * �� �������� ���� ���ֱ�
 */
public class bag_p extends JPanel implements Runnable {
	Font font = new Font("sserife", Font.BOLD, 17);
	Font hudfont = new Font("sserife", Font.BOLD, 22);
	Font namefont = new Font("sserife", Font.BOLD, 27);
	static JFrame frame;

	Graphics2D g2d;

	public static boolean isOpen = false;
	int w, h;
	private int x, y;
	private Thread t1;
	public Inventory my_inventory;
	private BufferedImage image;
	private Player cur_pl;
	private MouseManager mm = new MouseManager();

	public Inventory getMy_inventory() {
		return my_inventory;
	}

	public void setUIinInventory(Normal_UI NU) {
		my_inventory.setUIinInventory(NU);
	}

	public bag_p(int x, int y, Player p, Statement s) {
		this.setOpaque(false);
		// TODO Auto-generated constructor stub
		cur_pl = p;
		this.x = x;
		this.y = y;

		my_inventory = new Inventory(0 + 55, 0 + 70, cur_pl, s);

		this.addMouseMotionListener(mm);
		this.addMouseListener(mm);

		Threadstart();
		setVisible(true);

	}

	public void Threadstart() {

		t1 = new Thread(this);
		t1.start();

	}

	public void run() {
		while (true) {
			try {
				my_inventory.tick(mm);
				repaint();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g2d = (Graphics2D) g;

		if (g2d != null) {
			// g2d.setColor(Color.YELLOW);
			// g2d.fillRect(0, 0, 1000, 700);

			my_inventory.render(g2d);
		}
	}

}
