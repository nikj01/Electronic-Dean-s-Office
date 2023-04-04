package ua.dgma.electronicDeansOffice.exceptions;

import ua.dgma.electronicDeansOffice.utill.validators.data.StudentGroupValidationData;

public class StudentNotContainsInStudentGroup extends RuntimeException{
    public StudentNotContainsInStudentGroup(StudentGroupValidationData data){
    }
}
