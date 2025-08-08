package utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CredentialManager {
	
	public static final String FILE_PATH = "src/main/resources/credentials/credentials.properties";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static void saveCredentials(String username,String password) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.setProperty("username", username);
		props.setProperty("password", password);
		props.setProperty("createdDate", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
		
		try(FileOutputStream fos = new FileOutputStream(FILE_PATH)){
			props.store(fos, "OneLogin Credentilas");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties loadCredentials() {
		Properties props = new Properties();
		try(FileInputStream fis = new FileInputStream(FILE_PATH)){
			props.load(fis);
		}catch(IOException e) {
			System.out.println("No Credentials Found.");
		}
		return props;
	}
	
	public static boolean isCredentialValid() {
		Properties props = loadCredentials();
		String createdDateStr = props.getProperty("createdDate");
		
		if(createdDateStr ==  null) return false;
		
		try {
			Date createdDate = new SimpleDateFormat(DATE_FORMAT).parse(createdDateStr);
			long daysDiff = (new Date().getTime() - createdDate.getTime())/(1000*60*60*24);
			return daysDiff <= 30;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getUsername() {
		return loadCredentials().getProperty("username");
	}
	
	public static String getPassword() {
		return loadCredentials().getProperty("password");
	}
	
	public static String getCreatedDate() {
		return loadCredentials().getProperty("createdDate");
	}
}
