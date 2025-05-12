package io.upskilling.training.employee.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationControllerAdvice {

    private static final String DEFAULT_MESSAGE = "unhandled server message";

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException ex) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage(), ex);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ProblemDetail handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Employee Not Found", ex.getMessage(), ex);
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ProblemDetail handleInvalidEmailFormatException(InvalidEmailFormatException ex) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Invalid Email Format", ex.getMessage(), ex);
    }

    @ExceptionHandler(InvalidPhoneNumberFormatException.class)
    public ProblemDetail handleInvalidPhoneNumberFormatException(InvalidPhoneNumberFormatException ex) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Invalid Phone Number Format", ex.getMessage(), ex);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ProblemDetail handleEmployeeAlreadyExistsException(EmployeeAlreadyExistsException ex) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Employee Already Exists", ex.getMessage(), ex);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail globalExceptionHandler(Exception ex) {
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Global Exception", DEFAULT_MESSAGE, ex);
    }

    private ProblemDetail createProblemDetail(HttpStatus status,
                                              String title,
                                              String detail,
                                              Exception ex) {
        log.warn("{}: {}", title, ex.getMessage(), ex);
        var problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        return problemDetail;
    }
}
