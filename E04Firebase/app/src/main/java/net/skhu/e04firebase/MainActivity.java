package net.skhu.e04firebase;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    static final int RC_LOGIN = 1; // 로그인 액티비티 호출을 구별하기 위한 request code이다.
    FirebaseUser currentUser = null; // 현재 사용자
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView_userName);
        showUserName();
    }
    public void button_clicked(View view) {
        Class classObj = null;
        switch (view.getId()) {
            case R.id.button1: classObj = Firebase1Activity.class; break;
            case R.id.button2: classObj = MemoList1Activity.class; break;
            case R.id.button3: classObj = MemoList2Activity.class; break;
            case R.id.button4: classObj = MemoList3Activity.class; break;
        }
        Intent intent = new Intent(this, classObj);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem_login = menu.findItem(R.id.action_login);
        MenuItem menuItem_logout = menu.findItem(R.id.action_logout);
        menuItem_login.setVisible(currentUser ==null);
        menuItem_logout.setVisible(currentUser !=null);
        if(currentUser !=null){
            String s =currentUser.getDisplayName()+" "+menuItem_logout.getTitle().toString();
            menuItem_logout.setTitle(s);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) { // 로그인 메뉴 클릭
            startLoginInActivity();
            return true;
        } else if (id == R.id.action_logout) { // 로그아웃 메뉴 클릭
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void startLoginInActivity() {
        // 이메일 인증과 구글 계정 인증을 사용하여 로그인 기능하도록 설정
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), // 이메일 로그인 기능을 사용
        new AuthUI.IdpConfig.GoogleBuilder().build()); // google계정 로그인 기능을 사용

        //로그인 액티비티 호출
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_LOGIN); // 로그인 액티비티 호출을 구별하기 위한 request code
    }
    //화면에 현재 사용자 이름을 표시
    void showUserName(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser(); //현재 사용자 객체를 구한다.
        if(currentUser!=null)
            textView.setText(currentUser.getDisplayName());
        else // 로그인하지 않았을 때 Anonymous 출력
            textView.setText("Anonymous");
    }
    void logout(){
        FirebaseAuth.getInstance().signOut();
        showUserName();
        invalidateOptionsMenu();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RC_LOGIN) { // 로그인 액티비티 호출 결과
            IdpResponse response = IdpResponse.fromResultIntent(intent);
            if (resultCode == RESULT_OK) { // 로그인 작업이 성공인 경우
                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
            } else {
                // 로그인 작업이 실패한 경우
                String message = "Authentication failure. " + response.getError().getErrorCode()
                        + " " + response.getError().getMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
            showUserName();
            invalidateOptionsMenu();
        }

    }

}



