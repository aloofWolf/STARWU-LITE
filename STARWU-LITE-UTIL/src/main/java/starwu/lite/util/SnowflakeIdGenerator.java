package starwu.lite.util;

public class SnowflakeIdGenerator {

    // 起始时间戳：2020‑01‑01 00:00:00
    private static final long EPOCH = 1577836800000L;

    //机器ID位数 10bit
    private static final long WORKER_ID_BITS = 10L;
    //序列号位数 12bit
    private static final long SEQUENCE_BITS = 12L;

    private static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1;
    private static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 允许最大回拨毫秒：如果回拨小于该值，等待时钟追上；超过直接抛异常
    private static final long MAX_BACKWARD_MS = 50L;

    private static final long workerId = EnvironmentUtil.getContainerId();
    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    public static synchronized long nextId() {
        long now = System.currentTimeMillis();

        // ========== 时间回拨处理 ==========
        if (now < lastTimestamp) {
            long offset = lastTimestamp - now;
            if (offset <= MAX_BACKWARD_MS) {
                //小幅度回拨：等待时钟追上 lastTimestamp
                while (now <= lastTimestamp) {
                    now = System.currentTimeMillis();
                }
            } else {
                //大幅度回拨，无法自愈，抛出异常交给上层处理
                throw new RuntimeException(
                        String.format("时间大幅度回拨，offset=%d ms, lastTimestamp=%d, now=%d",
                                offset, lastTimestamp, now));
            }
        }

        if (now == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                now = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = now;

        return ((now - EPOCH) << TIMESTAMP_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    private static long waitNextMillis(long lastTs) {
        long ts = System.currentTimeMillis();
        while (ts <= lastTs) {
            ts = System.currentTimeMillis();
        }
        return ts;
    }

    //解析ID
    public static SnowflakeInfo parseId(long id) {
        long timestamp = (id >> TIMESTAMP_SHIFT) + EPOCH;
        long workerId = (id >> WORKER_ID_SHIFT) & MAX_WORKER_ID;
        long sequence = id & SEQUENCE_MASK;
        return new SnowflakeInfo(timestamp, workerId, sequence);
    }

    public static class SnowflakeInfo {
        public final long timestamp;
        public final long workerId;
        public final long sequence;

        public SnowflakeInfo(long timestamp, long workerId, long sequence) {
            this.timestamp = timestamp;
            this.workerId = workerId;
            this.sequence = sequence;
        }

        @Override
        public String toString() {
            return "SnowflakeInfo{timestamp=" + timestamp + ", workerId=" + workerId + ", sequence=" + sequence + "}";
        }
    }

    //测试
    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator();
        for (int i = 0; i < 10; i++) {
            long id = generator.nextId();
            System.out.println(id + " → " + parseId(id));
        }
    }
}
