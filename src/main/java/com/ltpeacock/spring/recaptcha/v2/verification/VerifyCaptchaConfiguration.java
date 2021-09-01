package com.ltpeacock.spring.recaptcha.v2.verification;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * The base configuration class for reCAPTCHA v2 verification. 
 * If not using Spring Boot, this class must be imported into one of the configuration classes.
 * @author LieutenantPeacock
 *
 */
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class VerifyCaptchaConfiguration {
	
}
