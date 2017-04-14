package com.dm.asde.controllerAdvice;

import com.dm.asde.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dm.asde.model.dto.GeneralErrorNeoDTO;
import com.dm.asde.model.dto.SysError;
import com.dm.asde.util.Messages;
import io.jsonwebtoken.JwtException;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller advice to centralize code in one place. Here we use it as an Exception manager to handle error thrown by all endpoints.
 */
@ControllerAdvice
class AdviceController {

    private Logger logger = LogManager.getLogger(AdviceController.class);

    @Autowired
    private Messages messages;


    /**
     * Handler exception that returns an http-500 internal server error when an error was not handled properly.
     */
    @ExceptionHandler({Throwable.class, DmAPIException.class})
    ResponseEntity handleBadRequests(Throwable throwable, HttpServletRequest request, HttpServletResponse response) throws IOException {

        SysError error = new SysError("0014", messages.get("unavailable_service"));
        GeneralErrorNeoDTO neoError = new GeneralErrorNeoDTO(error);
        int statusCode = HttpStatus.SC_INTERNAL_SERVER_ERROR;
        Object params = null;

        try {


            if (throwable instanceof RestClientException || throwable.getCause() instanceof RestClientException) {
                String errorStr = "";
                if (throwable instanceof RestClientException)
                    errorStr = ((RestClientResponseException) throwable).getResponseBodyAsString();
                else if (throwable.getCause() instanceof RestClientException)
                    errorStr = ((RestClientResponseException) throwable.getCause()).getResponseBodyAsString();

                ObjectMapper objectMapper = new ObjectMapper();
                error = objectMapper.readValue(errorStr, SysError.class);

                if (error.getCode().equals("00404")) {
                    error.setCode("0216");
                    error.setDescription(messages.get("user_not_found"));
                } else {
                    error.setCode("0014");
                }

                if (error.getDescription() == null || error.getDescription().isEmpty())
                    error.setDescription(messages.get("unavailable_service"));

                neoError = new GeneralErrorNeoDTO(error);

            } else if (throwable instanceof JwtException || throwable.getCause() instanceof JwtException) {
                statusCode = HttpStatus.SC_UNAUTHORIZED;
                error.setCode("0004");
                error.setDescription(messages.get("invalid_user_or_password"));

            } else if (throwable instanceof InvalidUserException || throwable.getCause() instanceof InvalidUserException) {
                statusCode = HttpStatus.SC_UNAUTHORIZED;
                error.setCode("0004");
                error.setDescription(messages.get("invalid_user_or_password"));

            } else if (throwable instanceof InvalidEmailException || throwable.getCause() instanceof InvalidEmailException) {
                error.setCode("0005");
                error.setDescription(messages.get("invalid_email"));

            } else if (throwable instanceof NonUniqueUserNameException || throwable.getCause() instanceof NonUniqueUserNameException) {
                error.setCode("0021");
                error.setDescription(messages.get("non_unique_username"));

            } else if (throwable instanceof EmailExistsException || throwable.getCause() instanceof EmailExistsException) {
                error.setCode("0055");
                error.setDescription(messages.get("already_exists_email"));

            } else if (throwable instanceof PasswordDoesNotMeetTheRequirementsExpection || throwable.getCause() instanceof PasswordDoesNotMeetTheRequirementsExpection) {
                error.setCode("1618");
                error.setDescription(messages.get("password_does_not_meet_the_requirements_exception"));
            } else if (throwable instanceof MultipartException
                    && throwable.getCause() instanceof IllegalStateException
                    && throwable.getCause().getCause() instanceof FileUploadBase.SizeLimitExceededException) {
                error.setCode("1620");
                error.setDescription(messages.get("file_exceeded_size"));
            } else if (throwable instanceof CaseNotFoundException || throwable.getCause() instanceof CaseNotFoundException) {
                error.setCode("3060");
                error.setDescription(messages.get("case_not_found"));
            } else if (throwable instanceof InvalidUploadException || throwable.getCause() instanceof InvalidUploadException) {
                error.setCode("9021");
                error.setDescription(messages.get("invalid_upload"));
            }

            if (throwable instanceof DmAPIException) {
                DmAPIException exception = (DmAPIException) throwable;
                params = exception.getRequestParams();

                if ((error.getDescription() == null || error.getDescription().isEmpty()) && exception.getMessage() != null && !exception.getMessage().isEmpty())
                    error.setDescription(exception.getMessage());

                if (exception.getExceptionLevel() != null && exception.getExceptionLevel() == DmAPIException.ExceptionLevel.WARN) {
                    logger.warn("\n path: " + request.getRequestURI() + "\n warn message: " + error + "\n params:" + params, throwable);
                } else {
                    logger.error("\n path: " + request.getRequestURI() + "\n error message: " + error + "\n params:" + params, throwable);
                }


            } else {

                logger.error("\n path: " + request.getRequestURI() + "\n error message: " + error, throwable);
            }

            return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON_UTF8).body(neoError);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(statusCode).contentType(MediaType.APPLICATION_JSON_UTF8).body(neoError);
        }

    }

}