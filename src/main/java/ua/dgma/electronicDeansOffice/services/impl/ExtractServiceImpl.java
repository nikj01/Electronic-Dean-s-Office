package ua.dgma.electronicDeansOffice.services.impl;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.extractWithGrades.Extract;
import ua.dgma.electronicDeansOffice.mapstruct.dtos.person.PersonSlimGetDTO;
import ua.dgma.electronicDeansOffice.mapstruct.mappers.interfaces.PersonMapper;
import ua.dgma.electronicDeansOffice.models.EventTypeEnum;
import ua.dgma.electronicDeansOffice.models.Report;
import ua.dgma.electronicDeansOffice.models.Teacher;
import ua.dgma.electronicDeansOffice.services.interfaces.ExtractService;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class ExtractServiceImpl implements ExtractService {
    private final PersonMapper teacherMapper;

    @Autowired
    public ExtractServiceImpl(PersonMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    class ExtractData {
        @NonNull
        private Report report;
        @NonNull
        private Long studentId;
        @NonNull
        private Extract extract;
    }

    @Override
    public Extract getExtractWithGradeForStudent(Report report, Long studentId) {
        ExtractData data = new ExtractData(report, studentId, new Extract());

        setPageName(data);
        setEventType(data);
        setTeacher(data);
        setEventDate(data);
        setGrade(data);

        return data.getExtract();
    }

    private void setPageName(ExtractData data) {
        data.getExtract().setPageName(getPageNameFromReport(data));
    }

    private String getPageNameFromReport(ExtractData data) {
        return data.getReport().getEventData().getPageName();
    }

    private void setEventType(ExtractData data) {
        data.getExtract().setEventType(getEventTypeFromReport(data));
    }

    private EventTypeEnum getEventTypeFromReport(ExtractData data) {
        return data.getReport().getEventData().getEventType();
    }

    private void setTeacher(ExtractData data) {
        data.getExtract().setTeacher(getTeacherFromReport(data));
    }

    private PersonSlimGetDTO getTeacherFromReport(ExtractData data) {
        return teacherMapper.toPersonSlimGetDTO(getTeacher(data));
    }

    private Teacher getTeacher(ExtractData data) {
        return data.getReport().getEventData().getTeacher();
    }

    private void setEventDate(ExtractData data) {
        data.getExtract().setEventDate(getEventDate(data));
    }

    private LocalDateTime getEventDate(ExtractData data) {
        return data.getReport().getEventData().getEventDate();
    }

    private void setGrade(ExtractData data) {
        data.getExtract().setGrade(getGradeFromReport(data));
    }

    private Integer getGradeFromReport(ExtractData data) {
        return data.getReport().getStudentMarks().get(data.getStudentId());
    }
}
