package com.testvagrant.ekam.commons.workflows;

public interface Workflow<Layout, Next> {

  Layout create();

  Next next();
}
