package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.utill.ValidationData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.validators.StudentGroupValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkExistsWithSuchID;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.checkPaginationParameters;

@Service
@Transactional(readOnly = true)
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final StudentGroupValidator studentGroupValidator;
    private final ExceptionData exceptionData;
    private String className;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository,
                                   StudentGroupValidator studentGroupValidator,
                                   ExceptionData exceptionData) {
        this.studentGroupRepository = studentGroupRepository;
        this.studentGroupValidator = studentGroupValidator;
        this.exceptionData = exceptionData;
        className = StudentGroup.class.getSimpleName();
    }

    @Override
    public StudentGroup findByName(String name) {
        return studentGroupRepository.getByName(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    @Override
    public List<StudentGroup> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage) {
        if(checkPaginationParameters(page, peoplePerPage))
            return studentGroupRepository.findAll();
        else
            return studentGroupRepository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    @Transactional
    public void registerNew(StudentGroup studentGroup, BindingResult bindingResult) {
        validateObject(new ValidationData<>(studentGroupValidator, studentGroup, bindingResult));
        studentGroupRepository.save(studentGroup);
    }

    @Override
    @Transactional
    public void updateByName(String name, StudentGroup updatedStudentGroup, BindingResult bindingResult) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, studentGroupRepository));
        validateObject(new ValidationData<>(studentGroupValidator, updatedStudentGroup, bindingResult));

//        updatedStudentGroup.setStudents();

        studentGroupRepository.save(updatedStudentGroup);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchID(new CheckExistsByIdData<>(className, name, studentGroupRepository));
        studentGroupRepository.deleteByName(name);
    }
}
