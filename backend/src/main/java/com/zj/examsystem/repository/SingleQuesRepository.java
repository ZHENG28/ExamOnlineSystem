package com.zj.examsystem.repository;

import com.zj.examsystem.model.SingleQuestion;
import com.zj.examsystem.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SingleQuesRepository extends JpaRepository<SingleQuestion, Integer>, JpaSpecificationExecutor<SingleQuestion>
{
    List<SingleQuestion> findBySub(Subject sub);
}
