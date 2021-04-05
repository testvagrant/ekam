package com.testvagrant.ekam.commons.models;

import com.github.javafaker.Faker;

public interface Model<Model> {

  /**
   * Creates entity with random date for test parallelism. Implement only for entities that needs
   * radomness;
   *
   * @return
   */
  default Model random() {
    throw new UnsupportedOperationException();
  }

  Model init();

  default Faker faker() {
    return new Faker();
  }
}
