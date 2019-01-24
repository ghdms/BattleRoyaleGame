package breaktime1.DB;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import breaktime1.GameObject.Player;

// 일정 시간마다 DB의 정보를 업데이트
public class DataUpdateRunnable {

	private Statement statement;

	public DataUpdateRunnable(Statement st) {
		statement = st;

	}

	private static final int DELAY = 1000;

	public int GetNum() throws SQLException // 현재 로그인한 Player의 수를 얻어온다.
	{
		String qr = "SELECT COUNT(*) FROM player";
		ResultSet rs = statement.executeQuery(qr);
		int num = 0;
		while (rs.next())
			num = rs.getInt(1);
		return num;
	}

	public int GetReadyNum(int gameid) throws SQLException // 현재 로그인한 Player의 수를 얻어온다.
	{
		String qr = "select * from Game where id = " + gameid;
		ResultSet rs = statement.executeQuery(qr);
		int num = 0;
		while (rs.next())
			num = rs.getInt(4);

		return num;
	}

	public void UpdateZone(int Placename, Player cur_pl) {
		String upqr = "update player set zone = " + Placename + " where nickname = '" + cur_pl.getNickname() + "'";
		try {
			statement.executeUpdate(upqr);
			cur_pl.setZone_ID(Placename);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void create_game(int gameid) {
		try {
			String qr1 = "update Game set start = 1 where id = " + gameid;
			statement.executeUpdate(qr1);

			String qr2 = "create table `game" + gameid + "` as select * from `item`";
			statement.executeUpdate(qr2);
			String qr3 = "alter table `game" + gameid + "` add (`rest` int(2))";
			statement.executeUpdate(qr3);
			String qr4 = "update game" + gameid + " set rest = 4";
			statement.executeUpdate(qr4);
			String qr44 = "update game" + gameid + " set rest = 7 where zone_id = 9";
			statement.executeUpdate(qr44);

			String qr5 = "create table `zone" + gameid + "` as select * from `zone`";
			statement.executeUpdate(qr5);
			String qr6 = "alter table `zone" + gameid + "` add (`flag` int(1))";
			statement.executeUpdate(qr6);
			String qr7 = "update zone" + gameid + " set flag = 1";
			statement.executeUpdate(qr7);
			String qr8 = "alter table `zone" + gameid + "` add (`next` int(1))";
			statement.executeUpdate(qr8);
			String qr9 = "update zone" + gameid + " set next = 0";
			statement.executeUpdate(qr9);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void after_fight(Player p) {
		try {
			String update_fight = "update player set fight = 0 where nickname = '" + p.getNickname() + "'";
			statement.executeUpdate(update_fight);
			String delete_fight = "delete from fight where pl_name = '" + p.getNickname() + "'";
			statement.executeUpdate(delete_fight);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
