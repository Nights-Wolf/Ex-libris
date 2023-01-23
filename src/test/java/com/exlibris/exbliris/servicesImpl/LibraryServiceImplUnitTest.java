package com.exlibris.exbliris.servicesImpl;

import com.exlibris.exbliris.database.LibraryRepository;
import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.User;
import com.exlibris.exbliris.services.LibraryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LibraryServiceImplUnitTest {

    @Mock
    private LibraryService libraryService;

    @Mock
    private LibraryRepository libraryRepository;

    private User user = new User(1L, "NightsWolf", "123", "dawi@wp.pl", "Dawid", "Całkowksi");
    private Library library = new Library(1L, "Bokshelf", user, "kaowpdkawd", new Date());

    @BeforeEach
    void setUp() {
        libraryService = new LibraryServiceImpl(libraryRepository);
    }

    @Test
    void shouldCreateLibrary() {
        Mockito.when(libraryRepository.save(library)).thenReturn(library);

        libraryService.createLibrary(library);

        ArgumentCaptor<Library> libraryArgumentCaptor = ArgumentCaptor.forClass(Library.class);
        Mockito.verify(libraryRepository).save(libraryArgumentCaptor.capture());

        Library capturedLibrary = libraryArgumentCaptor.getValue();

        Assertions.assertEquals(library, capturedLibrary);
    }

    @Test
    void shouldGetLibrary() {
        Mockito.when(libraryRepository.findById(library.getId())).thenReturn(Optional.ofNullable(library));

        Library response = libraryService.getLibrary(library.getId());

        Assertions.assertEquals(library, response);
    }

    @Test
    void shouldThrowLibraryNotFound() {
        Mockito.when(libraryRepository.findById(library.getId())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> libraryService.getLibrary(library.getId()));
    }

    @Test
    void getAllLibraries() {
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(library);

        Mockito.when(libraryRepository.findAll()).thenReturn(libraryList);

        List<Library> response = libraryService.getAllLibraries();

        Assertions.assertEquals(libraryList, response);
    }

    @Test
    void shouldEditLibrary() {
        Library editedLibrary = new Library(1L, "Bookshelf", user, "kaowpdkawd", new Date());

        Mockito.when(libraryRepository.findById(library.getId())).thenReturn(Optional.ofNullable(library));

        libraryService.editLibrary(library.getId(), editedLibrary);
        ArgumentCaptor<Library> libraryArgumentCaptor = ArgumentCaptor.forClass(Library.class);
        Mockito.verify(libraryRepository).save(libraryArgumentCaptor.capture());

        Library capturedLibrary = libraryArgumentCaptor.getValue();

        Assertions.assertEquals(editedLibrary, capturedLibrary);
    }

    @Test
    void shouldThrowLibraryToEditNotFound() {
        Library editedLibrary = new Library(1L, "Bookshelf", user, "kaowpdkawd", new Date());

        Mockito.when(libraryRepository.findById(library.getId())).thenThrow(HttpClientErrorException.class);

        Assertions.assertThrows(HttpClientErrorException.class, () -> libraryService.editLibrary(library.getId(), editedLibrary));
    }

    @Test
    void deleteLibrary() {
        libraryService.deleteLibrary(library.getId());

        Mockito.verify(libraryRepository).deleteById(library.getId());
    }
}