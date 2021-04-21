package com.testvagrant.ekam.db.exceptions;

public class InvalidQueryException extends RuntimeException {

  public InvalidQueryException(String query) {
    super(
        String.format(
            "Query \"%s\" does not contain where clause or query updates more than one row. Update query should contain a where clause and should not update more than 1 row at a time",
            query));
  }
}
