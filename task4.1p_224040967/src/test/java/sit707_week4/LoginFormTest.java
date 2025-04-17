package sit707_week4;

import org.junit.Assert;
import org.junit.Test;

public class LoginFormTest {

    @Test
    public void testStudentIdentity() {
        String studentId = "224040967";
        System.out.println("Running test: Student ID");
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Binil Tom Jose";
        System.out.println("Running test: Student Name");
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testEmptyUsernameAndEmptyPassword() {
        System.out.println("Test TC1: Empty username, empty password");
        LoginStatus status = LoginForm.login("", "");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Empty Username", status.getErrorMsg());
    }

    @Test
    public void testEmptyUsernameAndWrongPassword() {
        System.out.println("Test TC2: Empty username, wrong password");
        LoginStatus status = LoginForm.login("", "wrongpass");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Empty Username", status.getErrorMsg());
    }

    @Test
    public void testEmptyUsernameAndCorrectPassword() {
        System.out.println("Test TC3: Empty username, correct password");
        LoginStatus status = LoginForm.login("", "binil_pass");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Empty Username", status.getErrorMsg());
    }

    @Test
    public void testWrongUsernameAndEmptyPassword() {
        System.out.println("Test TC4: Wrong username, empty password");
        LoginStatus status = LoginForm.login("wronguser", "");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Empty Password", status.getErrorMsg());
    }

    @Test
    public void testWrongUsernameAndWrongPassword() {
        System.out.println("Test TC5: Wrong username, wrong password");
        LoginStatus status = LoginForm.login("wronguser", "wrongpass");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Credential mismatch", status.getErrorMsg());
    }

    @Test
    public void testWrongUsernameAndCorrectPassword() {
        System.out.println("Test TC6: Wrong username, correct password");
        LoginStatus status = LoginForm.login("wronguser", "binil_pass");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Credential mismatch", status.getErrorMsg());
    }

    @Test
    public void testCorrectUsernameAndEmptyPassword() {
        System.out.println("Test TC7: Correct username, empty password");
        LoginStatus status = LoginForm.login("binil", "");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Empty Password", status.getErrorMsg());
    }

    @Test
    public void testCorrectUsernameAndWrongPassword() {
        System.out.println("Test TC8: Correct username, wrong password");
        LoginStatus status = LoginForm.login("binil", "wrongpass");
        Assert.assertFalse(status.isLoginSuccess());
        Assert.assertEquals("Credential mismatch", status.getErrorMsg());
    }

    @Test
    public void testCorrectLoginAndEmptyValidationCode() {
        System.out.println("Test TC9: Correct login, empty validation code");
        LoginStatus status = LoginForm.login("binil", "binil_pass");
        Assert.assertTrue(status.isLoginSuccess());
        Assert.assertFalse(LoginForm.validateCode(""));
    }

    @Test
    public void testCorrectLoginAndWrongValidationCode() {
        System.out.println("Test TC10: Correct login, wrong validation code");
        LoginStatus status = LoginForm.login("binil", "binil_pass");
        Assert.assertTrue(status.isLoginSuccess());
        Assert.assertFalse(LoginForm.validateCode("abcdef"));
    }

    @Test
    public void testCorrectLoginAndCorrectValidationCode() {
        System.out.println("Test TC11: Correct login, correct validation code");
        LoginStatus status = LoginForm.login("binil", "binil_pass");
        Assert.assertTrue(status.isLoginSuccess());
        Assert.assertTrue(LoginForm.validateCode("123456"));
    }
}
