package edu.deakin;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SimpleLoginFormTest {

    private SimpleLoginForm form;

    @Before
    public void setUp() {
        form = new SimpleLoginForm();
    }

    @Test
    public void testValidLogin() {
        assertEquals("success", form.login("admin", "pass123"));
    }

    @Test
    public void testValidUsernameInvalidPassword() {
        assertEquals("failure", form.login("admin", "wrongpass"));
    }

    @Test
    public void testInvalidUsernameValidPassword() {
        assertEquals("failure", form.login("wronguser", "pass123"));
    }

    @Test
    public void testInvalidUsernameAndPassword() {
        assertEquals("failure", form.login("wronguser", "wrongpass"));
    }

    @Test
    public void testNullUsername() {
        assertEquals("error", form.login(null, "pass123"));
    }

    @Test
    public void testNullPassword() {
        assertEquals("error", form.login("admin", null));
    }

    @Test
    public void testNullUsernameAndPassword() {
        assertEquals("error", form.login(null, null));
    }
    
    //Task2 
    
 // 1. Username with trailing spaces
    @Test
    public void testUsernameWithTrailingSpaces() {
        assertEquals("failure", form.login("admin ", "pass123"));
    }

    // 2. Password with uppercase characters (case sensitivity check)
    @Test
    public void testPasswordCaseSensitivity() {
        assertEquals("failure", form.login("admin", "Pass123"));
    }

    // 3. SQL Injection-like input
    @Test
    public void testSQLInjectionLikeInput() {
        assertEquals("failure", form.login("admin' OR '1'='1", "pass123"));
    }

    // 4. Special characters in username
    @Test
    public void testUsernameWithSpecialCharacters() {
        assertEquals("failure", form.login("admin!", "pass123"));
    }

    // 5. Extremely long input values
    @Test
    public void testLongUsernameAndPassword() {
        String longUsername = "admin".repeat(100);
        String longPassword = "pass123".repeat(100);
        assertEquals("failure", form.login(longUsername, longPassword));
    }

}
