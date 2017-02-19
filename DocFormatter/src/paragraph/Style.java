package paragraph;

import Static.OldFormats;

public class Style {

	public static String getNewStyle( String OldStyle) {

		String newStyle = new String();
		if(OldStyle == null) {
			System.out.println("No style Syyle in old Doc");
			return null;
		}
		switch (OldStyle) {
		case "BlackBullet": 
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
		case "SubBullet":
			newStyle = "Heading2";

			break;
		case "ArrowEnding":
			newStyle = "checkedlist";

			break;
			
		case "ArrowStarting":
			newStyle = "checkedlist";

			break;
			
		case "1stCaption":
			newStyle = "Heading1";

			break;

		default:
			System.out.println("Unhandled Syyle: "+ OldStyle );
			newStyle = "null";
			break;
		}

		return newStyle;
	}

}
