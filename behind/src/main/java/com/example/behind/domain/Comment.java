package com.example.behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "post")
    private XDanPost post;

    @ManyToOne
    private User user;

    @Column(name = "floor")
    private Integer floor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private Date time;

    @PrePersist
    public void prePersist() {
        if (this.floor == 0 && this.post != null) {
            // 计算楼层数
            int maxFloor = this.post.getComments().stream()
                    .mapToInt(Comment::getFloor)
                    .max()
                    .orElse(0);
            this.floor = maxFloor + 1;
        }
    }
}
