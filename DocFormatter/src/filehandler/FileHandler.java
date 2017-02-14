package filehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class FileHandler {
	
	public static XWPFDocument openFile(String filePath) throws IOException {
		return new XWPFDocument(new FileInputStream(new File(filePath)));
	}
	
	public static void writeFile(String fileName, XWPFDocument doc) throws IOException{
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		doc.write(fos);
	}
	
	
	public static void copyFile(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

}
