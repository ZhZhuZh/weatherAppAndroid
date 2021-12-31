package ru.rudn.weatherandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<String> cities = null;
    Adapter adapter;
    Adapter adapter1;

    public void Save(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < cities.size() - 1; i++){
            str.append(cities.get(i)).append(",");
        }
        str.append(cities.get(cities.size() - 1));
        editor.putString("list", str.toString());
        editor.apply();
        editor.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        preferences = getPreferences(MODE_PRIVATE);
        editor = preferences.edit();
        String str = preferences.getString("list", null);
        if(str == null){
            cities = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
            Save();
        }
        else{
            cities = new ArrayList<>(Arrays.asList(str.split(",")));
        }

        RecyclerView recyclerView = findViewById(R.id.rec);
        adapter = new Adapter(new ArrayList<>(), new ArrayList<>(), getApplicationContext());
        RecyclerView recyclerView1 = findViewById(R.id.rec1);
        adapter1 = new Adapter(new ArrayList<>(), new ArrayList<>(), getApplicationContext());

        Network.getInstance().getApi().getCurrentWeather(
                getResources().getString(R.string.key), "москва", "ru"
        ).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Double tmp = (double) response.body().getCurrent().getTempC();
                for (String c : cities){
                    Network.getInstance().getApi().getCurrentWeather(
                            getResources().getString(R.string.key), c, "ru"
                    ).enqueue(new Callback<Weather>() {
                        @Override
                        public void onResponse(Call<Weather> call, Response<Weather> response) {
                            adapter.Add(c, (double) response.body().getCurrent().getTempC());
                            if(Math.abs(tmp - (double)response.body().getCurrent().getTempC()) < 0.2){
                                adapter1.Add(c, (double)response.body().getCurrent().getTempC());
                            }
                        }

                        @Override
                        public void onFailure(Call<Weather> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(adapter1);
    }

    public void click(View w){
        String city = ((EditText)findViewById(R.id.edit)).getText().toString();
        cities.add(city);
        Save();


        Network.getInstance().getApi().getCurrentWeather(
                getResources().getString(R.string.key), "москва", "ru"
        ).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Double tmp = (double) response.body().getCurrent().getTempC();
                Network.getInstance().getApi().getCurrentWeather(
                        getResources().getString(R.string.key), city, "ru"
                ).enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        adapter.Add(city, (double) response.body().getCurrent().getTempC());
                        if(Math.abs(tmp - (double)response.body().getCurrent().getTempC()) < 0.2){
                            adapter1.Add(city, (double)response.body().getCurrent().getTempC());
                        }
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }
}