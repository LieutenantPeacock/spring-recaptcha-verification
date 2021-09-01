package com.ltpeacock.spring.recaptcha.v2.verification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 
 * @author LieutenantPeacock
 *
 */
@Aspect
@Component
public class VerifyCaptchaAspect {
	@Autowired
	private VerifyCaptchaService verifyCaptchaService;

	@Around("@annotation(verifyCaptcha)")
	public Object verifyCaptcha(final ProceedingJoinPoint joinPoint, final VerifyCaptcha verifyCaptcha) throws Throwable {
		final ServletRequestAttributes requestAttrs = ((ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes());
		final HttpServletRequest request = requestAttrs.getRequest();
		final HttpServletResponse response = requestAttrs.getResponse();
		if (this.verifyCaptchaService.verifyCaptcha(request)) {
			return joinPoint.proceed();
		} else {
			final FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			flashMap.put("captchaVerificationError", true);
			final String errorRedirectURL = verifyCaptcha.errorRedirectURL();
			final String actualRedirectURL = errorRedirectURL == null || VerifyCaptcha.DEFAULT_ERROR_REDIRECT_URL.equals(errorRedirectURL)
					? ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString() : errorRedirectURL;
			RequestContextUtils.saveOutputFlashMap(actualRedirectURL, request, response);
			response.sendRedirect(actualRedirectURL);
			return null;
		}
	}
}
