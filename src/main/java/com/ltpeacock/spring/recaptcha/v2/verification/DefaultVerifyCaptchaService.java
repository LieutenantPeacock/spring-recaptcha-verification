package com.ltpeacock.spring.recaptcha.v2.verification;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

/**
 * 
 * @author LieutenantPeacock
 *
 */
public class DefaultVerifyCaptchaService implements VerifyCaptchaService {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultVerifyCaptchaService.class);
	private final WebClient webClient = WebClient.create();
	private final String secretKey;
	
	/**
	 * Construct a {@code DefaultVerifyCaptchaService} with the given secret key 
	 * for verification requests.
	 * @param secretKey The reCAPTCHA secret key.
	 */
	public DefaultVerifyCaptchaService(final String secretKey) {
		this.secretKey = secretKey;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyCaptcha(final String captchaResponse) {
		try {
			final Map<String, Object> map = this.webClient.post().uri(
					uriBuilder -> uriBuilder.scheme("https")
						.host("www.google.com").path("/recaptcha/api/siteverify")
						.queryParam("secret", secretKey).queryParam("response", captchaResponse)
						.build()
				).retrieve().bodyToMono(Map.class).block();
			LOG.debug("Response from reCAPTCHA verify [{}]", map);
			return map != null && (boolean) map.get("success");
		} catch(WebClientException e) {
			LOG.error("WebClientException", e);
			return false;
		}
	}
}
