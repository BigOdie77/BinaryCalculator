package com.odette.binarycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TruthTable extends AppCompatActivity {

    ImageView imgV;
    String operationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truth_table);

        //Grab the operations button info from the passed intent
        operationType = getIntent().getExtras().getString("operation");
        imgV = (ImageView) findViewById(R.id.imageViewTTable);

        //Check what type of table to populate the image with
        switch (operationType){
            case "AND":
                imgV.setImageResource(R.drawable.andtable);
                break;
            case "OR":
                imgV.setImageResource(R.drawable.ortable);
                break;
            case "XOR":
                imgV.setImageResource(R.drawable.xortable);
                break;
            case "NAND":
                imgV.setImageResource(R.drawable.nandtable);
                break;
            case "NOR":
                imgV.setImageResource(R.drawable.nortable);
                break;
            case "XNOR":
                imgV.setImageResource(R.drawable.xnortable);
                break;
            default:
                break;
        }
    }

    //Go back to the main activity
    public void onClick(View view) {
        // open Main Activity again
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("operation", operationType);
        startActivity(i);
    }
}
