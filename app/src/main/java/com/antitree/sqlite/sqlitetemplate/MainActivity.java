package com.antitree.sqlite.sqlitetemplate;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    EditText textFieldOne, textFieldTwo, textFieldThree, textFieldFour,
            idField;
    Button addButton;
    SQLiteManager db;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
            // Android specific calls
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            db = new SQLiteManager(this);
            setupViews();

            addButtonListeners();

        }
        catch (Exception e)
        {
            Log.e("ERROR", e.toString());
            e.printStackTrace();
        }
    }




    private void setupViews()
    {
        textFieldOne= 		(EditText)findViewById(R.id.ssid);
        textFieldTwo= 		(EditText)findViewById(R.id.bssid);
        textFieldThree =    (EditText)findViewById(R.id.lat);
        textFieldFour =     (EditText)findViewById(R.id.lon);
        addButton = 		(Button)findViewById(R.id.button);
    }

    private void addButtonListeners()
    {
        addButton.setOnClickListener
                (
                        new View.OnClickListener()
                        {
                            @Override public void onClick(View v) {addRow();}
                        }
                );



    }


    private void addRow()
    {
        try
        {
            db.addRow
                    (
                            textFieldOne.getText().toString(),
                            textFieldTwo.getText().toString(),
                            textFieldThree.getText().toString(),
                            textFieldFour.getText().toString()
                    );
            Toast toast = Toast.makeText(getApplicationContext(), "SCORE! Successfully added content into db", Toast.LENGTH_LONG);
            toast.show();


            emptyFormFields();
        }
        catch (Exception e)
        {
            Log.e("Add Error", e.toString());
            e.printStackTrace();
        }
    }

    // need this?
    private void deleteRow()
    {
        try
        {
            db.deleteRow(Long.parseLong(idField.getText().toString()));
        }
        catch (Exception e)
        {
            Log.e("Delete Error", e.toString());
            e.printStackTrace();
        }
    }


    // clear things out
    private void emptyFormFields()
    {
        textFieldOne.setText("");
        textFieldTwo.setText("");
        textFieldThree.setText("");
        textFieldFour.setText("");
    }

}
