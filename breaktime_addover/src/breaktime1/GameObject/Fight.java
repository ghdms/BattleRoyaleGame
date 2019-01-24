package breaktime1.GameObject;

import java.sql.*;

public class Fight implements Runnable {
	private Statement stmt;
	private Player cur_pl;
	private int result;
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Fight(Statement s, Player p) {
		stmt = s;
		cur_pl = p;
		result = 0;
		flag = true;
	}

	public void run() {
		try {
			while (true)
			{
				ResultSet rs;
				if (flag && !cur_pl.getNickname().equals(""))
				{
					String qr = "select fight from player where nickname = '" + cur_pl.getNickname() + "'";
					try {
						Statement s5 = stmt;
						rs = s5.executeQuery(qr);
						while (rs.next()) 
						{
							result = rs.getInt(1);
							if (result == 1) {
								System.out.print("전투정보올리기");
								Statement s6 = stmt;
								String qr2 = "update fight set damage = " + cur_pl.cal_da() + ", defense = "
										+ cur_pl.cal_de() + " where pl_name = '" + cur_pl.getNickname() + "'";
								s6.executeUpdate(qr2);
								System.out.println(qr2);
								flag = false;
							}
							break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Thread.sleep(2000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}