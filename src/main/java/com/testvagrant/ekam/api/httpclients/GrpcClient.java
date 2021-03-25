package com.testvagrant.ekam.api.httpclients;

import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.internal.Platform;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.okhttp.OkHttpChannelBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class GrpcClient {

    public ManagedChannel build(String host) {
        URI uri = getUri(host);
        return NettyChannelBuilder.forAddress(uri.getHost(), uri.getPort())
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
    }

    private URI getUri(String host) {
        try {
            URI uri = new URI(host);
            return uri;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Invalid host "+host);
    }
}
