package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// "최소", "최대" 급여 범위를 입력 받아
		
		// EMPLOYEE 테이블에서
		// 급여 "최소" 이상 "최대" 이하로 받는 사원의
		// 사번, 이름, 부서코드, 급여를
		// 급여 내림차순으로 조회
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		
		Class.forName("oracle.jdbc.OracleDriver");
			
		
		String type = "jdbc:oracle:thin:@";
		String host = "112.221.156.34";
		String port = ":12345";	
		String dbName = ":XE";
		String userName = "KH05_KDW";
		String password = "KH1234";
			
		conn = DriverManager.getConnection(
				type + host + port + dbName, // DB URL
				userName,
				password
				);
		
		// 범위 입력 받기
		System.out.println("=== 범위 내 급여 받은 직원 조회 ===");
		
		System.out.print("최소 값 입력 : ");
		int min = sc.nextInt();
		
		System.out.print("최대 값 입력 : ");
		int max = sc.nextInt();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY ");
		sb.append("FROM EMPLOYEE ");
		sb.append("WHERE SALARY BETWEEN ");
		sb.append(min);
		sb.append(" AND ");
		sb.append(max);
		sb.append(" ORDER BY SALARY DESC ");
		
		String sql = sb.toString();
		
		stmt = conn.createStatement();
		
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			
			String empId = rs.getString("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			String deptCode = rs.getString("DEPT_CODE");
			int    salary = rs.getInt("SALARY");
			
			System.out.printf(
					"%s / %s / %s / %d \n",
					empId,empName,deptCode,salary);
		}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		try {
			// 생성 역순으로 close 하는 것이 좋다!!
			if(rs  != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
