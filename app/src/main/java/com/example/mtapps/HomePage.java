package com.example.mtapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        ((TextView)findViewById(R.id.title_View_home)).setText("Welcome "+signInAccount.getDisplayName());


        //Bottom Nav Manenos
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.expenses:
                        startActivity(new Intent(getApplicationContext(),FarmInput.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.income:
                        startActivity(new Intent(getApplicationContext(),FarmOutput.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        DBaseHandler db = new DBaseHandler(this);
        int result = db.getTotal();
        TextView status = (TextView) findViewById(R.id.statusView);
        if (result > 0){
            status.setText("+ ksh. "+result);
            status.setTextColor(Color.GREEN);
        } else if(result < 0){
            status.setText("ksh. "+result);
            status.setTextColor(Color.RED);
        } else {
            status.setText("ksh.0");
            status.setTextColor(Color.BLACK);
        }

        DBaseHandler db0 = new DBaseHandler(this);
        ArrayList<HashMap<String, String>> incomeList = db0.GetIncome();
        ListView lv = (ListView) findViewById(R.id.outputsView);
        ListAdapter adapter = new SimpleAdapter(HomePage.this, incomeList, R.layout.list_row,new String[]{"name","description","date","number","cost","total"}, new int[]{R.id.name, R.id.description, R.id.date,R.id.quantity,R.id.unit,R.id.total});
        lv.setAdapter(adapter);

        DBaseHandler db1 = new DBaseHandler(this);
        ArrayList<HashMap<String, String>> expenseList = db1.GetExpense();
        ListView lv1 = (ListView) findViewById(R.id.inputsView);
        ListAdapter adapter1 = new SimpleAdapter(HomePage.this, expenseList, R.layout.list_row,new String[]{"name","description","date","number","cost","total"}, new int[]{R.id.name, R.id.description, R.id.date,R.id.quantity,R.id.unit,R.id.total});
        lv1.setAdapter(adapter1);
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
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}