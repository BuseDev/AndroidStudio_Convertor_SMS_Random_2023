package com.example.busedev_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;

import java.util.Random;

public class RandomActivity extends AppCompatActivity {

    EditText AdetInput;
    EditText MaxInput;
    EditText MinInput;
    Button GenerateButton;
    LinearLayout RandomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        AdetInput = findViewById(R.id.AdetInput);
        MaxInput = findViewById(R.id.MaxInput);
        MinInput = findViewById(R.id.MinInput);
        GenerateButton = findViewById(R.id.GenerateButton);
        RandomLayout = findViewById(R.id.RandomLinearLayout);

        GenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomProgressBars();
            }
        });
    }

    private void generateRandomProgressBars() {
        try {
            int quantity = Integer.parseInt(AdetInput.getText().toString());
            double minValue = Double.parseDouble(MinInput.getText().toString());
            double maxValue = Double.parseDouble(MaxInput.getText().toString());

            if (minValue >= maxValue) {
                throw new IllegalArgumentException("Minimum value must be less than maximum value");
            }

            RandomLayout.removeAllViews(); // Mevcut view'ları temizle

            for (int i = 0; i < quantity; i++) {
                double progress = minValue + (maxValue - minValue) * new Random().nextDouble();

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout linearLayoutBottom = new LinearLayout(this);
                linearLayoutBottom.setOrientation(LinearLayout.HORIZONTAL);

                ProgressBar progressBar = createProgressBar((int) progress,(int) minValue, (int) maxValue);
                setGravity(progressBar, Gravity.CENTER);

                TextView percentageTextView = createTextView(String.format(progressBar.getProgress() + " = %.2f%%", ((progressBar.getProgress() - minValue) / (maxValue - minValue)) * 100));
                setGravity(percentageTextView, Gravity.TOP | Gravity.CENTER_HORIZONTAL);

                TextView minValueTextView = createTextView(String.format("Min: " + minValue));
                setGravity(minValueTextView, Gravity.BOTTOM | Gravity.START);


                TextView maxValueTextView = createTextView(String.format("Max: " + maxValue));
                setGravity(maxValueTextView, Gravity.BOTTOM | Gravity.END);

                linearLayout.addView(percentageTextView);
                linearLayoutBottom.addView(minValueTextView);
                linearLayoutBottom.addView(progressBar);
                linearLayoutBottom.addView(maxValueTextView);

                RandomLayout.addView(linearLayout);
                RandomLayout.addView(linearLayoutBottom);
            }
        } catch (NumberFormatException e) {
            // Hata durumunda kullanıcıyı bilgilendir
            // Örneğin: Kullanıcı sayısal bir değer girmemiş olabilir.
        } catch (IllegalArgumentException e) {
            // Hata durumunda kullanıcıyı bilgilendir
            // Örneğin: Minimum değer maksimum değerden büyük veya eşit olamaz.
        }
    }

    private void setGravity(View view, int gravity) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.gravity = gravity;
        view.setLayoutParams(params);
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(text);
        return textView;
    }

    private ProgressBar createProgressBar(int progress, int minValue, int maxValue) {
        ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LinearLayout.LayoutParams(680, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            progressBar.setMin(minValue);
        }
        progressBar.setMax(maxValue);
        progressBar.setProgress(progress);
        return progressBar;
    }
}