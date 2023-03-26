package ua.dgma.electronicDeansOffice.models;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum PersonRoleEnum {
    ROOT, ADMIN, DEANERY_WORKER, TEACHER, STUDENT;
}
