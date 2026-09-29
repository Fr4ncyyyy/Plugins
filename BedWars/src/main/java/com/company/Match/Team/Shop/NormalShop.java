package com.company.Match.Team.Shop;

import com.company.Config.ShopConfig;

import java.util.ArrayList;

public class NormalShop extends Shop{
    public NormalShop(int size, String title) {
        super(size, title);
    }

    public void initShop(ArrayList<ShopElement> shopElements){

        this.shopElements = shopElements;

    }
}
