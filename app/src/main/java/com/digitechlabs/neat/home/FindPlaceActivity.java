package com.digitechlabs.neat.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dgtech.neat.R;


public class FindPlaceActivity extends Activity {
    Button findPlace;
    TextView bookTabletxt,ordertaketxt,orderTbaletxt;
    RelativeLayout rlbookTbl,relordederTke,relordTbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
     /*   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_find_places);
        findPlace= (Button) findViewById(R.id.btnFindPLace);
        bookTabletxt= (TextView) findViewById(R.id.txtBokTbl);
        ordertaketxt= (TextView) findViewById(R.id.txtOrderTake);

        findPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RestaurentListActivity.class);
                startActivity(intent);
            }
        });
  /*      bookTabletxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),CardPaymentActivity.class);
                startActivity(intent);
            }
        });
        ordertake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OrderConfirmationActivity.class);
                startActivity(intent);
            }
        });*/





    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
