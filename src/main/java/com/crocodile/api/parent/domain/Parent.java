package com.crocodile.api.parent.domain;

import com.crocodile.api.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Parent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String parentName;

    @Column(nullable = false)
    private String email;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
//    private List<Lesson> categoryShopMapList = new ArrayList<>();
}
