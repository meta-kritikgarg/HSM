import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hwmf.record.HwmfBitmapDib.Compression;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import Static.OldFormats;
import filehandler.FileFounder;
import filehandler.FileHandler;
import paragraph.ParagraphAnalizer;

public class DocFormatter {
	public static void main(String[] args) throws Exception {

		
		List<String> files = FileFounder.getAllfiles("/C:/Users/Kritik Garg/Documents/OutSide/Weston/OME");
		Set<String> setOfFormats = new HashSet<String>();

//		for (String fileName : files) {
			
		
		
		int tableCounter = 0;
		try {
			String fileName = "templete.docx";
			if (!(fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
				throw new FileFormatException();
			} else {

				XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
				//FileOutputStream fos = new FileOutputStream("C:/Users/admin/Documents/Drive/OneDrive/"+fileName);
				
				XWPFDocument template = FileHandler.openFile("templete.docx");       
				      
				
				
				XWPFParagraph para = template.createParagraph();
				para.setStyle("checkedlist");

				XWPFRun run = para.createRun();
				run.setText("hi this is dot text");
				FileOutputStream nfos = new FileOutputStream("v1.docx");

				template.write(nfos);
				
				
				FileOutputStream fos = new FileOutputStream("v1"+fileName);

				Iterator<IBodyElement> bodyElementIterator = doc.getBodyElementsIterator();

				List<String> docElements = new ArrayList<String>();


				int pcounter = -1;
				int tcounter = -1;

				
				while (bodyElementIterator.hasNext()) {
					IBodyElement element = bodyElementIterator.next();
					docElements.add(element.getElementType().name());
					
					if ("PARAGRAPH".equalsIgnoreCase(element.getElementType().name())) {
						pcounter++;
						//ParagraphAnalizer.getStyle(doc.getParagraphArray(pcounter));
						setOfFormats.add(ParagraphAnalizer.getStyle(doc.getParagraphArray(pcounter)));
					}
					
				}


				

		/*		for(int i=0 ; i < docElements.size() ; i++) {
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

*/

				doc.write(fos);

				doc.close();

				System.out.println(setOfFormats);
				System.out.println(doc.getTables().size());
				System.out.println(doc.getParagraphs().size());
				System.out.println(doc.getBodyElements().size());


			//	System.out.println(tableCounter);

			}
		} catch (FileFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
//	}

}
