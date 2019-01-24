package breaktime1.GameUI;

import java.awt.Color;
import javax.swing.JLabel;

public class ThreadClock extends JLabel implements Runnable {
	private Thread thread;
	private int time;

	// �����ڿ��� �����ӻ���
	public ThreadClock() {
		setForeground(Color.GREEN);
		setBounds(60, 770, 130, 130); // ������ ũ�� ����
		setFont(getFont().deriveFont(50.0f));
		time = 90;
		setText(String.valueOf(time));
		setVisible(true); // �������� ������

		// run �޼ҵ� ��ü ����
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				if (time <= 0) {
					time = 90;
					setForeground(Color.GREEN);
				}
				if (time <= 30) {
					setForeground(Color.RED);
				}
				time--;
				setText(String.valueOf(time));

				Thread.sleep(1000); // 1�ʸ��� �����ؾ��ؼ�
			} catch (Exception e) {
				// TODO: handle exception
			}
			revalidate();
			repaint();
		}
	}
}