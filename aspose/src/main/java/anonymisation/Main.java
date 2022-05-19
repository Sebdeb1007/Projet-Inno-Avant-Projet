package anonymisation;


import com.aspose.pdf.*;


public class Main {

    
    private static String _dataDir = "N:/PI/file-anonymization/";
    public static void ReplaceTextOnAllPages() {
        Document pdfDocument = new Document(_dataDir+"sample.pdf");

        // Create TextAbsorber object to find all instances of the input search phrase
        TextFragmentAbsorber textFragmentAbsorber = new TextFragmentAbsorber("Sébastien De Bod");
        
        // Accept the absorber for first page of document
        pdfDocument.getPages().accept(textFragmentAbsorber);
        
        // Get the extracted text fragments into collection
        TextFragmentCollection textFragmentCollection = textFragmentAbsorber.getTextFragments();
        
        // Loop through the fragments
        for (TextFragment textFragment : (Iterable<TextFragment>) textFragmentCollection) {
            // Update text and other properties
            textFragment.setText("token");
            textFragment.getTextState().setFont(FontRepository.findFont("Verdana"));
            textFragment.getTextState().setFontSize(12);
            textFragment.getTextState().setForegroundColor(Color.getBlue());
            textFragment.getTextState().setBackgroundColor(Color.getGray());
        }
        // Save the updated PDF file
        pdfDocument.save(_dataDir+"Updated_Text_Aspose.pdf");
    }
    public static void main(String[] args) {
        ReplaceTextOnAllPages();
        
    }
    
    
}
