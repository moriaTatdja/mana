package com.hervmo.manager.services.imp;

import com.hervmo.manager.models.entities.Articles;
import com.hervmo.manager.models.entities.Order;
import com.hervmo.manager.models.entities.OrderDetails;
import com.hervmo.manager.models.repositories.OrderDetailsRepository;
import com.hervmo.manager.models.repositories.OrderRepository;
import com.hervmo.manager.services.OrderService;
import com.hervmo.manager.utils.OrderType;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    protected OrderRepository orderRepo;
    @Autowired
    protected OrderDetailsRepository orderDetailsRepo;
    @Autowired
    protected PdfGeneratorImp pdf;


    @Override
    public Order addArticleInCart(List<Articles> articlesList, String hashCart, double taxRate) {
        Order order = orderRepo.findByHashCart(hashCart).orElse(null);
        if (order == null) {
            order = new Order();
            order.setOrderType(OrderType.CART);
            order.setNetPrice(0);
            order.setTotalPrice(0);
            order.setHashCart(hashCart);
            order.setActive(true);
            // @Todo add State to the Order
            order.setCreatedAt();
            //order.setOrderStatus();
            //order = orderRepo.saveAndFlush(order);
        }
        List<OrderDetails> detailsList = new ArrayList<>();
        for (Articles article : articlesList) {
            //order.setNetPrice(order.getNetPrice() + (article.getPrice() * article.getQty()));
            OrderDetails orderDetail = orderDetailsRepo.findByArticleAndAndOrder(article, order).orElse(null);
            if (orderDetail == null) {
                orderDetail = new OrderDetails();
                orderDetail.setOrder(order);
            }
            orderDetail.setArticle(article);
            orderDetail.setPrice(article.getPrice());
            orderDetail.setQuantity(article.getQty());
            detailsList.add(orderDetail);
        }
        order.setTaxRate(taxRate);
        order.setOrderDetailsList(detailsList);
        double netPrice = calculateNetPriceForOrder(order);
        order.setNetPrice(netPrice);
        netPrice = roundTwoDecimal((netPrice * (100 + order.getTaxRate())) / 100);
        order.setTotalPrice(netPrice);
        order.setUpdateAt();
        return orderRepo.saveAndFlush(order);
    }

    @Override
    public Order removeArticleInCart(Long articleId, Long orderId) {
        Optional<Order> o = orderRepo.findById(orderId);
        if (o.isPresent()) {
            if (o.get().getOrderDetailsList().size() > 0) {
                Optional<OrderDetails> detail = o.get().getOrderDetailsList().stream().filter(d -> {
                    return Objects.equals(d.getArticle().getArticleId(), articleId);
                }).findFirst();
                detail.ifPresent(orderDetails -> orderDetailsRepo.delete(orderDetails));
                orderDetailsRepo.findByOrder(o.get()).ifPresent(orderDetails -> o.get().setOrderDetailsList(orderDetails));
            }

            return o.get();
        }
        return null;
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        AtomicBoolean deleted = new AtomicBoolean(false);
        Optional<Order> o = orderRepo.findById(orderId);
        o.ifPresent(order -> {
            orderRepo.delete(order);
            deleted.set(true);
        });
        return deleted.get();
    }

    @Override
    public double calculateNetPriceForOrder(Order o) {
        double netPrice = 0.00;
        for (OrderDetails detail : o.getOrderDetailsList()) {
            netPrice += (detail.getPrice() * detail.getQuantity());
        }
        return roundTwoDecimal(netPrice);
    }

    public double roundTwoDecimal(double number) {
        BigDecimal b = new BigDecimal(number).setScale(2, RoundingMode.HALF_UP);
        return b.doubleValue();
    }

    @Override
    public Order orderBuy(Order o) {
        return null;
    }

    @Override
    public byte[] downloadInvoice(Long id) {
        try {
            return pdf.generateInvoice(null);
        } catch (FileNotFoundException | JRException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
