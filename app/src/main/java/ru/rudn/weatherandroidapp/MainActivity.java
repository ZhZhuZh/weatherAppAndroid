package ru.rudn.weatherandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Click(View w){
        Network.getInstance().getApi().getCurrentWeather(
                getResources().getString(R.string.key), "Москва", "ru")
                .enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                ((TextView)findViewById(R.id.weather)).setText(response.body().getCurrent().getTempC() + " C");
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Cl(View w){
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(i);
    }
}