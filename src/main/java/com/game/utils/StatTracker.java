package com.game.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * GENERIC PROGRAMMING EXAMPLE
 * 
 * Generic class untuk tracking statistics dengan tipe data Number (Integer,
 * Double, Long, dll).
 * Demonstrates penggunaan Generic Type Parameter dengan bounded type (extends
 * Number).
 * 
 * @param <T> Tipe data yang extends Number (Integer, Double, Long, Float, dll)
 */
public class StatTracker<T extends Number> {

    private final List<T> records;
    private final String statName;

    /**
     * Constructor untuk StatTracker.
     * 
     * @param statName Nama statistik yang di-track (e.g., "Damage", "Healing",
     *                 "Wins")
     */
    public StatTracker(String statName) {
        this.statName = statName;
        this.records = new ArrayList<>();
    }

    /**
     * Mencatat value baru ke tracker.
     * 
     * @param value Value yang dicatat
     */
    public void record(T value) {
        if (value != null) {
            records.add(value);
        }
    }

    /**
     * Mendapatkan total dari semua values yang dicatat.
     * 
     * @return Total dalam bentuk double
     */
    public double getTotal() {
        return records.stream()
                .mapToDouble(Number::doubleValue)
                .sum();
    }

    /**
     * Mendapatkan average dari semua values.
     * 
     * @return Average, atau 0 jika tidak ada data
     */
    public double getAverage() {
        if (records.isEmpty()) {
            return 0.0;
        }
        return getTotal() / records.size();
    }

    /**
     * Mendapatkan jumlah records yang dicatat.
     * 
     * @return Jumlah records
     */
    public int getCount() {
        return records.size();
    }

    /**
     * Mendapatkan maximum value.
     * 
     * @return Maximum value, atau 0 jika tidak ada data
     */
    public double getMax() {
        return records.stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .orElse(0.0);
    }

    /**
     * Mendapatkan minimum value.
     * 
     * @return Minimum value, atau 0 jika tidak ada data
     */
    public double getMin() {
        return records.stream()
                .mapToDouble(Number::doubleValue)
                .min()
                .orElse(0.0);
    }

    /**
     * Reset semua records.
     */
    public void clear() {
        records.clear();
    }

    /**
     * Mendapatkan nama statistik.
     * 
     * @return Nama stat
     */
    public String getStatName() {
        return statName;
    }

    /**
     * Mendapatkan semua records sebagai list.
     * 
     * @return Copy dari list records
     */
    public List<T> getAllRecords() {
        return new ArrayList<>(records);
    }

    @Override
    public String toString() {
        return String.format("%s [Count: %d, Total: %.2f, Avg: %.2f, Max: %.2f, Min: %.2f]",
                statName, getCount(), getTotal(), getAverage(), getMax(), getMin());
    }
}
