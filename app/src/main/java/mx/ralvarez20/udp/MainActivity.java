package mx.ralvarez20.udp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import mx.ralvarez20.udp.db.DBHelper;
import mx.ralvarez20.udp.db.MacContract;
import mx.ralvarez20.udp.utils.WoL;

public class MainActivity extends AppCompatActivity {

    private final HashMap<String, String> optionsMap = new HashMap<>();
    private final List<String> spOptions = new ArrayList<>();
    private SQLiteDatabase wDB, rDB;
    private Spinner spDefaultOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper helper = new DBHelper(this);

        wDB = helper.getWritableDatabase();
        rDB = helper.getReadableDatabase();

        Button btnSend = findViewById(R.id.btnSend);
        Button btnSave = findViewById(R.id.btnSave);
        ImageButton btnInfo = findViewById(R.id.btnInfo);
        spDefaultOptions = findViewById(R.id.spDefaultOpts);
        EditText etMacAddr = findViewById(R.id.etMac);
        EditText etMacName = findViewById(R.id.txtAddressName);

        getAllAddress();

        setListOptions();

        btnInfo.setOnClickListener(v -> {
            startActivity(new Intent(this, InfoActivity.class));
        });

        btnSave.setOnClickListener(v -> {
            String name = etMacName.getText().toString();
            String addr = etMacAddr.getText().toString();

            if (name.isEmpty() || addr.isEmpty()){
                Toast.makeText(this, "No puede dejar los campos vacíos", Toast.LENGTH_SHORT).show();
            }else {
                if(insertAddress(name, addr)> 0){
                    Toast.makeText(this, "Se ha guardado correctamente", Toast.LENGTH_SHORT).show();
                    getAllAddress();
                    setListOptions();
                }else{
                    Toast.makeText(this, "Ha ocurrido un error al guardar la dirección", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSend.setOnClickListener(v -> {
            if (etMacAddr.getText().toString().isEmpty()){
                //Cargar desde SP
                if(spDefaultOptions.getSelectedItemPosition() > 0){
                    String optSel = (String) spDefaultOptions.getSelectedItem();
                    //System.out.println(optSel);
                    new WoL(this.getBaseContext(), optionsMap.get(optSel) ).sendPacket();
                }else {
                    Toast.makeText(this, "Por favor, seleccione una opcion", Toast.LENGTH_SHORT).show();
                }
            }else {
                new WoL(this.getBaseContext(), etMacAddr.getText().toString()).sendPacket();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wDB.close();
        rDB.close();
    }


    private void setListOptions(){
        spOptions.clear();

        spOptions.add("");

        spOptions.addAll(optionsMap.keySet());

        ArrayAdapter<String> adp1 = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, spOptions);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDefaultOptions.setAdapter(adp1);
    }

    private void getAllAddress(){
        Cursor c = rDB.query(MacContract.MacEntry.TABLE_NAME, null, null, null, null, null, null);
        while (c.moveToNext()){
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex(MacContract.MacEntry.NAME));
            @SuppressLint("Range") String mac = c.getString(c.getColumnIndex(MacContract.MacEntry.MAC_ADDRESS));
            optionsMap.put(name, mac);
        }
    }

    private long insertAddress(String name, String address){
        ContentValues cv = new ContentValues();

        cv.put(MacContract.MacEntry.NAME, name);
        cv.put(MacContract.MacEntry.MAC_ADDRESS, address);

        return wDB.insert(MacContract.MacEntry.TABLE_NAME, null, cv );
    }


}