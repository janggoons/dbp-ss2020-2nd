<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page language="java" import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Test JSP and Oracle</title>
</head>
<body>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String password = "1234";
	
	String sql = "select first_name, last_name, salary from employees where salary > 9000 order by salary desc";
	
	try{
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, password);
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		out.println("<table border=\"1\">");
		out.println("<tr>");
		out.println("<th>" + "first_name" + "</th>");
		out.println("<th>" + "last_name" + "</th>");
		out.println("<th>" + "salary" + "</th>");
		out.println("</tr>");
		while(rs.next()){
			out.println("<tr>");
			out.println("<td>" + rs.getString("first_name") + "</td>");
			out.println("<td>" + rs.getString("last_name") + "</td>");
			out.println("<td>" + rs.getString("salary") + "</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		
	} catch  (ClassNotFoundException e){
		out.println("연결 드라이버 없음");
	} catch (SQLException e){
		out.print("연결 실패 : ");
		if(e.getMessage().indexOf("ORA-28009") > -1)
			out.println("허용되지 않는 접속 권한 없음");
		else if(e.getMessage().indexOf("ORA-01017") > -1)
			out.println("유저/패스워드 확인");
		else if(e.getMessage().indexOf("IO") > -1)
			out.println("IO - 연결확인!");
		else 
			out.println("기본 연결확인!");
	} finally {
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}

%>

</body>
</html>