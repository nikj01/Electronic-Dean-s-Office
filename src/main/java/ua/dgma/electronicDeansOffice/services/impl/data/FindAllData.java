package ua.dgma.electronicDeansOffice.services.impl.data;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class FindAllData {
    private Integer page;
    private Integer objectsPerPage;
    private Boolean deleted;
    private Long facultyId;

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
                       Boolean deleted) {
        this.page = page;
        this.objectsPerPage = objectsPerPage;
        this.deleted = deleted;
    }
}
