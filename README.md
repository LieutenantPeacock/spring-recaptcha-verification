# Spring reCAPTCHA Verification
Simple library for verifying reCAPTCHA v2 responses in Spring projects.

## Installation
(JDK 8 is required at a minimum.)

Include the [dependency from Maven Central](https://search.maven.org/artifact/com.lt-peacock/spring-recaptcha-verification). For Maven users, add the following to pom.xml:

```xml
<dependency>
	<groupId>com.lt-peacock</groupId>
	<artifactId>spring-recaptcha-verification</artifactId>
	<version>1.0.0</version>
</dependency>
```

If Spring Boot is not used, import `com.ltpeacock.spring.recaptcha.v2.verification.VerifyCaptchaConfiguration` into one of your configuration classes.

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.ltpeacock.spring.recaptcha.v2.verification.VerifyCaptchaConfiguration;

@Configuration
@Import(VerifyCaptchaConfiguration.class)
public class MyConfiguration{

}
```

## Usage
Register an instance of `VerifyCaptchaService` as a bean. You may use your own implementation or the provided `DefaultVerifyCaptchaService`.

```java
@Bean
public VerifyCaptchaService verifyCaptchaService() {
	return new DefaultVerifyCaptchaService("your secret key");
}
```

To manually verify a user response token, autowire the service into a controller and call `verifyCaptcha`.

```java
@Controller
public class MyController {
	@Autowired
	private VerifyCaptchaService verifyCaptchaService;

	@PostMapping("/")
	public String test(HttpServletRequest request){
		if(verifyCaptchaService.verifyCaptcha(request)){
			// proceed normally
		} else {
			// show error
		}
	}
}
```

The `@VerifyCaptcha` annotation can also be used on a controller method to indicate that the reCAPTCHA response should be verified before calling the method. Specify the `errorRedirectURL` attribute to set the URL to redirect to if the token is not valid (i.e. verification fails). By default, it will redirect back to the same page. Additionally, when verification fails, the value of the specified `errorAttribute` will be set to `true` in the model (`"captchaVerificationError"` if not set). Check this attribute to display an appropriate error message. 

```java
@Controller
public class MyController {
	@PostMapping("/")
	@VerifyCaptcha(errorRedirectURL = "/captchaFailed")
	public String test(){
		// if this is reached, reCAPTCHA verification passed
	}
}
```