package org.example.anomaly.anomalymonitor;

import org.junit.jupiter.api.Test;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AnomalyMonitorTest {

    @Test
    void shouldHaveDefaultThresholds() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        assertThat(monitor.getHeapHighThreshold()).isEqualTo(0.85);
        assertThat(monitor.getGcThrashRatioThreshold()).isEqualTo(0.20);
    }

    @Test
    void shouldUpdateHeapHighThreshold() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        monitor.setHeapHighThreshold(0.70);

        assertThat(monitor.getHeapHighThreshold()).isEqualTo(0.70);
    }

    @Test
    void shouldRejectInvalidHeapHighThresholds() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        assertThatThrownBy(() -> monitor.setHeapHighThreshold(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 < v < 1");

        assertThatThrownBy(() -> monitor.setHeapHighThreshold(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 < v < 1");

        assertThatThrownBy(() -> monitor.setHeapHighThreshold(-0.1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 < v < 1");
    }

    @Test
    void shouldUpdateGcThrashRatioThreshold() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        monitor.setGcThrashRatioThreshold(0.50);

        assertThat(monitor.getGcThrashRatioThreshold()).isEqualTo(0.50);
    }

    @Test
    void shouldAllowGcThrashRatioThresholdEqualToOne() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        monitor.setGcThrashRatioThreshold(1.0);

        assertThat(monitor.getGcThrashRatioThreshold()).isEqualTo(1.0);
    }

    @Test
    void shouldRejectInvalidGcThrashRatioThresholds() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        assertThatThrownBy(() -> monitor.setGcThrashRatioThreshold(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 < v < 1");

        assertThatThrownBy(() -> monitor.setGcThrashRatioThreshold(1.1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 < v < 1");

        assertThatThrownBy(() -> monitor.setGcThrashRatioThreshold(-0.1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("0 < v < 1");
    }

    @Test
    void shouldReturnNoActiveAnomaliesByDefault() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        assertThat(monitor.getActiveAnomalies()).isEmpty();
        assertThat(monitor.isGcThrashing()).isFalse();
        assertThat(monitor.isHeapHigh()).isFalse();
        assertThat(monitor.isCpuSpiking()).isFalse();
        assertThat(monitor.isDeadlocked()).isFalse();
    }

    @Test
    void tickShouldNotThrowException() {
        AnomalyMonitor monitor = new AnomalyMonitor();

        assertThatCode(monitor::tick)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldAllowAddingNotificationListener() {
        AnomalyMonitor monitor = new AnomalyMonitor();
        List<Notification> notifications = new ArrayList<>();

        monitor.addNotificationListener(
                (notification, handback) -> notifications.add(notification),
                null,
                null
        );

        assertThatCode(monitor::tick)
                .doesNotThrowAnyException();
    }
}
