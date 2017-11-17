package com.example.asus.tictactoe;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenuScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_menu);

        ((Button) findViewById(R.id.one_player)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent(MainMenuScreen.this, MainActivity.class);
                intent.putExtra("gameType", true);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.two_player)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent(MainMenuScreen.this, MainActivity.class);
                intent.putExtra("gameType", false);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.exit_game)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                MainMenuScreen.this.finish();
            }
        });


        final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = backgroundOne.getHeight();
                final float translationY = height * progress;
                backgroundOne.setTranslationY(translationY);
                backgroundTwo.setTranslationY(translationY - height);
            }
        });
        animator.start();
    }
}
