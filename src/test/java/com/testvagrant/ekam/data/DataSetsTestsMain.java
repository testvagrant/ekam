package com.testvagrant.ekam.data;

import com.testvagrant.ekam.commons.data.DataSetsClient;
import com.testvagrant.ekam.dataclients.CredentialsDataClient;
import com.testvagrant.ekam.dataclients.DataClient;
import com.testvagrant.ekam.models.Credentials;
import com.testvagrant.ekam.models.Models;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetSystemProperty;

import java.util.List;

@SetSystemProperty(key = "datasets.dir", value = "data_sets_main")
public class DataSetsTestsMain extends CacheTestBase {

  @Test
  public void shouldLockDataWhenSpecified() {
    CredentialsDataClient credentialsDataClient = new CredentialsDataClient();
    Credentials standardUser = credentialsDataClient.getAuthorizedUser(true);
    Credentials anotherStandardUser = credentialsDataClient.getAuthorizedUser(true);

    credentialsDataClient.release(standardUser);
    credentialsDataClient.release(anotherStandardUser);

    Assertions.assertNotEquals(standardUser.getUsername(), anotherStandardUser.getUsername());
  }

  @Test
  public void shouldNotLockDataUnlessSpecified() {
    CredentialsDataClient credentialsDataClient = new CredentialsDataClient();
    Credentials standardUser = credentialsDataClient.getUnAuthorizedUser(false);
    Credentials anotherStandardUser = credentialsDataClient.getUnAuthorizedUser(false);

    credentialsDataClient.release(standardUser);
    credentialsDataClient.release(anotherStandardUser);

    Assertions.assertEquals(standardUser.getUsername(), anotherStandardUser.getUsername());
  }

  @Test
  @SetSystemProperty(key = "datasets.env", value = "staging")
  public void shouldFetchDataFromStagingEnvironment() {
    CredentialsDataClient credentialsDataClient = new CredentialsDataClient();
    Credentials user = credentialsDataClient.getAuthorizedUser(true);
    Assertions.assertTrue(user.getUsername().contains("staging"));
  }

  @Test
  @SetSystemProperty(key = "datasets.env", value = "qa")
  public void shouldFetchDataFromQAEnvironment() {
    CredentialsDataClient credentialsDataClient = new CredentialsDataClient();
    Credentials user = credentialsDataClient.getAuthorizedUser(true);
    Assertions.assertTrue(user.getUsername().contains("qa"));
  }

  @Test
  @SetSystemProperty(key = "datasets.env", value = "qa")
  @SetSystemProperty(key = "env", value = "staging")
  public void shouldFetchFromDataSetsEnvEvenIfEnvIsSpecified() {
    CredentialsDataClient credentialsDataClient = new CredentialsDataClient();
    Credentials user = credentialsDataClient.getAuthorizedUser(true);
    Assertions.assertTrue(user.getUsername().contains("qa"));
  }

  @Test
  public void should_Fetch_Key_That_Matches_Exact_Key_When_Multiple_Keys_StartWith_Same_Name() {
    DataClient dataClient = new DataClient();
    String debitCardsType = dataClient.get("debitCards", String.class);
    String debitCardsGatewayType = dataClient.get("debitCardsGateway", String.class);
    Assertions.assertEquals(debitCardsType, "visa");
    Assertions.assertEquals(debitCardsGatewayType, "mastercard");
  }

  @Test
  public void shouldGetList() {
    DataSetsClient dataClient = new DataSetsClient();
    List<String> packageManagers = dataClient.getListValue("phone_models");
    Assertions.assertEquals(packageManagers.size(), 7);
  }

  @Test
  public void shouldGetListValuesForKey() {
    DataSetsClient dataClient = new DataSetsClient();
    List<Models> models = dataClient.getListValue("models", Models.class);
    Assertions.assertEquals(models.size(), 2);
  }
}
