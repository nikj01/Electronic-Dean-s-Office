package ua.dgma.electronicDeansOffice.utill.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;

@Component
public class CheckExistenceStudentGroup {

    private static StudentGroupRepository repository;

    @Autowired
    public CheckExistenceStudentGroup(StudentGroupRepository repository) {
        this.repository = repository;
    }

    public static void checkExistenceStudentGroup(StudentGroup group, Errors errors){
        if(!repository.getByName(group.getName()).isPresent())
            errors.rejectValue("name", "Student group with NAME " + group.getName() + " does not exist!");
    }

}
