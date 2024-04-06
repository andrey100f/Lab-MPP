package ro.mpp2024.concertpro.dao.validator;

import ro.mpp2024.concertpro.dao.exception.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
