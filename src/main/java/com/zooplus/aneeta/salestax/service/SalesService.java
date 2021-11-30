package com.zooplus.aneeta.salestax.service;

import com.zooplus.aneeta.salestax.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class SalesService {

    @Autowired
    private ItemService itemService;
    private static final Double SALES_TAX = 10.0d;
    private static final Double IMPORT_TAX = 5.0d;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private Map<String, Item> itemMap = new HashMap<>();

    public void doBilling() throws IOException {
        boolean proceed = true;
        String items = displayItems();
        Scanner scanner = new Scanner(System.in);

        Map<String, Item> billingMap = new HashMap<>();
        while (proceed) {


            System.out.println("Please select the item number");
            System.out.println(items);
            String itemId = scanner.next();
            Item selectedItem = itemMap.get(itemId);
            if (null == selectedItem) {
                System.out.println("item dosent exist");
                continue;
            }

            System.out.println("Please enter the quantity required");
            Integer qty = scanner.nextInt();

            selectedItem = selectedItem.newItem();
            selectedItem.setQuantity(qty);
            addItem(billingMap, selectedItem);

            System.out.println("Do you want to add another Item (y/n) ? ");
            String next = scanner.next();

            proceed = "y".equalsIgnoreCase(next) ? true : false;

        }

        generateBill(billingMap);

    }

    String displayItems() throws IOException {
        List<Item> items = itemService.getItems();
        StringBuilder sb = new StringBuilder();

        items.stream().collect(Collectors.toMap(Item::getItemId, i -> {
            sb.append(i.getItemId() + " | " + (i.isImported() ? "imported " : "") + i.getItemName() + " | " + df.format(i.getPrice()));
            sb.append("\n");
            itemMap.put(i.getItemId(), i);
            return i;}));

        return sb.toString();
    }

    private void addItem(Map<String, Item> billingMap, Item item) {
        if (billingMap.containsKey(item.getItemId())) {
            Item billingItem = billingMap.get(item.getItemId());
            billingItem.setQuantity(billingItem.getQuantity() + item.getQuantity());
        } else {
            billingMap.put(item.getItemId(), item);
        }

    }

    private void generateBill(Map<String, Item> billingMap) {
        Double salesTaxAmount = 0.0d;
        Double billPrice = 0.0d;

        for (Map.Entry<String, Item> itemEntry : billingMap.entrySet()) {
            Item item = itemEntry.getValue();
            Double totalPrice = new BigDecimal(item.getQuantity() * item.getPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            item.setTotalPrice(totalPrice);

            if (Boolean.TRUE.equals(!item.getExemptFrmTax())) {
                Double taxAmount = totalPrice * SALES_TAX / 100;
                salesTaxAmount = getaDouble(salesTaxAmount, item, taxAmount);
            }
            if (item.isImported()) {
                Double taxAmount = totalPrice * IMPORT_TAX / 100;
                salesTaxAmount = getaDouble(salesTaxAmount, item, taxAmount);
            }
            billPrice = new BigDecimal(billPrice + item.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(item.getQuantity() + " " + (item.isImported() ? "imported " : "") + item.getItemName() + " : " + df.format(item.getTotalPrice()));
        }
        System.out.println("SalesTaxes : " + df.format(salesTaxAmount));
        System.out.println("Total : " + df.format(billPrice));

    }

    private Double getaDouble(Double salesTaxAmount, Item item, Double taxAmount) {
        Double roundedDbl = roundOff(new BigDecimal(taxAmount).setScale(2, RoundingMode.HALF_UP).doubleValue());
        salesTaxAmount = new BigDecimal(salesTaxAmount + roundedDbl).setScale(2, RoundingMode.HALF_UP).doubleValue();
        item.setTotalPrice((new BigDecimal(item.getTotalPrice() + roundedDbl).setScale(2, RoundingMode.HALF_UP).doubleValue()));
        return salesTaxAmount;
    }


    Double roundOff(Double value) {
        Double newValue = 0.0d;
        Double reminder = value % .05;

        newValue = value + 0.05 - reminder;

        return newValue;
    }

}
