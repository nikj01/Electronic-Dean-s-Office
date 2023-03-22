package ua.dgma.electronicDeansOffice.utill;

import org.springframework.stereotype.Component;

import static ua.dgma.electronicDeansOffice.utill.ErrorsBuilder.returnErrorsToClient;

@Component
public class ValidateObject {

//    private Validator validator;
//    private T objectToBeValidated;
//    private BindingResult bindingResult;
//
//    public ValidateObject(ValidationData<T> validationData) {
//        this.validator = validationData.getValidator();
//        this.objectToBeValidated = validationData.getObjectToBeValidated();
//        this.bindingResult = validationData.getBindingResult();
//    }

    public static void validateObject(ValidationData validationData) {
        validationData.getValidator().validate(validationData.getObjectToBeValidated(), validationData.getBindingResult());
        if(validationData.getBindingResult().hasErrors())
            returnErrorsToClient(validationData.getBindingResult());
    }
}
