package com.UDSNotice.BackendUdsNotice.DTO;

import com.UDSNotice.BackendUdsNotice.models.Document;

import java.util.Set;

public class PublicationRequest {

    public Long id;
    public String title;
    public String content;
    public String cover;
    public String description;
    public int like;
    public Long user_id;
    public Long notification_id;
    public Set<Document> documentSet;

}
