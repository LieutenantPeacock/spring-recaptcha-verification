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
	/**
	 * The default host used for verification requests. Use {@link #ALTERNATE_HOST}
	 * when {@code www.google.com} is not accessible.
	 */
	public static final String DEFAULT_HOST = "www.google.com";
	/**
	 * Alternate host recommended by Google for use when {@link #DEFAULT_HOST} is not accessible.
	 */
	public static final String ALTERNATE_HOST = "www.recaptcha.net";
	private final WebClient webClient = WebClient.create();
	private final String secretKey;
	private final String host;

	/**
	 * Construct a {@code DefaultVerifyCaptchaService} with the given secret key
	 * for verification requests, using the {@link #DEFAULT_HOST default host}.
	 * @param secretKey The reCAPTCHA secret key.
	 */
	public DefaultVerifyCaptchaService(final String secretKey) {
		this(secretKey, DEFAULT_HOST);
	}

	/**
	 * Construct a {@code DefaultVerifyCaptchaService} with the given secret key
	 * and host for verification requests.
	 * @param secretKey The reCAPTCHA secret key.
	 * @param host The host to send verification requests to, e.g. {@link #DEFAULT_HOST}
	 * or {@link #ALTERNATE_HOST}.
	 */
	public DefaultVerifyCaptchaService(final String secretKey, final String host) {
		this.secretKey = secretKey;
		this.host = host;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyCaptcha(final String captchaResponse) {
		try {
			final Map<String, Object> map = this.webClient.post().uri(
					uriBuilder -> uriBuilder.scheme("https")
						.host(host).path("/recaptcha/api/siteverify")
						.queryParam("secret", secretKey).queryParam("response", captchaResponse)
						.build()
				).retrieve().bodyToMono(Map.class).block();
			LOG.debug("Response from reCAPTCHA verify [{}]", map);
			return map != null && (boolean) map.get("success");
		} catch (WebClientException e) {
			LOG.error("WebClientException", e);
			return false;
		}
	}
}
