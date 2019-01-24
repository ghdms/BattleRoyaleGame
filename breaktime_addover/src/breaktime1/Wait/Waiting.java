package breaktime1.Wait;

import java.sql.*;

import breaktime1.DB.DataUpdateRunnable;
import breaktime1.GameObject.Player;

public class Waiting {
	private Statement stmt;
	public boolean myready;
	private DataUpdateRunnable DR;
	
	public Waiting(Statement stamt) 
	{
		stmt = stamt;
		DR = new DataUpdateRunnable(stamt);
		myready = false;
	}

	//����� �����ؼ� �Ű���
	public int insert_player(String id) {
		System.out.print(id);
		System.out.println("�������ֱ�!");
		
		try {
			String qr = "select id, start from Game";
			String qr2 = new String();
			ResultSet rs = stmt.executeQuery(qr);
			int tmpid = 0;
			int flag = 0;
			int count = 0;
			boolean check = true;
			while (rs.next()) {
				tmpid = rs.getInt(1);
				flag = rs.getInt(2);
				count++;
				if(flag == 0)
				{
					check = false;
					break;
				}
			}
			if(count == 0)
			{
				tmpid = 1;
				String qr4 = "insert into Game values (" + tmpid + ", 0, 0, 0)";
				stmt.executeUpdate(qr4);
			}
			else if(!check)
			{
				tmpid = count;
			}
			else
			{
				tmpid = count + 1;
				String qr10 = "insert into Game values (" + tmpid + ", 0, 0, 0)";
				stmt.executeUpdate(qr10);
			}
			qr2 ="insert into player values('" + id + "', " + tmpid + ", " + 0 + ", " + 100 + ", " + 0 + ", " + "now(), 0)";
			stmt.executeUpdate(qr2);

			String qr3 = "update Game set pl_num = pl_num + 1 where id = " + tmpid;
			stmt.executeUpdate(qr3);
			
			return tmpid;
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -100;
		}
	}
	
	public void setready(Player pl) {
		// ���߿� ���� ��� �����ϰ� �ϸ� ������
		if (!pl.isMyready()) // ó�� ���� ��ư�� ������ ����
		{
			String qr = "update Game set ready = ready + 1 where id = " + pl.getGame_ID();
			try {
				stmt.executeUpdate(qr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pl.setMyready(true);
			myready = true;
		}
	}

	public void isQuefull(String id, int gameid) //üũ
	{
		// ���� ��� ť�� ���� �ο����� ���ٸ� �����Ű�� ��
		{
			String qr = "select nickname, max(time) from player where room = " + gameid;
			try {
				ResultSet rs = stmt.executeQuery(qr);
				String x = new String();
				String y = new String();
				while (rs.next()) {
					x = rs.getString(1);
					y = rs.getString(2);
				}
				if (x.equals(id)) {
					DR.create_game(gameid);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
