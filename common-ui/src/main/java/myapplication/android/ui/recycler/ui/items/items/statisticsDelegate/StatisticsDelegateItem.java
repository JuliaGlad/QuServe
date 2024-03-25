package myapplication.android.ui.recycler.ui.items.items.statisticsDelegate;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class StatisticsDelegateItem implements DelegateItem {

    StatisticsModel value;

    public StatisticsDelegateItem(StatisticsModel value) {
        this.value = value;
    }

    @Override
    public Object content() {
        return value;
    }

    @Override
    public int id() {
        return value.hashCode();
    }

    @Override
    public boolean compareToOther(DelegateItem other) {
        return other.content() == content();
    }
}
