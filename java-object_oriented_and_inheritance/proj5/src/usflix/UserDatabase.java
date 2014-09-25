package usflix;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDatabase {
	
	public static final int minPwdLength = 6;
	protected HashMap<String,User> userDB;
	protected ArrayList<String> compU;
	
	UserDatabase(){
		userDB = new HashMap<String,User>();
		compU = new ArrayList<String>();
	}
	//Creates an account and ensures that the username is unique
	//that the pw is long enough and the pw is not in the username
	public User createAccount(String f, String l, String u, String p){
		if (minPwdLength>p.length()){
			return null;
		}if (p.contains(u)||u.contains(p)){
			return null;
		}if (isAvailable(u)==false){
			return null;
		}else{
			User nu = new User(f, l, u, p);
			userDB.put(u,nu);
			compU.add(u);
			return nu;
			
		}
	}
	//Logs the user using User.login and returns User object
	public User login(String u, String p){
		if (userDB.get(u)!=null){
			if(userDB.get(u).login(p)){
				return userDB.get(u);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	//checks to see if username is available
	public boolean isAvailable(String u){
		if (userDB.get(u)!=null){
			return false;
		}else{
			return true;
		}
	}
	//Passes in the user database and the user to compare the others against
	//returns the recommended movie list from the other function.
	public ArrayList<MovieRating> UCompare(User u){
		User[] uDB = new User[compU.size()];
		for (int i=0; i<compU.size(); i++){
			uDB[i] = userDB.get(compU.get(i));
		}
		return (u.MCompare(u, uDB));
	}
	
	
}
