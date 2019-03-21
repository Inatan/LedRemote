package com.example.inatan.raspremote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //EditText editText;
   //EditText editText2;
    RadioButton radio1;
    RadioButton radio2;
    RadioButton radio3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //editText = (EditText) findViewById(R.id.Name);
        //editText2 = (EditText) findViewById(R.id.part_nr);
        radio1 = (RadioButton) findViewById(R.id.radio_led1);
        radio2 = (RadioButton) findViewById(R.id.radio_led2);
        radio3 = (RadioButton) findViewById(R.id.radio_led3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onRadioButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "Led selecionado com sucesso ", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Enviar(View view) {
        new Create_Part().execute();
    }

    class Create_Part extends AsyncTask<String, String, String> {
        private String String_name;
        private String Int_Part;
        private Toast toast;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("M"); //Set the message for the loading window
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show(); //Place the loading message on the screen*/
            String_name = "test"; //editText.getText().toString(); //Get the inserted text from the editText files


            if(radio1.isChecked())
                Int_Part = "1";
            else if(radio2.isChecked())
                Int_Part = "2";
            else if(radio3.isChecked())
                Int_Part = "4";

            //Int_Part = editText2.getText().toString();
        }

        @Override
        protected String doInBackground(String... args) {
            List params = new ArrayList<>();
            params.add(new BasicNameValuePair("Name", String_name)); //Add the parameters to an array
            params.add(new BasicNameValuePair("part_nr", Int_Part));
            // Do the HTTP POST Request with the JSON parameters
            // Change "RaspberryPi_IP to your home IP address or Noip service
            System.out.println("passei por aqui");
            JSONObject json = jsonParser.makeHttpRequest("http://143.54.12.146//db_create.php", "POST", params);
            try {
                int success = json.getInt("success");
                if(success == 1){
                    //Intent i = new Intent(getApplicationContext(), ConfirmActivity.class);
                    System.out.println("passei por aqui outra atividade");
                    // Open a new activity to confirm it was sent. We're going to create it later.
                    //startActivity(i);
                    return "Mensagem enviada com sucesso";
                    //finish();
                } else {
                   return "Mensagem inválida";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                // // whatever you wana do with valid result
        }
    }
}





