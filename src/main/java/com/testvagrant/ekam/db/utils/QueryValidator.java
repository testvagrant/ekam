package com.testvagrant.ekam.db.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryValidator {
  private final Connection connection;
  private volatile int resultSetCount;

  public QueryValidator(Connection connection) {
    this.connection = connection;
  }

  public boolean isUpdateQueryValid(String query) {
    if (!isValidQuery(query)) return false;
    String selectQuery = convertUpdateToSelect(query);
    try {
      PreparedStatement prepStatement = connection.prepareStatement(selectQuery);
      ResultSet resultSet = prepStatement.executeQuery();
      while (resultSet.next()) {
        resultSetCount++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resultSetCount == 1;
  }

  public boolean isValidQuery(String query) {
    return query.toLowerCase().contains("where");
  }

  private String convertUpdateToSelect(String updateQuery) {
    String updatePattern = "UPDATE (.*) SET (.*) WHERE (.*)";
    Pattern pattern = Pattern.compile(updatePattern);
    Matcher matcher = pattern.matcher(updateQuery);
    if (matcher.matches()) {
      String tableName = matcher.group(1);
      String whereCondition = matcher.group(3);
      return String.format("SELECT * FROM %S WHERE %S", tableName, whereCondition);
    }
    return "";
  }
}
