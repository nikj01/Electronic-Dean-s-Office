package ua.dgma.electronicDeansOffice.services.impl.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class FindAllData {
    private Integer page;
    private Integer objectsPerPage;
    private Boolean deleted;
    private Long facultyId;
    private Integer semester;
    private LocalDateTime searchFrom;

    public FindAllData(Integer page,
                       Integer objectsPerPage,
                       Boolean deleted,
                       Long facultyId) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
        this.facultyId = facultyId;
    }
    public FindAllData(Integer page,
                       Integer objectsPerPage,
                       Boolean deleted,
                       Long facultyId,
                       Integer semester) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
        this.facultyId = facultyId;
        this.semester = semester;
    }
    public FindAllData(Integer page,
                       Integer objectsPerPage,
                       Boolean deleted,
                       Long facultyId,
                       Integer semester,
                       LocalDateTime searchFrom) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
        this.facultyId = facultyId;
        this.semester = semester;
        this.searchFrom = searchFrom;
    }

    public FindAllData(Integer page,
                       Integer objectsPerPage,
                       Boolean deleted) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
    }
}
