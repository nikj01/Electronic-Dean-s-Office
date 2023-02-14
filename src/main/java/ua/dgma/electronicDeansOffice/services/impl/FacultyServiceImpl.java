package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FacultyServiceImpl {

    private final FacultyRepository repository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty findOne(String name){
        Optional<Faculty> foundFaculty = repository.findById(name);
        System.out.println(foundFaculty);
        return foundFaculty.orElse(null);
    }

    public String getName(String name){
        Faculty faculty = findOne(name);
        String nameOfFaculty = faculty.getName();
        return nameOfFaculty;
    }
}
