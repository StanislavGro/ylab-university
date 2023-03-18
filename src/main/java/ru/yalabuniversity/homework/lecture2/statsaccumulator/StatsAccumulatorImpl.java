package ru.yalabuniversity.homework.lecture2.statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {

    //Значения по умолчанию 0. Помним, что поля класса при создании объекта инициализируются нулями, а ссылки null
    private long value;
    private int minValue;
    private int maxValue;
    private int counter;

    @Override
    public void add(int value) {
        this.counter++;
        if(value > this.maxValue)
            this.maxValue = value;
        else if(value < this.minValue)
            this.minValue = value;

        this.value += value;
    }

    @Override
    public int getMin() {
        return this.minValue;
    }

    @Override
    public int getMax() {
        return this.maxValue;
    }

    @Override
    public int getCount() {
        return this.counter;
    }

    @Override
    public Double getAvg() {
        //Автоупаковка
        return (double) value / (double) counter;
    }
}
