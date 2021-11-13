package com.gildedrose;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item sourceItem : items) {
            var item = createItemForSale(sourceItem);
            if (!item.name.equals("Aged Brie")
                && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                    item.quality = item.quality.decreased();
                }
            } else {
                item.quality = item.quality.increased();

                if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.sellIn < 11) {
                        item.quality = item.quality.increased();
                    }

                    if (item.sellIn < 6) {
                        item.quality = item.quality.increased();
                    }
                }
            }

            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                if (!item.name.equals("Aged Brie")) {
                    if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                            item.quality = item.quality.decreased();
                        }
                    } else {
                        item.quality = Quality.zero();
                    }
                } else {
                    item.quality = item.quality.increased();
                }
            }

            sourceItem.sellIn = item.sellIn;
            sourceItem.quality = item.quality.value;
        }
    }

    private ItemForSale createItemForSale(Item item) {
        return new ItemForSale(item.name, item.sellIn, item.quality);
    }

    private static class ItemForSale {

        final String name;
        int sellIn;
        Quality quality;

        public ItemForSale(String name, int sellIn, int quality) {
            this.name = name;
            this.sellIn = sellIn;
            this.quality = new Quality(quality);
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
