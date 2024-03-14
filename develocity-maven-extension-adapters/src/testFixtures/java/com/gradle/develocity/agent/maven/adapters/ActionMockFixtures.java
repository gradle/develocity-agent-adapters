package com.gradle.develocity.agent.maven.adapters;

import org.mockito.stubbing.Stubber;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.mockito.Mockito.doAnswer;

public final class ActionMockFixtures {

    private ActionMockFixtures() {
    }


    public static <T> Stubber doExecuteActionWith(T obj) {
        return doExecuteActionWith(obj, 0);
    }

    public static <T> Stubber doExecuteActionWith(T obj, int actionArgIdx) {
        return doAnswer(invocation -> {
            Consumer<? super T> action = invocation.getArgument(actionArgIdx);
            action.accept(obj);
            return null;
        });
    }

    public static class ArgCapturingAction<T> implements Consumer<T> {

        private final AtomicReference<T> arg = new AtomicReference<>();

        @Override
        public void accept(T t) {
            arg.set(t);
        }

        public T getValue() {
            return arg.get();
        }
    }

}
