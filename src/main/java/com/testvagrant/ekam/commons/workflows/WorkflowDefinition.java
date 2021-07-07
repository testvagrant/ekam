package com.testvagrant.ekam.commons.workflows;

public abstract class WorkflowDefinition implements Workflow {

  protected UseCase useCase;

  public WorkflowDefinition() {
    this.useCase = UseCase.builder().build();
  }

  public WorkflowDefinition(UseCase useCase) {
    this.useCase = useCase;
  }

  protected <Current, Next> Next proceedToNext(
      FulfillCondition<Current> fulfillCondition, Next whereTo) {
    if (!useCase.isACompletedState(this)) {
      fulfillCondition.apply();
      useCase.persistState(this);
    }
    return whereTo;
  }
}
