package ua.dgma.electronicDeansOffice.services.impl.data;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@Data
public class findAllByFacultyData {
//    @NonNull
    Integer page;
//    @NonNull
    Integer peoplePerPage;
//    @NonNull
    Boolean isDeleted;
//    @NonNull
    String facultyName;
//    @NonNull
    Specification specification;

    public findAllByFacultyData(Integer page,
                                Integer peoplePerPage,
                                Boolean isDeleted,
                                String facultyName,
                                Specification specification) {
        this.page = page;
        this.peoplePerPage = peoplePerPage;
        this.isDeleted = isDeleted;
        this.facultyName = facultyName;
        this.specification = specification;
    }
}
