package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class DiaryDetails extends AppCompatActivity {

    EditText dateEditText, diaryentryEditText;
    ImageButton saveDiaryBtn;
    TextView pageTitleTextView;

    TextView deleteEntryTetViewBtn;

    String date,content,docID;

    boolean isEditmode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_details);

        dateEditText = findViewById(R.id.diary_date);
        diaryentryEditText = findViewById(R.id.diary_entry);
        saveDiaryBtn = findViewById(R.id.save_entry_btn);
        pageTitleTextView = findViewById(R.id.page_tittle);
        deleteEntryTetViewBtn = findViewById(R.id.delete_entry_btn);

        date = getIntent().getStringExtra("date");
        content = getIntent().getStringExtra("content");
        docID = getIntent().getStringExtra("docID");

        if(docID!=null && !docID.isEmpty()){
            isEditmode = true;
        }

        dateEditText.setText(date);
        diaryentryEditText.setText(content);
        if (isEditmode){
            pageTitleTextView.setText("Edit your diary");
            deleteEntryTetViewBtn.setVisibility(View.VISIBLE);
        }



        saveDiaryBtn.setOnClickListener((v)->saveDiary());

        deleteEntryTetViewBtn.setOnClickListener((v)-> deleteEntryFromFirebase());

    }
    void saveDiary(){
        String date = dateEditText.getText().toString();
        String diaryContent = diaryentryEditText.getText().toString();
        if (date==null || diaryContent.isEmpty()){
            dateEditText.setError("Enter date of log");
            return;
        }
        diaryentry diaryentry = new diaryentry();
        diaryentry.setDate(date);
        diaryentry.setContent(diaryContent);
        diaryentry.setTimestamp(Timestamp.now());

        saveDiaryToFirebase(diaryentry);

    }

    void saveDiaryToFirebase(diaryentry diaryentry){

        DocumentReference documentReference;
        if(isEditmode){
            documentReference = Utility.getCollectionReferenceForNotes().document(docID);

        }else {
        documentReference = Utility.getCollectionReferenceForNotes().document();}

        documentReference.set(diaryentry).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DiaryDetails.this,"Diary Entry Added Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(DiaryDetails.this,"Failed to add Entry",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void deleteEntryFromFirebase(){

        DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForNotes().document(docID);



        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DiaryDetails.this,"Diary Entry Deleted Successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(DiaryDetails.this,"Failed to Delete Entry",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}