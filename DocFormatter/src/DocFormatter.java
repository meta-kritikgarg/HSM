import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class DocFormatter {
	public static void main(String[] args) throws Exception {


		List<String> files =  FileFounder.getAllfiles("OME/");		
		System.out.println(files);

		for (String file : files) {



			int tableCounter = 0;
			try {
				String fileName = file;//"HSM Doc 005 - Verification, Review & Evaluation v5.docx";
				if (!(fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
					throw new FileFormatException();
				} else {

					XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
					//FileOutputStream fos = new FileOutputStream("C:/Users/admin/Documents/Drive/OneDrive/"+fileName);

					Path p = Paths.get(fileName);

					FileOutputStream fos = new FileOutputStream("OMEOut/"+ p.getFileName());

					Iterator<IBodyElement> bodyElementIterator = doc.getBodyElementsIterator();

					List<String> docElements = new ArrayList<String>();


					int pcounter = -1;
					int tcounter = -1;

					while (bodyElementIterator.hasNext()) {
						IBodyElement element = bodyElementIterator.next();
						docElements.add(element.getElementType().name());
					}



					for(int i=0 ; i < docElements.size() ; i++) {
						if ("TABLE".equalsIgnoreCase(docElements.get(i))) {
							tcounter++;
							System.out.println(tcounter);
							if(tcounter!=0) {
								Indent.formatTable(doc.getTableArray(tcounter));
								CellBorder.formatTable(doc.getTableArray(tcounter));
								Spacing.formatTable(doc.getTableArray(tcounter));
								Spacing.setbeforeTable(doc.getParagraphArray(pcounter));
								Spacing.setafterTable(doc.getParagraphArray(pcounter+1));
							}
							//doc.getParagraphArray(pcounter).getText();
							//System.out.println( doc.getParagraphArray(pcounter).getText() );
						}

						if ("PARAGRAPH".equalsIgnoreCase(docElements.get(i))) {
							pcounter++;
						}
					}

					System.out.println(docElements);



					doc.write(fos);

					doc.close();

					System.out.println(doc.getTables().size());
					System.out.println(doc.getParagraphs().size());
					System.out.println(doc.getBodyElements().size());


					System.out.println(tableCounter);

				}
			} catch (FileFormatException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
