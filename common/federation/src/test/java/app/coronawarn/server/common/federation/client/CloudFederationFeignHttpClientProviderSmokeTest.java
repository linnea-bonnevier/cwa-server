package app.coronawarn.server.common.federation.client;

import static org.assertj.core.api.Assertions.assertThat;

import app.coronawarn.server.common.federation.client.config.FederationGatewayConfig;
import app.coronawarn.server.common.federation.client.config.FederationGatewayConfig.Ssl;
import app.coronawarn.server.common.federation.client.hostname.DefaultHostnameVerifierProvider;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(properties = "spring.main.lazy-initialization=true", webEnvironment = WebEnvironment.RANDOM_PORT)
class CloudFederationFeignHttpClientProviderSmokeTest {

  @Test
  void testCanLoadKeystore() {
    Ssl ssl = new Ssl();
    ssl.setKeyStore(new File("../../docker-compose-test-secrets/ssl.p12"));
    ssl.setKeyStorePassword("123456");
    ssl.setCertificateType("PKCS12");
    ssl.setTrustStore(new File("../../docker-compose-test-secrets/contains_efgs_truststore.jks"));
    ssl.setTrustStorePassword("123456");
    FederationGatewayConfig config = new FederationGatewayConfig();
    config.setConnectionPoolSize(1);
    config.setSsl(ssl);
    CloudFederationFeignHttpClientProvider cut = new CloudFederationFeignHttpClientProvider(config,
        new DefaultHostnameVerifierProvider());
    assertThat(cut.createFeignClient()).isNotNull();
  }

  @Test
  void testCertificate(){
    Ssl ssl = new Ssl();
    ssl.setCertificateDn("X.509");
    assertThat(ssl.getCertificateDn()).isNotEqualTo("PKCS12");
  }

  @Test
  void testCertificateSHA(){
    Ssl ssl = new Ssl();
    ssl.setCertificateSha("randomString123123");
    assertThat(ssl.getCertificateSha()).isEqualTo("randomString123123");
  }

  @Test
  void testGetBaseUrl(){
    FederationGatewayConfig config = new FederationGatewayConfig();
    config.setBaseUrl("https://github.com/cwa-server/tree/main");
    assertThat(config.getBaseUrl()).isNotNull();
  }

  @Test
  void testGetCertificationType(){
    Ssl ssl = new Ssl();
    ssl.setCertificateType("PKCS12");
    assertThat(ssl.getCertificateType()).isEqualTo("PKCS12");
  }

}
