import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import filehandler.FileFounder;
import filehandler.FileHandler;
import paragraph.ParagraphAnalizer;

public class DocFormatter {
	public static void main(String[] args) throws Exception {


		List<String> files = FileFounder.getAllfiles("/C:/Users/Kritik Garg/Documents/OutSide/Weston/OMDR/exp");
		Set<String> setOfFormats = new HashSet<String>();


		XWPFTable xwpfTable = null;

		int i =1;
		for (String fileName : files) {

			List<XWPFTable> listOfGeneratedTable = new ArrayList<XWPFTable>();

			try {
				//String fileName = "OME Doc No. 001 - Engine Room Operational Requirements v1.docx";
				if (!(fileName.endsWith(".doc") || fileName.endsWith(".docx"))) {
					throw new FileFormatException();
				} else {

					XWPFDocument doc = new XWPFDocument(new FileInputStream(fileName));
					//FileOutputStream fos = new FileOutputStream("C:/Users/admin/Documents/Drive/OneDrive/"+fileName);

					XWPFDocument template = FileHandler.openFile("templete1.docx");     
					//template.

					List<String> subBullets = new ArrayList<String>();
					subBullets.add("In This Document");

					List<IBodyElement> bodyElementList = doc.getBodyElements();
					
						
					
					 
					Boolean tableSwitch = false;
					int inThisDoc = -1;
					
					for (int j = 0; j < bodyElementList.size(); j++) {
						
						IBodyElement element = bodyElementList.get(j);
						
						
						
						if ("PARAGRAPH".equalsIgnoreCase(element.getElementType().name())) {
							XWPFParagraph oPara = (XWPFParagraph) element;
							if(ParagraphAnalizer.isValidParagraph(oPara)) {

								if("SubBullet" .equalsIgnoreCase(ParagraphAnalizer.getStyle(oPara))){
									tableSwitch = true;
									if(inThisDoc==-1)
									inThisDoc = j;
									subBullets.add(oPara.getText());
								}
								//ArrowEnding
								if("ArrowEnding" .equalsIgnoreCase(ParagraphAnalizer.getStyle(oPara))){
									tableSwitch = false;
								}
								if(tableSwitch && isStylePartofTable(oPara)){
									if(xwpfTable == null ) {
										//MyOrangeStyle
										XWPFTable table = template.createTable();
										table.getCTTbl().getTblPr().unsetTblBorders();
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
									
									if(xwpfTable!= null){
										
										xwpfTable = null;
									}
								}
								ParagraphAnalizer.getStyle(oPara);
								setOfFormats.add(ParagraphAnalizer.getStyle(oPara));
							}
						}
						else {
							//System.out.println(element.getElementType().name());
							if ("Table".equalsIgnoreCase(element.getElementType().name())) {
								XWPFTable tab = (XWPFTable)element;
								//XWPFTable newTable = template.createTable();
								CTTbl tbl = template.createTable().getCTTbl(); 

								tbl.set(tab.getCTTbl()); 

								XWPFTable table2 = new XWPFTable(tbl, template); 

								//table2.getRows().get(0).getCell(0).setText("test");
							}
							else {
								
								System.out.println(element.getElementType().name());
							}
						}

					}

					//template.createTable(subBullets.size(), 2);
					

					Path p = Paths.get(fileName);
					
					FileOutputStream nfos = new FileOutputStream("OMDout/"+p.getFileName().toString());

					template.write(nfos);


					doc.close();
					
/*					
					for (XWPFTable xwpfTable2 : listOfGeneratedTable) {
						Indent.formatTable(xwpfTable2);
						CellBorder.formatTable(xwpfTable2);
						Spacing.formatTable(xwpfTable2);
					}
*/
					
					
					if(doc.getBodyElements().size() == (doc.getAllPictures().size()+ doc.getTables().size() + doc.getParagraphs().size())){
						System.out.println("All elements copied \n" + fileName);
					} else {
						System.out.println("Unhandled Elements in Doc " + fileName);
						System.out.println(doc.getAllPictures().size());
						System.out.println(doc.getTables().size());
						System.out.println(doc.getParagraphs().size());
						System.out.println(doc.getBodyElements().size());
					}
					System.out.println(setOfFormats);

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
