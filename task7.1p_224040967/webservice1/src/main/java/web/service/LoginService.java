package web.service;

/**
 * Business logic to handle login functions.
 * 
 * @author Binil
 */
public class LoginService {

    /**
     * Static method returns true for successful login, false otherwise.
     * @param username
     * @param password
     * @param dob in yyyy-mm-dd format
     * @return
     */
    public static boolean login(String username, String password, String dob) {
        if (username == null || password == null || dob == null) {
            return false;
        }

        return "ahsan".equals(username)
            && "ahsan_pass".equals(password)
            && "2000-01-01".equals(dob);
    }
}
