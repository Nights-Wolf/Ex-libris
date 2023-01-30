package com.exlibris.exbliris.api;

import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/library")
public class LibraryRestController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryRestController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    private ResponseEntity<Object> createLibrary(@RequestBody Library library) {
        try {
            libraryService.createLibrary(library);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getLibrary(@PathVariable("id") Long id) {
        try {
            Library library = libraryService.getLibrary(id);
            if (library == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(library, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/all")
    private ResponseEntity<Object> getAllLibraries() {
        try {
            List<Library> libraries = libraryService.getAllLibraries();

            return new ResponseEntity<>(libraries, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @GetMapping("/getByUser/{id}")
    private ResponseEntity<Object> getByUser(@PathVariable("id") Long id) {
        try {
            List<Library> libraries = libraryService.getByUser(id);

            return new ResponseEntity<>(libraries, HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> editLibrary(@PathVariable("id") Long id, @RequestBody Library library) {
        try {
            libraryService.editLibrary(id, library);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> deleteLibrary(@PathVariable("id") Long id) {
        try {
            libraryService.deleteLibrary(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (HttpClientErrorException e) {
            HashMap error = new HashMap<>();
            error.put("Message", e.getMessage());
            error.put("Status", e.getStatusCode());
            error.put("Cause", e.getCause());

            return new ResponseEntity<>(error, e.getStatusCode());
        }
    }
}
