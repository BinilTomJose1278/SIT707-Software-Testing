package edu.deakin;

public class SimpleLoginForm {

    public String login(String username, String password) {
        if (username == null || password == null) {
            return "error";
        }

        if (username.equals("admin") && password.equals("pass123")) {
            return "success";
        }

        return "failure";
    }
}
