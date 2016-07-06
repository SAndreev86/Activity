package ru.simbirsoft.intensiv.workWithDB;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkWithDB {

	private static List<String> list = new ArrayList<>();

	private WorkWithDB() {
	}

	static{
		list.add("чай");
		list.add("завтрак");
		list.add("обед");
		list.add("ужин");
		list.add("соц.сеть");
		list.add("пробежка");
		list.add("работа");
		list.add("отдых");
		list.add("курение");
	}


	public static boolean checkExistDB() {
		boolean isExist = true;
		Statement stmt = null;
		try {

			stmt = ConnectDB.getCon().getC().createStatement();

			ResultSet r = stmt.executeQuery("SELECT * FROM STATISTICS;");
			while (r.next()) {
				r.getInt("id");
				r.getString("activity");
				r.getInt("time");
				r.getString("comment");
				r.getString("date");
				r.getString("name");
			}

			ResultSet r1 = stmt.executeQuery("SELECT * FROM USERS;");
			while (r1.next()) {
				r1.getInt("id");
				r1.getString("password");
				r1.getString("name");
			}

			ResultSet r2 = stmt.executeQuery("SELECT * FROM ACTIVITY;");
			while (r2.next()) {
				r2.getInt("id");
				r2.getString("activity");
				r2.getString("name");
			}

			stmt.close();

		} catch (SQLException e) {
			isExist= false;
			System.out.println(0);
			e.printStackTrace();
		}

		return isExist;
	}

	public static void createTable(){
		Statement stmt = null;
		try {
			stmt = ConnectDB.getCon().getC().createStatement();

			String sql = "CREATE TABLE USERS " +
					"(ID INT PRIMARY KEY     NOT NULL," +
					" NAME           TEXT    NOT NULL, " +
					" PASSWORD        TEXT     ) ";
			stmt.executeUpdate(sql);

			String sql1 = "INSERT INTO USERS (ID,NAME,PASSWORD) " +
					"VALUES (1, 'Paul', '12');";
			stmt.executeUpdate(sql1);
			ConnectDB.getCon().getC().commit();
			System.out.println(4);



			String sql2 = "CREATE TABLE ACTIVITY " +
					"(ID INT PRIMARY KEY     NOT NULL," +
					" ACTIVITY           TEXT    NOT NULL, " +
					" NAME        TEXT     NOT NULL) ";
			stmt.executeUpdate(sql2);

			String sql3 = "INSERT INTO ACTIVITY (ID, ACTIVITY, NAME) " +
					"VALUES (1, 'SON', 'Paul');";
			stmt.executeUpdate(sql3);
			ConnectDB.getCon().getC().commit();
			System.out.println(5);



			String sql4 = "CREATE TABLE STATISTICS " +
					"(ID INT PRIMARY KEY     NOT NULL," +
					" ACTIVITY           TEXT    NOT NULL, " +
					" TIME           INT    NOT NULL, " +
					" COMMENT           TEXT    , " +
					" DATE           TEXT    NOT NULL, " +
					" NAME        TEXT     NOT NULL) ";
			stmt.executeUpdate(sql4);

			String sql5 = "INSERT INTO STATISTICS (ID, ACTIVITY, TIME, COMMENT, DATE, NAME) " +
					"VALUES (1, 'SON', 12, 'dddd', '" +
					new SimpleDateFormat("YYYY-MM-dd").format(new Date())+"', 'Paul');";
			stmt.executeUpdate(sql5);
			ConnectDB.getCon().getC().commit();
			System.out.println(6);

			stmt.close();
		}catch ( SQLException e){
			e.printStackTrace();
		}
	}

	/*
     * метод записывает активность в базу. в него поступают имя пользователя
     * который создал эту новую активность и саму активность.
     */
	public static void writeActivity(String activity, String name) {
		try {
			try {
				PreparedStatement statement = ConnectDB.getCon().getC().prepareStatement(
						"INSERT INTO ACTIVITY (ID, ACTIVITY, NAME) " +
								"VALUES (?, ?, ?)");
				statement.setInt(1, getMaxIdActivity() + 1);
				statement.setString(2, activity);
				statement.setString(3, name);
				statement.executeUpdate();
				ConnectDB.getCon().getC().commit();
			} catch (SQLException ex) {
				ConnectDB.getCon().getC().rollback();
			} finally {
				ConnectDB.getCon().getC().setAutoCommit(true);
			}
		}catch (SQLException e){}
	}

	/*
     * внутренний метод. не для вас
     */
	private static int getMaxIdActivity() {
		int a = 0;
		Statement stmt = null;
		try {
			stmt = ConnectDB.getCon().getC().createStatement();
			ResultSet r1 = stmt.executeQuery("SELECT * FROM ACTIVITY;");
			while (r1.next()) {
				if(a < r1.getInt("id")){
					a = r1.getInt("id");
				}
			}
			stmt.close();
		}catch ( SQLException e){
			e.printStackTrace();
		}
		return a;
	}

	/*
     * внутренний метод. не для вас
     */
	private static int getMaxIdUser() {
		int a = 0;
		Statement stmt = null;
		try {
			stmt = ConnectDB.getCon().getC().createStatement();
			ResultSet r1 = stmt.executeQuery("SELECT * FROM USERS;");
			while (r1.next()) {
				if(a < r1.getInt("id")){
					a = r1.getInt("id");
				}
			}
			stmt.close();
		}catch ( SQLException e){
			e.printStackTrace();
		}
		return a;
	}

	/*
     * внутренний метод. не для вас
     */
	private static int getMaxIdStatistic() {
		int a = 0;
		Statement stmt = null;
		try {
			stmt = ConnectDB.getCon().getC().createStatement();
			ResultSet r1 = stmt.executeQuery("SELECT * FROM STATISTICS;");
			while (r1.next()) {
				if(a < r1.getInt("id")){
					a = r1.getInt("id");
				}
			}
			stmt.close();
		}catch ( SQLException e){
			e.printStackTrace();
		}
		return a;
	}

	/*
     * возвращает список активностей определенного пользователя те которые есть
     * у всех плюс те которые он создал сам
     */
	public static String[] getListActivity(String name) {
		List<String> list1 = new ArrayList<>();
		try {
			try {
				PreparedStatement statement = ConnectDB.getCon().getC().prepareStatement(
						"SELECT * FROM ACTIVITY WHERE name = ?");
				statement.setString(1, name);
				ResultSet rs = statement.executeQuery();
				while(rs.next()){
					list.add(rs.getString("activity"));
				}
			} catch (SQLException ex) {
				ConnectDB.getCon().getC().rollback();
			} finally {

			}
		}catch (SQLException e){}
		list1.addAll(list);
		String [] stockArr = list1.toArray(new String[0]);
		return stockArr;
	}

	/*
     * записывает нового юзера
     */
	public static void writeNewUser(String name, String password) {
		try {
			try {
				PreparedStatement statement = ConnectDB.getCon().getC().prepareStatement(
						"INSERT INTO USERS (ID, NAME, PASSWORD) " +
								"VALUES (?, ?, ?)");
				statement.setInt(1, getMaxIdUser() + 1);
				statement.setString(2, name);
				statement.setString(3, password);
				statement.executeUpdate();
				ConnectDB.getCon().getC().commit();
			} catch (SQLException ex) {
				ConnectDB.getCon().getC().rollback();
			} finally {
				ConnectDB.getCon().getC().setAutoCommit(true);
			}
		}catch (SQLException e){}
	}

	/*
     * получает пароль по имени юзера
     */
	public static String getPassword(String name) {
		String sd = "qwevvfvvadvserbsdvsdfvdfgvvdbrtbrf";
		try {
			PreparedStatement statement = ConnectDB.getCon().getC().prepareStatement(
					"SELECT * FROM USERS WHERE name = ?");
			statement.setString(1, name);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				sd = rs.getString("password");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return sd;
	}

	/*
     * записывает статистику в базу. поступающие данные активность время
     * активности коммент имя пользователя и дата
     */
	public static void wtiteStatistic(String activity, long time, String comment, String name, Date date) {
		int time1 = (int)time;
		String comment1 = "";
		if(comment.length() != 0) {
			if (comment.charAt(comment.length() - 1) == '\n') {
				comment1 = comment.substring(0, comment.length() - 1);
			} else {
				comment1 = comment;
			}
		}
		try {
			try {
				PreparedStatement statement = ConnectDB.getCon().getC().prepareStatement(
						"INSERT INTO STATISTICS (ID, ACTIVITY, TIME, COMMENT, DATE, NAME) " +
								"VALUES (?, ?, ?, ?, ?, ?)");
				statement.setInt(1, getMaxIdStatistic() + 1);
				statement.setString(2, activity);
				statement.setInt(3, time1);
				statement.setString(4, comment1);
				statement.setString(5, new SimpleDateFormat("YYYY-MM-dd").format(date));
				statement.setString(6, name);
				statement.executeUpdate();
				ConnectDB.getCon().getC().commit();
			} catch (SQLException ex) {
				ConnectDB.getCon().getC().rollback();
			} finally {
				ConnectDB.getCon().getC().setAutoCommit(true);
			}
		}catch (SQLException e){}

	}

	/*
     * вот этот метод Серег для тебя!!!! передаешь имя пользователя и дату и он
     * возвращает список статистики из которого ты потом легко достанешь все
     * данные для себя. чтобы установить дату: Date date = new Date();
     * date.setDate(текущая дата минус какое-то число чтобы получилась нужная
     * тебе дата); например тебе нужно позавчера: сегодня 3 - 2 получаем 1. у
     * нас всего три дня это будет легко настроить.
     */
	public static List<DataStatisticDB> getStatistic(String name, Date date) {
		List<DataStatisticDB> list1 = new ArrayList<>();
		try {
			try {
				PreparedStatement statement = ConnectDB.getCon().getC().prepareStatement(
						"SELECT * FROM STATISTICS where name = ? and date = '"+
								new SimpleDateFormat("YYYY-MM-dd").format(date)+"'");
				statement.setString(1, name);
				ResultSet rs = statement.executeQuery();
				while(rs.next()){
					DataStatisticDB data = new DataStatisticDB();
					data.setName(rs.getString("name"));
					data.setActivity(rs.getString("activity"));
					data.setComment(rs.getString("comment"));
					data.setTime(rs.getLong("time"));
					data.setDate(createDate(rs.getString("date")));
					list1.add(data);
				}
			} catch (SQLException ex) {
				ConnectDB.getCon().getC().rollback();
			} finally {

			}
		}catch (SQLException e){}
		return list1;
	}

	private static Date createDate(String date){
		Date date1 = new Date();
		date1.setYear(Integer.parseInt(date.substring(0, 4)));
		date1.setMonth(Integer.parseInt(date.substring(5, 7)));
		date1.setDate(Integer.parseInt(date.substring(8, 10)));
		return date1;
	}


	public static boolean isExistUser(String name) {
		boolean isExist = true;
		String sd1 = "sdifviduvbfduvbalsdjhbv";
		try {
			PreparedStatement statement1 = ConnectDB.getCon().getC().prepareStatement(
					"SELECT * FROM USERS WHERE name = ?");
			statement1.setString(1, name);
			ResultSet rs = statement1.executeQuery();
			while(rs.next()){
				sd1 = rs.getString("password");
			}
		} catch (SQLException ex) {}
		if(sd1.equals("sdifviduvbfduvbalsdjhbv")){
			isExist = false;
		}
		return isExist;
	}

}
