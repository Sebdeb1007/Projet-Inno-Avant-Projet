package anonymisation;

import java.io.BufferedReader;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import org.apache.pdfbox.pdmodel.common.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.*;
import org.apache.pdfbox.pdfparser.*;
import org.apache.pdfbox.cos.*;

import javax.sound.sampled.SourceDataLine;
import org.apache.pdfbox.contentstream.operator.*;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import java.io.OutputStream;

public class Main {

    public static final String DEST = "N:\\Project\\Projet-Inno-Avant-Projet\\sample-result-pdfbox.pdf";
    public static final String SRC = "N:\\Project\\Projet-Inno-Avant-Projet\\sample.pdf";

    public static void main(String[] args) {
      PDDocument doc = null;
        try {

              //load PDDocument
              File file = new File(SRC);
              doc = PDDocument.load(file);

              //For each PDPage in Doc
              for (PDPage page : doc.getPages()) {
                //parser used to get  All the token in a page 
                PDFStreamParser parser = new PDFStreamParser(page);
                parser.parse();
                List<Object> tokens = parser.getTokens();
                List<Object> newTokens = new ArrayList<Object>();

                // go through all the tokens  in the page
                for( int j=0; j<tokens.size(); j++ ){
                  //Not all tokens are Operator --> Cast Object  
                  Object next = tokens.get( j );
                  // if token is operator 
                  if (next instanceof Operator) {
                      //casting object to operator
                      Operator op = (Operator) next;
                      //System.out.println(op.getName());
                      if( op.getName().equals( "TJ" ))
                        {
                            // COSArray is an array for each TJ operators that looks like COSArray{[COSString{TE}, COSInt{-2}, COSString{X}, COSInt{8}, COSString{T }]}
                            COSArray previous = (COSArray)tokens.get( j-1 );
                           // System.out.println(previous.toString());
                            String s = "";
                            for( int k=0; k<previous.size(); k++ )
                            {   
                                Object arrElement = previous.getObject( k );
                                //there is COSInt and COSString ,... so filter to only get cosint 
                                if( arrElement instanceof COSString )


                                {
                                    
                                    COSString cosString = (COSString)arrElement;
                                    String string = cosString.getString();
                                    s += string;
                                }
                            }
                            if(s.equals("Sébastien De Bod")){
    
                              Operator newOp = Operator.getOperator("Token");
                              tokens.set(j,(Object) newOp);
                            }
                        }
                      }
                      PDStream updatedStream = new PDStream(doc);
                      OutputStream out = updatedStream.createOutputStream();
                      ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
                      tokenWriter.writeTokens( tokens );
                      page.setContents(updatedStream);
                      out.close();
                      
                  }
                  doc.save(DEST);
                }
              }
        catch (Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            }
    }
}
