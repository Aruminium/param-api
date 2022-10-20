package com.example.wsbp.service.ptj;

import com.example.wsbp.domain.models.ptj.IPartTimeJobRepository;
import com.example.wsbp.domain.models.ptj.PartTimeJob;
import com.example.wsbp.infrastructure.repository.ptj.PartTimeJobRepository;
import com.example.wsbp.infrastructure.service.ptj.IPartTimeJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * レポジトリーを呼び出すためのクラス（サービス）
 */

@Service
public class PartTimeJobService implements IPartTimeJobService {

    private final IPartTimeJobRepository partTimeJobRepository;

    @Autowired
    public PartTimeJobService(final PartTimeJobRepository PartTimeJobRepository) {
        this.partTimeJobRepository = PartTimeJobRepository;
    }

    @Override
    public List<PartTimeJob> find(final String studentNumber, final Date date) {
        return partTimeJobRepository.find(studentNumber, date);
    }

    @Override
    public PartTimeJob find(final int id, final String studentNumber, final Date date) {
        return partTimeJobRepository.find(id, studentNumber, date);
    }

    @Override
    public List<PartTimeJob> find(final String studentNumber, final Date date, final String subjectName) {
        return partTimeJobRepository.find(studentNumber, date, subjectName);
    }
}
