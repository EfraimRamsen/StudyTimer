package model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

public class Camera {

	public UUID getId(){
		UUID iD = null;
		return iD.randomUUID(); //TODO might need rework
	}

	public String getPhotoFilename(){
		return "IMG_" + getId().toString() + ".jpg"; //sida 310 nerd ranch
	}

//	public File getPhotoFile(){
//		File filesDir =
//		return new File(filesDir,getPhotoFilename());&//TODO
//	}
}
