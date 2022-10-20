package com.example.wsbp.domain.models.subject;

import com.example.wsbp.domain.models.IPtjRequestEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.Serializable;

// Subject Entity
public class Subject implements Serializable, IPtjRequestEntity {
    @JsonIgnore
    private final int id;
    private final String name;
    @JsonProperty("teacher_name")
    private final String teacherName;

    public Subject(final int id, final String name, final String teacherName) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
    }

    @Override
    public String mapperJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
