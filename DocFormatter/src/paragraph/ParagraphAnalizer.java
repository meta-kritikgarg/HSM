package paragraph;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

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


	public static Boolean isValidParagraph(XWPFParagraph para) {
		if(para.getText()!=null && para.getText().length() > 0 && para.getText().trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	 /**
	  * To make copy of a paragraph
	  * @param clone Destination paragraph pointer
	  * @param source Source Paragraph pointer
	  */
	public static void cloneParagraph(XWPFParagraph clone, XWPFParagraph source) {
		
		String style = Style.getNewStyle(ParagraphAnalizer.getStyle(source));
		if(style!=null) {
		clone.setStyle(style);
		}

		//clone.setSpacingAfter(0);
		//clone.setSpacingBefore(0);
		
		for (XWPFRun run : source.getRuns()) {  
			String textInRun = run.getText(0);
			if (textInRun == null || textInRun.isEmpty()) {
				continue;
			}

			
			//int fontSize = run.getFontSize();
			//System.out.println("run text = '" + textInRun + "' , fontSize = " + fontSize); 
			XWPFRun newRun = clone.createRun();
			// Copy text
			newRun.setText(textInRun);
			if(style==null) {

			// Apply the same style
//			newRun.setFontSize( ( fontSize == -1) ? DEFAULT_FONT_SIZE : run.getFontSize() );    
			newRun.setFontFamily( "Helvetica Neue");
			newRun.setBold( run.isBold() );
			newRun.setItalic( run.isItalic() );
			newRun.setColor( run.getColor() );
			}
//			
		}   
	}

}



