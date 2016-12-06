package com.digitechlabs.neat.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgtech.neat.R;

public class AddLocationActivity extends HeaderFooterActivity {

    RelativeLayout firstRel,secondRel,thirdRel,fourthrel,fifthRel,sixthRel,seventhRel,eithRel,nithRel,tenthRel;
    TextView firstAdrs,secondAdrs,thirdAdrs,fourthAdrs,fifthAdrs,sixthAdrs,seventhAdrs,eithAdrs,nithAdrs,tenthAdrs;
    View view1,view2,view3,view4,view5,view6,view7,view8,view9;
    EditText locationOne,locationTwo,locationThree,locationFour,locationFive,locationSix,locationSeven,locationEight,locationNine,locationTen;

    int counter=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        restaurentSearch.setVisibility(View.GONE);
        backArroow.setVisibility(View.GONE);
        restaurentText.setVisibility(View.GONE);
        initUI();
    }

    public void initUI(){
        /***initiate the reltive layout***/
        firstRel= (RelativeLayout) findViewById(R.id.firstLocRel);
        secondRel= (RelativeLayout) findViewById(R.id.secondLocRel);
        thirdRel= (RelativeLayout) findViewById(R.id.thirdLocRel);
        fourthrel= (RelativeLayout) findViewById(R.id.fourthLocRel);
        fifthRel= (RelativeLayout) findViewById(R.id.fifthLocRel);
        sixthRel= (RelativeLayout) findViewById(R.id.sixthLocRel);
        seventhRel= (RelativeLayout) findViewById(R.id.seventhLocRel);
        eithRel= (RelativeLayout) findViewById(R.id.eightLocRel);
        nithRel= (RelativeLayout) findViewById(R.id.ninthLocRel);
        tenthRel= (RelativeLayout) findViewById(R.id.tenthLocRel);

        /***initiate the EditText***/


        /***initiate the view***/
        view1=findViewById(R.id.viewone);
        view2=findViewById(R.id.viewtwo);
        view3=findViewById(R.id.viewthree);
        view4=findViewById(R.id.viewfour);
        view5=findViewById(R.id.viewfive);
        view6=findViewById(R.id.viewsix);
        view7=findViewById(R.id.viewseven);
        view8=findViewById(R.id.vieweight);
        view9=findViewById(R.id.viewnine);

        /***initiate the EditText***/
        locationOne= (EditText) findViewById(R.id.firstLoaction);
        locationTwo= (EditText) findViewById(R.id.secondLoaction);
        locationThree= (EditText) findViewById(R.id.thirdLoaction);
        locationFour= (EditText) findViewById(R.id.fourthLoaction);
        locationFive= (EditText) findViewById(R.id.fifthLoaction);
        locationSix= (EditText) findViewById(R.id.sixthLoaction);
        locationSeven= (EditText) findViewById(R.id.seventhLoaction);
        locationEight= (EditText) findViewById(R.id.eightLoaction);
        locationNine= (EditText) findViewById(R.id.ninthLoaction);
        locationTen= (EditText) findViewById(R.id.tenthLoaction);

        /** Textview initialize**/
        firstAdrs= (TextView) findViewById(R.id.firstEdit);
        secondAdrs= (TextView) findViewById(R.id.secondEdit);
        thirdAdrs= (TextView) findViewById(R.id.thirdEdit);
        fourthAdrs= (TextView) findViewById(R.id.fourthEdit);
        fifthAdrs= (TextView) findViewById(R.id.fifthtEdit);
        sixthAdrs= (TextView) findViewById(R.id.sixthEdit);
        seventhAdrs= (TextView) findViewById(R.id.seventhtEdit);
        eithAdrs= (TextView) findViewById(R.id.eidthEdit);
        nithAdrs= (TextView) findViewById(R.id.ninthEdit);
        tenthAdrs= (TextView) findViewById(R.id.tenthEdit);



    }

    public void addLocation(View view) {
        counter++;

      if(counter==1) {
          firstRel.setVisibility(View.VISIBLE);

      }else if(counter==2 && editTextSize(locationOne)>0) {
          secondRel.setVisibility(View.VISIBLE);
          view1.setVisibility(View.VISIBLE);
          locationOne.setFocusable(false);

          locationOne.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationOne.setClickable(false);
          locationOne.setCursorVisible(false);

      }else if(counter==3 &&  editTextSize(locationTwo)>0) {
          thirdRel.setVisibility(View.VISIBLE);
          view2.setVisibility(View.VISIBLE);
          locationTwo.setFocusable(false);
          locationTwo.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationTwo.setClickable(false);
          locationTwo.setCursorVisible(false);

      }else if(counter==4 && editTextSize(locationThree)>0) {
          fourthrel.setVisibility(View.VISIBLE);
          view3.setVisibility(View.VISIBLE);
          locationThree.setFocusable(false);
          locationThree.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationThree.setClickable(false);
          locationThree.setCursorVisible(false);

      }else if(counter==5 && editTextSize(locationFour)>0) {
          fifthRel.setVisibility(View.VISIBLE);
          view4.setVisibility(View.VISIBLE);
          locationFour.setFocusable(false);
          locationFour.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationFour.setClickable(false);
          locationFour.setCursorVisible(false);

      }else if(counter==6 && editTextSize(locationFive)>0) {
          sixthRel.setVisibility(View.VISIBLE);
          view5.setVisibility(View.VISIBLE);
          locationFive.setFocusable(false);
          locationFive.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationFive.setClickable(false);
          locationFive.setCursorVisible(false);

      }else if(counter==7 && editTextSize(locationSix)>0) {
          seventhRel.setVisibility(View.VISIBLE);
          view6.setVisibility(View.VISIBLE);
          locationSix.setFocusable(false);
          locationSix.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationSix.setClickable(false);
          locationSix.setCursorVisible(false);
      }else if(counter==8 && editTextSize(locationSeven)>0) {
          eithRel.setVisibility(View.VISIBLE);
          view7.setVisibility(View.VISIBLE);
          locationSeven.setFocusable(false);
          locationSeven.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationSeven.setClickable(false);
          locationSeven.setCursorVisible(false);
      }else if(counter==9 && editTextSize(locationEight)>0) {
          nithRel.setVisibility(View.VISIBLE);
          view8.setVisibility(View.VISIBLE);
          locationEight.setFocusable(false);
          locationEight.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationEight.setClickable(false);
          locationEight.setCursorVisible(false);
      }else if(counter==10 && editTextSize(locationNine)>0) {
          tenthRel.setVisibility(View.VISIBLE);
          view9.setVisibility(View.VISIBLE);
          locationNine.setFocusable(false);
          locationNine.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationNine.setClickable(false);
          locationNine.setCursorVisible(false);

      }else if(counter==11 && editTextSize(locationNine)>0) {
          locationTen.setFocusable(false);
          locationTen.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
          locationTen.setClickable(false);
          locationTen.setCursorVisible(false);

      }else{
          Toast.makeText(getApplicationContext(),"Please Enter a valid Address",Toast.LENGTH_LONG).show();
      }





    }

    public void editLocation(View v) {

        switch (v.getId()) {

            case R.id.firstEdit:
                // do your code
                locationOne.setFocusable(true);
                locationOne.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationOne.setClickable(true);
                locationOne.setCursorVisible(true);
                break;

            case R.id.secondEdit:
                // do your code
                locationTwo.setFocusable(true);
                locationTwo.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationTwo.setClickable(true);
                locationTwo.setCursorVisible(true);
                break;

            case R.id.thirdEdit:
                // do your code
                locationThree.setFocusable(true);
                locationThree.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationThree.setClickable(true);
                locationThree.setCursorVisible(true);
                break;
            case R.id.fourthEdit:
                // do your code
                locationFour.setFocusable(true);
                locationFour.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationFour.setClickable(true);
                locationFour.setCursorVisible(true);
                break;
            case R.id.fifthtEdit:
                // do your code
                locationFive.setFocusable(true);
                locationFive.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationFive.setClickable(true);
                locationFive.setCursorVisible(true);
                break;
            case R.id.sixthEdit:
                // do your code
                locationSix.setFocusable(true);
                locationSix.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationSix.setClickable(true);
                locationSix.setCursorVisible(true);
                break;
            case R.id.seventhtEdit:
                // do your code
                locationSeven.setFocusable(true);
                locationSeven.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationSeven.setClickable(true);
                locationSeven.setCursorVisible(true);
                break;
            case R.id.eidthEdit:
                // do your code
                locationEight.setFocusable(true);
                locationEight.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationEight.setClickable(true);
                locationEight.setCursorVisible(true);
                break;
            case R.id.ninthEdit:
                // do your code
                locationNine.setFocusable(true);
                locationNine.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationNine.setClickable(true);
                locationNine.setCursorVisible(true);
                break;
            case R.id.tenthEdit:
                // do your code
                locationTen.setFocusable(true);
                locationTen.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                locationTen.setClickable(true);
                locationTen.setCursorVisible(true);
                break;
            default:
                break;
        }

    }
    /** to check the size of the EdidText**/
    public int editTextSize(EditText txt) {
        int size=0;
        String ed1 = txt.getText().toString();
        size = ed1.length();
        return  size;

    }

}
