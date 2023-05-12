package ua.dgma.electronicDeansOffice.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_ROOT', 'ROLE_ADMIN', 'ROLE_DEANERY_WORKER', 'ROLE_TEACHER', 'ROLE_STUDENT', 'ROLE_HEAD_OF_THE_DEPARTMENT')")
public @interface AllPeople {
}
