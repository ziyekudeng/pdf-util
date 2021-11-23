package com.testautomationguru;

import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;

import java.io.IOException;

public final class Main {

	public static void main(String[] args) throws IOException {

		if(args.length<2){
			showUsage();
		}else{
            final PDFUtil pdfutil = new PDFUtil();
			pdfutil.setCompareMode(CompareMode.VISUAL_MODE);

			if(args.length>2){
				pdfutil.highlightPdfDifference(true);
                pdfutil.generateAllCompareImage(true);
				pdfutil.setImageDestinationPath(args[2]);
			}

			pdfutil.compare(args[0], args[1]);
		}

	}

	private static void showUsage(){
		System.out.println("Usage: java -jar pdf-util.jar file1.pdf file2.pdf [Optional:image-destination-path]");
	}
}
