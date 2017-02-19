import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Indent {

	public static void formatTable(XWPFTable xwpfTable) {

		if(xwpfTable==null){
			System.out.println("Null");
			return;
		}
		List<XWPFTableRow> row = xwpfTable.getRows();
		int rows = row.size();
		for(int i = 0 ; i < rows ;i++) {
			List<XWPFTableCell> cell = row.get(i).getTableCells();
			for (XWPFTableCell xwpfTableCell : cell) {
				if (xwpfTableCell != null) {
					
					List<XWPFParagraph> listOfParagraph = xwpfTableCell.getParagraphs();
					XWPFParagraph p = xwpfTableCell.getParagraphArray(0);
					
					for (XWPFParagraph xwpfParagraph : listOfParagraph) {
						setIndentLevelofParagraph(xwpfParagraph);
					}
					//p.setIndentFromLeft(490);
				}
			}
		}

	}


	public static void setIndentLevelofParagraph(XWPFParagraph p) {
		System.out.println(p.getIndentFromLeft() + p.getStyle()+" -- "+ p.getText());
		if(p.getStyleID()!= null && p.getStyleID().contains("dotlist")) {
			System.out.println("--------dot");
			p.setIndentFromLeft(490);
		}
		if(p.getStyleID()!= null && p.getStyleID().contains("secondList")) {
			System.out.println("--------secondList");
			p.setIndentFromLeft(964);
		}
		if(p.getStyleID()!= null && p.getStyleID().contains("listthird")) {
			System.out.println("--------listthird");
			p.setIndentFromLeft(1392);
		}
		if(p.getIndentFromLeft()==1800) {
			p.setIndentFromLeft(1392);
		}
	}

}