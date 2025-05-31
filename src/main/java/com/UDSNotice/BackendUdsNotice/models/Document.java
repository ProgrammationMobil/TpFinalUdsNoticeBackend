package com.UDSNotice.BackendUdsNotice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;

    @ManyToMany(mappedBy = "documentList",fetch = FetchType.LAZY)
    private Set<Publication> publicationList = new HashSet<>();

    public void addPublication (Publication publication) {
        this.getPublicationList().add(publication);
    }
}
