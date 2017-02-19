import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import filehandler.FileFounder;
import filehandler.FileHandler;
import paragraph.ParagraphAnalizer;

public class DocFormatter {
	public static void main(String[] args) throws Exception {


		List<String> files = FileFounder.getAllfiles("/C:/Users/Kritik Garg/Documents/OutSide/Weston/OME");
		Set<String> setOfFormats = new HashSet<String>();

		List<XWPFTable> listOfGeneratedTable = new ArrayList<XWPFTable>();

		XWPFTable xwpfTable = null;

		int i =1;
		for (String fileName : files) {

			

			try {
				//String fileName = "OME Doc No. 001 - Engine Room Operational Requirements v1.docx";
				if (!(fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
					throw new FileFormatException();
				} else {

					XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
					//FileOutputStream fos = new FileOutputStream("C:/Users/admin/Documents/Drive/OneDrive/"+fileName);

					XWPFDocument template = FileHandler.openFile("templete1.docx");     
					//template.

					Iterator<IBodyElement> bodyElementIterator = doc.getBodyElementsIterator();

					Boolean tableSwitch = false;
					
					while (bodyElementIterator.hasNext()) {
						IBodyElement element = bodyElementIterator.next();
						
						

						if ("PARAGRAPH".equalsIgnoreCase(element.getElementType().name())) {
							XWPFParagraph oPara = (XWPFParagraph) element;
							if(ParagraphAnalizer.isValidParagraph(oPara)) {

								if("SubBullet" .equalsIgnoreCase(ParagraphAnalizer.getStyle(oPara))){
									tableSwitch = true;
								}
								//ArrowEnding
								if("ArrowEnding" .equalsIgnoreCase(ParagraphAnalizer.getStyle(oPara))){
									tableSwitch = false;
								}
								if(tableSwitch && isStylePartofTable(oPara)){
									if(xwpfTable == null ) {
										//MyOrangeStyle
										XWPFTable table = template.createTable();
										table.setStyleID("MyOrangeStyle");

										listOfGeneratedTable.add(table);
										XWPFTableRow row = table.getRow(0);

										ParagraphAnalizer.cloneParagraph(row.getCell(0).getParagraphs().get(0),oPara);
									} else {
										XWPFTableRow row = xwpfTable.createRow();
										ParagraphAnalizer.cloneParagraph(row.getCell(0).getParagraphs().get(0),oPara);
									}
								} else {
									ParagraphAnalizer.cloneParagraph(template.createParagraph(),oPara);
									xwpfTable = null;
								}
								ParagraphAnalizer.getStyle(oPara);
								setOfFormats.add(ParagraphAnalizer.getStyle(oPara));
							}
						}
						else {
							System.out.println(element.getElementType().name());
							if ("Table".equalsIgnoreCase(element.getElementType().name())) {
								XWPFTable tab = (XWPFTable)element;
								System.out.println(tab.getStyleID());
							}
						}

					}


					FileOutputStream nfos = new FileOutputStream("doc"+i+".docx");

					template.write(nfos);


					doc.close();

					System.out.println(setOfFormats);
					System.out.println(doc.getTables().size());
					System.out.println(doc.getParagraphs().size());
					System.out.println(doc.getBodyElements().size());

					i++;
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
	}


	public static Boolean isStylePartofTable(XWPFParagraph para ) {

		String oldStyle = ParagraphAnalizer.getStyle(para);
		if(("BlackBullet".equalsIgnoreCase(oldStyle))
				|| ("HollowBullets".equalsIgnoreCase(oldStyle) )
				|| ("SquareBullet".equalsIgnoreCase(oldStyle)) ) {

			return true;
		}
		return false;

	}
}
