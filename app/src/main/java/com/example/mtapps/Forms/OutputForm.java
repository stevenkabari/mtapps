package com.example.mtapps.Forms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mtapps.DBaseHandler;
import com.example.mtapps.FarmOutput;
import com.example.mtapps.MainActivity;
import com.example.mtapps.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OutputForm extends AppCompatActivity {
    Button save,cancel;
    TextInputEditText name,description,cost,quantity,date,dateForm;
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_output_dialog);


        dateForm =findViewById(R.id.ex_Date);
        dateForm.setInputType(InputType.TYPE_NULL);
        dateForm.requestFocus();


        name =findViewById(R.id.name);
        description =findViewById(R.id.description);
        cost =findViewById(R.id.cost);
        quantity =findViewById(R.id.quantity);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        date = findViewById(R.id.ex_Date);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OutputForm.this, FarmOutput.class);
                startActivity(intent);
            }
        });
        DatePickerDialog.OnDateSetListener datePickerListener = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Log.v("aya",sdf.format(myCalendar.getTime()));
            dateForm.setText(sdf.format(myCalendar.getTime()));
        };

        dateForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(OutputForm.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                send();
//                 Log.v("ffs","i was clicked ye");
             }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(OutputForm.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void send() {
        String FieldName=name.getText().toString().trim();
        String FieldDescription=description.getText().toString().trim();
        String FieldDate=date.getText().toString().trim();

        Double FieldCost = Double.parseDouble(cost.getText().toString());
        Double FieldQuantity = Double.parseDouble(quantity.getText().toString());

        double total = FieldCost*FieldQuantity;

        String totals = String.valueOf(total);
        String cost = String.valueOf(FieldCost);
        String quantity = String.valueOf(FieldQuantity);

        Log.v("ffs","Did I get here");
        if (TextUtils.isEmpty(FieldDate)  ) {
            Toast.makeText(this, "Input all the fields.", Toast.LENGTH_SHORT).show(); }
        else {
            DBaseHandler db = new DBaseHandler(OutputForm.this);
            db.saveIncome(FieldName,FieldDescription,FieldDate,cost,quantity,totals);

            Toast.makeText(this, "Farm Income saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OutputForm.this, FarmOutput.class));
        }




}
}
