package net.skhu.e04firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class MemoEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        final EditText editText_title = (EditText) findViewById(R.id.editText_title);
        final EditText editText_content = (EditText) findViewById(R.id.editText_content);
        Memo memo = (Memo)getIntent().getSerializableExtra("MEMO");
        if (memo != null) {
            editText_title.setText(memo.getTitle());
            editText_content.setText(memo.getBody());
        }

        Button button = (Button) findViewById(R.id.btnSave);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                if (isEmptyOrWhiteSpace(title)) {
                    editText_title.setError("제목을 입력하세요");
                    return;
                }

                String body = editText_content.getText().toString();
                if (isEmptyOrWhiteSpace(body)) {
                    editText_content.setError("내용을 입력하세요");
                    return;
                }

                Memo memo = new Memo(title, body, new Date());
                Intent intent = new Intent();
                intent.putExtra("MEMO", memo);
                setResult(RESULT_OK, intent);
                finish();
            }
        };
        button.setOnClickListener(listener);
    }

    static boolean isEmptyOrWhiteSpace(String s) {
        if (s == null) return true;
        return s.toString().trim().length() == 0;
    }
}
