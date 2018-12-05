package com.example.android.anonymoustwitter;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseQueryLiveData extends LiveData<Long> {
    private static final String LOG_TAG = "point";

    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    public FirebaseQueryLiveData(DatabaseReference ref) {
        this.query = ref;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addListenerForSingleValueEvent(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            System.out.println("We're done loading the initial " + dataSnapshot.getChildrenCount() + " items");
            setValue(dataSnapshot.getChildrenCount());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.i(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}