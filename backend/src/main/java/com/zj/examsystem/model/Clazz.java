package com.zj.examsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(of = "clazzId")
public class Clazz
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clazzId;

    @NonNull
    private String clazzName;

    @NonNull
    private String major;

    // 接收student外键，不需要单独的列
    @OneToMany(mappedBy = "clazz", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JsonBackReference(value = "studentSet")
    private Set<Student> studentSet = new HashSet<>();

    @OneToMany(mappedBy = "testClazz", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JsonBackReference(value = "testSet")
    private Set<Test> testSet = new HashSet<>();
}
