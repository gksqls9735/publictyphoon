package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	public static Connection makeConnection() {
		String filePath = "D:/doitjava/publicData/db.properties";
		Connection con = null;
		try {
			//db.properties로 db주소, 사용자명, 사용자 암호 로드하기
			Properties properties = new Properties();
			properties.load(new FileReader(filePath));
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			//jdbc 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
//			System.out.println("데이타베이스 드라이버 로드 성공");
			//db 연결
			con = DriverManager.getConnection(url, user, password);
//			System.out.println("데이타베이스 접속 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("데이타베이스 드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("데이타베이스 연결 실패");
		} catch (FileNotFoundException e) {
			System.out.println("db.properties 연결 실패");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("db.properties 연결 실패");
			e.printStackTrace();
		}
		return con;
	}
}
