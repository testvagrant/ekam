package com.testvagrant.ekam.atoms.mobile;

import org.openqa.selenium.By;

public interface QueryFunctions {

  default By query(String value) {
    return By.xpath(value); // Default mobile query is by xpath
  }

  default By queryByText(String tagName, String text) {
    return By.xpath(String.format("//%1$s[@text='%2$s']", tagName, text));
  }

  default By queryById(String id) {
    return By.id(id);
  }

  default By queryByContentDesc(String value) {
    return queryByAttribute("content-desc", value);
  }

  default By queryByName(String name) {
    return By.name(name);
  }

  default By queryByLinkText(String linkText) {
    return By.linkText(linkText);
  }

  default By queryByPartialLinkText(String partialLinkText) {
    return By.linkText(partialLinkText);
  }

  default By queryByTagName(String tagName) {
    return By.linkText(tagName);
  }

  default By queryByAttribute(String attributeName, String value) {
    return queryByAttribute("*", attributeName, value);
  }

  default By queryByAttribute(String tagName, String attributeName, String value) {
    return query(String.format("//%s[@%s='%s']", tagName, attributeName, value));
  }

  default By queryByIgnoreSpaceAndCase(String attribute, String value) {
    return query(
        String.format(
            "//*[normalize-space(translate(@%1$s, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '%2$s']",
            attribute, value.toLowerCase()));
  }

  default By queryByIgnoreSpaceAndCase(String value) {
    return query(
        String.format(
            "//*[normalize-space(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) = '%1$s']",
            value.toLowerCase()));
  }

  default By queryByIgnoreSpaceAndCase(String value, Boolean partial) {
    if (!partial) {
      return queryByIgnoreSpaceAndCase(value);
    }

    return query(
        String.format(
            "//*[contains(normalize-space(translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) , '%1$s')]",
            value.toLowerCase()));
  }

  default By queryByIgnoreSpaceAndCase(String attribute, String value, Boolean partial) {
    if (!partial) {
      return queryByIgnoreSpaceAndCase(attribute, value);
    }

    return query(
        String.format(
            "//*[contains(normalize-space(translate(@%1%s, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')) , '%2$s')]",
            attribute, value.toLowerCase()));
  }
}
