package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ua.dgma.electronicDeansOffice.exceptions.people.ExceptionData;
import ua.dgma.electronicDeansOffice.exceptions.people.NotFoundException;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.repositories.StudentGroupRepository;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.utill.validators.StudentGroupValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;
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
        return studentGroupRepository.getByName(name).orElseThrow(() -> new NotFoundException(new ExceptionData<String>(className, "name", name)));
    }

    @Override
    public List<StudentGroup> findAllWithPaginationOrWithout(Integer page, Integer peoplePerPage) {
        if(checkPaginationParameters(page, peoplePerPage))
            return studentGroupRepository.findAll();
        else
            return studentGroupRepository.findAll(PageRequest.of(page, peoplePerPage)).getContent();
    }

    @Override
    public void registerNew(StudentGroup studentGroup, BindingResult bindingResult) {
        validateStudentGroup(studentGroup, bindingResult);
        studentGroupRepository.save(studentGroup);
    }

    @Override
    public void updateByName(String name, StudentGroup updatedStudentGroup, BindingResult bindingResult) {
        checkExistsWithSuchName(name);
        validateStudentGroup(updatedStudentGroup, bindingResult);

//        updatedStudentGroup.setStudents();

        studentGroupRepository.save(updatedStudentGroup);
    }

    @Override
    public void deleteByName(String name) {
        checkExistsWithSuchName(name);
        studentGroupRepository.deleteByName(name);
    }

    @Override
    public void validateStudentGroup(StudentGroup studentGroup, BindingResult bindingResult) {
        studentGroupValidator.validate(studentGroup, bindingResult);
        if(bindingResult.hasErrors())
            returnErrorsToClient(bindingResult);
    }

    @Override
    public void checkExistsWithSuchName(String name) {
        if(!studentGroupRepository.existsByName(name)) throw new NotFoundException(new ExceptionData<String>(className, "name", name));
    }
}
