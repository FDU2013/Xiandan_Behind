package com.example.behind.domain;

import com.example.behind.common.domain_attributes.RoleType;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account", length = 36, nullable = false, unique = true)
    private String userID;

    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "stu_num", length = 11, nullable = false, unique = true)
    private String stuNum;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleType role;

}
