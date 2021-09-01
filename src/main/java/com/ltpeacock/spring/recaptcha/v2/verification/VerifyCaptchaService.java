package com.ltpeacock.spring.recaptcha.v2.verification;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author LieutenantPeacock
 *
 */
public interface VerifyCaptchaService {
	String CAPTCHA_PARAM_NAME = "g-recaptcha-response";
	/**
	 * Verify the Captcha response.
	 * @param captchaResponse The user response token provided by the reCAPTCHA client-side integration.
	 * @return Whether or not the response token is valid.
	 */
	boolean verifyCaptcha(final String captchaResponse);
	
	/**
	 * Verify the Captcha response from the request with the parameter {@link VerifyCaptchaService#CAPTCHA_PARAM_NAME}.
	 * @param request The request that was sent with the user response token.
	 * @return Whether or not the response token is valid.
	 */
	default boolean verifyCaptcha(final HttpServletRequest request) {
		return verifyCaptcha(request.getParameter(CAPTCHA_PARAM_NAME));
	}
}
