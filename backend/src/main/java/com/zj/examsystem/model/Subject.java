package com.zj.examsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "subId")
@Entity
public class Subject
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subId;

    @NonNull
    private String subName;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "sub", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "singleQuestions")
    private Set<SingleQuestion> singleQuestions = new HashSet<>();

    @OneToMany(mappedBy = "sub", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JsonBackReference(value = "tests")
    private Set<Test> tests = new HashSet<>();
}
