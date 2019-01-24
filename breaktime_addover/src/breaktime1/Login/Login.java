package breaktime1.Login;

import java.sql.*;

import breaktime1.DB.DataUpdateRunnable;

public class Login {
	private Statement statement;
	private DataUpdateRunnable DR;
	
	public Login(Statement stmt)
		{
			statement = stmt;
			DR = new DataUpdateRunnable(statement);
		}
	public boolean islogin(String id, String password)
	{
		String qr = "select nickname from user where nickname = '" + id + "' and password = '" + password + "'";
		String qr2 = "select nickname from player where nickname = '" + id + "'";
		
		boolean islogin = false;
		
		try {
			ResultSet rs = statement.executeQuery(qr);
			if (rs.next()) 
			{
				ResultSet rs2 = statement.executeQuery(qr2);
				if (rs2.next() == false){
					islogin = true;
				}
				else{
					islogin = false;
				}
			} 
			else{
				islogin = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return islogin;
	}

	public int insert_player(String id) {
		String qr = "select id, start from Game";
		String qr2 = "insert into player values('" + id + "', ";
		try {
			ResultSet rs = statement.executeQuery(qr);
			int tmpid = 0;
			int flag = 0;
			boolean f = true;
			while (rs.next() && f) {
				tmpid = rs.getInt(1);
				flag = rs.getInt(2);
				if (flag == 0) {
					f = false;
				}
			}
			if (f) {
				tmpid++;
				String qr4 = "insert into Game values (" + tmpid + ", 0, 0, 0)";
				statement.executeUpdate(qr4);
			}
			qr2 += tmpid;
			qr2 = qr2 + ", " + tmpid + ", " + 0 + ", " + 100 + ", " + 0 + ", " + "now()" + ", 0)";
			statement.executeUpdate(qr2);

			String qr3 = "update Game set pl_num = pl_num + 1 where id = " + tmpid;
			statement.executeUpdate(qr3);

			return tmpid;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -100;
		}
	}
	public void signup()
	{
		Signup s = new Signup(statement);
	}
	
	/*
	public void ins_pl(String id) {
		int tmpid = DR.insert_player(id);
		F.addw(id, tmpid);
	}*/
	
}
