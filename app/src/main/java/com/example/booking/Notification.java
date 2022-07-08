package com.example.booking;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    private FirebaseFirestore db;
    private MyAdapter adapter;
    private List<Booking> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Notification.this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new MyAdapter(Notification.this,list);
        recyclerView.setAdapter(adapter);

        showData();
    }

    private void showData(){
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String id = fAuth.getCurrentUser().getUid();
        db.collection("Documents").whereEqualTo("id",id).get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    for(DocumentSnapshot snapshot : task.getResult()){
                        Booking book = new Booking(snapshot.getString("id"),snapshot.getString("date"),snapshot.getString("time"),snapshot.getString("doctor"));
                        list.add(book);

                    }
                    adapter.notifyDataSetChanged();
                }).addOnFailureListener(e -> Toast.makeText(Notification.this,"Błąd wewnętrzny",Toast.LENGTH_SHORT).show());
    }

}
