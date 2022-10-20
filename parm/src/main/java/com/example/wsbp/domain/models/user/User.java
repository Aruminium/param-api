package com.example.wsbp.domain.models.user;

import com.example.wsbp.domain.models.IPtjRequestEntity;
import com.example.wsbp.domain.models.user.value.Name;
import com.example.wsbp.domain.models.user.value.Pass;
import com.example.wsbp.domain.models.user.value.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.Serializable;

// User Entity
// Wicketの Model に使うかもしれないクラスは、 implements Serializable をつける
// SerializableはDAOと呼ばれるデータベース等ののデータストアを操作するオブジェクトのクラスである
public class User implements Serializable, IPtjRequestEntity {
    @JsonProperty("student_number")
    private final String studentNumber;
    @JsonProperty("name")
    private final Name name;
    @JsonIgnore
    private Pass pass;
    @JsonProperty("user_type")
    private final UserType userType;

    public User(final String studentNumber, final String name, final String pass) {
        this.studentNumber = studentNumber;
        this.name = new Name(name);
        this.pass = new Pass(pass);
        this.userType = UserType.whichUserType(studentNumber);
    }

    @Override
    public String mapperJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(this);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getName() {
        return name.getName();
    }

    public String getPass() {
        return pass.getPass();
    }

    public void setPass(String pass) {
        this.pass = new Pass(pass);
    }

    public String getUserType() {
        return userType.name();
    }
}
