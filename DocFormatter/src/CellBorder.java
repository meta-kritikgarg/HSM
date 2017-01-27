import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
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

					//					System.out.println(p.getIndentFromLeft() + p.getStyle()+" -- "+ p.getText());
					if(p.getStyleID()!= null && p.getStyleID().contains("dotlist")) {
						System.out.println("Cell Border -- dotList");

						//p.setIndentFromLeft(490);
						levelOne = true;
					}

					if(levelOne) {
						CTTc cttc = xwpfTableCell.getCTTc();
						CTTcPr tcPr = cttc.getTcPr();

						CTTcBorders borders = tcPr.addNewTcBorders();
						CTBorder ctb;
						if(borders.getTop()==null) {
							ctb = borders.addNewTop();
						} else {
							ctb = borders.addNewTop();
						}
						ctb.setSz(BigInteger.valueOf(12));
						ctb.setVal(STBorder.SINGLE);
						ctb.setColor("000000");
					}

					//p.setIndentFromLeft(490);
				}
			}
		}

		CTBorder top;
		if(xwpfTable.getCTTbl().getTblPr().getTblBorders()!=null ){
			 top = xwpfTable.getCTTbl().getTblPr().getTblBorders().getLeft();
		} else {
			 top = xwpfTable.getCTTbl().getTblPr().addNewTblBorders().addNewLeft();
		}
		CTTblBorders borders = xwpfTable.getCTTbl().getTblPr().getTblBorders();
		borders.setBottom(top);
		borders.setTop(top);
	}

}
