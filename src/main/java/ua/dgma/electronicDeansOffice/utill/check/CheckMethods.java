package ua.dgma.electronicDeansOffice.utill.check;

import org.springframework.stereotype.Component;

@Component
public class CheckMethods {

    public static boolean checkPaginationParameters(Integer page, Integer numberOfItemsOnThePage) {
        return page == null || numberOfItemsOnThePage == null;
    }
}
