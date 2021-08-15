package task1;

import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class specialisation {

	static Scanner sc = new Scanner(System.in);
	static ResultSet rs;
	
	public static Statement connector(String username, String password) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kluniversity", username, password);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		return stmt;
	}

	public static void createTables(String username, String password) {
		try {
			Statement stmt = connector(username, password);
			String query0 = "create table availableSpecs(spec_ID integer primary key,spec_name varchar(30) not null,slots integer not null)";
			String query1 = "create table studentDetails(student_ID integer primary key,student_name varchar(30) not null,enrolled_into varchar(30) not null)";
			stmt.executeUpdate(query0);
			stmt.executeUpdate(query1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void printMenuSuperUser() {
		System.out.println("Select Your options\n " + "1) specialisations and seats available \n "
				+ "2) view students and their respective specialisations \n "
				+ "3) Enter Student Details(admin Access) \n " + "4) Enter Specialisation Details(admin Access) \n "
				+ "5) Exit the application");
	}
	
	public static void printMenu() {
		System.out.println("Select Your options\n " + "1) specialisations and seats available \n "
				+ "2) view students and their respective specialisations \n "
				+ "5) Exit the application");
	}

	public static void switchClass(int opt) throws InterruptedException, SQLException {
		switch (opt) {
		case 1: {
			System.out.println("Available Options");
			Statement stmt = connector("root", "saisum@2");
			String query = "select * from availableSpecs";
			rs = stmt.executeQuery(query);
			System.out.println("ID of specialisation | \tSpecialization name | \tAvailable slots");
			System.out.println("------------------------------------------------------------");
			while (rs.next()) {
				System.out.println(rs.getInt("spec_ID") + "\t\t\t" +"|"+ rs.getString("spec_name")+"|" + "\t\t\t"+"|" + rs.getInt("slots")+"|");
			}
			break;
		}
		case 2: {
			System.out.println("Students Info");
			Statement stmt = connector("root", "saisum@2");
			String query = "select * from  studentDetails";
			rs = stmt.executeQuery(query);
			System.out.println("ID of student \t  Student name \tSpecialisation");
			System.out.println("------------------------------------------------------------");
			while (rs.next()) {
				System.out.print(rs.getInt("student_ID") + "\t" + rs.getString("student_name") + "\t");
				System.out.println(rs.getString("enrolled_into"));
			}
			break;
		}

		case 3: {
			System.out.println("Student details - entered here(priviledge mode: Admin)");
			System.out.println("select the ID to enroll into the specialisations");
			Statement stmt = connector("root", "saisum@2");
			int o = sc.nextInt();
			String query = " select * from availableSpecs where spec_ID = " + o + "";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (o == rs.getInt("spec_ID")) {
					int slots = rs.getInt("slots");
					if (slots == 0)
						System.out.println("SLOTS WERE FILLED");
					else {
						String specialization = rs.getString("spec_name");
						System.out.println("Enter your id : ");
						int id = sc.nextInt();
						System.out.println("Enter your name : ");
						String name = sc.next();
						query = "insert into studentDetails values(" + id + ",'" + name + "','"
								+ specialization + "')";
						stmt.executeUpdate(query);
						slots = slots - 1;
						query = "update availableSpecs set slots = " + slots + " where spec_ID = " + o + "";
						stmt.executeUpdate(query);
						System.out.println("Sucessfully Registered into your Specialisation");
					}

				}
			}

			break;
		}
		case 4:{
			System.out.println("Speacialisation details - entered here(priviledge mode: Admin)");
			
			int id;
			String name = null;
			Statement stmt = connector("root", "saisum@2");
			System.out.println("Enter ID for inserting specialisation:");
			id = sc.nextInt();
			System.out.println("Enter specialisation name:");
			name = sc.next();
			System.out.println("Enter Slots available:");
			int slots = sc.nextInt();
			String query8 = " Insert into availableSpecs values(" + id + ",'" + name + "'," + slots + ")";
			stmt.executeUpdate(query8);
			System.out.println("Record is inserted");
			
			break;
		}
		case 5: {
			System.out.println("You have choose to exit the application \n" + "You'll leave in 5 secs");
			TimeUnit.SECONDS.sleep(5);
			System.out.println("Bye \n" + "See You Later");
			System.exit(0);
			break;
		}
		default:
			System.out.println("Invalid Options choose Correctly");
			break;
		}

	}

	public static void main(String args[]) throws InterruptedException, SQLException {
//		
//		createTables("root", "saisum@2");
		System.out.println("enter the username and password");
		String userName = sc.next();
		String pass = sc.next();
		int k;
		if (userClass.isAdmin(userName, pass)) {
			System.out.println("Welcome"+userName);
			printMenuSuperUser();
			
			while(true) {
				k=sc.nextInt();
				switchClass(k);
			}
			
			
		} else {
			System.out.println("Welcome"+userName);
			printMenu();
			while(true) {
				k=sc.nextInt();
				if(k==1 || k==2 || k==5) {
						switchClass(k);
				}
				else {
						switchClass(100);
				}
			}
		}
		
		
	}
}
