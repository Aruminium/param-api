package com.example.wsbp.service.ptj;

import com.example.wsbp.domain.models.ptj.IPartTimeJobUpdateRepository;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.infrastructure.repository.ptj.PartTimeJobUpdateRepository;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
public class PartTimeJobUpdateService implements IPartTimeJobUpdateService {
    private final IPartTimeJobUpdateRepository repository;

    @Autowired
    public PartTimeJobUpdateService(final PartTimeJobUpdateRepository PartTimeJobRepository) {
        this.repository = PartTimeJobRepository;
    }

    @Override
    public void insert(final String userStudentNumber, final int subjectId, final Date ptjDate, final Time startTime, final Time finishTime, final int breakTimeMinutes, final float officeHours, final String duties) {
        repository.insert(userStudentNumber, subjectId, ptjDate, startTime, finishTime, breakTimeMinutes, officeHours, duties);
    }

    @Override
    public void update(final PartTimeJob ptjRequest) {
        repository.update(ptjRequest);
    }

    @Override
    public void delete(PartTimeJob ptjRequest) {
        repository.delete(ptjRequest);
    }

    @Override
    public void deletes(final List<PartTimeJob> ptjRequests) {
        repository.deletes(ptjRequests);
    }

}
