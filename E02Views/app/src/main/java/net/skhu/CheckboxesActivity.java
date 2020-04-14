package net.skhu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class CheckboxesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkboxes);
        CompoundButton.OnCheckedChangeListener listener1 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String s = String.format("%s : %b", buttonView.getText(), isChecked);
                Toast.makeText(CheckboxesActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        final Switch switch1 = (Switch) findViewById(R.id.switch1);
        checkBox1.setOnCheckedChangeListener(listener1);
        switch1.setOnCheckedChangeListener(listener1);

        RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                String s = radioButton.getText().toString();
                Toast.makeText(CheckboxesActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        final RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup1.setOnCheckedChangeListener(listener2);

        RadioGroup.OnCheckedChangeListener listener3 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int drawableId =0;
                switch (checkedId) {
                    case R.id.radioCat: drawableId = R.drawable.animal_cat_large; break;
                    case R.id.radioDog: drawableId = R.drawable.animal_dog_large; break;
                    case R.id.radioOwl: drawableId = R.drawable.animal_owl_large; break;
                }
                ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
                imageView1.setImageResource(drawableId);

            }
        };
        final RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup2.setOnCheckedChangeListener(listener3);
        Button button = (Button)findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "checkBox1=" + checkBox1.isChecked() + " ";
                s += "switch1=" + switch1.isChecked() + " ";
                s += "radioGroup1=";
                switch (radioGroup1.getCheckedRadioButtonId()) {
                    case R.id.radioButton1: s += "red "; break;
                    case R.id.radioButton2: s += "yellow "; break;
                    case R.id.radioButton3: s += "blue "; break;
                }
                switch (radioGroup2.getCheckedRadioButtonId()) {
                    case R.id.radioCat: s += "cat"; break;
                    case R.id.radioDog: s += "dog"; break;
                    case R.id.radioOwl: s += "owl"; break;
                }
                Toast.makeText(CheckboxesActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
