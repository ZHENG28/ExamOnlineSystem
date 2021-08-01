package com.zj.examsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(of = "stuId")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(unique = true)
    private String account;

    @NonNull
    private String stuName;

    @NonNull
    private String password;

    private Integer sex;

    // 外键到clazz表，需要单独的列
    @ManyToOne
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    @OneToMany(mappedBy = "stu", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "histories")
    private Set<TestHistory> histories = new HashSet<>();
}
