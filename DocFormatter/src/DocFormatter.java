import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.border.Border;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

public class DocFormatter {
	public static void main(String[] args) throws Exception {

		int tableCounter = 0;
		try {
			String fileName = "HSM Doc 005 - Verification, Review & Evaluation v5.docx";
			if (!(fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
				throw new FileFormatException();
			} else {

				XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
				FileOutputStream fos = new FileOutputStream(fileName);

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
						if(tcounter!=1) {
							Indent.formatTable(doc.getTableArray(tcounter));
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
