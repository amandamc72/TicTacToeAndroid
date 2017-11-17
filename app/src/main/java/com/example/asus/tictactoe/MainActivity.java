package com.example.asus.tictactoe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

@SuppressWarnings("deprecation")

public class MainActivity extends ActionBarActivity {

    private TicTacToeGame mGame;
    private Button mBoardButtons[];

    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mPlayerTwoCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;
    private Button mNewGameBtn;

    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mPlayerTwoCounter = 0;

    private boolean mPlayerOneFirst = true;
    private boolean mGameOver = false;
    private boolean mIsCurrPlayerOne = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.getBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mPlayerOneText = (TextView)findViewById(R.id.playerOne);
        mPlayerTwoText = (TextView)findViewById(R.id.playerTwo);


        if (gameType()){
            mPlayerOneText.setText(getResources().getString(R.string.human));
            mPlayerTwoText.setText(getResources().getString(R.string.android));
        }
        else{
            mPlayerOneText.setText(getResources().getString(R.string.player_one));
            mPlayerTwoText.setText(getResources().getString(R.string.player_two));
        }

        mNewGameBtn = (Button) findViewById(R.id.newGameButton);
        mNewGameBtn.setVisibility(View.INVISIBLE);


        mNewGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
                mNewGameBtn.setVisibility(View.INVISIBLE);
            }
        });

        mInfoTextView = (TextView) findViewById(R.id.information);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tieCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.androidCount);

        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));

        mGame = new TicTacToeGame();

        startNewGame();
    }

    public boolean gameType() {
        Bundle extras = getIntent().getExtras();
        return extras.getBoolean("gameType");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newGame:
                startNewGame();
                break;
            case R.id.exitGame:
                MainActivity.this.finish();
                break;
        }
        return true;
    }

    private void startNewGame() {
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mBoardButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
        }

        if (gameType()) {
            if (mPlayerOneFirst) {
                mInfoTextView.setText(R.string.first_human);
                mPlayerOneFirst = false;
            } else {
                mInfoTextView.setText(R.string.turn_computer);
                int move = mGame.getComputerMove();
                setMove(mGame.PLAYER_TWO, move);
                mPlayerOneFirst = true;
            }
        } else {
            if (mPlayerOneFirst) {
                mInfoTextView.setText(R.string.first_player_one);
                mIsCurrPlayerOne = true;
                mPlayerOneFirst = false;
            } else {
                mInfoTextView.setText(R.string.first_player_two);
                mIsCurrPlayerOne = false;
                mPlayerOneFirst = true;
            }
        }
        mGameOver = false;
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);

        if (player == mGame.PLAYER_ONE) {
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
        } else {
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (gameType()) {
                if (!mGameOver) {
                    if (mBoardButtons[location].isEnabled()) {
                        setMove(mGame.PLAYER_ONE, location);

                        int winner = mGame.checkForWinner();

                        if (winner == 0) {
                            mInfoTextView.setText(R.string.turn_computer);
                            int move = mGame.getComputerMove();
                            setMove(mGame.PLAYER_TWO, move);
                            winner = mGame.checkForWinner();
                        }

                        if (winner == 0)
                            mInfoTextView.setText(R.string.turn_human);
                        else if (winner == 1) {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (winner == 2) {
                            mInfoTextView.setText(R.string.result_human_win);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        } else {
                            mInfoTextView.setText(R.string.result_android_win);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    }
                }
            } else {

                if (!mGameOver) {
                    if (mBoardButtons[location].isEnabled()) {
                        if (mIsCurrPlayerOne) {
                            mInfoTextView.setText(R.string.turn_player_two);
                            setMove(mGame.PLAYER_ONE, location);
                            mIsCurrPlayerOne = false;
                        } else {
                            mInfoTextView.setText(R.string.turn_player_one);
                            setMove(mGame.PLAYER_TWO, location);
                            mIsCurrPlayerOne = true;
                        }

                        if (mGame.checkForWinner() == 1) {
                            mInfoTextView.setText(R.string.result_tie);
                            mTieCounter++;
                            mTieCount.setText(Integer.toString(mTieCounter));
                            mGameOver = true;
                        } else if (mGame.checkForWinner() == 2) {
                            mInfoTextView.setText(R.string.result_player_one_wins);
                            mPlayerOneCounter++;
                            mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                            mGameOver = true;
                        } else if (mGame.checkForWinner() == 3) {
                            mInfoTextView.setText(R.string.result_player_two_wins);
                            mPlayerTwoCounter++;
                            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoCounter));
                            mGameOver = true;
                        }
                    }
                }
            }
            if(mGameOver) {
                mNewGameBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}
