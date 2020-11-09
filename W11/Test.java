package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Test {

	private static String className = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "hr";
	private static String password = "1234";
	
	public Connection getConnection() {
		Connection conn = null;
		
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("connection success");						
			
		} catch (ClassNotFoundException cnfe) {
			System.out.println("failed db driver loading\n" + cnfe.toString());			
		} catch (SQLException sqle) {
			System.out.println("failed db connection\n" + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unknown error");
			e.printStackTrace();
		}		
		return conn;		
	}
	
	public void closeAll(Connection conn, PreparedStatement psmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (psmt != null) psmt.close();
			if (conn != null) conn.close();
			System.out.println("All closed");
		} catch (SQLException sqle) {
			System.out.println("Error!!");
			sqle.printStackTrace();
		}
	}

	private void select() {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from (select * from employees order by rownum DESC) where rownum = 1";		
		
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();			
			while(rs.next()) {
				System.out.print("employee_id: " + rs.getString("employee_id"));
				System.out.print("\tfirst_name: " + rs.getString("first_name"));
				System.out.println("\tsalary: " + rs.getString("salary"));			
			}						
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, psmt, rs);
		}
	}
	
	private void update() {
		Connection conn = null;
		PreparedStatement psmt = null;		
		String sql = "update employees set salary = 4000 where employee_id = 207";		
		
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			int count = psmt.executeUpdate();
			System.out.println(count + " row updated");								
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, psmt, null);
		}
	}
	
	private void insert() {
		Connection conn = null;
		PreparedStatement psmt = null;		
		String sql = "insert into employees values (207, 'minjun', 'do', 'minjun.do', '123.456.789', TO_DATE(sysdate, 'dd-MM-yyyy'), 'IT_PROG', 15000, NULL, NULL, NULL)";		
		
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			int count = psmt.executeUpdate();
			System.out.println(count + " row inserted");						
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, psmt, null);
		}
	}
	
	private void delete() {
		Connection conn = null;
		PreparedStatement psmt = null;		
		String sql = "delete from employees where employee_id = 207";		
		
		try {
			conn = this.getConnection();
			psmt = conn.prepareStatement(sql);
			int count = psmt.executeUpdate();
			System.out.println(count + " row deleted");						
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(conn, psmt, null);
		}
	}
	

	public static void main(String[] args) {		
		
		Test so = new Test();
		so.select();
		so.insert();
		so.select();
		so.update();
		so.select();
		so.delete();
		so.select();
			
	}
}
