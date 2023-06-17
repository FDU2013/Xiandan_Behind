package com.example.behind.domain;

import com.example.behind.common.domain_attributes.EvaluationResult;
import com.example.behind.common.domain_attributes.EvaluationState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "result")
    private EvaluationResult result;   //good or bad

    @Column(name = "user", nullable = false)
    private String userID;  //评价者

    @Lob
    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private EvaluationState state;

    @OneToOne
    @JoinColumn(name = "trade", nullable = false)
    private TradeRecord trade;

    public String getBeEvaluatedUserID(){
        String posterID = trade.getPost().getUser().getAccount().getUserID();
        if(userID.equals(posterID)){
            return trade.getApplicant();
        }
        return posterID;
    }
}
