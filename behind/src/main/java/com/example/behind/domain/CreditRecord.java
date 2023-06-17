package com.example.behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_record")
public class CreditRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "appraiser", nullable = false)
    private String appraiser;

    @Column(name = "target_user", nullable = false)
    private String targetUser;

    @Column(name = "add_num", nullable = false)
    private Integer add;

    @Column(name = "sub_num", nullable = false)
    private Integer sub;

}
