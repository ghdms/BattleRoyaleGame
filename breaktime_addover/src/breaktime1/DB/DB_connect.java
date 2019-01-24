package breaktime1.DB;

import java.sql.DriverManager;
import java.sql.*;

public class DB_connect {
	private Statement stmt;

	public DB_connect() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://1.201.141.109:3306/breaktime?useServerPrepStmts=false&rewriteBatchedStatements=true", "root", "chamel186");
			// here sonoo is database name, root is username and password
			stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from user");
			while (rs.next())
				System.out.println(rs.getString(1) + "  " + rs.getString(2));
			
			// con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Statement getStatement()
	{
		return stmt;
	}

}
