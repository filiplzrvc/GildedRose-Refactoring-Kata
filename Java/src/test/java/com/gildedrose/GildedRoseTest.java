package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void normalItem_qualityDecreases() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(9, items[0].quality);
        assertEquals(4, items[0].sellIn);
    }

    @Test
    void normalItem_degradesTwiceAfterSellIn() {
        Item[] items = new Item[] { new Item("Normal Item", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(8, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    @Test
    void qualityNeverNegative() {
        Item[] items = new Item[] { new Item("Normal Item", 5, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality);
    }

    @Test
    void brieIncreasesQuality() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(1, items[0].quality);
    }

    @Test
    void brieMaxQualityFifty() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 50) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality);
    }

    @Test
    void sulfurasNeverChanges() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].quality);
        assertEquals(0, items[0].sellIn);
    }

    @Test
    void backstagePassesIncreaseAndDropToZero() {
        Item[] passes = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20)
        };
    
        GildedRose app = new GildedRose(passes);
        app.updateQuality();
    
        assertEquals(21, passes[0].quality); // +1
        assertEquals(22, passes[1].quality); // +2
        assertEquals(23, passes[2].quality); // +3
        assertEquals(0,  passes[3].quality); // 0 after concert
    }

}
