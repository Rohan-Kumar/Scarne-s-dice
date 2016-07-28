package rohan.scarnesdice;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Animation animation;
    int User = 0, UserTurn = 0, Comp = 0, CompTurn = 0;
    Random random = new Random();
    int dice[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    TextView score;
    ImageView diceImage;
    Button rollBtn, holdBtn, resetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
    }

    private void init() {
        score = (TextView) findViewById(R.id.score);
        diceImage = (ImageView) findViewById(R.id.dice);
        rollBtn = (Button) findViewById(R.id.roll);
        holdBtn = (Button) findViewById(R.id.hold);
        resetBtn = (Button) findViewById(R.id.reset);
    }

    public void roll(View view) {
        int rollVal = random.nextInt(6);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        diceImage.startAnimation(animation);
        diceImage.setImageResource(dice[rollVal]);
        rollVal++;
        Log.d("TAG", "Comp: roll Val:" + rollVal + " comp:" + Comp + "CTurn:" + CompTurn + " User:" + User + " UTurn" + UserTurn);
//        Toast.makeText(MainActivity.this, "" + rollVal, Toast.LENGTH_SHORT).show();
        if (rollVal == 1) {
            score.setText("Your score:" + (User) + " Computer score:" + (Comp));
            UserTurn = 0;
            computerChance();
        } else {
            UserTurn += rollVal;
            score.setText("Your score:" + (User + UserTurn) + " Computer score:" + (Comp));

            if ((User + UserTurn) >= 100)
                stopGame(0);
        }
    }

    private void computerChance() {

        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);
        resetBtn.setEnabled(false);

//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();

        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                int rollVal = random.nextInt(6);
                animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fadein);
                diceImage.startAnimation(animation);
                diceImage.setImageResource(dice[rollVal]);
                rollVal++;
                Log.d("TAG", "Comp: roll Val:" + rollVal + " comp:" + Comp + "CTurn:" + CompTurn + " User:" + User + " UTurn" + UserTurn);
//        Toast.makeText(MainActivity.this, ""+rollVal, Toast.LENGTH_SHORT).show();
                if (rollVal == 1) {
                    score.setText("Your score:" + (User) + " Computer score:" + (Comp));
                    CompTurn = 0;
                    userChance();
                    return;
                } else {
                    CompTurn += rollVal;
                    if ((Comp + CompTurn) >= 100) {
                        stopGame(1);
                        return;
                    }

                    score.setText("Your score:" + (User) + " Computer score:" + (Comp + CompTurn));
                    if (CompTurn > 20) {
                        Comp += CompTurn;
                        CompTurn = 0;
                        userChance();
                        return;
                    } else
                        computerChance();

                }

            }
        }.start();


    }

    private void stopGame(int winner) {
        if (winner == 0)
            score.setText("YOU WON!!!!!");
        else
            score.setText("COMPUTER WON!!!!");
        rollBtn.setEnabled(false);
        holdBtn.setEnabled(false);
    }

    private void userChance() {
        rollBtn.setEnabled(true);
        holdBtn.setEnabled(true);
        resetBtn.setEnabled(true);
    }

    public void reset(View view) {
        UserTurn = 0;
        User = 0;
        CompTurn = 0;
        Comp = 0;
        score.setText("Your score:0 Computer score:0");
        rollBtn.setEnabled(true);
        holdBtn.setEnabled(true);
    }

    public void hold(View view) {
        User += UserTurn;
        UserTurn = 0;
        computerChance();
    }
}
