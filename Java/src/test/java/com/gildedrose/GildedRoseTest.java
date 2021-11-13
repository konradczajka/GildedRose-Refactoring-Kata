package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {

    @Nested
    class RegularItem {

        @Test
        void loosesQualityAndSellInEachDay() {
            var item = new Item("Regular Item", 10, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(19);
            assertThat(item.sellIn).isEqualTo(9);
        }

        @Test
        void loosesQualityTwiceAsFastAthTheSellDate() {
            var item = new Item("Regular Item", 0, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(18);
            assertThat(item.sellIn).isEqualTo(-1);
        }

        @Test
        void loosesQualityTwiceAsFastOnceSellByDateHasPassed() {
            var item = new Item("Regular Item", -1, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(18);
            assertThat(item.sellIn).isEqualTo(-2);
        }

        @Test
        void neverReachesNegativeQualityBeforeTheSellDate() {
            var item = new Item("Regular Item", 10, 0);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(0);
            assertThat(item.sellIn).isEqualTo(9);
        }

        @Test
        void neverReachesNegativeQualityAfterTheSellDate() {
            var item = new Item("Regular Item", -1, 1);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(0);
            assertThat(item.sellIn).isEqualTo(-2);
        }
    }

    @Nested
    class AgedBrie {

        @Test
        void gainsQualityAndLoosesSellInEachDay() {
            var item = new Item("Aged Brie", 10, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(21);
            assertThat(item.sellIn).isEqualTo(9);
        }

        @Test
        void gainsQualityTwiceAsFastAtTheSellDate() {
            var item = new Item("Aged Brie", 0, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(22);
            assertThat(item.sellIn).isEqualTo(-1);
        }

        @Test
        void gainsQualityTwiceAsFastOnceSellByDateHasPassed() {
            var item = new Item("Aged Brie", -1, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(22);
            assertThat(item.sellIn).isEqualTo(-2);
        }

        @Test
        void neverReachesQualityHigherThan50BeforeTheSellDate() {
            var item = new Item("Aged Brie", 10, 50);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(50);
            assertThat(item.sellIn).isEqualTo(9);
        }

        @Test
        void neverReachesQualityHigherThan50AfterTheSellDate() {
            var item = new Item("Aged Brie", -1, 49);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(50);
            assertThat(item.sellIn).isEqualTo(-2);
        }
    }

    @Nested
    class Sulfuras {

        @Test
        void doesNotLooseQualityAndSellInEachDay() {
            var item = new Item("Sulfuras, Hand of Ragnaros", 10, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(20);
            assertThat(item.sellIn).isEqualTo(10);
        }
    }

    @Nested
    class BackstagePass {

        @Test
        void gainsQualityAndLoosesSellInEachDay() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(21);
            assertThat(item.sellIn).isEqualTo(14);
        }

        @Test
        void gainsQualityTwiceAsFastOnceSellByDateIsEqualTo10() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(22);
            assertThat(item.sellIn).isEqualTo(9);
        }

        @Test
        void gainsQualityTwiceAsFastOnceSellByDateIsLowerThan10() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 8, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(22);
            assertThat(item.sellIn).isEqualTo(7);
        }

        @Test
        void gainsQualityThriceAsFastOnceSellByDateIsEqualTo5() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(23);
            assertThat(item.sellIn).isEqualTo(4);
        }

        @Test
        void gainsQualityThriceAsFastOnceSellByDateIsLowerThan5() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 4, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(23);
            assertThat(item.sellIn).isEqualTo(3);
        }

        @Test
        void loosesQualityCompletelyAtTheSellDate() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(0);
            assertThat(item.sellIn).isEqualTo(-1);
        }

        @Test
        void loosesQualityCompletelyWhenTheSellInDateHasPassed() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 20);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(0);
            assertThat(item.sellIn).isEqualTo(-2);
        }

        @Test
        void neverReachesQualityHigherThan50WhenTheSellDateIsFar() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 20, 50);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(50);
            assertThat(item.sellIn).isEqualTo(19);
        }

        @Test
        void neverReachesQualityHigherThan50CloseToTheSellDate() {
            var item = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 49);

            new GildedRose(new Item[]{item}).updateQuality();

            assertThat(item.quality).isEqualTo(50);
            assertThat(item.sellIn).isEqualTo(0);
        }
    }
}
