package com.zj.examsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class SingleQuestion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quesId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "sub_id")
    private Subject sub;

    @NonNull
    private String quesTitle;

    private String optA;

    private String optB;

    private String optC;

    private String optD;

    private String answer;
}
