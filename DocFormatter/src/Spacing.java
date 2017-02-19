
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

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
						
						
						//borders.setBottom(ctb);
						
						//tcPr.
		                //CTShd ctshd = tcPr.addNewShd();
		                //ctshd.setColor("A7BFDE");
		                //ctshd.setColor();
						
						XWPFParagraph p = xwpfTableCell.getParagraphArray(0);
						//System.out.println(p.getIndentFromLeft() + p.getStyleID()+" -- "+ p.getText());
						//p.setIndentFromLeft(490);
						setSpacingbyIndentLevelofParagraph(p);
						if(i==0) { setFirstLine(p);}
						if(i==rows-1) { setLastLine(p);}

					}
				}
			}

		}
	}


	public static void setSpacingbyIndentLevelofParagraph(XWPFParagraph p) {
		System.out.println(p.getIndentFromLeft() + p.getStyle()+" -- "+ p.getText());
		if(p.getStyleID()!= null && p.getStyleID().contains("dotlist")) {
			System.out.println("--------dot");
			setMiddleLine(p);
		}
		if(p.getStyleID()!= null && p.getStyleID().contains("secondList")) {
			System.out.println("--------secondList");
			setMiddleLineSecondIndent(p);
		}
		if(p.getStyleID()!= null && p.getStyleID().contains("listthird")) {
			System.out.println("--------listthird");
			setMiddleLineSecondIndent(p);
			//p.s
			//p.setIndentFromLeft(1392);
		}
		if(p.getIndentFromLeft()==1800) {
			//p.setIndentFromLeft(1392);
		}
	}
	
	//Set Spacing before 5pt (First Line)
	public static void setFirstLine(XWPFParagraph para) {
		//para.setSpacingAfter(140);
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

	//Set Spacing before 0pt After 7pt (medium Line-- second indent)
	public static void setMiddleLineSecondIndent(XWPFParagraph para) {
		para.setSpacingAfter(140);
		para.setSpacingBefore(0);
	}

	//Set Spacing After 5pt (Last Line)
	public static void setLastLine(XWPFParagraph para) {
		para.setSpacingAfter(100);
		//para.setSpacingBefore(100);
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