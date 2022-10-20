package com.example.wsbp;

import java.util.ResourceBundle;

public class AdminProperties {
    private static final ResourceBundle rb = ResourceBundle.getBundle("application");
    public final static String STUDENT_NUMBER = rb.getString("admin.studentNumber");
    public final static String PASSWORD = rb.getString("admin.password");
}
