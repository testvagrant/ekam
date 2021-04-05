package com.testvagrant.ekam.integrations.slack;

import com.palantir.roboslack.api.attachments.Attachment;
import com.palantir.roboslack.api.attachments.components.Field;
import com.testvagrant.ekam.commons.Toggles;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

public class SlackListener extends TestListenerAdapter {

  @Override
  public void onFinish(ITestContext testContext) {
    if (Toggles.SLACK_NOTIF.isOn()) {
      SlackClient slackClient = new SlackClient();
      Field passed_count = buildField("Passed Count", testContext.getPassedTests().size());
      Field failed_count = buildField("Failed Count", testContext.getFailedTests().size());
      Field skipped_count = buildField("Skipped Count", testContext.getSkippedTests().size());
      Field total_tests = buildField("Total Tests", testContext.getAllTestMethods().length);
      boolean state = isBuildPassed(testContext);
      Attachment attachment =
          slackClient.buildGroupAttachment(
              state, passed_count, failed_count, skipped_count, total_tests);
      if (!isBuildPassed(testContext) || Toggles.SLACK_NOTIFY_ME_EVERYTIME.isOn()) {
        slackClient.postMessage(attachment);
      }
    }
  }

  private boolean isBuildPassed(ITestContext testContext) {
    return testContext.getFailedTests().size() == 0;
  }

  public Field buildField(String title, int value) {
    return Field.of(title, String.valueOf(value));
  }
}
