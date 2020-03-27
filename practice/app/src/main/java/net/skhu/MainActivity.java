package net.skhu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        View.OnClickListener listenerObj = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1 = (EditText)findViewById(R.id.editText);
                CharSequence s= e1.getText();
                EditText e2 = (EditText)findViewById(R.id.editText2);
                CharSequence s2 = e2.getText();
                e1.setText(s2);
                e2.setText(s);
            }
        };
        b.setOnClickListener(listenerObj);
    }
}
