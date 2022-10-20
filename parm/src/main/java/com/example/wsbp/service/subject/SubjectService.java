package com.example.wsbp.service.subject;

import com.example.wsbp.domain.models.subject.ISubjectRepository;
import com.example.wsbp.domain.models.subject.Subject;
import com.example.wsbp.infrastructure.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements ISubjectService {

    private final ISubjectRepository subjectRepository;

    @Autowired
    public SubjectService(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<String> find() {
        return subjectRepository.find();
    }

    @Override
    public Subject findForName(final String name) {
        return subjectRepository.findForName(name);
    }
}
