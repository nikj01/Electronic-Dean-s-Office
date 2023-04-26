package ua.dgma.electronicDeansOffice.utill.check;

import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.exceptions.NotFoundException;
import ua.dgma.electronicDeansOffice.exceptions.ObjectExistsException;
import ua.dgma.electronicDeansOffice.exceptions.data.ExceptionData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByIdData;
import ua.dgma.electronicDeansOffice.utill.check.data.CheckExistsByNameData;

@Component
public class CheckMethods {

    public static boolean checkPaginationParameters(Integer page, Integer numberOfItemsOnThePage) {
        return page == null || numberOfItemsOnThePage == null;
    }

    public static void checkExistenceObjectWithSuchID(CheckExistsByIdData data) {
        if(!data.getRepository().existsById(data.getId())) throw new NotFoundException(new ExceptionData<String>(data.getClassName(), "id", data.getId().toString()));
    }

    public static void checkExistenceObjectWithSuchName(CheckExistsByNameData data) {
        if(!data.getRepository().existsByName(data.getName())) throw new NotFoundException(new ExceptionData<String>(data.getClassName(), "name", data.getName().toString()));
    }

    public static void checkExistenceObjectWithSuchIDBeforeRegistrationOrUpdate(CheckExistsByIdData data) {
        if(data.getRepository().existsById(data.getId())) throw new ObjectExistsException(new ExceptionData<String>(data.getClassName(), "id", data.getId().toString()));
    }
    public static void checkExistenceObjectWithSuchNameBeforeRegistrationOrUpdate(CheckExistsByNameData data) {
        if(data.getRepository().existsByName(data.getName())) throw new ObjectExistsException(new ExceptionData<String>(data.getClassName(), "name", data.getName().toString()));
    }
}
