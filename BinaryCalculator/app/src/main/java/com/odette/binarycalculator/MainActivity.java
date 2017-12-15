package com.odette.binarycalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// Loaded project on the A5 phone

public class MainActivity extends AppCompatActivity {

    LinearLayout liv1;
    LinearLayout liv2;
    EditText et1; // users first value
    EditText et2; // users second value
    Button operatorBtn; // button that shows what operator is being used
    TextView tvRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Restore preferences if there are any
        SharedPreferences settings = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        String val1 = settings.getString("value1", "");
        String val2 = settings.getString("value2", "");
        String res = settings.getString("results", "");
        String operator = settings.getString("selectedType", "");

        setContentView(R.layout.activity_main);

        liv1 = (LinearLayout) findViewById(R.id.LinearOne);
        liv2 = (LinearLayout) findViewById(R.id.LinearTwo);
        et1 = (EditText) findViewById(R.id.etFirst);
        et2 = (EditText) findViewById(R.id.etSecond);
        operatorBtn = (Button) findViewById(R.id.btnOperator);
        tvRes = (TextView) findViewById(R.id.tvRes);
        tvRes.setVisibility(View.GONE); // Hide this until we actually need a result.

        //Check if the previous data is availible
        if (val1 != "")
            et1.setText(val1);

        if (val2 != "")
            et2.setText(val2);

        if (operator != "")
            operatorBtn.setText(operator);
        else
            operatorBtn.setText(R.string.and_btn);

        if (res != "")
        {
            tvRes.setText(res);
            tvRes.setVisibility(View.VISIBLE); // Hide this until we actually need a result.
        }
    }

    // Used to switch the text of the button between (AND, OR, XOR, NAND, NOR, XNOR)
    public void onSwitchBtn(View view) {
        //Basic implementation: Switch text and use that as the operator choice
        switch (operatorBtn.getText().toString()){
            case "AND":
                operatorBtn.setText("OR");
                break;
            case "OR":
                operatorBtn.setText("XOR");
                break;
            case "XOR":
                operatorBtn.setText("NAND");
                break;
            case "NAND":
                operatorBtn.setText("NOR");
                break;
            case "NOR":
                operatorBtn.setText("XNOR");
                break;
            case "XNOR":
                operatorBtn.setText("AND");
                break;
            default:
                break;
        }

        //Advanced: Implement a small pop up at the bottom of the screen to switch between options? Maybe a model popup
        //          use fling or on tap to switch betwenn bit operations, maybe implement 2d fade animation.
    }

    // On pause
    @Override
    protected void onPause() {
        super.onPause();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context     
        SharedPreferences settings = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("selectedType", operatorBtn.getText().toString());
        editor.putString("value1", et1.getText().toString());
        editor.putString("value2", et2.getText().toString());
        editor.putString("results", tvRes.getText().toString());
        editor.commit();
    }

    //Evaluate the two values
    public void OnEval(View view) {
        //Make sure that there are 2 binary values to actually calculate a new value with
        if ((et1.getText().toString().equals("") || et1.getText().toString().equals(null))
         || (et2.getText().toString().equals("") || et2.getText().toString().equals(null))) {
            Toast.makeText(this, getString(R.string.error_msg_input), Toast.LENGTH_LONG).show();
        }
        else {
            // Evaluate the 2 values and put the results into the textview.
            String value1 = et1.getText().toString();
            String value2 = et2.getText().toString();

            //Check that both values have the same length, if not add 0's to the smaller one
            if (value1.length() != value2.length()) { // They arn't equal
                if (value1.length() < value2.length()) { // et1 needs more 0's
                    while (value1.length() < value2.length())
                        value1 = "0" + value1;
                }
                else { // et2 needs more 0's
                    while (value2.length() < value1.length())
                        value2 = "0" + value2;
                }
            }

            //Check what the operator is and perform that evaluation
            switch (operatorBtn.getText().toString()){
                case "AND":
                    ANDOperator(value1, value2);
                    break;
                case "OR":
                    OROperator(value1, value2);
                    break;
                case "XOR":
                    XOROperator(value1, value2);
                    break;
                case "NAND":
                    NANDOperator(value1, value2);
                    break;
                case "NOR":
                    NOROperator(value1, value2);
                    break;
                case "XNOR":
                    XNOROperator(value1, value2);
                    break;
                default:
                    break;
            }
        }
    } // end onEval button click listener
    
    //Go to about activity
    public void onAbout(View view) {
        //Save current data and go to the about window
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    public void onTruth(View view) {
        // Look at what binary operation is selected and go to the truth table for that one.
        // Send the binary operation through intent.
        Intent truthTableIntent = new Intent(this, TruthTable.class);
        truthTableIntent.putExtra("operation",operatorBtn.getText().toString());
        startActivity(truthTableIntent);
    }

    //Binary Arithmetic Helper Methods - future proof, set it up to use bitwise operators, need to change values to boolean?

    //AND Operator - only true if First bit and Second bit are 1
    public void ANDOperator (String val1, String val2) {
        String resultBin = ""; // result string
        char[] c1 = val1.toCharArray(); // char array for the first binary value
        char[] c2 = val2.toCharArray(); // char array for the second binary value

        for(int i = 0; i < c1.length; ++i){
            if (c1[i] ==  '1' && c2[i] == '1')
                resultBin += "1";
            else
                resultBin += "0";
        }

        //Set the result string
        tvRes.setText(resultBin);
        tvRes.setVisibility(View.VISIBLE);
    } // end ANDOperator

    //OR Operator - only true if either bit is 1
    public void OROperator (String val1, String val2) {
        String resultBin = ""; // result string
        char[] c1 = val1.toCharArray(); // char array for the first binary value
        char[] c2 = val2.toCharArray(); // char array for the second binary value

        for(int i = 0; i < c1.length; ++i){
            if (c1[i] == '1' || c2[i] == '1') // Check incase you need "" around them
                resultBin += "1";
            else
                resultBin += "0";
        }

        //Set the result string
        tvRes.setText(resultBin);
        tvRes.setVisibility(View.VISIBLE);
    } // end OROperator

    //XOR Operator - only true if First bit or Second bit is 1 but not both
    public void XOROperator (String val1, String val2) {
        String resultBin = ""; // result string
        char[] c1 = val1.toCharArray(); // char array for the first binary value
        char[] c2 = val2.toCharArray(); // char array for the second binary value

        for(int i = 0; i < c1.length; ++i){
            if ((c1[i] == '1' && c2[i] == '0') || (c1[i] == '0' && c2[i] == '1'))
                resultBin += "1";
            else
                resultBin += "0";
        }

        //Set the result string
        tvRes.setText(resultBin);
        tvRes.setVisibility(View.VISIBLE);
    } // end XOROperator

    //NAND Operator - only false if First bit and Second bit are 1 (opposite of AND)
    public void NANDOperator (String val1, String val2) {
        String resultBin = ""; // result string
        char[] c1 = val1.toCharArray(); // char array for the first binary value
        char[] c2 = val2.toCharArray(); // char array for the second binary value

        for(int i = 0; i < c1.length; ++i){
            if (c1[i] == '1' &&  c2[i] == '1')
                resultBin += "0";
            else
                resultBin += "1";
        }

        //Set the result string
        tvRes.setText(resultBin);
        tvRes.setVisibility(View.VISIBLE);
    } // end NANDOperator

    //NOR Operator - only true if First bit and Second bit are 0 (opposite of OR)
    public void NOROperator (String val1, String val2) {
        String resultBin = ""; // result string
        char[] c1 = val1.toCharArray(); // char array for the first binary value
        char[] c2 = val2.toCharArray(); // char array for the second binary value

        for(int i = 0; i < c1.length; ++i){
            if (c1[i] == '0' &&  c2[i] == '0')
                resultBin += "1";
            else
                resultBin += "0";
        }

        //Set the result string
        tvRes.setText(resultBin);
        tvRes.setVisibility(View.VISIBLE);
    } // end NOROperator

    //XNOR Operator - only true if both bits are the same value
    public void XNOROperator (String val1, String val2) {
        String resultBin = ""; // result string
        char[] c1 = val1.toCharArray(); // char array for the first binary value
        char[] c2 = val2.toCharArray(); // char array for the second binary value

        for(int i = 0; i < c1.length; ++i){
            if (c1[i] == c2[i])
                resultBin += "1";
            else
                resultBin += "0";
        }

        //Set the result string
        tvRes.setText(resultBin);
        tvRes.setVisibility(View.VISIBLE);
    } // end XNOROperator
}
