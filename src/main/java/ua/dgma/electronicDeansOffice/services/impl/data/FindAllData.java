package ua.dgma.electronicDeansOffice.services.impl.data;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class FindAllData {
    private Integer page;
    private Integer objectsPerPage;
    private Boolean deleted;
    private String facultyName;

    public FindAllData(Integer page, Integer objectsPerPage, Boolean deleted, String facultyName) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
        this.facultyName = facultyName;
    }

    public FindAllData(Integer page, Integer objectsPerPage, Boolean deleted) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
    }
}
