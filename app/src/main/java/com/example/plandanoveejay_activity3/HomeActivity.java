package com.example.plandanoveejay_activity3;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

     TextView text_player_1_score, text_player_2_score, text_draw_count, text_round_count;
     Button button_reset;
     Button[] button = new Button[9];
     int  player1scorecount, player2scorecount, roundcount, drawcount;
     boolean activeplayer;

     //p1 = 0 p2 = 1 empty/not yet clicked = 2
    // x = 0 O = 1 empty/not yet clicked = 2

    int[] gameState =  {2,2,2,2,2,2,2,2,2};
    int[][] winningStates = {

            {0,1,2},{3,4,5},{6,7,8},//horizontal
            {0,3,6},{1,4,7},{2,5,8},//vertical
            {0,4,8},{2,4,6}//diagonal

    };

    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_home);

        text_player_1_score = (TextView)findViewById(R.id.txt_player_1_score);
        text_player_2_score = (TextView)findViewById(R.id.txt_player_2_score);
        text_draw_count = (TextView)findViewById(R.id.txt_draw_count);
        button_reset = (Button)findViewById(R.id.btn_reset);

        for (int i=0; i < button.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            button[i] = (Button)findViewById(resourceID);
            button[i].setOnClickListener(this);

        }
        roundcount = 0;
        player1scorecount = 0;
        player2scorecount = 0;
        drawcount = 0;
        activeplayer = true;

    }

    @Override
    public void onClick(View view) {
        Log.i("test", "button is pressed");
        if (!
                ((Button)view).getText().toString().equals("")){
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));
        if(activeplayer){
            ((Button)view).setText("X");

            ((Button)view).setTextColor(Color.parseColor("#4287f5"));

            gameState[gameStatePointer] = 0;
        }
        else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#f55142"));

            gameState[gameStatePointer] = 1;
        }
        roundcount++;

        if (checkWinner()){
            if (activeplayer){

                Toast.makeText(this, "Player One won the game!", Toast.LENGTH_SHORT).show();
                player1scorecount++;
                updatePlayerScore();

                playAgain();
            } else{
                Toast.makeText(this,"Player Two won the game!", Toast.LENGTH_SHORT).show();
                player2scorecount++;
                updatePlayerScore();
                playAgain();
            }

        } else if (roundcount == 9) {
            drawcount++;
            playAgain();
            Toast.makeText(this," Draw!", Toast.LENGTH_SHORT).show();
            updateDrawCount();

        }else{
            activeplayer = !activeplayer;
        }

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               playAgain();
                player1scorecount = 0;
                player2scorecount = 0;
                roundcount = 0;
                drawcount = 0;
                updateDrawCount();
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int[]
                winningState:winningStates){
            if (gameState[winningState[0]] == gameState[winningState[1]]&&
            gameState[winningState[1]] == gameState[winningState[2]]&&
            gameState[winningState[0]]!=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        text_player_1_score.setText(Integer.toString(player1scorecount));
        text_player_2_score.setText(Integer.toString(player2scorecount));
    }
   public void playAgain(){
        roundcount = 0;
        activeplayer = true;
        for(int i = 0; i < button.length; i++){
            gameState[i] = 2;
            button[i].setText("");
        }
    }

    public void updateDrawCount(){
        text_draw_count.setText(Integer.toString(drawcount));
    }
}
