package stark.reshaper.spike.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.spike.service.AccountService;
import stark.reshaper.spike.service.CaptchaService;
import stark.reshaper.spike.service.constants.SecurityConstants;
import stark.reshaper.spike.service.dto.requests.RegistrationRequest;
import stark.reshaper.spike.service.dto.responses.CaptchaResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController
{
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/captcha")
    public ServiceResponse<CaptchaResponse> generateCaptcha(HttpServletResponse response) throws IOException
    {
        return captchaService.generateCaptcha(response);
    }

    @PostMapping("/register")
    public ServiceResponse<Boolean> register(@RequestBody RegistrationRequest registrationRequest)
    {
        return accountService.register(registrationRequest);
    }

    @GetMapping("/try-login")
    public void tryLogin(HttpServletResponse response,
                         @CookieValue(name = SecurityConstants.SSO_COOKIE_NAME, required = false) String ssoToken)
        throws IOException
    {
        accountService.tryLogin(response, ssoToken);
    }
}
