package ua.dgma.electronicDeansOffice.utill;

import java.time.LocalDateTime;

public class ConvertData {

    public static LocalDateTime convertData(String string) {
        return LocalDateTime.parse(string);
    }
    public static Integer convertSemester(String string) {
        return Integer.parseInt(string);
    }
}
