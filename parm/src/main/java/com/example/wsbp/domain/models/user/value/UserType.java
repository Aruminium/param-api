package com.example.wsbp.domain.models.user.value;

import com.example.wsbp.AdminProperties;

import java.util.EnumSet;
import java.util.regex.Pattern;


public enum UserType {

    SA("^b\\d+$"),
    TA("^[md]\\d+$"),
    ADMIN(String.format("^%s$", AdminProperties.STUDENT_NUMBER));

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public static UserType whichUserType(final String studentNumber) {
        EnumSet<UserType> set = EnumSet.allOf(UserType.class);
        for (UserType authUserType : set) {
            if (Pattern.matches(authUserType.userType, studentNumber)) {
                return authUserType;
            }
        }
        throw new IllegalArgumentException("UserTypeを付与できません");
    }

}
