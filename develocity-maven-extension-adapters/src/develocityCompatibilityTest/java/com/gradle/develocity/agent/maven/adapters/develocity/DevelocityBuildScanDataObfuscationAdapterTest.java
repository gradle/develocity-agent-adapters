package com.gradle.develocity.agent.maven.adapters.develocity;

import com.gradle.develocity.agent.maven.adapters.BuildScanDataObfuscationAdapter;
import com.gradle.develocity.agent.maven.api.scan.BuildScanApi;
import com.gradle.develocity.agent.maven.api.scan.BuildScanDataObfuscation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubber;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gradle.develocity.agent.maven.adapters.develocity.MockFactory.createBuildScanApi;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevelocityBuildScanDataObfuscationAdapterTest {

    private BuildScanDataObfuscation obfuscation;
    private BuildScanDataObfuscationAdapter adapter;

    @BeforeEach
    void setup() {
        obfuscation = mock();
        BuildScanApi buildScanApi = createBuildScanApi();
        when(buildScanApi.getObfuscation()).thenReturn(obfuscation);
        adapter = new DevelocityBuildScanApiAdapter(buildScanApi).getObfuscation();
    }

    @Test
    @DisplayName("can obfuscate username using proxy")
    void testUsername() {
        // given
        AtomicReference<String> capture = new AtomicReference<>();
        captureReturnValue("username", capture::set).when(obfuscation).username(any());

        // when
        adapter.username(it -> it + "_obfuscated");

        // then
        assertEquals("username_obfuscated", capture.get());
    }

    @Test
    @DisplayName("can obfuscate hostname using proxy")
    void testHostname() {
        // given
        AtomicReference<String> capture = new AtomicReference<>();
        captureReturnValue("hostname", capture::set).when(obfuscation).hostname(any());

        // when
        adapter.hostname(it -> it + "_obfuscated");

        // then
        assertEquals("hostname_obfuscated", capture.get());
    }

    @Test
    @DisplayName("can obfuscate IP addresses using proxy")
    void testIpAddresses() throws UnknownHostException {
        // given
        AtomicReference<List<String>> capture = new AtomicReference<>();
        captureReturnValue(Arrays.asList(InetAddress.getByName("1.2.3.4"), InetAddress.getByName("5.6.7.8")), capture::set).when(obfuscation).ipAddresses(any());

        // when
        adapter.ipAddresses(it -> it.stream().map(address -> address.toString() + "_obfuscated").collect(Collectors.toList()));

        // then
        assertEquals(Arrays.asList("/1.2.3.4_obfuscated", "/5.6.7.8_obfuscated"), capture.get());
    }

    private <I, O> Stubber captureReturnValue(I input, Consumer<O> resultConsumer) {
        return doAnswer(invocation -> {
            Function<I, O> func = invocation.getArgument(0);
            resultConsumer.accept(func.apply(input));
            return null;
        });
    }

}
