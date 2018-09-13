package com.example.demo.handler;

import com.example.demo.exception.AppException;
import com.example.demo.exception.DuplicateException;
import com.example.demo.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
            return (
                    httpResponse.getStatusCode().series() == CLIENT_ERROR
                            || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {

        if (httpResponse.getStatusCode()
                .series() == SERVER_ERROR) {
            throw new AppException(getErrorMessage(httpResponse),httpResponse.getStatusText());
        } else if (httpResponse.getStatusCode()
                .series() == CLIENT_ERROR) {
            switch (httpResponse.getStatusCode()){
                case UNAUTHORIZED:
                    throw  new AuthenticationException(getErrorMessage(httpResponse));
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(httpResponse));
                case CONFLICT:
                    throw new DuplicateException(getErrorMessage(httpResponse));
                default:
                    throw new AppException(getErrorMessage(httpResponse),httpResponse.getStatusText());
            }
        }
    }

    private String getErrorMessage(ClientHttpResponse httpResponse) throws IOException {
        InputStream is = httpResponse.getBody();
        String errorContext = IOUtils.toString(is, StandardCharsets.UTF_8);
        HashMap<String,Object> result = new ObjectMapper().readValue(errorContext, HashMap.class);
        return result.getOrDefault("message",errorContext).toString();
    }
}