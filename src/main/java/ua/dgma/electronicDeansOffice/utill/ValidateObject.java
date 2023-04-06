package ua.dgma.electronicDeansOffice.utill;

import org.springframework.stereotype.Component;
import ua.dgma.electronicDeansOffice.utill.validators.data.DataForAbstractValidator;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;

@Component
public class ValidateObject {

    public static void validateObject(ValidationData data) {
        data.getValidator().validate(data.getObjectToBeValidated(), data.getBindingResult());
        if(data.getBindingResult().hasErrors())
            returnErrorsToClient(data.getBindingResult());

    }

    public static void validateObject(DataForAbstractValidator data) {
        data.getValidator().validate(data.getObjectToBeValidated());
    }
}
