package com.gradle.develocity.agent.adapters;

import org.gradle.api.provider.Property;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

public final class PropertyMockFixtures {

    private PropertyMockFixtures() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Property<T> mockProperty() {
        return (Property<T>) mock(Property.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> Property<T> mockPropertyReturning(T value) {
        Property<T> prop = (Property<T>) mock(Property.class);
        lenient().when(prop.get()).thenReturn(value);
        lenient().when(prop.getOrNull()).thenReturn(value);
        return prop;
    }

}
