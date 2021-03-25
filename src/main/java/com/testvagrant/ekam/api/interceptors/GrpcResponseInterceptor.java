package com.testvagrant.ekam.api.interceptors;

import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;
import java.util.Objects;

public class GrpcResponseInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Arrays.stream(invocation.getArguments()).forEach(arg -> logGrpcRequest(arg.toString()));
        Object proceed = invocation.proceed();
        logGrpcResponse(proceed.toString());
        return proceed;
    }


    private void logGrpcRequest(String req) {
        final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();
        final HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder
                .create("Request", "")
                .setBody(req);
        processor.addAttachment(requestAttachmentBuilder.build(), new FreemarkerAttachmentRenderer("http-request.ftl"));
    }

    private void logGrpcResponse(String resp) {
        final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();
        final HttpResponseAttachment.Builder responseAttachmentBuilder = HttpResponseAttachment.Builder
                .create("Response");

        if (Objects.nonNull(resp)) {
            responseAttachmentBuilder.setBody(resp);
        }

        final HttpResponseAttachment responseAttachment = responseAttachmentBuilder.build();
        processor.addAttachment(responseAttachment, new FreemarkerAttachmentRenderer("http-response.ftl"));
    }
}
