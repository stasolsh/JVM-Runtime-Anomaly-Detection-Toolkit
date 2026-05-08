package org.example.anomaly;

import org.example.anomaly.detectcpuspikes.OsSnapshot;
import org.example.anomaly.detectgcthrash.GcSnapshot;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SnapshotTest {

    @Test
    void shouldCreateOsSnapshot() {
        OsSnapshot snapshot = new OsSnapshot(0.5, 0.7, 10, 100);

        assertThat(snapshot.processCpuLoad()).isEqualTo(0.5);
        assertThat(snapshot.systemCpuLoad()).isEqualTo(0.7);
        assertThat(snapshot.openFds()).isEqualTo(10);
        assertThat(snapshot.maxFds()).isEqualTo(100);
    }

    @Test
    void shouldCreateGcSnapshot() {
        GcSnapshot snapshot = new GcSnapshot(
                1000,
                200,
                5,
                800,
                1000,
                0.20,
                0.80
        );

        assertThat(snapshot.intervalMs()).isEqualTo(1000);
        assertThat(snapshot.gcTimeMs()).isEqualTo(200);
        assertThat(snapshot.gcCount()).isEqualTo(5);
        assertThat(snapshot.heapUsed()).isEqualTo(800);
        assertThat(snapshot.heapMax()).isEqualTo(1000);
        assertThat(snapshot.gcCpuRatio()).isEqualTo(0.20);
        assertThat(snapshot.heapUsedRatio()).isEqualTo(0.80);
    }
}
