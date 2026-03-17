package com.tuempresa.booking.hotel.api.endpoint.advise;


import com.tuempresa.booking.hotel.api.model.exception.BadRequestException;
import com.tuempresa.booking.hotel.api.model.exception.GatewayErrorException;
import com.tuempresa.booking.hotel.api.model.exception.ResourceNotFoundException;

import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvisor {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ProblemDetail> handleNotFoundErrors(RuntimeException ex) {
    return buildProblemDetailResponse(
            HttpStatus.NOT_FOUND,
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            ex
    );
  }

  @ExceptionHandler(GatewayErrorException.class)
  public ResponseEntity<ProblemDetail> handleGatewayError(RuntimeException ex) {
    return buildProblemDetailResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            ex.getMessage(),
            ex
    );
  }
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ProblemDetail> handleBadRequestError(RuntimeException ex) {
    return buildProblemDetailResponse(
            HttpStatus.BAD_REQUEST,
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            ex
    );
  }

  private ResponseEntity<ProblemDetail> buildProblemDetailResponse(
          HttpStatus status,
          String title,
          String detail,
          Exception exception
  ) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
    problemDetail.setTitle(title);
    problemDetail.setProperty("exception", exception.getClass().getSimpleName());

    return ResponseEntity.status(status).body(problemDetail);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
    String detail = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));

    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    problem.setTitle("Validation failed");
    return problem;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {
    String detail = ex.getConstraintViolations().stream()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .collect(Collectors.joining("; "));

    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    problem.setTitle("Constraint violation");
    return problem;
  }

}
