package in.ka4tik.tic_tac_toe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by ka4tik on 1/6/15.
 */
public class MainMenuScreen extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu);

        ((Button) findViewById(R.id.onePlayer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG","One player button Pressed!");
                Intent intent=new Intent(MainMenuScreen.this,TicTacToeActivity.class);
                intent.putExtra("gameType",true);
                startActivityForResult(intent,0);
            }
        });

        ((Button) findViewById(R.id.twoPlayer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG","Two player button Pressed!");
                Intent intent=new Intent(MainMenuScreen.this,TicTacToeActivity.class);
                intent.putExtra("gameType",false);
                startActivityForResult(intent,0);
            }
        });

        ((Button) findViewById(R.id.exitGame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG","Exit button Pressed!");
                MainMenuScreen.this.finish();
            }
        });
    }
}
