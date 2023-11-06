package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public final class ValidationUtils {

    public static Set<ValidationError> validate(Animal animal) {
        var result = new HashSet<ValidationError>();
        if (animal.age() < 0) {
            result.add(new ValidationError("age", "age is less than zero"));
        }
        if (animal.height() < 0) {
            result.add(new ValidationError("height", "height is less than zero"));
        }
        if (animal.weight() < 0) {
            result.add(new ValidationError("weight", "weight is less than zero"));
        }
        if (animal.type() == null) {
            result.add(new ValidationError("type", "no type specified"));
        }
        if (animal.sex() == null) {
            result.add(new ValidationError("sex", "no sex specified"));
        }
        return result;
    }

    private ValidationUtils() {
    }

}
