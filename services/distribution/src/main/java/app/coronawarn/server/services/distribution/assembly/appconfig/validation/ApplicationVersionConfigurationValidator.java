

package app.coronawarn.server.services.distribution.assembly.appconfig.validation;

import static app.coronawarn.server.services.distribution.assembly.appconfig.validation.ValidationError.ErrorType.MIN_GREATER_THAN_MAX;

import app.coronawarn.server.common.protocols.internal.ApplicationVersionConfiguration;
import app.coronawarn.server.common.protocols.internal.ApplicationVersionInfo;
import app.coronawarn.server.common.protocols.internal.SemanticVersion;

public class ApplicationVersionConfigurationValidator extends ConfigurationValidator {

  public static final String CONFIG_PREFIX = "app-version.";

  private final ApplicationVersionConfiguration config;

  public ApplicationVersionConfigurationValidator(ApplicationVersionConfiguration config) {
    this.config = config;
  }

  @Override
  public ValidationResult validate() {
    this.errors = new ValidationResult();
    validateApplicationVersionInfo("ios", config.getIos());
    validateApplicationVersionInfo("android", config.getAndroid());
    return this.errors;
  }

  private void validateApplicationVersionInfo(String name, ApplicationVersionInfo appVersionInfo) {
    SemanticVersion minVersion = appVersionInfo.getMin();
    ComparisonResult comparisonResult = compare(appVersionInfo.getLatest(), minVersion);
    if (ComparisonResult.LOWER.equals(comparisonResult)) {
      this.errors.add(new ValidationError(CONFIG_PREFIX + name + ".[latest|min]",
          minVersion.getMajor() + "." + minVersion.getMinor() + "." + minVersion.getPatch(), MIN_GREATER_THAN_MAX));
    }
  }

  private ComparisonResult comparePart(int left, int right) {
    if (left < right) {
      return ComparisonResult.LOWER;
    }
    if (right < left) {
      return ComparisonResult.HIGHER;
    }
    return ComparisonResult.EQUAL;
  }

  private ComparisonResult compare(SemanticVersion left, SemanticVersion right) {
    ComparisonResult compareMajor = comparePart(left.getMajor(), right.getMajor());
    ComparisonResult compareMinor = comparePart(left.getMinor(), right.getMinor());
    ComparisonResult comparePatch = comparePart(left.getPatch(), right.getPatch());

    return compareMajor != ComparisonResult.EQUAL ? compareMajor
         : compareMinor != ComparisonResult.EQUAL ? compareMinor
         : comparePatch != ComparisonResult.EQUAL ? comparePatch
         : ComparisonResult.EQUAL;
  }

  private enum ComparisonResult {
    LOWER,
    EQUAL,
    HIGHER
  }
}
