package com.example.myapplication.presentation.queue.participantList;

import static com.example.myapplication.presentation.utils.Utils.QUEUE_AUTHOR_KEY;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_LIST;
import static com.example.myapplication.presentation.utils.Utils.QUEUE_PARTICIPANTS_LIST;
import static com.example.myapplication.presentation.utils.Utils.fireStore;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.example.myapplication.presentation.queue.participantList.participantListItem.ParticipantListDelegateItem;
import com.example.myapplication.presentation.queue.participantList.participantListItem.ParticipantListModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapplication.android.ui.recycler.delegate.DelegateItem;

public class ParticipantsListViewModel extends ViewModel {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String userID = auth.getCurrentUser().getUid();
    private DocumentReference docRef;
    private List<String> newParticipants;

    private final MutableLiveData<List<DelegateItem>> _items = new MutableLiveData<>();
    public LiveData<List<DelegateItem>> item = _items;

    private final List<DelegateItem> participantList = new ArrayList<>();

    private List<String> participants;
    private int queueLength = 0;

    private void initRecyclerView(Fragment fragment) {
        addParticipantListDelegateItems(fragment);
        _items.postValue(participantList);
    }

    private void addParticipantListDelegateItems(Fragment fragment){
        if (queueLength != 0) {
            for (int i = 0; i < queueLength; i++) {
                int number = i + 1;
                participantList.add(new ParticipantListDelegateItem(new ParticipantListModel(2, fragment.getString(R.string.participant) + " " + "#" + number)));
            }
        } else {
            Log.d("Participant List", "No users yet :)");
        }
    }

    public void getParticipantsList(Fragment fragment) {
        fireStore.collection(QUEUE_LIST)
                .whereEqualTo(QUEUE_AUTHOR_KEY, userID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot snapshot : snapshotList) {
                        docRef = fireStore.collection(QUEUE_LIST).document(snapshot.getId());
                        participants = new ArrayList<>(Arrays.asList(snapshot.get(QUEUE_PARTICIPANTS_LIST).toString().split(",")));
                        queueLength = participants.size();

                        initRecyclerView(fragment);
                        addDocumentSnapshot(snapshot, fragment);
                    }
                });
    }

    private void addDocumentSnapshot(DocumentSnapshot snapshot, Fragment fragment) {
        docRef.addSnapshotListener((value, error) -> {
            if (value != null) {
                if (snapshot.exists()) {
                    newParticipants = new ArrayList<>(Arrays.asList(value.get(QUEUE_PARTICIPANTS_LIST).toString().split(",")));
                }

                if (newParticipants != null) {
                    if (!newParticipants.equals(participants)) {
                        if (participants.size() < newParticipants.size()) {

                            List<DelegateItem> newItems = new ArrayList<>();
                            newItems.addAll(_items.getValue());
                            newItems.add(new ParticipantListDelegateItem(new ParticipantListModel(3, fragment.getString(R.string.participant) + "#" + newParticipants.size())));
                            _items.setValue(newItems);
                        }
                    }
                }
            }
        });
    }

}

