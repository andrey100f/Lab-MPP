package concertpro.validator;

import concertpro.exception.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
