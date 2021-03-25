package com.testvagrant.ekam.integrations.slack;

import com.palantir.roboslack.api.attachments.Attachment;
import com.palantir.roboslack.api.attachments.components.Field;
import com.testvagrant.ekam.commons.SystemProperties;
import com.testvagrant.ekam.integrations.slack.SlackClient;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

public class SlackListener extends TestListenerAdapter {

    SlackClient slackClient;

    @Override
    public void onFinish(ITestContext testContext) {
        if(SystemProperties.SLACK_NOTIF) {
            slackClient = new SlackClient();
            Field passed_count = buildField("Passed Count", testContext.getPassedTests().size());
            Field failed_count = buildField("Failed Count", testContext.getFailedTests().size());
            Field skipped_count = buildField("Skipped Count", testContext.getSkippedTests().size());
            Field total_tests = buildField("Total Tests", testContext.getAllTestMethods().length);
            boolean state = testContext.getFailedTests().size() == 0;
            Attachment attachment = slackClient.buildGroupAttachment(state, passed_count, failed_count, skipped_count, total_tests);
            slackClient.postMessage(attachment);
        }
    }


    public Field buildField(String title, int value) {
        return Field.of(title, String.valueOf(value));
    }

}
