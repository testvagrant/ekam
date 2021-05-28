package com.testvagrant.ekam.commons.workflows;

import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class UseCase {
  @Builder.Default private Map<Class<?>, Object> useCases = new ConcurrentHashMap<>();
  @Builder.Default private List<Class<?>> completedStates = new CopyOnWriteArrayList<>();

  public UseCase addToUseCase(Object data) {
    useCases.put(data.getClass(), data);
    return this;
  }

  public <Q> Q getData(Class<Q> tClass) {
    return (Q) useCases.get(tClass);
  }

  public WorkflowDefinition persistState(WorkflowDefinition currentState) {
      this.completedStates.add(currentState.getClass());
      return currentState;
  }

  public boolean isACompletedState(WorkflowDefinition workflowDefinition){
    return completedStates.contains(workflowDefinition.getClass());
  }
}
