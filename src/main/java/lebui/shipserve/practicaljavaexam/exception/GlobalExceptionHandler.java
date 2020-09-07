package lebui.shipserve.practicaljavaexam.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleUnauthorizedException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

}
