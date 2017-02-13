package paragraph;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ParagraphAnalizer {

	public static String getStyle(XWPFParagraph para) {
		if(para.getText()!=null && para.getText().length() > 0 && para.getText().trim().length() > 0) {
		if(para.getStyle()!= null) {
			//System.out.println(para.getStyleID()+"--"+para.getText());
			return para.getStyleID();
		} else {
			System.out.println(para.getText());
		}
	}
		return "null";
}
	}
