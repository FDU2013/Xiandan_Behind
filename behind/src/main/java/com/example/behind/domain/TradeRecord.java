package com.example.behind.domain;

import com.example.behind.common.domain_attributes.RecordState;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trade_record")
public class TradeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "post", nullable = false)
    private XDanPost post;

    @Column(name = "applicant", nullable = false)
    private String applicant;

    @Column(name = "post_msg")
    private String postMsg;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private RecordState state;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trade")
//    private Evaluation applicantEvaluation;
//
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trade")
//    private Evaluation posterEvaluation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deal_time")
    private Date dealTime;
}
