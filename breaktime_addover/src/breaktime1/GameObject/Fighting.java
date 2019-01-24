package breaktime1.GameObject;

import java.sql.*;

public class Fighting 
{
	Statement stmt;
	String myname;
	String enename;
	int eneHP = 100;
	int enedamage;
	int enebest=0;
	int mybest =0;
	
	int myshake = 0;
	int yourshake = 0;
	
	public Fighting(Statement stmt)
	{
		this.stmt = stmt;
	}
	public int getscore(String id)
	{	
		int score = 0;
		try {
			String qr = "select shake from fight where pl_name = '" + id + "'";
			ResultSet rs = stmt.executeQuery(qr);
			while(rs.next())
			{
				score = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -100;
		}
		// 서버에서 id의 스코어 받아오기
		return score;
	}
	public void sethpf(int hp , String id)
	{
		String qr1 = "update player set hp = " + hp + " where nickname = '" + id + "'";
        try {
           stmt.executeUpdate(qr1);
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        }
	}
	public void Setname(String name) {
		myname = name;
		try {
			String qr = "select pl_name from fight where pl_name <> " + "'" + name + "' and id = (select id from fight where pl_name = '" + myname + "')";
			ResultSet rs = stmt.executeQuery(qr);
			while (rs.next()) {
				enename = rs.getString(1);
			}
			int a= 1;
			System.out.println(a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 서버에서 적 이름 받아오기
		// enename
	}
	public String getEnename() 
	{
		return enename;
	}
	public int getbest() {
		try {
			String qr = "select shake from player where nickname = '" + enename + "'";
			ResultSet rs = stmt.executeQuery(qr);
			while (rs.next()) {
				enebest = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -100;
		}
		// 적 플레이어 최고기록 가져오기
		return enebest;
	}
	public void setbest(int num) {
		// 서버 플레이어 최고기록 num 으로 갱신하기
		try {
			String qr = "select shake from player where nickname = '" + myname + "'";
			ResultSet rs = stmt.executeQuery(qr);
			while (rs.next()) {
				mybest = rs.getInt(1);
			}
			if(num > mybest)
			{
				String qr1 = "update player set shake = " + num + " where nikcname = '" + myname + "'";
				int rs1 = stmt.executeUpdate(qr1);
				mybest = num;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getEnedamage() 
	{
		try {
			String qr = "select damage from fight where id = '" + enename + "'";
			ResultSet rs = stmt.executeQuery(qr);
			while (rs.next()) {
				enedamage = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 서버에서 데미지 받아오기
		return enedamage;
	}

	public int getEneHP() {
		try {
			String qr = "select hp from player where nickname = '" + enename + "'";
			ResultSet rs = stmt.executeQuery(qr);
			while (rs.next()) {
				eneHP = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 서버에서 적 체력 받아오기
		return eneHP;
	}
}
