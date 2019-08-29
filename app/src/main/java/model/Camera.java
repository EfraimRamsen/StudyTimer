package model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

import activity.MainActivity;

import static activity.MainActivity.CAMERA_REQUEST_CODE;

public class Camera {

	private File mCameraFile;
	public final static String FILE="com.example.studytimer.FILE";
	private MainActivity mMainActivity;
	private Uri mUri;

	public Camera(MainActivity mainActivity){
		mMainActivity = mainActivity;
		setFileDir();
		mUri = FileProvider.getUriForFile(mMainActivity.getApplicationContext(),
				mMainActivity.getPackageName() + ".fileprovider", mCameraFile);
	}

	public File getCameraFile(){
		return mCameraFile;
	}

	public void setCameraFile(File cameraFile){
		mCameraFile = cameraFile;
	}

	public void setFileDir(){
		File dir = mMainActivity.getFilesDir();
		mCameraFile = new File(dir, "mypic.jpg");
	}

	public void	startCamera() {
		// Skapa ett intent för att ta en bild
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		/* Bestäm vart filen ska sparas
		 * Observera att Kamera-appen måste kunna skriva till platsen
		 * där filen finns
		 */
			// låter oss spara filen var som helst utan att behöva
			// bry os om att kameraappen har rättighet at skriva dit. Dessutom har vi
			// om vi sparar filen på ett ställe vi har koll på kontroll över att ingen
			// annan app sabbar något

//			setFileDir();


			i.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
			i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

		//Starta aktiviteten
		mMainActivity.startActivityForResult(
				i, CAMERA_REQUEST_CODE);
	}

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
