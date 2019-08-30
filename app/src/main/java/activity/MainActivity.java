package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studytimer.R;
import java.util.Random;

import model.Camera;
import model.Timer;
import model.TimerEvent;

/**
 * This class handles the buttons and imageView visible on activity_main.xml and my_menu.xml
 * @author Efraim
 */
public class MainActivity extends AppCompatActivity {

	private TextView mCountdownText;
	private ImageView mImageView;
	private Button mStartStopButton;
	private Timer mTimer;
	private Context mContext;
	private Camera mCamera;
	private int mBreakImageNumber;

	public static final int CAMERA_REQUEST_CODE = 10;
	private static final String KEY_TIMER_RUNNING = "trn";
	private static final String KEY_TIME_LEFT_IN_MILLIS = "tlim";
	private static final String KEY_TIMER_EVENT_ARRAYLIST = "teal";
	private static final String KEY_ACTIVE_TIMER_EVENT_INDEX = "atei";
	private static final String KEY_BREAK_IMAGE_NUMBER = "brin";


	/**
	 * Saves certain values on rotation or closed activity
	 * @param savedInstanceState, Bundle
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(KEY_TIMER_RUNNING,mTimer.getTimerRunning());
		savedInstanceState.putLong(KEY_TIME_LEFT_IN_MILLIS,mTimer.getTimeLeftInMillis());
		savedInstanceState.putParcelableArrayList(KEY_TIMER_EVENT_ARRAYLIST, mTimer.getTimerEventArrayList());
		savedInstanceState.putInt(KEY_ACTIVE_TIMER_EVENT_INDEX, mTimer.getActiveTimerEventIndex());
		savedInstanceState.putInt(KEY_BREAK_IMAGE_NUMBER,mBreakImageNumber);
	}

	/**
	 * This is run on the creation of the application; at first start, rotation and new activity
	 * @param savedInstanceState, Bundle that is null or has values from onSaveInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTimer = new Timer(this);
		mCamera = new Camera(this);


		setContentView(R.layout.activity_main);
		mImageView = findViewById(R.id.photo_view);

		Toolbar myToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(myToolbar);

		mCountdownText = findViewById(R.id.text_countdown);
		mStartStopButton = findViewById(R.id.button_start);
		Button resetButton = findViewById(R.id.button_reset);
		Button skipButton = findViewById(R.id.button_skip);

		// Everything loaded on restored instance state
		if(savedInstanceState != null){
			mTimer.setTimerRunning(savedInstanceState.getBoolean(KEY_TIMER_RUNNING));
			mTimer.setTimeLeftInMillis(savedInstanceState.getLong(KEY_TIME_LEFT_IN_MILLIS));
			mTimer.setTimerEventArrayList(savedInstanceState.<TimerEvent>getParcelableArrayList(KEY_TIMER_EVENT_ARRAYLIST));
			mTimer.setActiveTimerEventIndex(savedInstanceState.getInt(KEY_ACTIVE_TIMER_EVENT_INDEX));
			mBreakImageNumber = savedInstanceState.getInt(KEY_BREAK_IMAGE_NUMBER);

			if(mTimer.getTimerRunning()){
				mTimer.startTimer();
			}
			else{
				mTimer.pauseTimer();
			}
		}

		mContext = this.getApplicationContext();

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
				mTimer.nextRound();
			}
		});

		mTimer.updateCountdownText();
	}

	/**
	 * Creates the menu on the toolbar
	 * @param menu, Menu
	 * @return created menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.my_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 *
	 * @param item, the item selected in the menu. In this version there is only
	 *              the camera button but other buttons might be added in future
	 *              releases, that's why it's a switch.
	 * @return selected item
	 */
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

	/**
	 * Set the countdown text (25:00, etc)
	 * @param countdownText, String
	 */
	public void setCountdownText(String countdownText) {
		mCountdownText.setText(countdownText);

	}

	/**
	 * Set the the text for the start/stop button. Used when
	 * pressed so it will be clear what happens if it's pressed
	 * again
	 * @param buttonTextId, int, the resource id for the text
	 */
	public void setStartStopButtonText(int buttonTextId){
		mStartStopButton.setText(buttonTextId);
	}

	/**
	 * @return the ImageView mImageView
	 */
	public ImageView getImageView() {
		return mImageView;
	}

	/**
	 * Called when the current window of the activity gains or loses
	 * focus. Updates the mImageView with correct image.
	 * @param hasFocus, boolean, if the window has focus or not
	 */
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(mCamera.getCameraFile() != null && mCamera.getCameraFile().exists() && hasFocus){
			updateImageView();
		}
	}

	/**
	 * This method is used to get the right image resource id for a corresponding number.
	 * Example:
	 * @param imageNumber, int, if the number is 2:
	 * @return imageResId = R.drawable.stretching_02
	 * If there is no corresponding image for the number, image number 1 is chosen as default.
	 */
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

	/**
	 * Updates the ImageView with a stretching image if the new event is a break or with the
	 * saved photo if the event is work.
	 */
	public void updateImageView(){
		if(mTimer.getTimerEventArrayList()
				.get(mTimer.getActiveTimerEventIndex())
				.isBreak()) {
			mImageView.setImageResource(getBreakImageResId(mBreakImageNumber));
		}
		else{
			mCamera.updateImageViewFromFile();
		}
	}

	/**
	 * Used to get a random number (1-19) to get a random stretching image.
	 * Global variable mBreakImageNumber is set to make it possible to save it on rotation etc.
	 */
	public void setRandomBreakImageNumber(){
		Random r = new Random();
		int lowest = 1;
		int highest = 19;

		mBreakImageNumber =  r.nextInt(highest - lowest)+ lowest;
	}
}
