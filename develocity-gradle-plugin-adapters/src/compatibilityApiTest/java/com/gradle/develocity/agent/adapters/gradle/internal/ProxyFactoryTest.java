package com.gradle.develocity.agent.adapters.gradle.internal;

import com.gradle.develocity.agent.gradle.adapters.internal.ProxyFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProxyFactoryTest {

    @Test
    @DisplayName("adapts enum method arguments")
    void testEnumArg() {
        // given
        TargetWithEnum target = new TargetWithEnumImpl();
        TargetWithEnum proxy = ProxyFactory.createProxy(target, TargetWithEnum.class);

        // when
        String result = proxy.methodWithEnumArg(TargetWithEnum.TargetEnum.B);

        // then
        assertEquals("B", result);
    }

    @Test
    @DisplayName("adapts enum return values")
    void testEnumReturn() {
        // given
        TargetWithEnum target = new TargetWithEnumImpl();
        TargetWithEnum proxy = ProxyFactory.createProxy(target, TargetWithEnum.class);

        // when
        TargetWithEnum.TargetEnum result = proxy.methodReturningEnum();

        // then
        assertEquals(TargetWithEnum.TargetEnum.A, result);
    }

    interface TargetWithEnum {
        enum TargetEnum {
            A, B, C
        }

        String methodWithEnumArg(TargetEnum arg);

        TargetEnum methodReturningEnum();
    }

    class TargetWithEnumImpl implements TargetWithEnum {

        @Override
        public String methodWithEnumArg(TargetEnum arg) {
            return arg.name();
        }

        @Override
        public TargetEnum methodReturningEnum() {
            return TargetEnum.A;
        }
    }

}
