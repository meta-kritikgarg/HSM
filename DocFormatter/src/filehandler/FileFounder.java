package filehandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFounder {
	
	public static List<String> getAllfiles(String folderPath) {
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		List<String> listOfStringFiles = new ArrayList<String>();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	listOfStringFiles.add(file.toString());
		    }
		}		
		return listOfStringFiles;
	}

}
