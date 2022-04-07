package com.zj.examsystem.serviceImpl;

import com.zj.examsystem.model.SingleQuestion;
import com.zj.examsystem.model.Subject;
import com.zj.examsystem.repository.SingleQuesRepository;
import com.zj.examsystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SingleQuesServiceImpl1
{
    @Autowired
    private SingleQuesRepository singleQuesRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    public Page<SingleQuestion> findAll(Integer pageno, Integer size)
    {
        Pageable pageable = PageRequest.of(pageno, size);
        return singleQuesRepo.findAll(pageable);
    }

    public SingleQuestion addSingleQues(SingleQuestion sq)
    {
        return singleQuesRepo.save(sq);
    }

    @Transactional
    public void deleteSingleQues(Integer[] quesId)
    {
        List<Integer> quesIds = new ArrayList<>();
        for (int i = 0; i < quesId.length; i++) {
            quesIds.add(quesId[i]);
        }
        singleQuesRepo.deleteAllByIdInBatch(quesIds);
    }

    public SingleQuestion findById(Integer quesId)
    {
        return singleQuesRepo.findById(quesId).orElseThrow();
    }

    @Transactional
    public SingleQuestion modifySingleQues(SingleQuestion sq)
    {
        return singleQuesRepo.save(sq);
    }

    public List<SingleQuestion> findQuesBySubId(Integer subId)
    {
        Subject sub = subjectRepo.findById(subId).orElseThrow();
        return singleQuesRepo.findBySub(sub);
    }
}
