package com.example.booking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

public class Appointment extends AppCompatActivity {

    EditText date_in;
    EditText time_in;
    Spinner spinnerName;
    Button btnInsertData,btnChooseData;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }
        date_in = findViewById(R.id.date_input);
        time_in = findViewById(R.id.time_input);
        spinnerName = findViewById(R.id.spinner);
        btnInsertData = findViewById(R.id.book_btn);
        btnChooseData = findViewById(R.id.detail_btn);

        db = FirebaseFirestore.getInstance();

        btnChooseData.setOnClickListener(v -> startActivity(new Intent(Appointment.this, Notification.class)));
        btnInsertData.setOnClickListener(v -> {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            String id = fAuth.getCurrentUser().getUid();
            String date = date_in.getText().toString().trim();
            String time = time_in.getText().toString().trim();
            String doctor = spinnerName.getSelectedItem().toString().trim();
            saveToFirestore(id,date,time,doctor);
            });

        date_in.setInputType(InputType.TYPE_NULL);
        time_in.setInputType(InputType.TYPE_NULL);


        date_in.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showDateDialog(date_in);
            }
        });
        time_in.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showTimeDialog(time_in);
            }
        });


        }

    private void saveToFirestore(String id, String date,String time, String doctor){
        if(!date.isEmpty() && !time.isEmpty()){
            HashMap<String,Object>map = new HashMap<>();
            map.put("id",id);
            map.put("date",date);
            map.put("time",time);
            map.put("doctor",doctor);

            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Appointment.this,"Pomyślnie dodano wizytę",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Appointment.this,"Błąd dodawania wizyty",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this,"Uzupełnij dane ",Toast.LENGTH_SHORT).show();
        }

    }

   @RequiresApi(api = Build.VERSION_CODES.N)
    private void showTimeDialog(final EditText time_in) {
        final Calendar calendar=Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

         new TimePickerDialog(Appointment.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showDateDialog(final EditText date_in){
       final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayofMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayofMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            date_in.setText(simpleDateFormat.format(calendar.getTime()));
        };
          new DatePickerDialog(Appointment.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}
