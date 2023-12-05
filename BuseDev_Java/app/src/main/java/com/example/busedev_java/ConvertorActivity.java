package com.example.busedev_java;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ConvertorActivity extends AppCompatActivity {

    EditText DecConInput;
    Spinner DecConSpinner;
    TextView DecConLabel;
    Button DecConButton;

    EditText ByteConInput;
    Spinner ByteConSpinner;
    TextView ByteConLabel;
    Button ByteConButton;

    EditText CelConInput;
    RadioButton FahrenaytRadio;
    RadioButton KelvinRadio;
    TextView CelConLabel;
    Button CelConButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        DecConInput = findViewById(R.id.DecimalConvertorInput);
        DecConSpinner = findViewById(R.id.DecimalConvertorSpinner);
        DecConLabel = findViewById(R.id.DecimalConvertorLabel);
        DecConButton = findViewById(R.id.DecimalConvertorButton);

        ByteConInput = findViewById(R.id.ByteConvertorInput);
        ByteConSpinner = findViewById(R.id.ByteConvertorSpinner);
        ByteConLabel = findViewById(R.id.ByteConvertorLabel);
        ByteConButton = findViewById(R.id.ByteConvertorButton);

        CelConInput = findViewById(R.id.CelciusConvertorInput);
        FahrenaytRadio = findViewById(R.id.FahrenaytRadio);
        KelvinRadio = findViewById(R.id.KelvinRadio);
        CelConLabel = findViewById(R.id.CelciusConvertorLabel);
        CelConButton = findViewById(R.id.CelciusConvertorButton);

        //Spinner a seçenek eklme
        String[] decoptions = {"Binary", "Octal", "Hexadecimal"};

        ArrayAdapter<String> decadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, decoptions);
        decadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DecConSpinner.setAdapter(decadapter);

        //Spinner a seçenek eklme
        String[] byteoptions = {"kilobyte", "byte", "kibibyte", "bit"};

        ArrayAdapter<String> byteadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, byteoptions);
        byteadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ByteConSpinner.setAdapter(byteadapter);

        DecConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });

        ByteConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byteconvert();
            }
        });

        CelConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celciusconvert();
            }
        });

    }

    private void celciusconvert() {
        try {
            double inputValue = Double.parseDouble(CelConInput.getText().toString());
            double result;

            // Fahrenheit seçiliyse
            if (FahrenaytRadio.isChecked()) {
                result = celsiusToFahrenheit(inputValue);
            }
            // Kelvin seçiliyse
            else if (KelvinRadio.isChecked()) {
                result = celsiusToKelvin(inputValue);
            }
            // Seçili birim yoksa hata ver
            else {
                throw new IllegalArgumentException("Select a unit");
            }

            CelConLabel.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            CelConLabel.setText("Invalid input");
        }
    }

    private void byteconvert(){
        try {
            double inputValue = Double.parseDouble(ByteConInput.getText().toString());
            String conversionType = ByteConSpinner.getSelectedItem().toString();
            double result;

            switch (conversionType) {
                case "kilobyte":
                    result = megaByteToKiloByte(inputValue);
                    break;
                case "byte":
                    result = megaByteToBytes(inputValue);
                    break;
                case "kibibyte":
                    result = megaByteToKibiByte(inputValue);
                    break;
                case "bit":
                    result = megaByteToBits(inputValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid conversion type");
            }

            ByteConLabel.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            ByteConLabel.setText("Invalid input");
        }
    }

    private void convert() {
        try {
            double inputValue = Double.parseDouble(DecConInput.getText().toString());
            String conversionType = DecConSpinner.getSelectedItem().toString();
            String result;

            switch (conversionType) {
                case "Binary":
                    result = decimalToBinary(inputValue);
                    break;
                case "Octal":
                    result = decimalToOctal(inputValue);
                    break;
                case "Hexadecimal":
                    result = decimalToHexadecimal(inputValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid conversion type");
            }

            DecConLabel.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            DecConLabel.setText("Invalid input");
        }
    }

    private String decimalToBinary(double decimal) {
        return Integer.toBinaryString((int) decimal);
    }

    private String decimalToOctal(double decimal) {
        return Integer.toOctalString((int) decimal);
    }

    private String decimalToHexadecimal(double decimal) {
        return Integer.toHexString((int) decimal);
    }

    private double megaByteToKiloByte(double megaByte) {
        return megaByte * 1024;
    }

    private double megaByteToBytes(double megaByte) {
        return megaByte * 1024 * 1024;
    }

    private double megaByteToKibiByte(double megaByte) {
        return megaByte * 1000;
    }

    private double megaByteToBits(double megaByte) {
        return megaByte * 1024 * 1024 * 8;
    }

    private double celsiusToFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }

    private double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }
}