package com.example.wsbp.domain.models.ptj;

import com.example.wsbp.domain.models.IPtjRequestEntity;
import com.example.wsbp.domain.models.ptj.value.BreakTimeMinutes;
import com.example.wsbp.domain.models.ptj.value.FinishTime;
import com.example.wsbp.domain.models.ptj.value.OfficeHours;
import com.example.wsbp.domain.models.ptj.value.StartTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

// PtjRequest Entity
// PTJ_REQUESTS テーブルのデータを入れるクラス
// Wicketの Model に使うかもしれないクラスは、 implements Serializable をつける
public class PartTimeJob implements Serializable, IPtjRequestEntity {
    @JsonIgnore
    private final int id;
    @JsonIgnore
    private final String studentNumber;
    @JsonIgnore
    private final int subjectId;
    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
    private final Date date;

    @JsonUnwrapped
    private final StartTime startTime;
    @JsonUnwrapped
    private final FinishTime finishTime;
    @JsonProperty("break_time_minutes")
    private final BreakTimeMinutes breakTimeMinutes;
    @JsonProperty("office_hours")
    private final OfficeHours officeHours;
    @JsonProperty("duties")
    private final String duties;

    public PartTimeJob(final int id, final String studentNumber, final int subjectId, final Date ptjDate, final Time startTime, final Time finishTime, final int breakTimeMinutes, final float officeHours, final String duties) {
        this.id = id;
        this.studentNumber = studentNumber;
        this.subjectId = subjectId;
        this.date = ptjDate;
        this.startTime = new StartTime(startTime);
        this.finishTime = new FinishTime(finishTime);
        this.breakTimeMinutes = new BreakTimeMinutes(breakTimeMinutes);
        this.officeHours = new OfficeHours(officeHours);
        this.duties = duties;
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

    public String getStudentNumber() {
        return this.studentNumber;
    }

    public int getSubjectId() {
        return this.subjectId;
    }

    @JsonIgnore
    public Date getDate() {
        return this.date;
    }


    public StartTime getStartTime() {
        return this.startTime;
    }


    public FinishTime getFinishTime() {
        return finishTime;
    }

    public int getBreakTimeMinutes() {
        return breakTimeMinutes.getBreakTimeMinutes();
    }

    public float getOfficeHours() {
        return officeHours.getOfficeHours();
    }

    public String getDuties() {
        return duties;
    }
}
