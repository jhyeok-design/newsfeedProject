package com.example.project.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "follows",
        uniqueConstraints = {@UniqueConstraint(name = "uk_follower_following", columnNames = {"followers_id", "followings_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "followers_id",nullable = false)
    private User followers;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "followings_id",nullable = false)
    private User followings  ;


    public Follow(User followers, User followings) {
        this.followers = followers;
        this.followings = followings;

    }

}
