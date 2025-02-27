package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) {
		// 부서명을 입력 받아
		// 해당 부서의 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		Scanner sc = new Scanner(System.in);
		
		/* 1. JDBC 객체 참조 변수 선언 */
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		/* 2. DriverManager 객체를 이용해 Connection 객체 생성하기 */ 
		Class.forName("oracle.jdbc.OracleDriver");
			
		// DB 연결 정보
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
		
		// 부서명 입력 받기
		System.out.println("=== 부서명 ===");
		
		System.out.print("부서명 입력: ");
		String input = sc.nextLine();
			
		// """ """ : 작성된 문자열 형태 그대로 저장
		
		/* 3. SQL 작성 */
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME ");
		sb.append("FROM EMPLOYEE E");
		sb.append("WHERE DEPT_TITLE ");
		sb.append("ORDER BY JOB_CODE ASC ");
		
		
		String sql = String.format("""
				SELECT E.EMP_ID, E.EMP_NAME, D.DEPT_TITLE, J.JOB_NAME 
				FROM EMPLOYEE E
				JOIN JOB J ON (E.JOB_CODE = J.JOB_CODE)
				JOIN DEPARTMENT D ON (E.DEPT_CODE = D.DEPT_ID)
				WHERE DEPT_TITLE = '%s'
				ORDER BY E.JOB_CODE ASC 
				""", input);
		
		/* 4. sql을 전달하고 결과를 받아올 Statement 객체 생성 */
		stmt = conn.createStatement();
		
		/* 5. Statement 객체를 이용해서 SQL을 DB로 전달 후 수행 
		  1) SELECT문 : executeQuery() -> ResultSet으로 반환

		  2) DML문    : executeUpdate() -> 결과 행의 개수(int) 반환
		*/
		rs = stmt.executeQuery(sql);

		/* (5번 sql이 SELECT인 경우에만)
		 * 6. 조회 결과가 저장된 ResultSet을 
		 * 1행 씩 접근하여 각 행에 기록된 컬럼 값 얻어오기 */
		
		while(rs.next()) {
			// rs.next() : ResultSet의 Cursor를 다음 행으로 이동
			// 다음 행이 있으면 true, 없으면 false
			
			String empId = rs.getString("EMP_ID");
			String empName = rs.getString("EMP_NAME");
			String deptTitle = rs.getString("DEPT_TITLE");
			String jobName = rs.getString("JOB_NAME");
			
			System.out.printf(
					"%s / %s / %s / %s \n",
					empId, empName, deptTitle, jobName);
		}

		} catch (Exception e) {
			e.printStackTrace();
		} 
		/* 7. 사용 완료된 JDBC 객체 자원 반환 */
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
