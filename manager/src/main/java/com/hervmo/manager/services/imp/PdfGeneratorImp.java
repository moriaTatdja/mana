package com.hervmo.manager.services.imp;

import com.hervmo.manager.models.entities.Order;
import com.hervmo.manager.services.PdfGenerator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class PdfGeneratorImp implements PdfGenerator {
    @Override
    public byte[] generateInvoice(Order o) throws FileNotFoundException, JRException, URISyntaxException {
        //JRBeanArrayDataSource beanArrayDataSource =
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("invoice.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport compileReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint report = JasperFillManager.fillReport(compileReport, null, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(report);
    }
}
