package stark.reshaper.spike.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import stark.dataworks.basic.data.redis.RedisQuickOperation;
import stark.dataworks.basic.params.OutValue;
import stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse;
import stark.dataworks.boot.web.ServiceResponse;
import stark.reshaper.spike.dao.AccountBaseInfoMapper;
import stark.reshaper.spike.domain.AccountBaseInfo;
import stark.reshaper.spike.service.constants.Genders;
import stark.reshaper.spike.service.constants.PhoneNumberPrefixes;
import stark.reshaper.spike.service.dto.requests.RegistrationRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@Validated
@LogArgumentsAndResponse
public class AccountService //implements IAccountService
{
    @Autowired
    private RedisQuickOperation redisQuickOperation;

    @Autowired
    private AccountBaseInfoMapper accountBaseInfoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ServiceResponse<Boolean> register(@Valid RegistrationRequest registrationRequest)
    {
        // 1. Validate registration information.
        OutValue<String> message = new OutValue<>();
        if (!validateRegistrationInfo(registrationRequest, message))
        {
            log.error("Validation error: " + message.getValue());
            return ServiceResponse.buildErrorResponse(-100, message.getValue());
        }

        // 2. Encrypt the password.
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        // 3. Put the registration info (account info) into DB.
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        accountBaseInfo.setUsername(registrationRequest.getUsername());
        accountBaseInfo.setNickname(registrationRequest.getNickname());
        accountBaseInfo.setAvatarUrl(registrationRequest.getAvatarUrl());
        accountBaseInfo.setEncryptedPassword(encodedPassword);
        accountBaseInfo.setPhoneNumber(registrationRequest.getPhoneNumber());
        accountBaseInfo.setPhoneNumberPrefix(registrationRequest.getPhoneNumberPrefix());
        accountBaseInfo.setEmail(registrationRequest.getEmail());
        accountBaseInfo.setGender(registrationRequest.getGender().toLowerCase(Locale.ROOT));
        accountBaseInfoMapper.insert(accountBaseInfo);

        // 4. Return success.
        return ServiceResponse.buildSuccessResponse(true);
    }

    private boolean validateRegistrationInfo(RegistrationRequest registrationRequest, OutValue<String> message)
    {
        // Validations:
        // 1.1*: If accept the agreement.
        // 1.2: If the captcha is correct.
        // 1.3*: If the username matches the pattern.
        // 1.4: If the provided username, phone number, email exists.
        // 1.5*: If the password matches the pattern.
        // 1.6: If the confirmed password equals password.
        // 1.7: If the value of gender is in the given range.
        // 1.8*: If the nickname matches the pattern.
        // 1.9*: If the email matches the pattern.
        // 1.10*: If the phone number matches the pattern.
        // 1.11: If the phone number prefix matches the pattern.
        // Some of them can be done by spring boot validation (marked with *).
        // Here is the validation of the rest.

        // region 1.2
        String captchaId = registrationRequest.getCaptchaId();
        String captchaText = redisQuickOperation.get(captchaId);
        if (!StringUtils.hasText(captchaText))
        {
            message.setValue("The captcha has expired.");
            return false;
        }

        if (!captchaText.equals(registrationRequest.getCaptcha()))
        {
            message.setValue("Incorrect captcha, please try again.");
            return false;
        }
        // endregion

        // region 1.4
        String username = registrationRequest.getUsername();
        String phoneNumber = registrationRequest.getPhoneNumber();
        String phoneNumberPrefix = registrationRequest.getPhoneNumberPrefix();
        String email = registrationRequest.getEmail();
        List<AccountBaseInfo> accounts = accountBaseInfoMapper.getByUsernamePhoneNumberEmail(username,
            phoneNumber,
            phoneNumberPrefix,
            email);
        if (!CollectionUtils.isEmpty(accounts))
        {
            for (AccountBaseInfo accountBaseInfo : accounts)
            {
                if (accountBaseInfo.getUsername().equals(username))
                {
                    message.setValue("The provided username already exists, try another one.");
                    return false;
                }

                if (accountBaseInfo.getPhoneNumber().equals(phoneNumber) && accountBaseInfo.getPhoneNumberPrefix().equals(phoneNumberPrefix))
                {
                    message.setValue("The provided phone number already exists, try another one.");
                    return false;
                }

                if (accountBaseInfo.getEmail().equals(email))
                {
                    message.setValue("The provided email already exists, try another one.");
                    return false;
                }
            }
        }
        // endregion 1.4

        // region 1.6
        String password = registrationRequest.getPassword();
        String confirmedPassword = registrationRequest.getConfirmedPassword();
        if (!password.equals(confirmedPassword))
        {
            message.setValue("The two passwords that you entered do not match!");
            return false;
        }
        // endregion

        // region 1.7
        String gender = registrationRequest.getGender().toLowerCase(Locale.ROOT);
        if (!Genders.inRange(gender))
        {
            String acceptableGenders = Arrays.toString(Genders.acceptableValues());
            message.setValue("The gender you provide is incorrect, acceptable values are: " + acceptableGenders);
            return false;
        }
        // endregion

        // region 1.8
        if (!PhoneNumberPrefixes.inRange(phoneNumberPrefix))
        {
            String acceptablePrefixes = Arrays.toString(PhoneNumberPrefixes.acceptableValues());
            message.setValue("The gender you provide is incorrect, acceptable values are: " + acceptablePrefixes);
            return false;
        }
        // endregion

        return true;
    }

    public void tryLogin(HttpServletResponse response, String ssoUserIdKey) throws IOException
    {
        if (ssoUserIdKey != null)
        {
            String userIdString = redisQuickOperation.get(ssoUserIdKey);
//            long
        }

        response.sendRedirect("");
    }
}
