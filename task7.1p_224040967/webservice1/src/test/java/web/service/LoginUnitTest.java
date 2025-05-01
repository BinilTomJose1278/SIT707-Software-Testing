package web.service;

import org.junit.Assert;
import org.junit.Test;

public class LoginUnitTest {

    @Test
    public void testValidLogin() {
        boolean result = LoginService.login("ahsan", "ahsan_pass", "2000-01-01");
        Assert.assertTrue(result);
    }

    @Test
    public void testWrongUsername() {
        boolean result = LoginService.login("wrong_user", "ahsan_pass", "2000-01-01");
        Assert.assertFalse(result);
    }

    @Test
    public void testWrongPassword() {
        boolean result = LoginService.login("ahsan", "wrong_pass", "2000-01-01");
        Assert.assertFalse(result);
    }

    @Test
    public void testWrongDOB() {
        boolean result = LoginService.login("ahsan", "ahsan_pass", "1990-01-01");
        Assert.assertFalse(result);
    }

    @Test
    public void testEmptyFields() {
        boolean result = LoginService.login("", "", "");
        Assert.assertFalse(result);
    }

    @Test
    public void testNullValues() {
        boolean result = LoginService.login(null, null, null);
        Assert.assertFalse(result);
    }
}
