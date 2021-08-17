package com.testvagrant.ekam.dataclients;

import com.google.gson.internal.LinkedTreeMap;
import com.testvagrant.ekam.commons.data.DataSetsClient;
import com.testvagrant.ekam.models.Credentials;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class CredentialsDataClient extends DataSetsClient {

  public List<Credentials> getAuthorizedUsers() {
    List<Credentials> credentials = getCredentialsList(DataKeys.AUTHORIZED_USER);
    if (credentials.isEmpty()) {
      throw new RuntimeException("No credentials found for the key" + DataKeys.AUTHORIZED_USER);
    }

    return credentials;
  }

  public Credentials getAuthorizedUser(boolean lockUser) {
    Credentials credentials = getUser(DataKeys.AUTHORIZED_USER, lockUser);
    if (Objects.isNull(credentials.getUsername())) {
      throw new RuntimeException("No credentials available/Credentials locked at the moment");
    }

    return credentials;
  }

  public Credentials getUnAuthorizedUser(boolean lockUser) {
    Credentials credentials = getUser(DataKeys.UNAUTHORIZED_USER, lockUser);
    if (Objects.isNull(credentials.getUsername())) {
      throw new RuntimeException("No credentials available/Credentials locked at the moment");
    }

    return credentials;
  }

  public Credentials getUser(String key, boolean lockUser) {
    return getValue(key, Credentials.class, lockUser);
  }

  public List<Credentials> getCredentialsList(String key) {
    return getListValue(key, Credentials.class);
  }

  public void release(Credentials credentials) {
    if (Objects.nonNull(credentials) && Objects.nonNull(credentials.getUsername())) {
      super.release(credentialsPredicate(credentials));
    }
  }

  private Predicate<Map.Entry<String, LinkedTreeMap>> credentialsPredicate(
      Credentials credentials) {
    return entry -> entry.getValue().get("username").equals(credentials.getUsername());
  }

  public static final class DataKeys {
    public static final String AUTHORIZED_USER = "authorizedUser";
    public static final String UNAUTHORIZED_USER = "unauthorizedUser";
  }
}
