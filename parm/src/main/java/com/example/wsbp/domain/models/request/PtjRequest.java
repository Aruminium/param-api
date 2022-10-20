package com.example.wsbp.domain.models.request;

import com.example.wsbp.domain.models.IPtjRequestEntity;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.domain.models.subject.Subject;
import com.example.wsbp.domain.models.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.Serializable;
import java.util.List;

public class PtjRequest implements IPtjRequestEntity, Serializable {
    final IPtjRequestEntity user;
    final IPtjRequestEntity subject;
    @JsonProperty("ptj_list")
    final List<PartTimeJob> partTimeJobs;

    public PtjRequest(final User user, final Subject subject, List<PartTimeJob> partTimeJobs) {
        this.user = user;
        this.subject = subject;
        this.partTimeJobs = partTimeJobs;
    }

    @Override
    public String mapperJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper.writeValueAsString(this);
    }

    public IPtjRequestEntity getUser() {
        return user;
    }

    public IPtjRequestEntity getSubject() {
        return subject;
    }

    public List<PartTimeJob> getPartTimeJobs() {
        return partTimeJobs;
    }
}
