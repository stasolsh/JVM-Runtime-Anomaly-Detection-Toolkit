# JVM Runtime Anomaly Detection Toolkit

> Lightweight JVM anomaly detection toolkit for monitoring GC pressure, CPU spikes, deadlocks, heap usage, and runtime health using Java MXBeans and JMX notifications.

This project demonstrates practical runtime diagnostics and anomaly detection techniques commonly used in backend systems, observability tooling, SRE environments, and production troubleshooting.

The toolkit focuses on lightweight runtime monitoring without external agents or heavyweight observability platforms.

---

# Features

- GC thrashing detection
- High heap usage detection
- CPU spike monitoring
- Deadlock detection
- File descriptor usage monitoring
- JMX MXBean integration
- Runtime notifications
- JVM health snapshots
- Lightweight anomaly probes
- Production-oriented diagnostics examples

---

# Technologies Used

- Java
- JVM MXBeans
- JMX Notifications
- GarbageCollectorMXBean
- MemoryMXBean
- ThreadMXBean
- OperatingSystemMXBean
- Java Records
- Runtime Monitoring APIs

---

# Project Goals

This repository was created to:

- understand JVM runtime internals
- explore production anomaly detection
- practice JVM observability concepts
- learn runtime diagnostics techniques
- experiment with JMX and MXBeans
- demonstrate lightweight monitoring approaches
- improve troubleshooting and SRE-oriented skills

---

# Architecture Overview

```text
JVM Runtime
     ↓
MXBeans & Runtime APIs
     ↓
Anomaly Probes
 ├── GC Probe
 ├── Thread Probe
 └── OS Probe
     ↓
Anomaly Monitor
     ↓
JMX Notifications / Alerts
```

---

# Repository Structure

| Component                                                                                            | Description |
|------------------------------------------------------------------------------------------------------|---|
| [AnomalyMonitor.java](src/main/java/com/example/anomaly/anomalymonitor/AnomalyMonitor.java)          | Central anomaly monitoring engine coordinating all runtime probes and publishing JMX notifications |
| [AnomalyMXBean.java](src/main/java/com/example/anomaly/anomalymonitor/AnomalyMXBean.java)            | JMX MXBean interface exposing anomaly states and configurable thresholds |
| [GcAnomalyProbe.java](src/main/java/com/example/anomaly/detectgcthrash/GcAnomalyProbe.java)          | Detects GC thrashing and high heap usage using JVM garbage collector statistics |
| [GcSnapshot.java](src/main/java/com/example/anomaly/detectgcthrash/GcSnapshot.java)                  | Immutable runtime snapshot containing GC and heap metrics |
| [ThreadAnomalyProbe.java](src/main/java/com/example/anomaly/detectdeadlocks/ThreadAnomalyProbe.java) | Detects deadlocks and blocked thread contention |
| [OsAnomalyProbe.java](src/main/java/com/example/anomaly/detectcpuspikes/OsAnomalyProbe.java)         | Monitors CPU load and file descriptor usage |
| [OsSnapshot.java](src/main/java/com/example/anomaly/detectcpuspikes/OsSnapshot.java)                 | Immutable snapshot of operating system metrics |
| [Demo.java](src/main/java/com/example/anomaly/detectcpuspikes/Demo.java)                             | Example runtime demonstrations for anomaly detection scenarios |

---

# Core Runtime Anomalies

## GC Thrashing Detection

The toolkit monitors:

- GC collection frequency
- GC CPU time ratio
- heap usage pressure

An anomaly is triggered when:

- GC consumes excessive runtime
- heap usage remains critically high

Implemented in:
- [GcAnomalyProbe.java](src/main/java/com/example/anomaly/detectgcthrash/GcAnomalyProbe.java)

Based on:
:contentReference[oaicite:0]{index=0}

---

## CPU Spike Detection

The operating system probe monitors:

- JVM process CPU usage
- system-wide CPU load
- open file descriptor usage

An anomaly is triggered when:

- process CPU exceeds configured thresholds
- file descriptor usage approaches system limits

Implemented in:
- [OsAnomalyProbe.java](src/main/java/com/example/anomaly/detectcpuspikes/OsAnomalyProbe.java)

Based on:
:contentReference[oaicite:1]{index=1}

---

## Deadlock Detection

The thread probe uses `ThreadMXBean` to detect:

- monitor deadlocks
- ownable synchronizer deadlocks
- blocked thread contention

Implemented in:
- [ThreadAnomalyProbe.java](src/main/java/com/example/anomaly/detectdeadlocks/ThreadAnomalyProbe.java)

Based on:
:contentReference[oaicite:2]{index=2}

---

# Central Monitoring Engine

`AnomalyMonitor` acts as the orchestration layer.

Responsibilities:

- collects runtime snapshots
- evaluates anomaly thresholds
- tracks runtime states
- emits JMX notifications
- exposes runtime metrics through MXBean APIs

Supported anomaly types:

- GC_THRASH
- HEAP_HIGH
- CPU_SPIKE
- DEADLOCK

Implemented in:
- [AnomalyMonitor.java](AnomalyMonitor.java)

Based on:
:contentReference[oaicite:3]{index=3}

---

# JMX Integration

The toolkit exposes runtime state through an MXBean interface:

```java
boolean isGcThrashing();
boolean isHeapHigh();
boolean isCpuSpiking();
boolean isDeadlocked();
```

Additional features:

- configurable thresholds
- active anomaly listing
- runtime observability integration

Implemented in:
- [AnomalyMXBean.java](AnomalyMXBean.java)

Based on:
:contentReference[oaicite:4]{index=4}

---

# Example Runtime Notifications

```text
ANOMALY: CPU spike detected
ANOMALY: GC thrashing detected
ANOMALY: Deadlock detected
ANOMALY: Heap usage critically high
```

---

# Example Usage

## GC Monitoring

```java
var probe = new GcAnomalyProbe();

var snapshot = probe.sample();

if (probe.isGcThrashing(snapshot)) {
    System.out.println("GC thrashing detected!");
}
```

---

## CPU Monitoring

```java
var probe = new OsAnomalyProbe();

var snapshot = probe.sample();

if (probe.cpuSpike(snapshot)) {
    System.out.println("CPU spike detected!");
}
```

---

## Deadlock Monitoring

```java
var probe = new ThreadAnomalyProbe();

long[] deadlocked = probe.findDeadlockedThreads();

if (deadlocked != null) {
    System.out.println("Deadlock detected!");
}
```

---

# Runtime Metrics Captured

## JVM Metrics

- heap usage
- GC time
- GC collections
- blocked thread metrics
- thread contention
- JVM uptime

## Operating System Metrics

- process CPU usage
- system CPU usage
- open file descriptors
- maximum file descriptors
