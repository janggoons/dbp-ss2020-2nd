package kr.ac.sungshin.w13;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static String className = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "hr";
	private static String password = "1234";	
	
	public static Connection getConnection() {
		Connection conn = null;		
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);			
			System.out.println("connection success");
			return conn;
		} catch (ClassNotFoundException e){
			System.out.println("연결 드라이버 없음");
			return null;
		} catch (SQLException e){
			System.out.print("연결 실패 : ");
			if(e.getMessage().indexOf("ORA-28009") > -1)
				System.out.println("허용되지 않는 접속 권한 없음");
			else if(e.getMessage().indexOf("ORA-01017") > -1)
				System.out.println("유저/패스워드 확인");
			else if(e.getMessage().indexOf("IO") > -1)
				System.out.println("IO - 연결확인!");
			else 
				System.out.println("기본 연결확인!");
			return null;
		}
	}
}
