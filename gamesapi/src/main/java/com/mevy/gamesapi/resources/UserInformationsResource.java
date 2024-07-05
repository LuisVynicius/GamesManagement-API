package com.mevy.gamesapi.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mevy.gamesapi.entities.UserInformations;
import com.mevy.gamesapi.entities.dtos.UserInformationsUpdateDTO;
import com.mevy.gamesapi.resources.interfaces.UserInformationResourceInterfaceDoc;
import com.mevy.gamesapi.services.UserInformationsService;

@RestController
@RequestMapping("/userInformations")
public class UserInformationsResource implements UserInformationResourceInterfaceDoc{
    
    @Autowired
    private UserInformationsService userInformationsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserInformations>> findAll() {
        List<UserInformations> userinformations = userInformationsService.findAll();
        return ResponseEntity.ok().body(userinformations);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    public ResponseEntity<UserInformations> getCurrentUserInformations() {
        UserInformations userInformations = userInformationsService.getCurrentUserInformations();
        return ResponseEntity.ok().body(userInformations);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserInformations> findById(
            @PathVariable
            Long id
    ) {
        UserInformations userInformations = userInformationsService.findById(id);
        return ResponseEntity.ok().body(userInformations);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/current")
    public ResponseEntity<Void> updateCurrentUserInformations(
            @RequestBody
            UserInformationsUpdateDTO userInformationsUpdateDTO
    ) {
        UserInformations userInformations = userInformationsService.fromDTO(userInformationsUpdateDTO);
        userInformationsService.updateCurrentUserInformations(userInformations);
        return ResponseEntity.noContent().build();
    }

}
