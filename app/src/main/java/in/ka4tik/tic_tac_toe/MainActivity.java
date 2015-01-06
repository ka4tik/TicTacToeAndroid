package in.ka4tik.tic_tac_toe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {


    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private TextView mHumanCount;
    private TextView mTieCount;
    private TextView mAndroidCount;

    private int mHumanCounter=0,mTieCounter=0,mAndroidCounter=0;

    private boolean mHumanFirst=true;
    private boolean mGameOver=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons=new Button[TicTacToeGame.getBOARD_SIZE()];
        mBoardButtons[0]=(Button)findViewById(R.id.one);
        mBoardButtons[1]=(Button)findViewById(R.id.two);
        mBoardButtons[2]=(Button)findViewById(R.id.three);
        mBoardButtons[3]=(Button)findViewById(R.id.four);
        mBoardButtons[4]=(Button)findViewById(R.id.five);
        mBoardButtons[5]=(Button)findViewById(R.id.six);
        mBoardButtons[6]=(Button)findViewById(R.id.seven);
        mBoardButtons[7]=(Button)findViewById(R.id.eight);
        mBoardButtons[8]=(Button)findViewById(R.id.nine);

        mInfoTextView=(TextView) findViewById(R.id.info);
        mHumanCount=(TextView) findViewById(R.id.humanCount);
        mAndroidCount=(TextView) findViewById(R.id.androidCount);
        mTieCount=(TextView) findViewById(R.id.tiesCount);

        mHumanCount.setText(Integer.toString(mHumanCounter));
        mAndroidCount.setText(Integer.toString(mAndroidCounter));
        mTieCount.setText(Integer.toString(mTieCounter));

        mGame=new TicTacToeGame();
        StartNewGame();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getGroupId())
        {
            case R.id.newGame:
                StartNewGame();
                break;
            case R.id.exitGame:
                MainActivity.this.finish();
                break;
        }
        return true;
    }

    private void StartNewGame()
    {
        mGame.clearBoard();
        for(int i=0;i<mBoardButtons.length;i++)
        {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        if(mHumanFirst)
        {
            mInfoTextView.setText(R.string.first_human);
            mHumanFirst=false;
        }
        else
        {
            mInfoTextView.setText(R.string.turn_computer);
            int move=mGame.getComputerMove();
            setMove(TicTacToeGame.ANDROID_PLAYER,move);
            mHumanFirst=true;
        }
    }
    private class ButtonClickListener implements View.OnClickListener
    {
        int location;
        public ButtonClickListener(int location)
        {
            this.location=location;
        }
        public void onClick(View view)
        {
            if(!mGameOver)
            {
                if(mBoardButtons[location].isEnabled())
                {
                    setMove(TicTacToeGame.HUMAN_PLAYER,location);
                    int winner=mGame.checkForWinner();
                    if(winner==0)
                    {
                        mInfoTextView.setText(R.string.turn_computer);
                        int move=mGame.getComputerMove();
                        setMove(TicTacToeGame.ANDROID_PLAYER,move);
                        winner=mGame.checkForWinner();
                    }
                    if(winner==0)
                        mInfoTextView.setText(R.string.turn_human);
                    else if(winner==1)
                    {
                        mInfoTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                    }
                    else if(winner==2)
                    {
                        mInfoTextView.setText(R.string.result_human_wins);
                        mHumanCounter++;
                        mHumanCount.setText(Integer.toString(mHumanCounter));

                    }
                    else
                    {
                        mInfoTextView.setText(R.string.result_android_wins);
                        mAndroidCounter++;
                        mAndroidCount.setText(Integer.toString(mAndroidCounter));

                    }
                }

            }
        }


    }

    private void setMove(char player,int location)
    {
        mGame.setMove(player,location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player==TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.GREEN);
        else
            mBoardButtons[location].setTextColor(Color.RED);
    }



}
