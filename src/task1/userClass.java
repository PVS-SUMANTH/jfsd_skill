package task1;

public class userClass {
	public static boolean isAdmin(String uname, String password) {
		if (uname.equals("sumanth") && password.equals("sumanthpassword") ){
			return true;
		}
		else {
			return false;
		}
	}
}
