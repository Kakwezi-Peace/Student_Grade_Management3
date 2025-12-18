package model;

import java.util.Map;

public class Statistics implements java.io.Serializable {
    private final double mean;
    private final double median;
    private final double stdDev;
    private final Map<String, Long> gradeBuckets; // A/B/C/D/F counts

    public Statistics(double mean, double median, double stdDev, Map<String, Long> gradeBuckets) {
        this.mean = mean;
        this.median = median;
        this.stdDev = stdDev;
        this.gradeBuckets = gradeBuckets;
    }

    public double getMean() { return mean; }
    public double getMedian() { return median; }
    public double getStdDev() { return stdDev; }
    public Map<String, Long> getGradeBuckets() { return gradeBuckets; }
}
