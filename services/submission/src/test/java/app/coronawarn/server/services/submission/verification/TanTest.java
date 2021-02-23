

package app.coronawarn.server.services.submission.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Objects;

class TanTest {

  @ParameterizedTest
  @ValueSource(strings = {
      "ANY SYNTAX", "123456", "ABCD23X", "ZZZZZZZ", "Bearer 3123fe", "", "&%$§&%&$%/%&",
      "LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONG"
  })
  void invalidTanShouldThrowException(String invalidSyntaxTan) {
    assertThatThrownBy(() -> Tan.of(invalidSyntaxTan)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void validTanShouldReturnTanInstance() {
    String tanString = UUID.randomUUID().toString();
    Tan tan = Tan.of(tanString);

    assertThat(tan).isNotNull().hasToString(tanString);
  }

  @Test 
  void equalsTestTan(){
    UUID tanUUID= UUID.randomUUID();
    Tan tan = Tan.of(tanUUID.toString());
    assertThat(tan.equals(tan)).isTrue();
    assertThat(tan.equals(null)).isFalse();
  }

  @Test 
  void hashTestTan(){
    UUID tanUUID1= UUID.randomUUID();
    UUID tanUUID2 = UUID.randomUUID();
    while(tanUUID2 == tanUUID1){
        tanUUID2 = UUID.randomUUID();
    }
    Tan tan1 = Tan.of(tanUUID1.toString());
    Tan tan2 = Tan.of(tanUUID2.toString());
    assertThat(tan1.hashCode()).isNotEqualTo(tan2.hashCode());
  }
}
