package com.lemonchat.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BasePost should only be used for reading without reply nesting.
 * This should be used for efficient direct post reading.
 */
@Entity
@Table(name="post")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // or another appropriate strategy
@DiscriminatorColumn(name = "post_type")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BasePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String topic;

    @Column(length = 10000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_post_id")
    private BasePost parentPost;
    
    public Long getParentPostId() {
        return parentPost != null ? parentPost.getPostId() : null;
    }
}
