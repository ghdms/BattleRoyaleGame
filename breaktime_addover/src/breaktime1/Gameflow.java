package breaktime1;

//인스턴스들을 생성하고  관리합니다.
//필요 없어진 인스턴스는 제거합니다
//패널 인자들은 메인프레임을 인자로 받습니다.

//~scene이 달린 것은 GUI 관련 클래스, 아닌 것은 시스템 관련 클래스입니다.
import java.sql.*;

import breaktime1.DB.DB_connect;
import breaktime1.GameObject.Player;

public class Gameflow {

	// 인스턴스 나열
	mainFrame mainframe;
	DB_connect DBset;
	Player player = new Player();

	// 변수들
	Statement statement;
	String PlayerID;

	public Gameflow() {
		player.set("", 0, 0, 10, 0, 100, 0, 0);
		DBset = new DB_connect();
		// 서버연결
		statement = DBset.getStatement();
		// 스테이트 세팅
		mainframe = new mainFrame(statement,player);
		// 메인 프레임 생성

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
