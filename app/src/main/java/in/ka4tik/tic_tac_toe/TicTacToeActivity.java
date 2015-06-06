package in.ka4tik.tic_tac_toe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class TicTacToeActivity extends Activity {


    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    private int mPlayerOneCounter =0,mTieCounter=0, mPlayerTwoCounter =0;

    private boolean mPlayerOneFirst =true;
    private boolean isSinglePlayer=false;
    private boolean isPlayerOneTurn=true;
    private boolean mGameOver=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        boolean mGameType=getIntent().getExtras().getBoolean("gameType");
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
        mPlayerOneCount =(TextView) findViewById(R.id.humanCount);
        mPlayerTwoCount =(TextView) findViewById(R.id.androidCount);
        mTieCount=(TextView) findViewById(R.id.tiesCount);

        mPlayerOneText=(TextView) findViewById(R.id.PlayerOneText);
        mPlayerTwoText=(TextView) findViewById(R.id.PlayerTwoText);

        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
        mTieCount.setText(Integer.toString(mTieCounter));

        mGame=new TicTacToeGame();
        StartNewGame(mGameType);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newGame:
                StartNewGame(isSinglePlayer);
                return true;
            case R.id.exitGame:
                TicTacToeActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void StartNewGame(boolean isSingle)
    {
        this.isSinglePlayer=isSingle;
        mGame.clearBoard();
        mGameOver=false;
        for(int i=0;i<mBoardButtons.length;i++)
        {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        if(isSingle)
        {
            mPlayerOneText.setText("Human: ");
            mPlayerTwoText.setText("Android: ");
            if(mPlayerOneFirst)
            {
                mInfoTextView.setText(R.string.first_human);
                mPlayerOneFirst =false;
            }
            else
            {
                mInfoTextView.setText(R.string.turn_computer);
                int move=mGame.getComputerMove();
                setMove(TicTacToeGame.PLAYER_TWO,move);
                mPlayerOneFirst =true;
                mInfoTextView.setText("Your turn");

            }
        }
        else
        {
            mPlayerOneText.setText("Player 1: ");
            mPlayerTwoText.setText("Player 2: ");
            if(mPlayerOneFirst)
            {
                mInfoTextView.setText("Player 1 turn");
                mPlayerOneFirst =false;
            }
            else
            {
                mInfoTextView.setText("Player 2 turn");
                mPlayerOneFirst =true;
            }
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
                    if(isSinglePlayer)
                    {
                        setMove(TicTacToeGame.PLAYER_ONE,location);
                        int winner=mGame.checkForWinner();
                        if(winner==0)
                        {
                            mInfoTextView.setText(R.string.turn_computer);
                            int move=mGame.getComputerMove();
                            setMove(TicTacToeGame.PLAYER_TWO,move);
                            winner=mGame.checkForWinner();
                        }
                        if(winner==0)
                            mInfoTextView.setText(R.string.turn_human);
                        else if(winner==1)
                        {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver=true;
                        }
                        else if(winner==2)
                        {
                            mInfoTextView.setText(R.string.result_human_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver=true;

                        }
                        else
                        {
                            mInfoTextView.setText(R.string.result_android_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver=true;

                        }

                    }
                    else
                    {
                        if(isPlayerOneTurn)
                        setMove(TicTacToeGame.PLAYER_ONE,location);
                        else
                        setMove(TicTacToeGame.PLAYER_TWO,location);
                        int winner=mGame.checkForWinner();
                        if(winner==0)
                        {
                            if(isPlayerOneTurn) {
                                mInfoTextView.setText("Player 2 turn");
                                isPlayerOneTurn=false;
                            }
                            else {
                                mInfoTextView.setText("Player 1 turn");
                                isPlayerOneTurn=true;
                            }

                        }

                        else if(winner==1)
                        {

                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver=true;
                        }
                        else if(winner==2)
                        {
                            mInfoTextView.setText("Player 1 won");
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver=true;

                        }
                        else
                        {
                            mInfoTextView.setText("Player 2 won");
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver=true;

                        }


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

        if(player==TicTacToeGame.PLAYER_ONE) {
            mBoardButtons[location].setTextColor(Color.GREEN);
            mBoardButtons[location].setShadowLayer(20,0,0,Color.GREEN);
            mBoardButtons[location].setBackgroundResource(R.drawable.green_border);
        }
        else {
            mBoardButtons[location].setTextColor(Color.RED);
            mBoardButtons[location].setShadowLayer(20,0,0,Color.RED);
            mBoardButtons[location].setBackgroundResource(R.drawable.red_border);

        }
    }



}
