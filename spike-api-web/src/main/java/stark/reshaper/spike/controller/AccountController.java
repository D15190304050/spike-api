package stark.reshaper.spike.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.spike.service.AccountService;
import stark.reshaper.spike.service.CaptchaService;
import stark.reshaper.spike.service.dto.params.RegistrationRequest;
import stark.reshaper.spike.service.dto.results.CaptchaResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

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

    @GetMapping("/validate-token")
    public void validateToken(HttpServletResponse response) throws IOException
    {
        accountService.validateToken(response);
    }
}
