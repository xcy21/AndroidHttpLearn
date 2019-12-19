package mysqltest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://localhost:3306/test ?serverTimezone=GMT%2B8";
	private String user = "root";
	private String pwd = "12345";
	private Statement sta;
	private ResultSet rs;
	
	public void openConnect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pwd);
			if(conn!= null) {
				System.out.println("数据库连接成功！");
			}
		}catch(Exception e){
			System.out.println("ERROR:"+e.getMessage());
		}
	}
	
	public ResultSet getUser() {
		try {
			sta = conn.createStatement();
			rs = sta.executeQuery("select * from user");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public boolean isExistInDB(String username,String password) {
		boolean isFlag = false;
		try {
			sta = conn.createStatement();
			rs = sta.executeQuery("select * from user");
			if(rs!=null) {
				while(rs.next()) {
					if(rs.getString("username").equals(username)) {
						if(rs.getString("password").equals(password)) {
							isFlag = true;
							break;
						}
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			isFlag = false;
		}
		return isFlag;
	}
	
	public boolean insertDataToDB(String username,String password) {
		String sql = "insert into user(username,password) values("+"'"+username+"',"+"'"+password+"')";
		try {
			sta = conn.createStatement();
			return sta.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void closeConnect() {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(sta!=null) {
				sta.close();
			}
			if(conn!=null) {
				conn.close();
			}
			System.out.println("关闭数据库连接成功");
		}catch(SQLException e) {
			System.out.println("Error:"+e.getMessage());
		}
	}
}
