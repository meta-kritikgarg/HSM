
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;

public class Spacing {


	public static void formatTable(XWPFTable xwpfTable) {

		if(xwpfTable==null){
			//System.out.println("Null");
			return;
		}
		List<XWPFTableRow> row = xwpfTable.getRows();
		int rows = row.size();
		if(rows == 1) {
			List<XWPFTableCell> cell = row.get(0).getTableCells();
			for (XWPFTableCell xwpfTableCell : cell) {
				if (xwpfTableCell != null) {
					//System.out.println(xwpfTableCell.getText());
					XWPFParagraph p = xwpfTableCell.getParagraphArray(0);
					setOneLine(p);
					//System.out.println(p.getIndentFromLeft() +" -- "+ p.getText());
				}
			}
		} else {
			for(int i = 0 ; i < rows ;i++) {
				List<XWPFTableCell> cell = row.get(i).getTableCells();
				for (XWPFTableCell xwpfTableCell : cell) {
					if (xwpfTableCell != null) {
						//System.out.println(xwpfTableCell.getText());
						//System.out.println(row.get(i).+" "+ xwpfTableCell.getText());
						CTTc cttc = xwpfTableCell.getCTTc();
						CTTcPr tcPr = cttc.getTcPr();
						
						
						
						 
						CTTcBorders borders = tcPr.addNewTcBorders();
						CTBorder ctb;
						if(borders.getBottom()==null) {
						 ctb = borders.addNewBottom();
						} else {
						 ctb = borders.getBottom();

						}
						ctb.setSz(BigInteger.valueOf(10));
						ctb.setVal(STBorder.SINGLE);
						ctb.setColor("FFFFFF");
						
						//borders.setBottom(ctb);
						
						//tcPr.
		                //CTShd ctshd = tcPr.addNewShd();
		                //ctshd.setColor("A7BFDE");
		                //ctshd.setColor();
						
						XWPFParagraph p = xwpfTableCell.getParagraphArray(0);
						//System.out.println(p.getIndentFromLeft() + p.getStyleID()+" -- "+ p.getText());
						//p.setIndentFromLeft(490);
						setMiddleLine(p);
						if(i==0) { setFirstLine(p);}
						if(i==rows-1) { setLastLine(p);}

					}
				}
			}

		}
	}


	//Set Spacing before 5pt After 7pt (First Line)
	public static void setFirstLine(XWPFParagraph para) {
		para.setSpacingAfter(140);
		para.setSpacingBefore(100);
	}


	//Set Spacing only one line 5pt After 5pt (one Line)
	public static void setOneLine(XWPFParagraph para) {
		para.setSpacingAfter(100);
		para.setSpacingBefore(100);
	}


	//Set Spacing before 5pt After 7pt (medium Line)
	public static void setMiddleLine(XWPFParagraph para) {
		para.setSpacingAfter(140);
		para.setSpacingBefore(100);
	}


	//Set Spacing before 5pt After 5pt (Last Line)
	public static void setLastLine(XWPFParagraph para) {
		para.setSpacingAfter(100);
		para.setSpacingBefore(100);
	}


	//Set Spacing para before table 12pt After
	public static void setbeforeTable(XWPFParagraph para) {
		para.setSpacingAfter(240);
	}

	//Set Spacing para after table 12pt before
	public static void setafterTable(XWPFParagraph para) {
		para.setSpacingBefore(240);
	}



}
