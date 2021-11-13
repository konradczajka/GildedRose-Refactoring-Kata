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
            } else if (item instanceof BackstagePass b) {
                item = b.atNextDay();
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
            var newQuality = quality.decreased();
            if (newSellIn.hasPassed()) {
                newQuality = newQuality.decreased();
            }
            return new RegularItem(newSellIn, newQuality);
        }
    }

    private static class AgedBrie extends ItemForSale {

        AgedBrie(SellInDate sellIn, Quality quality) {
            super(sellIn, quality);
        }

        AgedBrie atNextDay() {
            var newSellIn = sellIn.decreased();
            var newQuality = quality.increased();
            if (newSellIn.hasPassed()) {
                newQuality = newQuality.increased();
            }
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

        BackstagePass atNextDay() {
            var newSellIn = sellIn.decreased();
            if (sellIn.hasPassed()) {
                return new BackstagePass(newSellIn, Quality.zero());
            }

            var newQuality = quality.increased();
            if (sellIn.days <= 10) {
                newQuality = newQuality.increased();
            }
            if (sellIn.days <= 5) {
                newQuality = newQuality.increased();
            }
            return new BackstagePass(newSellIn, newQuality);
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
