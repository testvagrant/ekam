package com.testvagrant.ekam.data;

import com.testvagrant.ekam.commons.io.ResourcePaths;
import com.testvagrant.ekam.dataclients.ListDataClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataSetsNegativeTests extends CacheTestBase {

  @Test
  @ClearSystemProperty(key = "datasets.env")
  @SetSystemProperty(key = "datasets.dir", value = "blahblahblah")
  public void shouldThrowException() {
    ListDataClient dataClient = new ListDataClient();
    Exception exception =
        assertThrows(RuntimeException.class, () -> dataClient.getList("package_managers"));
    String exceptionMessage = exception.getMessage();

    Assertions.assertTrue(
        exceptionMessage.contains("'package_managers' key not found in data_sets")
            && exceptionMessage.contains(
                String.format(
                    "Files searched under directory: '%s/%s'",
                    ResourcePaths.TEST_RESOURCES, "blahblahblah")));
  }

  @Test
  @SetSystemProperty(key = "datasets.dir", value = "data_sets")
  @SetSystemProperty(key = "datasets.env", value = "staging")
  public void shouldThrowExceptionWhenFileNotPresentForAnEnv() {
    ListDataClient dataClient = new ListDataClient();
    Exception exception =
        assertThrows(
            RuntimeException.class,
            () -> dataClient.getList("package_managers"),
            ".*'package_managers' key not found in data_sets for Env: 'staging'.*");

    String exceptionMessage = exception.getMessage();

    String keyNotFoundInEnv = "'package_managers' key not found in data_sets for Env: 'staging'";
    String searchDirectory =
        String.format(
            "Files searched under directory: '%s/%s'",
            ResourcePaths.TEST_RESOURCES, System.getProperty("datasets.dir"));

    Assertions.assertTrue(
        exceptionMessage.contains(keyNotFoundInEnv) && exceptionMessage.contains(searchDirectory));
  }
}
