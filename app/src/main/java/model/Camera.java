package model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

import activity.MainActivity;

import static activity.MainActivity.CAMERA_REQUEST_CODE;

/**
 * The Camera class handles everything related to starting the external camera, saving the photo
 * and loading it from the app storage.
 */
public class Camera {

	private File mCameraFile;
	public final static String FILE="com.example.studytimer.FILE";
	private MainActivity mMainActivity;
	private Uri mUri;

	/**
	 * Constructor for the camera.
	 * @param mainActivity, passed into the constructor to use some methods in MainActivity and
	 *                      to set the imageView there in updateImageViewFromFile().
	 */
	public Camera(MainActivity mainActivity){
		mMainActivity = mainActivity;
		setFileDir();
		mUri = FileProvider.getUriForFile(mMainActivity.getApplicationContext(),
				mMainActivity.getPackageName() + ".fileprovider", mCameraFile);
	}

	/**
	 * Get method for the file where the photo is stored.
	 * @return mCameraFile, File
	 */
	public File getCameraFile(){
		return mCameraFile;
	}

	/**
	 * Set the directory where the file should be saved. Only one photo is used so when a new photo
	 * is taken it will overwrite the old one since it will have the same directory and filename.
	 */
	public void setFileDir(){
		File dir = mMainActivity.getFilesDir();
		mCameraFile = new File(dir, "mypic.jpg");
	}

	/**
	 * This method creates the intent for the camera and starts the camera activity.
	 */
	public void	startCamera() {
		// Create intent to take a photo
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			i.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
			i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

		//Start activity
		mMainActivity.startActivityForResult(
				i, CAMERA_REQUEST_CODE);
	}

	/**
	 * This method updates the imageView in MainActivity with the photo stored in the used
	 * directory. Before setting the imageView it will scale the picture to fit it.
	 */
	public void updateImageViewFromFile() {

		// Get the dimensions of the View
		int targetW = mMainActivity.getImageView().getWidth();
		int targetH = mMainActivity.getImageView().getHeight();

		// Get the dimensions of the bitmap
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;

		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		// Determine how much to scale down the image
		int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

		// Decode the image file into a Bitmap sized to fill the View
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		Bitmap bitmap = BitmapFactory.decodeFile(mCameraFile.getAbsolutePath(), bmOptions);
		mMainActivity.getImageView().setImageBitmap(bitmap);

	}
}
