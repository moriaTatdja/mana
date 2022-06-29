package com.hervmo.manager.services;

import com.hervmo.manager.models.entities.Order;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface PdfGenerator {

    byte[] generateInvoice(Order o) throws FileNotFoundException, JRException, URISyntaxException;
}
