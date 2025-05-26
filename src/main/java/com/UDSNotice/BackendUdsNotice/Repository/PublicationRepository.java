package com.UDSNotice.BackendUdsNotice.Repository;

import com.UDSNotice.BackendUdsNotice.models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
