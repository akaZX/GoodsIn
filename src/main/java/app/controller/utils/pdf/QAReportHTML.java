package app.controller.utils.pdf;

import app.controller.sql.dao.*;
import app.pojos.*;
import org.intellij.lang.annotations.Language;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QAReportHTML {

    List<RmtQaRecords> list;

    String po;


    public QAReportHTML(String po) {

        this.po = po;
        list = new RmtQaRecordsDao().getAll(po);
    }


    public String getReport() {


        StringBuilder html = new StringBuilder(getTop(po));


        if (list.size() > 0) {

            for (RmtQaRecords records : list) {
                html.append(recordData(records));
            }

        }else{
            return null;
        }


        return html.append(getFooter()).toString();


    }


    private String recordData(RmtQaRecords record) {

        Materials      material = new MaterialsDao().get(record.getmCode());
        QaRecordWeight weight   = new QaRecordWeightDao().get(record.getRowid());
        String         color    = "";
        if (record.getDecision().equalsIgnoreCase("REJECT")) {
            color += "bg-danger";
        }
        else if (record.getDecision().equalsIgnoreCase("ACCEPT")) {
            color += "bg-success";
        }
        else {
            color += "bg-warning";
        }


        @Language("HTML")
        String html =
                "    <div class=\"table-responsive\">\n" +
                "\n<p>" +
                "    <br/>\n" +
                "    <div class=\"p-3 mb-2 " + color + " text-white\"><h3>" + material + " - " +
                record.getDecision().toUpperCase() + "</h3></div>\n" +
                "\n" +
                "        <table class=\"table table-bordered table-striped\">\n" +
                "            <thead>\n" +
                "            <tr>\n" +
                "                <th style=\"width: 20%\">Material information</th>\n" +
                "                <th style=\"width: 20%\">Test results</th>\n" +
                "                <th style=\"width: 25%\">Defects/Foreign bodies</th>\n" +
                "                <th style=\"width: 15%\">Comments</th>\n" +
                "                <th style=\"width: 20%\">Arrived</th>\n" +
                "\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            <tbody>\n" +
                "            <tr>\n" +
                "\n" +
                "                <td><h5><strong>" + material.getMCode() + "<br/>" + material.getName() +
                "</strong><h5/> <h5><strong>General Parameters:</strong></h5>" +
                "<p>" + record.getDetails().getGeneralDetails() + "</p></td>\n" +
                "                <td>" + record.getDetails().getNumericalParams() + "</td>\n" +
                "                <td>" + record.getDetails().getDefectsText() + "</td>\n" +
                "                <td>" + record.getDetails().getComments() + "</td>\n" +
                "                <td style=\"width: 20%\">" +
                "                     <p>Weight: " + weight.getWeight() + " KG/EA</p>" +
                "                     <p>" + (weight.getBoxes() == 0 ? "" : "Boxes: " + weight.getBoxes()) + "</p>" +
                "                 </td>\n" +
                "            </tr>\n" +
                "\n" +
                "\n" +
                "\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </div>";


        return html;
    }


    private String getTop(String po) {

        SupplierOrders order    = new SupplierOrderDao().getBy(po, "po");
        Suppliers      supplier = new SuppliersDao().get(order.getSuppCode());


        @Language("HTML")
        String top = "<!DOCTYPE html>\n" +
                     "<html>\n" +
                     "<head>\n" +
                     "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                     "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                     "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>\n" +
                     "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n" +
                     "</head>\n" +
                     "<style>\n" +
                     "\n" +
                     "    .top-column {\n" +
                     "        float: left;\n" +
                     "        width: 50%;\n" +
                     "    }\n" +
                     "\n" +
                     "    /* Clear floats after the columns */\n" +
                     "    .row{\n" +
                     "        display: flex;\n" +
                     "    }\n" +
                     "    .top-label{\n" +
                     "        font-weight: bold;\n" +
                     "        font-size: 2.2em;\n" +
                     "    }\n" +
                     "\n" +
                     "</style>\n" +
                     "<body>\n" +
                     "\n" +
                     "\n" +
                     "<div class=\"container\">\n" +
                     "\n" +
                     "    <div class=\"row\">\n" +
                     "        <div class=\"top-column\">\n" +
                     "            <p class =\"top-label\">BAKKAVOR SALADS BOURNE</p>\n" +
                     "            <h3>Raw Material Quality Assessment Report</h3>\n" +
                     "        </div>\n" +
                     "\n" +
                     "        <div class=\"top-column\" align=\"right\">\n" +
                     "            <img src=\"src\\main\\resources\\images\\bakkavor_log.jpeg\" height=\"60\" width=\"250\"/>\n" +
                     "        </div>\n" +
                     "\n" +
                     "    </div>\n" +
                     "\n<hr>" +
                     "    <div class=\"row\">\n" +
                     "        <div style=\"float: left; width: 60%\">\n" +
                     "            <h4><strong>Supplier name: </strong></h4><h4>" + supplier.getSupplierName() +
                     "</h4>\n" +
                     "        </div>\n" +
                     "        <div style=\"float: left; width: 25%\">\n" +
                     "            <h4><strong>Po number: </strong></h4><h4> " + order.getPoNumber() + "</h4>\n" +
                     "        </div>\n" +
                     "        <div style=\"float: left; width: 15%\">\n" +
                     "            <h4><strong>Po Date: </strong></h4><h4>" + order.getOrderDate() + "</h4>\n" +
                     "        </div>\n" +
                     "    </div><hr>\n";
        return top;
    }


    private String getFooter() {

        @Language("HTML")
        String footer = "<br/>\n" +
                        "\n" +
                        "<div>\n<hr>" +
                        "    <p>Any rejections will be disposed of after 48 hours unless collected or instructed otherwise.</p>\n" +
                        "    <p>All weight queries must be made within 24 hours of receiving this QA report.</p>\n" +
                        "    <hr>\n" +
                        "    <p>This report supersedes any previously received for this PO number prior to the date and time below: " +
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy")) + "  " +
                        (LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))) + "</p>\n" +
                        "</div>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";


        return footer;
    }


}
