package com.UDSNotice.BackendUdsNotice.Services;

import com.UDSNotice.BackendUdsNotice.DTO.PublicationRequest;
import com.UDSNotice.BackendUdsNotice.Exception.NotificationNotFound;
import com.UDSNotice.BackendUdsNotice.Exception.PublicationNotFoundException;
import com.UDSNotice.BackendUdsNotice.Exception.UserNotFound;
import com.UDSNotice.BackendUdsNotice.Repository.DocumentRepository;
import com.UDSNotice.BackendUdsNotice.Repository.NotificationRepository;
import com.UDSNotice.BackendUdsNotice.Repository.PublicationRepository;
import com.UDSNotice.BackendUdsNotice.Repository.UserRepository;
import com.UDSNotice.BackendUdsNotice.models.Document;
import com.UDSNotice.BackendUdsNotice.models.Notification;
import com.UDSNotice.BackendUdsNotice.models.Publication;
import com.UDSNotice.BackendUdsNotice.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final DocumentRepository documentRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public PublicationService(PublicationRepository publicationRepository, DocumentRepository documentRepository, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.publicationRepository = publicationRepository;
        this.documentRepository = documentRepository;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Publication creerPublication (PublicationRequest publicationRequest) {

        Optional<User> optionaluser = userRepository.findById(publicationRequest.user_id);
        User user = optionaluser.orElseThrow(() -> new UserNotFound("Utilisateur inexistant, impossible de  créer une nouvelle publication"));
        Optional<Notification> optionalNotification = notificationRepository.findById(publicationRequest.notification_id);
        Notification notification = optionalNotification.orElseThrow(() -> new NotificationNotFound("Notification inexistante, impossible de créer une nouvelle publication"));

        Publication publication = new Publication();

        publication.setContent(publicationRequest.content);
        publication.setTitle(publicationRequest.title);
        publication.setCover(publicationRequest.cover);
        publication.setDescription(publicationRequest.description);
        publication.setLike(publicationRequest.like);
        publication.setUser(user);
        publication.setNotification(notification);
        publication.setDocumentList(publicationRequest.documentSet
                .stream()
                .map(document -> {
                    Document ddocument = document;
                    if (ddocument.getId() > 0) {
                        ddocument = documentRepository.findById(ddocument.getId()).orElse(new Document());
                    }
                    ddocument.addPublication(publication);
                    return ddocument;
                }).collect(Collectors.toSet()));

        publication.setModifiedAt();
        publication.setCreatedAt();
        return publicationRepository.save(publication);
    }

    public List<Publication> recuptout () {
        return publicationRepository.findAll();
    }

    public Optional<Publication> recupparid (Long id) {
        return publicationRepository.findById(id);
    }

    public Publication mettreajour (Long id, PublicationRequest publicationRequest) {

        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new PublicationNotFoundException("Publication non trouvee"));

        Optional<Notification> optionalNotification = notificationRepository.findById(publicationRequest.notification_id);
        Notification notification = optionalNotification.orElseThrow(() -> new NotificationNotFound("Notification inexistante, impossible de créer une nouvelle publication"));


        publication.setContent(publicationRequest.content);
        publication.setTitle(publicationRequest.title);
        publication.setCover(publicationRequest.cover);
        publication.setDescription(publicationRequest.description);
        publication.setLike(publicationRequest.like);
        publication.setNotification(notification);

        publication.setDocumentList(publicationRequest.documentSet
                .stream()
                .map(document -> {
                    Document ddocument = document;
                    if (ddocument.getId() > 0) {
                        ddocument = documentRepository.findById(ddocument.getId()).orElse(new Document());
                    }
                    else{
                        throw new RuntimeException("Identifiant non associé à un compte");
                    }
                    ddocument.addPublication(publication);
                    return ddocument;
                }).collect(Collectors.toSet()));

        publication.setModifiedAt();

        return publicationRepository.save(publication);
    }

    public void supprimerpubli (Long id) {
        publicationRepository.deleteById(id);
    }
}
