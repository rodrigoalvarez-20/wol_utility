package mx.ralvarez20.udp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageButton btnBack = findViewById(R.id.btnBack);
        TextView txtInfo = findViewById(R.id.txtInfo);

        txtInfo.setText(HtmlCompat.fromHtml( getResources().getString(R.string.lblInfo), HtmlCompat.FROM_HTML_MODE_LEGACY));

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });


    }
}