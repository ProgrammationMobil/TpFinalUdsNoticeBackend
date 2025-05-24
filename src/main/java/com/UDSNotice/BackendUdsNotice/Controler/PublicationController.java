package com.UDSNotice.BackendUdsNotice.Controler;

import com.UDSNotice.BackendUdsNotice.Exception.PublicationNotFoundException;
import com.UDSNotice.BackendUdsNotice.models.Publication;
import com.UDSNotice.BackendUdsNotice.Repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    final PublicationRepository publicationRepository;

    public PublicationController(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Publication>> getAllPublication () {
        return new ResponseEntity<>(publicationRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Publication> createPublication (@RequestBody Publication publication) {
        publication.setCreatedAt();
        publication.setModifiedAt();
        Publication publicreated = publicationRepository.save(publication);
        return new ResponseEntity<>(publicreated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> getPublicationById (@PathVariable Long id){
        Optional<Publication> publication = publicationRepository.findById(id);
        return publication.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseThrow(() -> new PublicationNotFoundException("Publication Not Found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publication> updatePublic (@PathVariable Long id, @RequestBody Publication publidetails) {
        Optional<Publication> publication = publicationRepository.findById(id);

        if (publication.isPresent()){
            Publication existedpubli = publication.get();
            existedpubli.setContent(publidetails.getContent());
            existedpubli.setTitle(publidetails.getTitle());
            existedpubli.setCover(publidetails.getCover());
            existedpubli.setDescription(publidetails.getDescription());
            existedpubli.setLike(publidetails.getLike());
            existedpubli.setUser(publidetails.getUser());
            existedpubli.setNotification(publidetails.getNotification());
            existedpubli.setDocumentList(publidetails.getDocumentList());
            existedpubli.setModifiedAt();

            Publication updatepubli = publicationRepository.save(existedpubli);
            return new ResponseEntity<>(updatepubli, HttpStatus.OK);
        }
        throw new PublicationNotFoundException("Publication Not Found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletepubli (@PathVariable Long id){
        Optional<Publication> publication = publicationRepository.findById(id);

        if (publication.isPresent()){
            publicationRepository.delete(publication.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new PublicationNotFoundException("Publication Not Found");
    }
}
