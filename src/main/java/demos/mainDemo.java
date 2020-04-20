package demos;


import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import java.io.*;

public class mainDemo{


    public static void main(String[] args){
        try {// IO

            PdfWriter   writer   = new PdfWriter("output.pdf");
            PdfDocument pdf      = new PdfDocument(writer);
            Document    document = new Document(pdf, PageSize.A4.rotate());


            File htmlSource = new File("C:\\Users\\akazx\\OneDrive\\Desktop\\GoodsIn\\Download.html");
//            File pdfDest    = new File("output.pdf");
            // pdfHTML specific code
            ConverterProperties converterProperties = new ConverterProperties();



            HtmlConverter.convertToPdf(new FileInputStream(htmlSource),
                    pdf, converterProperties);
            System.out.println("Success");
        }catch (IOException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }


    }

}
