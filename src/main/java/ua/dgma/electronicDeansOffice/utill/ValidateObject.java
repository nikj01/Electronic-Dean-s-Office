package ua.dgma.electronicDeansOffice.utill;

import org.springframework.stereotype.Component;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;

@Component
public class ValidateObject {

    public static void validateObject(ValidationData validationData) {
        validationData.getValidator().validate(validationData.getObjectToBeValidated(), validationData.getBindingResult());
        if(validationData.getBindingResult().hasErrors())
            returnErrorsToClient(validationData.getBindingResult());
    }
}
