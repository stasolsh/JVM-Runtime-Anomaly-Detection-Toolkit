package org.example.anomaly.detectcpuspikes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OsAnomalyProbeTest {

    private final OsAnomalyProbe probe = new OsAnomalyProbe();

    @Test
    void shouldDetectCpuSpikeWhenProcessCpuLoadIsAboveThreshold() {
        OsSnapshot snapshot = new OsSnapshot(0.91, 0.5, 10, 100);

        assertThat(probe.cpuSpike(snapshot)).isTrue();
    }

    @Test
    void shouldNotDetectCpuSpikeWhenProcessCpuLoadIsBelowThreshold() {
        OsSnapshot snapshot = new OsSnapshot(0.90, 0.5, 10, 100);

        assertThat(probe.cpuSpike(snapshot)).isFalse();
    }

    @Test
    void shouldNotDetectCpuSpikeWhenCpuLoadIsUnavailable() {
        OsSnapshot snapshot = new OsSnapshot(-1.0, -1.0, 10, 100);

        assertThat(probe.cpuSpike(snapshot)).isFalse();
    }

    @Test
    void shouldDetectFileDescriptorLeakWhenUsageIsHigh() {
        OsSnapshot snapshot = new OsSnapshot(0.1, 0.2, 81, 100);

        assertThat(probe.fdLeakSuspected(snapshot)).isTrue();
    }

    @Test
    void shouldNotDetectFileDescriptorLeakWhenUsageIsLow() {
        OsSnapshot snapshot = new OsSnapshot(0.1, 0.2, 80, 100);

        assertThat(probe.fdLeakSuspected(snapshot)).isFalse();
    }

    @Test
    void shouldNotDetectFileDescriptorLeakWhenValuesAreUnavailable() {
        OsSnapshot snapshot = new OsSnapshot(0.1, 0.2, -1, -1);

        assertThat(probe.fdLeakSuspected(snapshot)).isFalse();
    }

    @Test
    void shouldReturnOsSnapshotFromSample() {
        OsSnapshot snapshot = probe.sample();

        assertThat(snapshot).isNotNull();
    }

}
