package org.example.anomaly.detectdeadlocks;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class ThreadAnomalyProbeTest {

    @Test
    void shouldFindDeadlockedThreadsWithoutThrowing() {
        ThreadAnomalyProbe probe = new ThreadAnomalyProbe();

        assertThatCode(probe::findDeadlockedThreads)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldPrintTopBlockedThreadsWithoutThrowing() {
        ThreadAnomalyProbe probe = new ThreadAnomalyProbe();

        assertThatCode(() -> probe.printTopBlockedThreads(3))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldHandleZeroTopBlockedThreads() {
        ThreadAnomalyProbe probe = new ThreadAnomalyProbe();

        assertThatCode(() -> probe.printTopBlockedThreads(0))
                .doesNotThrowAnyException();
    }
}
