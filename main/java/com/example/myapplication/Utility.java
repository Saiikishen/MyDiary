package com.example.myapplication;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {
    static CollectionReference getCollectionReferenceForNotes(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("diaryentry").document(currentUser.getUid()).collection("my_diaryenttry");

    }

    static String timestampToString(Timestamp timestamp){
       return  new SimpleDateFormat("dd/MM/yyyy)").format(timestamp.toDate());
    }
}
