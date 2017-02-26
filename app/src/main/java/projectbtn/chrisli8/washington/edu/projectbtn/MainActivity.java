package projectbtn.chrisli8.washington.edu.projectbtn;

import java.util.*;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;
import android.os.Handler.Callback;
import android.widget.Toast;


public class MainActivity extends Activity {

    public List<Button> buttons;
    public Button changeValues;
    public TextView points;
    public RelativeLayout grid;
    public Handler timeHandler;

    public int[] colorArry = {Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN};

    private boolean gameFragmentOn = false;

    // Power Ups
    public boolean colorCoded = false;

    boolean blackOrWhite = true;
    int maxReached = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnFragment = (Button)findViewById(R.id.menuBtn);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateFragment();
            }
        });

        rotateFragment();


        //grid = (RelativeLayout) findViewById(R.id.grid);
        // grid = (RelativeLayout) findViewById(R.id.grid);
        timeHandler= new Handler();

        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBoard(grid);
                timeHandler.postDelayed(this, 800); // 0.8 of a second
            }
        }, 800);

        changeValues = (Button) findViewById(R.id.botBtn);
        changeValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resets the game including acheivement
                maxReached = 0;
                points.setText(0 + "");
                setBoard(grid);
            }
        });

        buttons = new ArrayList<Button>();
        points = (TextView) findViewById(R.id.topBtn);

        setBoard(grid);

    }

    private void rotateFragment() {
        Fragment frag = null;

        GameFragment gameFrag = new GameFragment();
        gameFrag.getLayout();

        if (!gameFragmentOn) {
            gameFragmentOn = true;
            frag = new GameFragment();
            grid = (RelativeLayout) frag.getView().findViewById(R.id.grid);
        }
        else {
            gameFragmentOn = false;
            frag = new MenuFragment();
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder, frag);
        fragmentTransaction.commit();
    }

    private void setBoard(RelativeLayout grid) {
        Random r = new Random(); // should this be a field or something?
        for (int i = 0; i < grid.getChildCount(); i++) {
            LinearLayout l = (LinearLayout) grid.getChildAt(i);
            for (int j = 0; j < l.getChildCount(); j++) {
                Button b = (Button) l.getChildAt(j);
                buttons.add(b);

                // Select random number
                int btnValue = r.nextInt(31) - 15;

                int color = r.nextInt(6);
                b.setBackgroundColor(colorArry[color]);

                // Color Coded Block
                if (colorCoded) {
                    if (btnValue == 0) {
                        b.setBackgroundColor(Color.BLUE);
                    } else if (btnValue > 0) {
                        b.setBackgroundColor(Color.GREEN);
                    } else {
                        b.setBackgroundColor(Color.RED);
                    }
                }
                b.setVisibility(View.VISIBLE);

                // Randomly select to show or not 2 / 3
                int visibleChance = r.nextInt(3);
                if (visibleChance == 0 || visibleChance == 1) {
                    b.setVisibility(View.INVISIBLE);
                }

                b.setText("" + btnValue);
                b.setOnClickListener(new MyListener(btnValue, b));
            }


        }
    }

    private class MyListener implements View.OnClickListener {
        public int value;
        public Button thisBtn;

        public MyListener(int value, Button b) {
            this.value = value;
            this.thisBtn = b;
        }

        @Override
        public void onClick(View view) {
            int oldValue = Integer.parseInt(points.getText().toString());
            if (oldValue + value > maxReached) { // works for zero case

//                if (blackOrWhite) {
//                    changeValues.setBackgroundColor(Color.BLACK);
//                    blackOrWhite = false;
//                } else {
//                    changeValues.setBackgroundColor(Color.WHITE);
//                    blackOrWhite = true;
//                }
                String toastText = "You beat " + (maxReached) + " points!";

                // set style and layout of toast
                LinearLayout layout = new LinearLayout(getApplicationContext());
                layout.setBackgroundColor(Color.BLACK);
                TextView txtView = new TextView(getApplicationContext());
                txtView.setTextColor(Color.RED);
                txtView.setTextSize(30);
                txtView.setPadding(10, 10, 10, 10);
                txtView.setText(toastText);
                layout.addView(txtView);


                maxReached += 100;
                Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 50);
                toast.setView(layout);
                toast.show();
            }
            points.setText(oldValue + this.value + "");
            thisBtn.setVisibility(View.INVISIBLE);

        }
    }

}
