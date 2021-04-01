package com.testvagrant.ekam.atoms.web;

import org.openqa.selenium.By;

public interface QueryFunctions {
  default By query(String value) {
    return value.contains("//") ? By.xpath(value) : By.cssSelector(value);
  }

  default By queryById(String id) {
    return By.id(id);
  }

  default By queryByName(String name) {
    return By.name(name);
  }

  default String ignoreSpaceAndCase(String attribute, String value) {
    return String.format(
        "normalize-space(translate(@%1$s, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '%2$s'",
        attribute, value.toLowerCase());
  }

  default String ignoreSpaceAndCase(String value) {
    return String.format(
        "normalize-space(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '%1$s'",
        value.toLowerCase());
  }

  default String ignoreSpaceAndCase(String value, Boolean partial) {
    if (!partial) {
      return ignoreSpaceAndCase(value);
    }

    return String.format(
        "contains(normalize-space(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) , '%1$s')",
        value.toLowerCase());
  }

  default String ignoreSpaceAndCase(String attribute, String value, Boolean partial) {
    if (!partial) {
      return ignoreSpaceAndCase(attribute, value);
    }

    return String.format(
        "contains(normalize-space(translate(@%1%s, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) , '%2$s')",
        attribute, value.toLowerCase());
  }
}