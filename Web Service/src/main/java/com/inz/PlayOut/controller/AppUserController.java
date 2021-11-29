package com.inz.PlayOut.controller;

import com.inz.PlayOut.model.entites.AppUser;
import com.inz.PlayOut.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appUser")
public class AppUserController implements CRUDController<AppUser>{

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping()
    @Override
    public ResponseEntity<Object> findAll() {
        List<AppUser> found = appUserService.findAll();
        if (found.isEmpty()){
            return new ResponseEntity<>("Repository is empty!", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id){
        Optional<AppUser> found = appUserService.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>("Bad id", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping(path = "/username/{username}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> checkUsername(@PathVariable String username){
        Optional<AppUser> found = appUserService.findByUsername(username);
        if(found.isPresent()){
            return new ResponseEntity<>("User exist", HttpStatus.FOUND);
        } else
            return new ResponseEntity<>("Username available", HttpStatus.NO_CONTENT);
    }

    @Override
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> add(@RequestBody AppUser object) {
        if (object != null) {
            appUserService.save(object);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<AppUser> found = appUserService.findById(id);
        if (found.isPresent()) {
            appUserService.delete(id);
            return new ResponseEntity<>(found,HttpStatus.OK);
        } else
            return new ResponseEntity<>("Not found object to delete!", HttpStatus.NOT_FOUND);
    }

}
