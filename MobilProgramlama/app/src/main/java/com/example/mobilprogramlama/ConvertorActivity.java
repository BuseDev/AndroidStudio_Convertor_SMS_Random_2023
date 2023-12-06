package com.example.mobilprogramlama;

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
    Button DecConButton;
    TextView DecConLabel;

    EditText ByteConInput;
    Spinner ByteConSpinner;
    Button ByteConButton;
    TextView ByteConLabel;

    EditText CelConInput;
    RadioButton FahreneitRadio;
    RadioButton KelvinRadio;
    Button CelConButton;
    TextView CelConLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        DecConInput = findViewById(R.id.DecimalTextInput);
        DecConSpinner = findViewById(R.id.deciaml_spinner);
        DecConButton = findViewById(R.id.decimal_button);
        DecConLabel = findViewById(R.id.decimal_label);

        ByteConInput = findViewById(R.id.ByteTextInput);
        ByteConSpinner = findViewById(R.id.byte_spinner);
        ByteConButton = findViewById(R.id.byte_button);
        ByteConLabel = findViewById(R.id.byte_label);

        CelConInput = findViewById(R.id.CelsiusTextInput);
        KelvinRadio = findViewById(R.id.KelvinRadioButton);
        FahreneitRadio = findViewById(R.id.FahreneitRadioButton);
        CelConButton = findViewById(R.id.celsius_button);
        CelConLabel = findViewById(R.id.celcius_label);

        //Spinner a seçenek eklme
        String[] decoptions = {"Binary", "Octal", "Hexadecimal"};

        ArrayAdapter<String> decadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, decoptions);
        DecConSpinner.setAdapter(decadapter);

        //Spinner a seçenek eklme
        String[] byteoptions = {"kilobyte", "byte", "kibibyte", "bit"};

        ArrayAdapter<String> byteadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, byteoptions);
        ByteConSpinner.setAdapter(byteadapter);

        DecConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });

        ByteConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byteconvert();
            }
        });

        CelConButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                celsiusconvert();
            }
        });
    }

    private void convert(){
        try{
            double inputValue = Double.parseDouble(DecConInput.getText().toString());
            String conversionType = DecConSpinner.getSelectedItem().toString();
            String result;

            switch(conversionType){
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
        }catch(NumberFormatException e){
            DecConLabel.setText("Invaid input");
        }
    }

    private void byteconvert(){
        try{
            double inputValue = Double.parseDouble(ByteConInput.getText().toString());
            String conversionType = ByteConSpinner.getSelectedItem().toString();
            double result;

            switch(conversionType){
                case "kilobyte":
                    result = megaByteToKiloByte(inputValue);
                    break;
                case "byte":
                    result = megaByteToByte(inputValue);
                    break;
                case "kibibyte":
                    result = megaByteToKibiByte(inputValue);
                    break;
                case "bit":
                    result = megaByteToBit(inputValue);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid conversion type");
            }
            ByteConLabel.setText(String.valueOf(result));
        }catch(NumberFormatException e){
        ByteConLabel.setText("Invaid input");
        }
    }

    private void celsiusconvert(){
        try{
            double inputValue = Double.parseDouble(CelConInput.getText().toString());
            double result;

            //Fahrenheit seçiliyse
            if(FahreneitRadio.isChecked()){
                result = celsiusToFahrenheit(inputValue);
            }
            //Kelvin Seçiliyse
            else if(KelvinRadio.isChecked()){
                result = celsiusToKelvin(inputValue);
            }
            //Seçili birim yoksa
            else{
                throw  new IllegalArgumentException("select a unit");
            }
            CelConLabel.setText(String.valueOf(result));
        }catch(NumberFormatException e){
            CelConLabel.setText("Invaid input");
        }
    }


    private String decimalToBinary(double  decimal) { return Integer.toBinaryString((int) decimal);}
    private String decimalToOctal(double decimal) { return Integer.toOctalString((int) decimal);}
    private String decimalToHexadecimal(double decimal){ return Integer.toHexString((int) decimal);}
    private double megaByteToKiloByte(double megaByte){return megaByte * 1024;}
    private  double megaByteToByte(double megaByte){return megaByte * 1024 * 1024;}
    private double megaByteToKibiByte(double megaByte){return megaByte * 1000;}
    private double megaByteToBit(double megaByte){return megaByte * 1024 * 1024 * 8;}
    private double celsiusToFahrenheit(double celsius){return celsius * 9 / 5 + 32;}
    private double celsiusToKelvin(double celsius){return celsius * 273.15;}
}