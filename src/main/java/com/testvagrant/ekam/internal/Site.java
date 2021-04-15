package com.testvagrant.ekam.internal;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.testvagrant.ekam.commons.models.Model;
import lombok.*;
import org.testng.Assert;

import java.util.Objects;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Site implements Model<Site> {
  private String title;

  @Inject
  @Named("title")
  String siteTitle;

  @Override
  public Site init() {
    return this.toBuilder().title("").build();
  }

  public void assertThatSiteIsUp() {
    Assert.assertTrue(title.startsWith(siteTitle));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Site site = (Site) o;
    return Objects.equals(title, site.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }

  @Override
  public String toString() {
    return "{" + "\"title\":\"" + title + "\"" + "}}";
  }
}
