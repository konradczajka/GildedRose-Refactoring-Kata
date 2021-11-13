package com.gildedrose;

class GildedRose {

    Item[] items;

    GildedRose(Item[] items) {
        this.items = items;
    }

    void updateQuality() {
        for (Item sourceItem : items) {
            var item = createItemForSale(sourceItem);
            if (item instanceof RegularItem r) {
                item = r.atNextDay();
            } else if (item instanceof AgedBrie a) {
                item = a.atNextDay();
            } else if (item instanceof Sulfuras s) {
                item = s.atNextDay();
            } else {
                if (!(item instanceof BackstagePass)) {
                    item.quality = item.quality.decreased();
                } else {
                    item.quality = item.quality.increased();

                    if (item instanceof BackstagePass) {
                        if (item.sellIn.days < 11) {
                            item.quality = item.quality.increased();
                        }

                        if (item.sellIn.days < 6) {
                            item.quality = item.quality.increased();
                        }
                    }
                }

                item.sellIn = item.sellIn.decreased();

                if (item.sellIn.hasPassed()) {
                    if (!(item instanceof BackstagePass)) {
                        item.quality = item.quality.decreased();
                    } else {
                        item.quality = Quality.zero();
                    }
                }
            }
            sourceItem.sellIn = item.sellIn();
            sourceItem.quality = item.quality();
        }
    }

    private ItemForSale createItemForSale(Item item) {
        return switch (item.name) {
            case "Aged Brie" -> new AgedBrie(new SellInDate(item.sellIn), new Quality(item.quality));
            case "Sulfuras, Hand of Ragnaros" -> new Sulfuras(new SellInDate(item.sellIn), new Quality(item.quality));
            case "Backstage passes to a TAFKAL80ETC concert" -> new BackstagePass(new SellInDate(item.sellIn), new Quality(item.quality));
            default -> new RegularItem(new SellInDate(item.sellIn), new Quality(item.quality));
        };
    }

    private static abstract class ItemForSale {

        SellInDate sellIn;
        Quality quality;

        ItemForSale(SellInDate sellIn, Quality quality) {
            this.sellIn = sellIn;
            this.quality = quality;
        }

        int sellIn() {
            return sellIn.days;
        }

        int quality() {
            return quality.value;
        }
    }

    private static class RegularItem extends ItemForSale {

        RegularItem(SellInDate sellIn, Quality quality) {
            super(sellIn, quality);
        }

        RegularItem atNextDay() {
            var newSellIn = sellIn.decreased();
            var newQuality = newSellIn.hasPassed()
                             ? quality.decreased().decreased()
                             : quality.decreased();
            return new RegularItem(newSellIn, newQuality);
        }
    }

    private static class AgedBrie extends ItemForSale {

        AgedBrie(SellInDate sellIn, Quality quality) {
            super(sellIn, quality);
        }

        AgedBrie atNextDay() {
            var newSellIn = sellIn.decreased();
            var newQuality = newSellIn.hasPassed()
                             ? quality.increased().increased()
                             : quality.increased();
            return new AgedBrie(newSellIn, newQuality);
        }
    }

    private static class Sulfuras extends ItemForSale {

        Sulfuras(SellInDate sellIn, Quality quality) {
            super(sellIn, quality);
        }

        Sulfuras atNextDay() {
            return this;
        }
    }

    private static class BackstagePass extends ItemForSale {

        BackstagePass(SellInDate sellIn, Quality quality) {
            super(sellIn, quality);
        }
    }

    private record Quality(int value) {

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

    private record SellInDate(int days) {

        SellInDate decreased() {
            return new SellInDate(days - 1);
        }

        boolean hasPassed() {
            return days < 0;
        }
    }
}
