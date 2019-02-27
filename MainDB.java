package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainDB {

	public static void main(String[] args) {

		try {
			createTable();
			insertPerson();
			get();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void get() throws SQLException {
		Connection con = getConnection();
		try {
			PreparedStatement statement = con.prepareStatement("SELECT firstName, lastName, dateOfBirth, course"
					+ " FROM students WHERE course = 3");

			ResultSet result = statement.executeQuery();
			while (result.next()) {
				System.out.println();
				System.out.printf("%8s %13s %15s %4s", result.getString("firstName"), result.getString("lastName"),
						result.getString("dateOfBirth"), result.getString("course"));
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static void insertPerson() throws SQLException {

		Connection con = getConnection();
		try {
			PreparedStatement posted = con.prepareStatement(
					"INSERT INTO students (firstName, lastName, dateOfBirth, course, rating, yearGraduation)"
							+ " VALUES ('Stanislav', 'Petrov', '1997-10-11', 4, 4.5, '2020')");
			posted.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			System.out.println("Insert completed!");
		}

	}

	public static void createTable() throws SQLException {
		Connection con = getConnection();
		try {
			PreparedStatement create = con.prepareStatement("CREATE TABLE IF NOT EXISTS students"
					+ "(id INT UNSIGNED NOT NULL AUTO_INCREMENT," 
					+ " firstName VARCHAR(55) NOT NULL,"
					+ " lastName VARCHAR(55) NOT NULL," 
					+ " dateOfBirth DATE NOT NULL," 
					+ " course TINYINT(1) NOT NULL,"
					+ " rating DECIMAL(3,2) NOT NULL," 
					+ " yearGraduation YEAR(4) NOT NULL," 
					+ " PRIMARY KEY(id))");
			create.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			System.out.println("Table has next fields: "
					+ "`student_id`, `firstName`, `lastName`, `date of birth`, `course`, `rating`, `year of graduation`.");
		}
	}

	public static Connection getConnection() throws SQLException {
		final String URL = "jdbc:mysql://127.0.0.1:3306/my_connect?serverTimezone=Europe/Kiev&useSSL=false";
		final String USERNAME = "root";
		final String PASSWORD = "nazarii";
		try {
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connected!");
			return conn;
		} catch (SQLException e) {
			System.out.println(e);
		}
		return null;
	}

}
