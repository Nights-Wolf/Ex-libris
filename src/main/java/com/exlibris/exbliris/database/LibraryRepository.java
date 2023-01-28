package com.exlibris.exbliris.database;

import com.exlibris.exbliris.models.Library;
import com.exlibris.exbliris.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

    List<Library> getByUserId(User user);
}
