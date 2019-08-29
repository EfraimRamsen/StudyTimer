package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytimer.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import model.Camera;
import model.Timer;
import static model.Camera.FILE;

public class MainActivity extends AppCompatActivity {

	private TextView mCountdownText;
	private ImageView mImageView;
	private Button mStartStopButton;
	private Timer mTimer;
	private Context mContext;
	private Camera mCamera;

	public static final int CAMERA_REQUEST_CODE = 10;
	private static final String KEY_ = "?";//TODO

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTimer = new Timer(this);
		mCamera = new Camera(this);

		if(savedInstanceState != null){

		}

		setContentView(R.layout.activity_main);
		mImageView = findViewById(R.id.photo_view);

//		if(mCamera.getCameraFile() != null){
//			mCamera.updateImageViewFromFile();
//		}

		Toolbar myToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		Button resetButton = findViewById(R.id.button_reset);
		Button skipButton = findViewById(R.id.button_skip);


		mContext = this.getApplicationContext();

		//TODO Glöm inte fixa onSaveInstancestate etc.




		mStartStopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressStartStopButton();
			}
		});

		resetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressResetButton();
			}
		});

		skipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTimer.pressSkipButton();
			}
		});

		mTimer.updateCountdownText();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.my_menu,menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.button_camera:

				mCamera.startCamera();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);

	}

	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}

	public ImageView getImageView() {
		return mImageView;
	}

	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(mCamera.getCameraFile() != null && mCamera.getCameraFile().exists() && hasFocus){
			updateImageView();
		}
//			mCamera.updateImageViewFromFile();
	}

	public int getBreakImageResId(int imageNumber){
		int imageResId;
		switch (imageNumber){
			default:
			case 1:
				imageResId = R.drawable.stretching_01;
				break;
			case 2:
				imageResId = R.drawable.stretching_02;
				break;
			case 3:
				imageResId = R.drawable.stretching_03;
				break;
			case 4:
				imageResId = R.drawable.stretching_04;
				break;
			case 5:
				imageResId = R.drawable.stretching_05;
				break;
			case 6:
				imageResId = R.drawable.stretching_06;
				break;
			case 7:
				imageResId = R.drawable.stretching_07;
				break;
			case 8:
				imageResId = R.drawable.stretching_08;
				break;
			case 9:
				imageResId = R.drawable.stretching_09;
				break;
			case 10:
				imageResId = R.drawable.stretching_10;
				break;
			case 11:
				imageResId = R.drawable.stretching_11;
				break;
			case 12:
				imageResId = R.drawable.stretching_12;
				break;
			case 13:
				imageResId = R.drawable.stretching_13;
				break;
			case 14:
				imageResId = R.drawable.stretching_14;
				break;
			case 15:
				imageResId = R.drawable.stretching_15;
				break;
			case 16:
				imageResId = R.drawable.stretching_16;
				break;
			case 17:
				imageResId = R.drawable.stretching_17;
				break;
			case 18:
				imageResId = R.drawable.stretching_18;
				break;
			case 19:
				imageResId = R.drawable.stretching_19;
				break;

		}
		return imageResId;
	}

	public void updateImageView(){
		if(mTimer.getTimerEventArrayList()
				.get(mTimer.getActiveTimerEventIndex())
				.isBreak()){
			//TEST, sätt random nummer sen
			mImageView.setImageResource(getBreakImageResId(randomBetween(1,19)));//TODO
//					mMainActivity.getBreakImageResId(1));
		}
		else{
			mCamera.updateImageViewFromFile();
		}
	}

	private int randomBetween(int lowest, int highest){
		Random r = new Random();

		return r.nextInt(highest - lowest)+ lowest;
	}
}
