package com.amirahmed.egyptiantaxauthority;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CarDetailsActivity extends AppCompatActivity {

    TextView number,number2,maker,brand,color,year,motor,chasse,cylinders,fuel,belongs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        number = findViewById(R.id.number);
        number2 = findViewById(R.id.number2);
        maker = findViewById(R.id.maker);
        brand = findViewById(R.id.brand);
        color = findViewById(R.id.color);
        year = findViewById(R.id.year);
        motor = findViewById(R.id.motor);
        chasse = findViewById(R.id.chasse);
        cylinders = findViewById(R.id.cylinders);
        fuel = findViewById(R.id.fuel);
        belongs = findViewById(R.id.belongs);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String numbers = extras.getString("number");
            String numbers2 = extras.getString("number2");
            String makers = extras.getString("maker");
            String brands = extras.getString("brand");
            String colors = extras.getString("color");
            String years = extras.getString("year");
            String motors = extras.getString("motor");
            String chasses = extras.getString("chasse");
            String cylinderss = extras.getString("cylinders");
            String fuels = extras.getString("fuel");
            String belongss = extras.getString("belongs");

            number.setText(numbers2);
            number2.setText(numbers);
            maker.setText(makers);
            brand.setText(brands);
            color.setText(colors);
            year.setText(years);
            motor.setText(motors);
            chasse.setText(chasses);
            cylinders.setText(cylinderss);
            fuel.setText(fuels);
            belongs.setText(belongss);
        }


    }
}
