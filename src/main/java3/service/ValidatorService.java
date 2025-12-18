package service;


import utils.ValidationResult;
import utils.ValidationUtils;

public class ValidatorService {

    public ValidationResult validateStudentCreation(
            String id,
            String name,
            String email,
            String phone,
            String date,
            String type // "Regular" | "Honors", can be separately validated if needed
    ) {
        ValidationResult r1 = ValidationUtils.validateStudentId(id);
        if (!r1.isValid()) return r1;

        ValidationResult r2 = ValidationUtils.validateName(name);
        if (!r2.isValid()) return r2;

        ValidationResult r3 = ValidationUtils.validateEmail(email);
        if (!r3.isValid()) return r3;

        ValidationResult r4 = ValidationUtils.validatePhone(phone);
        if (!r4.isValid()) return r4;

        ValidationResult r5 = ValidationUtils.validateDate(date);
        if (!r5.isValid()) return r5;

        // Optional: validate type as either "Regular" or "Honors"
        if (!("Regular".equalsIgnoreCase(type) || "Honors".equalsIgnoreCase(type))) {
            return ValidationResult.error(type, "Regular | Honors",
                    "VALIDATION ERROR: Invalid student type",
                    java.util.List.of("Regular", "Honors"));
        }

        return ValidationResult.ok("All inputs validated with regex patterns");
    }
}
