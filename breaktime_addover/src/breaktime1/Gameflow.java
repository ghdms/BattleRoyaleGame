package breaktime1;

//�ν��Ͻ����� �����ϰ�  �����մϴ�.
//�ʿ� ������ �ν��Ͻ��� �����մϴ�
//�г� ���ڵ��� ������������ ���ڷ� �޽��ϴ�.

//~scene�� �޸� ���� GUI ���� Ŭ����, �ƴ� ���� �ý��� ���� Ŭ�����Դϴ�.
import java.sql.*;

import breaktime1.DB.DB_connect;
import breaktime1.GameObject.Player;

public class Gameflow {

	// �ν��Ͻ� ����
	mainFrame mainframe;
	DB_connect DBset;
	Player player = new Player();

	// ������
	Statement statement;
	String PlayerID;

	public Gameflow() {
		player.set("", 0, 0, 10, 0, 100, 0, 0);
		DBset = new DB_connect();
		// ��������
		statement = DBset.getStatement();
		// ������Ʈ ����
		mainframe = new mainFrame(statement,player);
		// ���� ������ ����

	}
	
	public enum place {
		classroom(0), aisle(1), restaurant(2), art(3), gym(4), kitchen(5),
	    music(6),  science(7), nurse(8);
		private int value;

		private place(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	};
	
}
