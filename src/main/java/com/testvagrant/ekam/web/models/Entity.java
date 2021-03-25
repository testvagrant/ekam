package com.testvagrant.ekam.web.models;


public interface Entity<Entity> {

    /**
     * Creates entity with random date for test parallelism.
     * Implement only for entities that needs radomness;
     * @return
     */
    default Entity random() { throw new UnsupportedOperationException();}

    Entity init();
}
