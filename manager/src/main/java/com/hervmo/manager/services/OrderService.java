package com.hervmo.manager.services;

import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.Order;

import java.text.DecimalFormat;
import java.util.List;

public interface OrderService {

    Order addArticleInCart(List<Articles> articlesList, String cartHash, double taxRate);

    Order removeArticleInCart(Long orderDetailId, Long orderId);

    boolean deleteOrder(Long orderId);

    double calculateNetPriceForOrder(Order o);

    double roundTwoDecimal(double number);

    Order orderBuy(Order o);

    byte[] downloadInvoice(Long id);
}
