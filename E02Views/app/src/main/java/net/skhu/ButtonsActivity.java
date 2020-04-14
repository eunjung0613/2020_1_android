package net.skhu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ButtonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String msg;
                int buttonId = v.getId();
                switch (buttonId) {
                    case R.id.Button1: msg = "첫 번째 버튼이 클릭 되었습니다."; break;
                    case R.id.Button2: msg = "두 번째 버튼이 클릭 되었습니다."; break;
                    case R.id.Button3: msg = "세 번째 버튼이 클릭 되었습니다."; break;
                    case R.id.imageButton: msg = "첫 번째 이미지 버튼이 클릭 되었습니다."; break;
                    case R.id.imageButton2: msg = "두 번째 이미지 버튼이 클릭 되었습니다."; break;
                    case R.id.imageButton3: msg = "세 번째 이미지 버튼이 클릭 되었습니다."; break;
                    case R.id.imageButton4: msg = "네 번째 이미지 버튼이 클릭 되었습니다."; break;
                    default: msg = "알 수 없는 버튼이 클릭 되었습니다."; break;
                }
                Toast.makeText(ButtonsActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        };
        Button button1 = (Button)findViewById(R.id.Button1);
        Button button2 = (Button)findViewById(R.id.Button2);
        Button button3 = (Button)findViewById(R.id.Button3);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton);
        ImageButton imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        ImageButton imageButton4 = (ImageButton)findViewById(R.id.imageButton4);
        imageButton1.setOnClickListener(listener);
        imageButton2.setOnClickListener(listener);
        imageButton3.setOnClickListener(listener);
        imageButton4.setOnClickListener(listener);

    }
}
