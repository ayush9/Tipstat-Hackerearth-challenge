package com.example.geek.tipstat;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import android.app.Activity;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MyActivity extends Activity {
	HashMap<String, String> hashmap;
String fav[] =new String[200];
TextView t9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Toast.makeText(this,"AYUSH",Toast.LENGTH_LONG).show();

		setContentView(R.layout.activity_my);


	//Toast.makeText(this,"AGRAWAL",Toast.LENGTH_LONG).show();
		TextView t1,t2,t3,t4,t5,t6,t7,t8;
	//	RatingBar rb;
		Intent intent = getIntent();
		hashmap = (HashMap<String, String>) intent.getSerializableExtra("hashMap");
		t1=(TextView)findViewById(R.id.ethnicity);
		int e = Integer.parseInt(hashmap.get("ethnicity"));
		if(e==1)
		{
			t1.setText("Asian");
		}
		else if(e==2)
		{
			t1.setText("Indian");
		}
		else if(e==3)
		{
			t1.setText("African Americans");
		}
		else if(e==4)
		{
			t1.setText("Asian Americans");
		}
		else if(e==5)
		{
			t1.setText("European");
		}
		else if(e==6)
		{
			t1.setText("British");
		}
		else if(e==7)
		{
			t1.setText("Jewish");
		}
		else if(e==8)
		{
			t1.setText("Latino");
		}
		else if(e==9)
		{
			t1.setText("Native American");
		}
		//t2=(TextView)findViewById(R.id.id);
	//	t2.setText(hashmap.get("id"));
		t6=(TextView)findViewById(R.id.weight);
		float w = Integer.parseInt(hashmap.get("weight"));
		t6.setText(w/1000+"Kg");
		t3=(TextView)findViewById(R.id.height);
		t3.setText(hashmap.get("height")+"cm");

		  t5=(TextView)findViewById(R.id.dob);
		   t5.setText(hashmap.get("dob"));
		t7=(TextView)findViewById(R.id.is_veg);
		//Toast.makeText(MyActivity.this,hashmap.get("is_veg"),Toast.LENGTH_LONG).show();
		int a = Integer.parseInt(hashmap.get("is_veg"));
//		Toast.makeText(MyActivity.this,a,Toast.LENGTH_LONG).show();
		if(a == 1) {
			t7.setText("Yes");
		}
		else
		{
			t7.setText("No");
		}
		//rb=(RatingBar)findViewById(R.id.is_veg);
		//String str = hashmap.get("rating");
		t8=(TextView)findViewById(R.id.drink);
		int b = Integer.parseInt(hashmap.get("drink"));
		if(b==1){
			t8.setText("Yes");
		}
		else{
			t8.setText("No");
		}
t9 = (TextView)findViewById(R.id.status);
		t9.setText(hashmap.get("status"));
	//	float f= Float.parseFloat(str);
		//f=f%5;
		//rb.setRating(f);
		//iiitm=new LatLng(Float.parseFloat(hashmap.get("latitude")), Float.parseFloat(hashmap.get("longitude")));

		ImageView image = (ImageView) findViewById(R.id.imagee);
		image.setImageDrawable(getResources().getDrawable(R.drawable.ic_perm_identity_grey_600_18dp));
		new DownloadImageTask(image).execute(hashmap.get("imagee"));
		image.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
			public void onSwipeTop() {
				Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
			}

			public void onSwipeRight() {
				Toast.makeText(MyActivity.this, "right", Toast.LENGTH_SHORT).show();
			}

			public void onSwipeLeft() {
				Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
			}

			public void onSwipeBottom() {
				Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
			}

			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});

		final FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b);
		actionB.setBackgroundResource(R.drawable.sms);
		actionB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MyActivity.this, SMSActivity.class);
				startActivity(i);

			}
		});


		FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
		//actionC.setTitle("Hide/Show Action above");
		actionC.setBackgroundResource(R.drawable.share);
		actionC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Download the Tipstat App and Enjoy!");
				sendIntent.setType("text/plain");
				startActivity(sendIntent);
				// actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
			}
		});

		final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
		menuMultipleActions.addButton(actionC);
		//menuMultipleActions.setBackgroundResource(R.drawable.share);
		//    menuMultipleActions.setBackgroundColor(Color.parseColor("#e91e63"));



		ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
		drawable.getPaint().setColor(getResources().getColor(R.color.pink));
		// ((FloatingActionButton) findViewById(R.id.setter_drawable)).setIconDrawable(drawable);

		final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
		actionA.setBackgroundResource(R.drawable.fav);
		actionA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(MyActivity.this, "Added To Favourites", Toast.LENGTH_LONG).show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);

		return super.onCreateOptionsMenu(menu);
	}


	public void share(View v)
	{
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey,Download the ZoomCar App @ "+hashmap.get("link")+"  and Enjoy!");
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}



	public static Bitmap StringToBitmap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		} catch (NullPointerException e) {
			e.getMessage();
			return null;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}
	class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}

	class OnSwipeTouchListener implements OnTouchListener {

		protected final GestureDetector gestureDetector;

		public OnSwipeTouchListener (Context ctx){
			gestureDetector = new GestureDetector(ctx, new GestureListener());
		}

		private final class GestureListener extends SimpleOnGestureListener {

			private static final int SWIPE_THRESHOLD = 100;
			private static final int SWIPE_VELOCITY_THRESHOLD = 100;

			@Override
			public boolean onDown(MotionEvent e) {
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				boolean result = false;
				try {
					float diffY = e2.getY() - e1.getY();
					float diffX = e2.getX() - e1.getX();
					if (Math.abs(diffX) > Math.abs(diffY)) {
						if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
							if (diffX > 0) {
								onSwipeRight();
							} else {
								onSwipeLeft();
							}
						}
						result = true;
					}
					else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffY > 0) {
							onSwipeBottom();
						} else {
							onSwipeTop();
						}
					}
					result = true;

				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return result;
			}
		}

		public void onSwipeRight() {
		}

		public void onSwipeLeft() {
		}

		public void onSwipeTop() {
		}

		public void onSwipeBottom() {
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}