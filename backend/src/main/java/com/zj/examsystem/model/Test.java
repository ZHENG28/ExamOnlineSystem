package com.zj.examsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "testId")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Test
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    // 测验标题
    @NonNull
    private String testName;

    // 测验简介
    private String description;

    // 测验时长
    private Integer examDure;

    // 测验开始日期
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginDate;

    // 结束测验日期
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "test_clazz")
    private Clazz testClazz;

    // 测验总分
    private Integer totalScore;

    // 重复测验次数
    @NonNull
    private Integer examTime;

    // 题号（以，分隔）
    private String quesArr;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "sub_id", nullable = false)
    private Subject sub;

    @OneToMany(mappedBy = "test", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "histories")
    private Set<TestHistory> histories = new HashSet<>();
}
