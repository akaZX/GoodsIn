package app.controller.utils.pdf;

import app.controller.sql.dao.SupplierOrderDao;
import app.controller.utils.emailing.EmailClient;
import app.pojos.SupplierOrders;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class PDFFile {


    public static void createPDFFile(String email, String password, String po, Map<String, String> map) {

        SupplierOrders supplierOrders = new SupplierOrderDao().getBy(po, "po");
        QAReportHTML reportHTML = new QAReportHTML(po);
        String       report     = reportHTML.getReport();

        if (report != null) {
            try {
                String PATH           = new File(".").getCanonicalPath() + "/reports/";
                String supplierFolder = PATH.concat(supplierOrders.getSuppCode() + "/" + po + "/");
                File   directory      = new File(supplierFolder);
                if (! directory.exists()) {
                    directory.mkdirs();
                }

                try {
                    //gets nano-date and uses it as part of file name
                    String      fileName   = po + "_" + (LocalDateTime.now().getNano() / 10000 * 3) + ".pdf";
                    String      reportPath = supplierFolder + "/" + fileName;
                    PdfWriter   writer     = new PdfWriter(reportPath);
                    PdfDocument pdf        = new PdfDocument(writer);
                    Document    document   = new Document(pdf, PageSize.A4.rotate());

                    // pdfHTML specific code
                    ConverterProperties converterProperties = new ConverterProperties();

                    HtmlConverter.convertToPdf(report,
                            pdf, converterProperties);

                    boolean sent = EmailClient.sendEmailWithAttachement(email, password, reportPath, fileName, supplierOrders);

                    if (! sent) {
                        map.put(supplierOrders.getPoNumber(), "No emails found or wrong password");
                    }

                }
                catch (IOException e) {
                    map.put(supplierOrders.getPoNumber(), " Unknown error");
//                    System.out.println("Failed");
                    e.printStackTrace();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            map.put(supplierOrders.getPoNumber(), "No material records found");
        }

    }

}


