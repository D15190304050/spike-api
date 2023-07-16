package stark.reshaper.spike.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.exceptions.ExceptionInfoFormatter;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.spike.service.constants.SecurityConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class LoginFailureJsonHandler implements AuthenticationFailureHandler
{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String exceptionInfo = ExceptionInfoFormatter.formatMessageAndStackTrace(exception);
        log.error("Login failure: " + exceptionInfo);

        ServiceResponse<Boolean> loginResult = ServiceResponse.buildErrorResponse(-1, exceptionInfo);
        String resultJson = JsonSerializer.serialize(loginResult);
        response.getWriter().println(resultJson);
        response.flushBuffer();
    }
}
