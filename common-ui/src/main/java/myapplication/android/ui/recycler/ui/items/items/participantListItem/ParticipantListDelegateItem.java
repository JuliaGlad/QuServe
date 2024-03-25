package myapplication.android.ui.recycler.ui.items.items.participantListItem;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantListDelegateItem implements DelegateItem {

    private ParticipantListModel value;

    public ParticipantListDelegateItem(ParticipantListModel value){
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
