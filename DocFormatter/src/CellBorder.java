import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;


public class CellBorder {

	public static void formatTable(XWPFTable xwpfTable) {

		if(xwpfTable==null){
			//System.out.println("Null");
			return;
		}
		List<XWPFTableRow> row = xwpfTable.getRows();
		int rows = row.size();
		//Ignore first row
		for(int i = 1 ; i < rows ;i++) {
			List<XWPFTableCell> cell = row.get(i).getTableCells();
			for (XWPFTableCell xwpfTableCell : cell) {
				Boolean levelOne = false;
				if (xwpfTableCell != null) {
					XWPFParagraph p = xwpfTableCell.getParagraphArray(0);

					//System.out.println(p.getIndentFromLeft() + p.getStyle()+" -- "+ p.getText());
					if(p.getStyleID()!= null && p.getStyleID().contains("dotlist")) {
						//System.out.println("Cell Border -- dotList");
						levelOne = true;
					}

					if(levelOne) {
						CTTc cttc = xwpfTableCell.getCTTc();
						CTTcPr tcPr = cttc.getTcPr();

						CTTcBorders borders = tcPr.addNewTcBorders();
						CTBorder ctb;
						if(borders.getTop()==null) {
							ctb = borders.addNewTop();
							setCellBorder(ctb);
						} else {
							ctb = borders.getTop();
							setCellBorder(ctb);
						}
					}

				}
			}
		}

		
		CTTblPr tblpro = xwpfTable.getCTTbl().getTblPr();

		CTTblBorders borders = tblpro.addNewTblBorders();
		setTableBorder(borders.addNewBottom());		
		setTableBorder(borders.addNewRight());
		setTableBorder(borders.addNewLeft());
		setTableBorder(borders.addNewTop());	
	}
	
	
	public static void setTableBorder(CTBorder border) {
		border.setSz(BigInteger.valueOf(24));
		border.setVal(STBorder.SINGLE); 
		border.setColor("A6A6A6");
	}
	
	public static void setCellBorder(CTBorder ctb) {
		ctb.setSz(BigInteger.valueOf(12));
		ctb.setVal(STBorder.SINGLE);
		ctb.setColor("FFFFFF");
	}

}
