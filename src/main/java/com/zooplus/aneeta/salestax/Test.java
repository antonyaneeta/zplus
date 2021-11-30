package com.zooplus.aneeta.salestax;

import java.text.DecimalFormat;

public class Test {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args){
        Double d=12.33;

       System.out.println("SalesTaxes : " + df.format(roundOff(d)));
    }
    public static Double roundOff(Double value) {
        Double newValue = 0.0d;
        Double reminder = value % .05;

            newValue = value + 0.05 - reminder;

        return newValue;
    }

}
