package paragraph;

import Static.OldFormats;

public class Style {

	public static String getNewStyle( String OldStyle) {

		String newStyle = new String();
		if(OldStyle == null) {
			//System.out.println("No style Style in old Doc...");
			return null;
		}
		switch (OldStyle) {
		case "BlackBullet": 
			newStyle = "dotlist";
			break;
			
			//ecxmsonormal
		case "ecxmsonormal": 
			newStyle = "dotlist";
			break;
				
			
		case "HollowBullets":
			newStyle = "secondList";
			break;
		case "SquareBullet":
			newStyle = "listthird";

			break;
		case "Paragraph1":
			newStyle = "myParagraph";

			break;
			
		case "ListParagraph":
			newStyle = "myParagraph";

			break;
			//OmniPage1
			
		case "OmniPage1":
			newStyle = "myParagraph";

			break;
		case "SubBullet":
			newStyle = "Heading2";

			break;
		case "ArrowEnding":
			newStyle = "Redbg";

			break;
			
		case "ArrowStarting":
			newStyle = "checkedlist";

			break;
			
		case "1stCaption":
			newStyle = "Heading1";

			break;
			
			//ListParagraph

		default:
			System.out.println("Unhandled Syyle: "+ OldStyle );
			newStyle = null;
			break;
		}

		return newStyle;
	}

}
