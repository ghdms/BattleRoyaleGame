package breaktime1.GameObject;

import java.awt.Graphics;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

public class Clock {

	private GregorianCalendar time;

	private int hour = 0;
	private int min = 0;
	private int sec = 0;
	
	private int move_x = 945;
	private int move_y = 347;
		
	private int ratio = 40;

	public void paintClock(Graphics g) {
		time = new GregorianCalendar();

		// 시간 정보를 가져온다.
		min = time.get(Calendar.MINUTE);
		hour = time.get(Calendar.HOUR);
		sec = time.get(Calendar.SECOND);

		if (sec == 60) {
			sec = 0;
			min++;
		}
		if (min == 60) {
			min = 0;
			hour++;
		}
		if (min == 60 && hour == 12) {
			hour = 0;
		}
   
		// 초 / 분 / 시 바늘을 그린다.
		draw(g,150, 150, 150, 50, 95, sec * 6);
		draw(g,150, 150, 150, 50, 80, min * 6);
		draw(g,150, 150, 150, 50, 70, hour * 30 + min / 2);

	}



	// 초 / 분 / 시 를 표시하기 위해 drawLine(선을 그리는 메서드) 사용
	public void draw(Graphics g,int ox, int oy, int x, int y, double l, int angle) {
	
		x = move_x + ox + (int) ((l-ratio) * Math.sin(angle * Math.PI / 180));
		y = move_y + oy - (int) ((l-ratio) * Math.cos(angle * Math.PI / 180));
	
		ox+=move_x;
		oy+=move_y;
		
		g.drawLine(ox, oy, x, y);
		
	}
	

}
