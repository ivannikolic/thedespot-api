package rs.thedespot.config;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public MailjetClient mailjetClient() {
    ClientOptions options = ClientOptions.builder()
      .apiKey("f8b76c522f94565dc6c8585278ab7501")
      .apiSecretKey("d664647dc5c66688220a8db6fa8bc4c6")
      .build();
    return new MailjetClient(options);
  }

}
