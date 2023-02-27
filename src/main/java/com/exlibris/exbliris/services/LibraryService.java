package com.exlibris.exbliris.services;

import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.UserResponse;

import java.util.List;

public interface LibraryService {

    void createLibrary(Library library);

    Library getLibrary(Long id);

    List<Library> getAllLibraries();

    List<Library> getByUser(Long id);

    void editLibrary(Long id, Library library);

    void deleteLibrary(Long id);
}
