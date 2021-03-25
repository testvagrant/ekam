package com.testvagrant.ekam.integrations.slack;

import com.palantir.roboslack.api.MessageRequest;
import com.palantir.roboslack.api.attachments.Attachment;
import com.palantir.roboslack.api.attachments.components.Color;
import com.palantir.roboslack.api.attachments.components.Field;
import com.palantir.roboslack.api.attachments.components.Title;
import com.palantir.roboslack.api.markdown.SlackMarkdown;
import com.palantir.roboslack.webhook.SlackWebHookService;
import com.palantir.roboslack.webhook.api.model.WebHookToken;
import com.palantir.roboslack.webhook.api.model.response.ResponseCode;
import com.testvagrant.ekam.commons.SystemProperties;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;


public class SlackClient {


    private String channelToken;
    private WebHookToken webHookToken;

    public SlackClient() {
        this.channelToken = SystemProperties.SLACK_CHANNEL_TOKEN;
        if(Objects.isNull(channelToken)) {
            throw new RuntimeException("SlackNotif is true but channel token is not specified in env");
        }
        String[] tokens = channelToken.split("/");
        if(tokens.length!=3) {
            throw new RuntimeException("Invalid slack token");
        }
        webHookToken = WebHookToken.builder()
                .partT(tokens[0])
                .partB(tokens[1])
                .partX(tokens[2])
                .build();
    }

    public void postMessage(Attachment... attachments) {
        MessageRequest messageRequest = MessageRequest.builder()
                .username("Autobot Slack")
                .iconEmoji(SlackMarkdown.EMOJI.decorate("robot_face"))
                .text("Autobot Test Report " + SlackMarkdown.LINK.decorate(url(SystemProperties.PAGES_URL)," [Report Link]"))
                .addAttachments(attachments)
                .build();

        ResponseCode responseCode = SlackWebHookService.with(webHookToken).sendMessage(messageRequest);
        System.out.println(responseCode);
    }

    public Attachment buildGroupAttachment(boolean pass, Field... fields) {
        Attachment.Builder attachment = Attachment.builder()
                .addFields(fields)
                .title(Title.of("Test Status"));
        setColor(pass, attachment);
        attachment.fallback("Test report");
        return attachment.build();
    }

    private void setColor(boolean pass, Attachment.Builder attachment) {
        if(pass) {
            attachment.color(Color.good());
        } else {
            attachment.color(Color.danger());
        }
    }


    private static URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getLocalizedMessage(), e);
        }
    }
}
