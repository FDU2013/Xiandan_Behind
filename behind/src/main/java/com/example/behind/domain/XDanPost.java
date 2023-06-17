package com.example.behind.domain;

import com.example.behind.common.domain_attributes.CampusEnum;
import com.example.behind.common.domain_attributes.GoodsType;
import com.example.behind.common.domain_attributes.PostState;
import com.example.behind.common.domain_attributes.TradePostType;
import javax.persistence.*;

import com.example.behind.domain.dto.PostUpdateData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "xdan_post")
public class XDanPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    /*可搜索内容--start--*/
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition="TEXT", nullable = false)
    private String content;
    /*可搜索内容--end--*/

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    @JoinColumn(name = "record")
    private TradeRecord record;

    /*搜索时可筛选字段--start--*/
    @Column(name = "price", nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "campus")
    private CampusEnum address;

    @Enumerated(EnumType.STRING)
    @Column(name = "goods_type")
    private GoodsType goodsType;


    @OneToMany(cascade = CascadeType.ALL)
    private List<TagBind> tags;
    /*搜索时可筛选字段--end--*/

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private PostState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TradePostType type;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public void update(PostUpdateData updateData){
        if(updateData.getTitle() != null) title = updateData.getTitle();
        if(updateData.getContent() != null) content = updateData.getContent();
        if(updateData.getPrice() != null) price = updateData.getPrice();
        if(updateData.getGoodsType() != null) goodsType = updateData.getGoodsType();
        if(updateData.getAddress() != null) address = updateData.getAddress();
    }
}
