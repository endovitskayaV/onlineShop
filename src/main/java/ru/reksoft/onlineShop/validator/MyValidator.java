package ru.reksoft.onlineShop.validator;


import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;

@Service
public class MyValidator {//implements Validator {
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return CharacteristicDto.class.equals(clazz);
//    }

   // @Override
    public void validate(Object target, BindingResult bindingResult) {
        CharacteristicDto characteristic = (CharacteristicDto) target;
        if (characteristic.getValue().equals("") && characteristic.isRequired()) {
            bindingResult.addError(new FieldError("value", "value1","Fill in required characteristic value"));
//            errors.rejectValue("value",
//                    "nullValue",
//                    new Object[]{"'value'"},
//                    "Fill in required characteristic value");
        }

    }
}
