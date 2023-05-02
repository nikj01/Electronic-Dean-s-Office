package ua.dgma.electronicDeansOffice.models;

import lombok.ToString;

@ToString
public enum PersonRoleEnum {
    ROLE_ROOT,
    ROLE_ADMIN,
    ROLE_DEANERY_WORKER,
    ROLE_TEACHER,
    ROLE_STUDENT,
    ROLE_HEAD_OF_THE_DEPARTMENT;
}
