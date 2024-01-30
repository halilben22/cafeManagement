package com.halildev.cafeManagement.serviceImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.dao.BillDao;
import com.halildev.cafeManagement.pojo.Bill;
import com.halildev.cafeManagement.security.JwtAuthFilter;
import com.halildev.cafeManagement.service.BillService;
import com.halildev.cafeManagement.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.awt.Font.getFont;


@Service
@Slf4j

public class BillServiceImpl implements BillService {
    private final JwtAuthFilter jwtAuthFilter;
    private final BillDao billDao;

    public BillServiceImpl(JwtAuthFilter jwtAuthFilter, BillDao billDao) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.billDao = billDao;
    }

    @Override
    public ResponseEntity<String> generateReport(Map<String, String> requestMap) {
        log.info("Inside generateReport method");


        try {

            String fileName;

            if (validateRequestMap(requestMap)) {

                if (requestMap.containsKey("isGenerate") && !Boolean.parseBoolean(requestMap.get("isGenerate"))) {


                    fileName = (String) requestMap.get("uuid");

                } else {

                    fileName = CafeUtils.getUUID();

                    requestMap.put("uuid", fileName);

                    insertBill(requestMap);
                }

                String data = "Name:" + requestMap.get("name") + "\n" + "Contact Number:" + requestMap.get("contactNumber")
                        + "\n" + "Email:" + requestMap.get("email") + "\n" + "Payment Method:" + requestMap.get("paymentMethod");
                Document document = new Document();

                PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));
                document.open();
                setRectangleInPdf(document);
                Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));

                document.add(paragraph);


                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);
                JSONArray jsonArray = CafeUtils.getJsonArrayFromString(requestMap.get("productDetails"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }

                document.add(table);
                Paragraph footer = new Paragraph("Total : " + requestMap.get("totalAmount") + "\n"
                        + "Thank you for visiting!.");

                document.add(footer);
                document.close();

                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);


            }

            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {


        }


        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();

        if (jwtAuthFilter.isAdmin()) {
            list = billDao.getAllBills();
        } else {

            list = billDao.getBillByUsername(jwtAuthFilter.getCurrentUser());

        }


        return CafeUtils.getResponseEntityAsList(list,HttpStatus.OK);
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");


        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));

    }

    private void addTableHeader(PdfPTable table) {

        log.info("Inside table header");

        Stream.of("Name", "Category", "Quantity", "Price", "Sub total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void setRectangleInPdf(Document document) throws DocumentException {

        log.info("Inside setRectangleInPdf");
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBackgroundColor(BaseColor.LIGHT_GRAY);
        rectangle.setBorderWidth(1);
        document.add(rectangle);
    }

    private Font getFont(String type) {
        log.info("Inside getfont");

        switch (type) {

            case "Header":

                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;


            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);

                return dataFont;

            default:
                return new Font();
        }
    }

    private void insertBill(Map<String, String> requestMap) {


        try {

            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName(requestMap.get("name"));
            bill.setEmail(requestMap.get("email"));
            bill.setContactNumber(requestMap.get("contactNumber"));
            bill.setPaymentMethod("paymentMethod");
            bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            bill.setProductDetail(requestMap.get("productDetails"));
            bill.setCreatedBy(jwtAuthFilter.getCurrentUser());

            billDao.save(bill);
        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    private boolean validateRequestMap(Map<String, String> requestMap) {

        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }
}
