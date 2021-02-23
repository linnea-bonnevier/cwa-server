
package app.coronawarn.server.common.federation.client.upload;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import java.util.Arrays; 
import java.util.List;

class BatchUploadResponseTest {

  @Test
  void checkEmptyBatchUploadResponse() {
    BatchUploadResponse batchUploadResponse = new BatchUploadResponse();

    assertThat(batchUploadResponse.getStatus201()).isEmpty();
    assertThat(batchUploadResponse.getStatus409()).isEmpty();
    assertThat(batchUploadResponse.getStatus500()).isEmpty();
  }

  @Test 
  void toStringTestBatchUploadResponse(){
    List<String> status = Arrays.asList("test");
    BatchUploadResponse batchUploadResponse = new BatchUploadResponse(status,status,status);
    assertThat(batchUploadResponse.toString()).isEqualTo("BatchUploadResponse{"
    + "status409="
    + "test"
    + ", status500="
    + "test"
    + ", status201="
    + "test"
    + '}');
  }
}
