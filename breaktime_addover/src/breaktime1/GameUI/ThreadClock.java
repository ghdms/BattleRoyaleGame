package breaktime1.GameUI;

import java.awt.Color;
import javax.swing.JLabel;

public class ThreadClock extends JLabel implements Runnable {
	private Thread thread;
	private int time;

	// 생성자에서 프레임생성
	public ThreadClock() {
		setForeground(Color.GREEN);
		setBounds(60, 770, 130, 130); // 프레임 크기 설정
		setFont(getFont().deriveFont(50.0f));
		time = 90;
		setText(String.valueOf(time));
		setVisible(true); // 프레임을 보여줌

		// run 메소드 객체 생성
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

				Thread.sleep(1000); // 1초마다 시작해야해서
			} catch (Exception e) {
				// TODO: handle exception
			}
			revalidate();
			repaint();
		}
	}
}