package app.coronawarn.server.services.submission.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class SubmissionServiceConfigTest {
  @Test
  void testPayload() {
    SubmissionServiceConfig config = new SubmissionServiceConfig();

    SubmissionServiceConfig.Payload payload = new SubmissionServiceConfig.Payload();
    config.setPayload(payload);

    int maxNumberOfKeys = 1;
    String[] supportedCountries = new String[]{"DE"};
    String defaultOriginCountry = "DE";

    payload.setMaxNumberOfKeys(maxNumberOfKeys);
    payload.setSupportedCountries(supportedCountries);
    payload.setDefaultOriginCountry(defaultOriginCountry);

    assertThat(payload.getMaxNumberOfKeys()).isEqualTo(maxNumberOfKeys);
    assertThat(payload.getSupportedCountries()).isEqualTo(supportedCountries);
    assertThat(payload.getDefaultOriginCountry()).isEqualTo(defaultOriginCountry);
    assertThat(config.getMaxNumberOfKeys()).isEqualTo(maxNumberOfKeys);
    assertThat(config.getSupportedCountries()).isEqualTo(supportedCountries);
    assertThat(config.getDefaultOriginCountry()).isEqualTo(defaultOriginCountry);
  }

  @Test
  void testPayloadIndirect() {
    SubmissionServiceConfig config = new SubmissionServiceConfig();

    SubmissionServiceConfig.Payload payload = new SubmissionServiceConfig.Payload();
    config.setPayload(payload);

    String[] supportedCountries = new String[]{"DE"};

    config.setSupportedCountries(supportedCountries);

    assertThat(config.getSupportedCountries()).isEqualTo(supportedCountries);
    assertThat(config.getSupportedCountries()).isEqualTo(supportedCountries);
  }

  @Test
  void testClient() {
    SubmissionServiceConfig config = new SubmissionServiceConfig();

    SubmissionServiceConfig.Client client = new SubmissionServiceConfig.Client();
    config.setClient(client);

    assertThat(config.getClient()).isEqualTo(client);
  }

  @Test
  void testSsl() throws IOException {
    SubmissionServiceConfig.Client client = new SubmissionServiceConfig.Client();

    SubmissionServiceConfig.Client.Ssl ssl = new SubmissionServiceConfig.Client.Ssl();
    client.setSsl(ssl);

    Path tempDir = Files.createTempDirectory("cwa-server");
    File keyStore = new File(tempDir.toFile(), "keyStore");
    String keyStorePassword = "keyStorePassword";
    String keyPassword = "keyPassword";
    File trustStore = new File(tempDir.toFile(), "trustStore");;
    String trustStorePassword = "trustStorePassword";

    ssl.setKeyStore(keyStore);
    ssl.setKeyStorePassword(keyStorePassword);
    ssl.setKeyPassword(keyPassword);
    ssl.setTrustStore(trustStore);
    ssl.setTrustStorePassword(trustStorePassword);

    assertThat(client.getSsl()).isEqualTo(ssl);
    assertThat(client.getSsl().getKeyStore()).isEqualTo(keyStore);
    assertThat(client.getSsl().getKeyStorePassword()).isEqualTo(keyStorePassword);
    assertThat(client.getSsl().getKeyPassword()).isEqualTo(keyPassword);
    assertThat(client.getSsl().getTrustStore()).isEqualTo(trustStore);
    assertThat(client.getSsl().getTrustStorePassword()).isEqualTo(trustStorePassword);
  }
}
