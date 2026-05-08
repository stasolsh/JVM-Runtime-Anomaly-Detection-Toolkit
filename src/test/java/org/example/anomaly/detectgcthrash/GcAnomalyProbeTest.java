package org.example.anomaly.detectgcthrash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GcAnomalyProbeTest {

    private final GcAnomalyProbe probe = new GcAnomalyProbe();

    @Test
    void shouldDetectGcThrashingWhenGcRatioHighAndGcCountEnough() {
        GcSnapshot snapshot = new GcSnapshot(
                1000,
                300,
                5,
                100,
                1000,
                0.30,
                0.10
        );

        assertThat(probe.isGcThrashing(snapshot)).isTrue();
    }

    @Test
    void shouldNotDetectGcThrashingWhenGcRatioIsLow() {
        GcSnapshot snapshot = new GcSnapshot(
                1000,
                100,
                5,
                100,
                1000,
                0.10,
                0.10
        );

        assertThat(probe.isGcThrashing(snapshot)).isFalse();
    }

    @Test
    void shouldNotDetectGcThrashingWhenGcCountIsTooLow() {
        GcSnapshot snapshot = new GcSnapshot(
                1000,
                300,
                4,
                100,
                1000,
                0.30,
                0.10
        );

        assertThat(probe.isGcThrashing(snapshot)).isFalse();
    }

    @Test
    void shouldDetectHighHeapUsage() {
        GcSnapshot snapshot = new GcSnapshot(
                1000,
                0,
                0,
                900,
                1000,
                0.0,
                0.90
        );

        assertThat(probe.isHeapHigh(snapshot)).isTrue();
    }

    @Test
    void shouldNotDetectHighHeapUsageWhenBelowThreshold() {
        GcSnapshot snapshot = new GcSnapshot(
                1000,
                0,
                0,
                800,
                1000,
                0.0,
                0.80
        );

        assertThat(probe.isHeapHigh(snapshot)).isFalse();
    }

    @Test
    void shouldReturnGcSnapshotFromSample() {
        GcSnapshot snapshot = probe.sample();

        assertThat(snapshot).isNotNull();
        assertThat(snapshot.intervalMs()).isPositive();
        assertThat(snapshot.heapUsed()).isGreaterThanOrEqualTo(0);
        assertThat(snapshot.heapMax()).isGreaterThan(0);
    }
}