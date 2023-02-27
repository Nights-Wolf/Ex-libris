package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.LibraryRepository;
import com.exlibris.exbliris.database.UserRepository;
import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.Users;
import com.exlibris.exbliris.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;

    @Autowired
    public LibraryServiceImpl(LibraryRepository libraryRepository, UserRepository userRepository) {
        this.libraryRepository = libraryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createLibrary(Library library) {
        libraryRepository.save(library);
    }

    @Override
    public Library getLibrary(Long id) {
        Optional<Library> getLibrary = libraryRepository.findById(id);

        if (getLibrary.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        return getLibrary.get();
    }

    @Override
    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    @Override
    public List<Library> getByUser(Long id) {
        Optional<Users> user = userRepository.findById(id);

        return libraryRepository.getByUsersId(user.get());
    }

    @Override
    public void editLibrary(Long id, Library library) {
        Optional<Library> libraryToEdit = libraryRepository.findById(id).map(editingLibrary -> library);

        if (libraryToEdit.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        libraryRepository.save(libraryToEdit.get());
    }

    @Override
    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }
}
