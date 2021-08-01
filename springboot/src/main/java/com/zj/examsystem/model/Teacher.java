package com.zj.examsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @NotBlank
    @Column(unique = true)
    private String account;

    @NonNull
    @NotBlank
    private String tchName;

    @NonNull
    @NotBlank
    private String password;

    private String major;

    private Integer sex;

    private String tel;

    private String email;

    @OneToOne(mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JsonBackReference(value = "tch_sub")
    private Subject sub;
}
