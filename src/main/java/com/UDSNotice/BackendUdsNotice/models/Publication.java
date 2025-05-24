package com.UDSNotice.BackendUdsNotice.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String cover;
    private String description;
    private int like;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne(mappedBy = "publication")
    private Notification notification;

    @ManyToMany
    @JoinTable(
            name = "publication_document",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    private List<Document> documentList;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public void setModifiedAt() {
        this.modifiedAt = LocalDateTime.now();
    }
}
