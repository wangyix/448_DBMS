package database;

import java.io.*;
import java.util.*;

import exception.DatabaseException;

public class Users {

	private static final String usersFileName = "users.ser";
	
	public enum Type {
		ADMIN(2), USER_A(1), USER_B(0);
		private int rank;
		private Type(int rank) {
			this.rank = rank;
		}
	}
	
	
	private static Map<String, Type> usersMap = new HashMap<String, Type>();
	private static String currentUserName;
	private static Type currentUserType;
	
	
	public static void setCurrentUser(String userName) throws DatabaseException {
		if (userName.equals("admin")) {
			currentUserName = "admin";
			currentUserType = Type.ADMIN;
		} else {
			Type userType = usersMap.get(userName);
			if (userType == null) {
				throw new DatabaseException("User '"+userName+"' does not exist.");
			}
			currentUserName = userName;
			currentUserType = userType;
		}
	}
	
	public static void addUser(String userName, Type userType) throws DatabaseException {
		if (userName.equals("admin")) {
			throw new DatabaseException("Username 'admin' is reserved.");
		} else if (usersMap.containsKey(userName)) {
			throw new DatabaseException("User '"+userName+"' already exists.");
		}
		usersMap.put(userName, userType);
	}
	
	public static void deleteUser(String userName) throws DatabaseException {
		if (userName.equals("admin")) {
			throw new DatabaseException("Cannot delete user 'admin'.");
		} else if (!usersMap.containsKey(userName)) {
			throw new DatabaseException("User '"+userName+"' does not exist.");
		}
		usersMap.remove(userName);
	}
	
	
	public static void verifyCurrentUserRankAtLeast(Type type)
			throws DatabaseException {
		if (currentUserType.rank < type.rank) {
			throw new DatabaseException("Do not have sufficient user privilege.");
		}
	}
	
	public static boolean currentUserIsTypeB() {
		return currentUserType==Type.USER_B;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void readUsersFromDisk()
			throws IOException, ClassNotFoundException, FileNotFoundException {
		usersMap.clear();
		ObjectInputStream usersOis = new ObjectInputStream(new FileInputStream(usersFileName));
		usersMap = (HashMap<String, Type>)usersOis.readObject();			
		usersOis.close();
		for (String s : usersMap.keySet())
			System.out.println("User '"+s+"' found.");
	}
	public static void writeUsersToDisk()
			throws FileNotFoundException, IOException {
		ObjectOutputStream usersOos = new ObjectOutputStream(new FileOutputStream(usersFileName));
		usersOos.writeObject(usersMap);
		usersOos.close();
	}
	
	
	public String getCurrentUserName() {
		return currentUserName;
	}
}
