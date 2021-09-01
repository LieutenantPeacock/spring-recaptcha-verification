package com.ltpeacock.spring.recaptcha.v2.verification;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that a controller method contains a Captcha parameter that should be verified before proceeding.
 * <br>
 * If verification fails, a redirect will be sent back to {@link VerifyCaptcha#errorRedirectURL()} and the model
 * will have the attribute {@link VerifyCaptcha#errorAttribute()} set to {@code true}.
 * 
 * If {@code errorRedirectURL} is not specified, then a redirect will be sent back to the same page.
 * 
 * @author LieutenantPeacock
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface VerifyCaptcha {
	/**
	 * The default value for {@link VerifyCaptcha#errorRedirectURL()}.
	 * This value is not a valid URL and signifies that a redirect should be sent back 
	 * to the original request URL when Captcha verification fails.
	 */
	String DEFAULT_ERROR_REDIRECT_URL = "<>";
	String DEFAULT_ERROR_ATTRIBUTE = "captchaVerificationError";
	/**
	 * The URL to redirect to when Captcha verification fails. 
	 */
	String errorRedirectURL() default DEFAULT_ERROR_REDIRECT_URL;
	/**
	 * The attribute to set to {@code true} in the model when verification fails.
	 */
	String errorAttribute() default DEFAULT_ERROR_ATTRIBUTE;
}
