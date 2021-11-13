package com.gildedrose.java;

import java.util.function.IntUnaryOperator;

class GildedRose {

    Item[] items;

    GildedRose(Item[] items) {
        this.items = items;
    }

    void updateQuality() {
        for (Item item : items) {
            item.sellIn = applySellInAdjustment(item.sellIn, sellInAdjustment(item.name));
            item.quality = applyQualityAdjustment(item.quality, item.sellIn, createQualityAdjuster(item.name));
        }
    }

    private int applySellInAdjustment(int sellIn, int sellInAdjustment) {
        return sellIn + sellInAdjustment;
    }

    private int applyQualityAdjustment(int quality, int sellIn, IntUnaryOperator adjuster) {
        return Math.max(0, Math.min(50, quality + adjuster.applyAsInt(sellIn)));
    }

    private int sellInAdjustment(String itemName) {
        return itemName.equals("Sulfuras, Hand of Ragnaros") ? 0 : -1;
    }

    private IntUnaryOperator createQualityAdjuster(String itemName) {
        return switch (itemName) {
            case "Aged Brie" -> days -> days < 0 ? 2 : 1;
            case "Sulfuras, Hand of Ragnaros" -> days -> 0;
            case "Backstage passes to a TAFKAL80ETC concert" -> days -> days < 0 ? Integer.MIN_VALUE :
                                                                        days <= 5 ? 3 :
                                                                        days <= 10 ? 2 : 1;
            case "Conjured" -> days -> days < 0 ? -4 : -2;
            default -> days -> days < 0 ? -2 : -1;
        };
    }

}
