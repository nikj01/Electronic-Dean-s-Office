package ua.dgma.electronicDeansOffice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.models.Department;
import ua.dgma.electronicDeansOffice.models.Faculty;
import ua.dgma.electronicDeansOffice.models.StudentGroup;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.repositories.DepartmentRepository;
import ua.dgma.electronicDeansOffice.repositories.FacultyRepository;
import ua.dgma.electronicDeansOffice.services.impl.data.FindAllData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.RegisterDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.department.UpdateDepartmentData;
import ua.dgma.electronicDeansOffice.services.impl.data.person.RegisterPersonData;
import ua.dgma.electronicDeansOffice.services.interfaces.DepartmentService;
import ua.dgma.electronicDeansOffice.services.interfaces.PeopleService;
import ua.dgma.electronicDeansOffice.services.interfaces.StudentGroupService;
import ua.dgma.electronicDeansOffice.services.specifications.DepartmentSpecifications;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;
import ua.dgma.electronicDeansOffice.utill.validators.AbstractValidator;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import java.util.List;

import static ua.dgma.electronicDeansOffice.utill.ValidateObject.validateObject;
import static ua.dgma.electronicDeansOffice.utill.check.CheckMethods.*;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;
    private final PeopleService<Teacher> teacherService;
    private final StudentGroupService groupService;
    private final AbstractValidator departmentValidator;
    private final DepartmentSpecifications specifications;
    private String className;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 FacultyRepository facultyRepository,
                                 PeopleService<Teacher> teacherService,
                                 StudentGroupService groupService,
                                 AbstractValidator departmentValidator,
                                 DepartmentSpecifications specifications) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
        this.teacherService = teacherService;
        this.groupService = groupService;
        this.departmentValidator = departmentValidator;
        this.specifications = specifications;
        className = Department.class.getSimpleName();
    }


    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.getByNameContainingIgnoreCase(name).orElseThrow(() -> new NotFoundException(new ExceptionData<>(className, "name", name)));
    }

    public List<Department> findAllDepartments(FindAllData data) {
        if (checkPaginationParameters(data.getPage(), data.getObjectsPerPage()))
            return findAllWithSpec(getSpec(data));
        else
            return findAllWithSpecAndPagination(getSpec(data), data);
    }

    private List<Department> findAllWithSpec(Specification spec) {
        return departmentRepository.findAll(spec);
    }

    private List<Department> findAllWithSpecAndPagination(Specification spec, FindAllData data) {
        return departmentRepository.findAll(spec, PageRequest.of(data.getPage(), data.getObjectsPerPage())).getContent();
    }

    private Specification getSpec(FindAllData data) {
        return Specification.where(specifications.getDepartmentByFacultyCriteria(data.getFacultyName()).and(specifications.getObjectByDeletedCriteria(data.getDeleted())));
    }


    @Override
    @Transactional
    public void registerNew(RegisterDepartmentData data) {
        checkExistenceByNameBeforeRegistration(new CheckExistsByNameData<>(className, getDepartmentName(data), departmentRepository));
        validateObject(new DataForAbstractValidator(departmentValidator, data.getNewDepartment()));

        Department newDepartment = data.getNewDepartment();

        setFacultyInDepartment(newDepartment);

        saveDepartment(newDepartment);
        saveNewTeachers(getTeachers(newDepartment), data);
    }

    private String getDepartmentName(RegisterDepartmentData data) {
        return data.getNewDepartment().getName();
    }

    private void setFacultyInDepartment(Department department) {
        department.setFaculty(getExistingFaculty(department));
    }

    private Faculty getExistingFaculty(Department department) {
        return facultyRepository.getByName(getFacultyName(department)).get();
    }

    private String getFacultyName(Department department) {
        return department.getFaculty().getName();
    }

    private void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    private void saveNewTeachers(List<Teacher> teachers, RegisterDepartmentData data) {
        for (Teacher teacher : teachers)
            teacherService.registerNew(new RegisterPersonData<>(teacher, data.getBindingResult()));
    }

    private List<Teacher> getTeachers(Department department) {
        return department.getTeachers();
    }

    @Override
    @Transactional
    public void updateByName(UpdateDepartmentData data) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, data.getName(), departmentRepository));
        validateObject(new DataForAbstractValidator(departmentValidator, data.getUpdatedDepartment()));

        Department updatedDepartment = data.getUpdatedDepartment();
        setIdInDepartment(updatedDepartment, data);
        setFacultyInDepartment(updatedDepartment);
        setTeachersInDepartment(updatedDepartment, data);
        setStudentGroupsInDepartment(updatedDepartment, data);

        saveDepartment(updatedDepartment);
    }

    private void setIdInDepartment(Department department, UpdateDepartmentData data) {
        department.setId(getExistingDepartment(data).getId());
    }

    private Department getExistingDepartment(UpdateDepartmentData data) {
        return departmentRepository.getByName(getDepartmentName(data)).get();
    }

    private String getDepartmentName(UpdateDepartmentData data) {
        return data.getName();
    }

    private void setTeachersInDepartment(Department department, UpdateDepartmentData data) {
        department.setTeachers(getExistingTeachers(data));
    }

    private List<Teacher> getExistingTeachers(UpdateDepartmentData data) {
        return getExistingDepartment(data).getTeachers();
    }

    private void setStudentGroupsInDepartment(Department department, UpdateDepartmentData data) {
        department.setStudentGroups(getExistingStudentGroups(data));
    }

    private List<StudentGroup> getExistingStudentGroups(UpdateDepartmentData data) {
        return getExistingDepartment(data).getStudentGroups();
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, departmentRepository));

        departmentRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void softDeleteByName(String name) {
        checkExistsWithSuchName(new CheckExistsByNameData(className, name, departmentRepository));

        Department department = getExistingDepartment(name);

        softDeleteStudentGroups(department.getStudentGroups());
        softDeleteTeachers(department.getTeachers());

        saveDepartment(markDepartmentAsDeleted(department));
    }

    private Department getExistingDepartment(String departmentName) {
        return departmentRepository.getByName(departmentName).get();
    }

    private void softDeleteStudentGroups(List<StudentGroup> studentGroups) {
        groupService.softDeleteStudentGroups(studentGroups);
    }

    private void softDeleteTeachers(List<Teacher> teachers) {
        teacherService.softDeletePeople(teachers);
    }

    private Department markDepartmentAsDeleted(Department department) {
        department.setDeleted(true);
        return department;
    }

    @Override
    @Transactional
    public void softDeleteDepartments(List<Department> departments) {
        for (Department department : departments)
            softDeleteByName(department.getName());
    }
}
