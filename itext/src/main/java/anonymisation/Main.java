package anonymisation;

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.nio.charset.StandardCharsets;


public class Main {

    public static final String DEST = "[DESINATION FILE TO BE REPLACED]";
    public static final String SRC = "[SOURCE FILE TO BE REPLACED]";
    public static void main(String[] args) {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        try{
             manipulatePdf(DEST);
        }catch(Exception e){
            System.out.println(e);
        }
       
    }
    public static  void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(dest));
        PdfPage page = pdfDoc.getFirstPage();
        PdfDictionary dict = page.getPdfObject();

        PdfObject object = dict.get(PdfName.Contents);
        if (object instanceof PdfStream) {
            PdfStream stream = (PdfStream) object;
            byte[] data = stream.getBytes();
            String replacedData = new String(data).replace("TJ", "Token");
            stream.setData(replacedData.getBytes(StandardCharsets.ISO_8859_1));
        }

        pdfDoc.close();
    }
}
