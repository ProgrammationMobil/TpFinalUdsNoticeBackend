package com.UDSNotice.BackendUdsNotice.Repository;

import com.UDSNotice.BackendUdsNotice.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
