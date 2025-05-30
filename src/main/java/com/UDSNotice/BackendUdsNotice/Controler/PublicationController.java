package com.UDSNotice.BackendUdsNotice.Controler;

import com.UDSNotice.BackendUdsNotice.DTO.PublicationRequest;
import com.UDSNotice.BackendUdsNotice.Exception.PublicationNotFoundException;
import com.UDSNotice.BackendUdsNotice.Services.PublicationService;
import com.UDSNotice.BackendUdsNotice.models.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public List<Publication> getAllPublication () {
        return publicationService.recuptout();
    }

    @PostMapping
    public ResponseEntity<Publication> createPublication (@RequestBody PublicationRequest publicationRequest) {
        Publication publicreated = publicationService.creerPublication(publicationRequest);
        return new ResponseEntity<>(publicreated, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> getPublicationById (@PathVariable Long id){
        return publicationService.recupparid(id).map(ResponseEntity::ok).orElseThrow(()-> new PublicationNotFoundException("Publication Not Found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publication> updatePublic (@PathVariable Long id, @RequestBody PublicationRequest publidetails) {
        try {
            return ResponseEntity.ok(publicationService.mettreajour(id, publidetails));
        } catch (PublicationNotFoundException e) {
            throw new PublicationNotFoundException("Publication non trouvee");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletepubli (@PathVariable Long id){
        publicationService.supprimerpubli(id);
        return ResponseEntity.noContent().build();
    }
}
