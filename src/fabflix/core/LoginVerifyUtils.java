package fabflix.core;

import com.google.gson.JsonObject;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class LoginVerifyUtils {
    
    public static JsonObject verifyUsernamePassword(String username, String password) {
        // after recatpcha verfication, then verify username and password
        String returnedPassword = query_for_password(username);
//        printSessions();
        boolean success = false;
        System.out.println("Utility verify check username: "+username+" ,password: "+password);
        success = new StrongPasswordEncryptor().checkPassword(password, returnedPassword);
        if (success && customerEmailExists(username)) {
            // login success:

            JsonObject responseJsonObject = new JsonObject();
            responseJsonObject.addProperty("status", "success");
            responseJsonObject.addProperty("message", "success");
            
            return responseJsonObject;

        } else {
            // login fail
            JsonObject responseJsonObject = new JsonObject();
            responseJsonObject.addProperty("status", "fail");
            if (customerEmailExists(username)) {
                responseJsonObject.addProperty("message", "user " + username + " doesn't exist");
            } else if (success) {
                responseJsonObject.addProperty("message", "incorrect password");
            }

            return responseJsonObject;
        }
    }
    private static String query_for_password(String username) {
        System.out.println("query for password for username: "+username);
        ResultSetMetaData rsmd;
        try {
            PreparedStatement ps = Core.brain.getCon().prepareStatement(Queries.LOGIN_QUERY);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static  boolean customerEmailExists(String email){
        ResultSetMetaData rsmd;
        try {
            PreparedStatement ps = Core.brain.getCon().prepareStatement(Queries.CUSTOMER_EXISTS_QUERY);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            if (rs.next()) {
                // return rs.getBoolean("email");
                //    System.out.println(rs.getString("email"));
                //    if(!rs.getString(email).equals("null")){
                return true;
                //  }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
