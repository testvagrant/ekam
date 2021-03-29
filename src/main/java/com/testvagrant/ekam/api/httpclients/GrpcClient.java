package com.testvagrant.ekam.api.httpclients;

import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class GrpcClient {
  public ManagedChannel build(String host) throws URISyntaxException {
    URI uri = new URI(host);
    return NettyChannelBuilder.forAddress(uri.getHost(), uri.getPort())
        .negotiationType(NegotiationType.PLAINTEXT)
        .build();
  }
}
