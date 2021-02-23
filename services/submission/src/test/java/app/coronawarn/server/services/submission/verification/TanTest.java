

package app.coronawarn.server.services.submission.verification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Assertions;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    Tan tan = new Tan(tanUUID);
    Assertions.assertTrue(tan.equals(tan));
    Assertions.assertFalse(tan.equals(null);)
  }

  @Test 
  void hashTestTan(){
    UUID tanUUID= UUID.randomUUID();
    Tan tan = new Tan(tanUUID);
    Assertions.assertEquals(Objects.hash(tan),tan.hashCode())
  }
}
