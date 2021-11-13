package com.gildedrose;

class GildedRose {

    Item[] items;

    GildedRose(Item[] items) {
        this.items = items;
    }

    void updateQuality() {
        for (Item sourceItem : items) {
            var item = createItemForSale(sourceItem);
            if (!(item instanceof AgedBrie)
                && !(item instanceof BackstagePass)) {
                if (!(item instanceof Sulfuras)) {
                    item.quality = item.quality.decreased();
                }
            } else {
                item.quality = item.quality.increased();

                if (item instanceof BackstagePass) {
                    if (item.sellIn < 11) {
                        item.quality = item.quality.increased();
                    }

                    if (item.sellIn < 6) {
                        item.quality = item.quality.increased();
                    }
                }
            }

            if (!(item instanceof Sulfuras)) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!(item instanceof AgedBrie)) {
                    if (!(item instanceof BackstagePass)) {
                        if (!(item instanceof Sulfuras)) {
                            item.quality = item.quality.decreased();
                        }
                    } else {
                        item.quality = Quality.zero();
                    }
                } else {
                    item.quality = item.quality.increased();
                }
            }

            sourceItem.sellIn = item.sellIn();
            sourceItem.quality = item.quality();
        }
    }

    private ItemForSale createItemForSale(Item item) {
        return switch (item.name) {
            case "Aged Brie" -> new AgedBrie(item.sellIn, new Quality(item.quality));
            case "Sulfuras, Hand of Ragnaros" -> new Sulfuras(item.sellIn, new Quality(item.quality));
            case "Backstage passes to a TAFKAL80ETC concert" -> new BackstagePass(item.sellIn, new Quality(item.quality));
            default -> new RegularItem(item.sellIn, new Quality(item.quality));
        };
    }

    private static abstract class ItemForSale {

        int sellIn;
        Quality quality;

        ItemForSale(int sellIn, Quality quality) {
            this.sellIn = sellIn;
            this.quality = quality;
        }

        int sellIn() {
            return sellIn;
        }

        int quality() {
            return quality.value;
        }
    }

    private static class RegularItem extends ItemForSale {

        RegularItem(int sellIn, Quality quality) {
            super(sellIn, quality);
        }
    }

    private static class AgedBrie extends ItemForSale {

        AgedBrie(int sellIn, Quality quality) {
            super(sellIn, quality);
        }
    }

    private static class Sulfuras extends ItemForSale {

        Sulfuras(int sellIn, Quality quality) {
            super(sellIn, quality);
        }
    }

    private static class BackstagePass extends ItemForSale {

        BackstagePass(int sellIn, Quality quality) {
            super(sellIn, quality);
        }
    }

    private static final class Quality {

        final int value;

        Quality(int value) {
            this.value = value;
        }

        Quality increased() {
            var newValue = Math.min(value + 1, 50);
            return new Quality(newValue);
        }

        Quality decreased() {
            var newValue = Math.max(value - 1, 0);
            return new Quality(newValue);
        }

        static Quality zero() {
            return new Quality(0);
        }
    }
}
