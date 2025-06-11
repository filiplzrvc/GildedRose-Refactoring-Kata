package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
    Teststrategie:
    ----------------
    Ziel der Tests ist es, alle relevanten Item-Typen und deren Verhalten in der Methode updateQuality() abzudecken.
    Es werden normale Items, Aged Brie, Sulfuras und Backstage Passes getestet – inklusive Randfälle wie Qualität = 0 oder Qualität = 50.

    Die Tests wurden nach TDD-Prinzipien erstellt und decken sowohl Standardverhalten als auch Sonderregeln vollständig ab.

    Herausforderungen:
    --------------------
    - Die Logik basiert auf Strings anstatt Polymorphie, was zu schwer lesbarem Code führt.
    - Einige Regeln (z. B. bei Backstage passes) wirken auf den ersten Blick unintuitiv.
    - Der Code ist stark gekoppelt, was das gezielte Testen einzelner Aspekte erschwert. 
*/

class GildedRoseTest {

    @Test
    void normalItem_qualityDecreases() {
        // Normales Item verliert 1 Qualität und 1 SellIn
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 5, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(9, items[0].quality);
        assertEquals(4, items[0].sellIn);
    }

    @Test
    void normalItem_degradesTwiceAfterSellIn() {
        // Nach Ablauf sinkt Qualität doppelt
        Item[] items = new Item[] { new Item("Normal Item", 0, 10) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(8, items[0].quality);
        assertEquals(-1, items[0].sellIn);
    }

    @Test
    void qualityNeverNegative() {
        // Qualität kann nicht unter 0 fallen
        Item[] items = new Item[] { new Item("Normal Item", 5, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(0, items[0].quality);
    }

    @Test
    void brieIncreasesQuality() {
        // Aged Brie erhöht Qualität täglich
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(1, items[0].quality);
    }

    @Test
    void brieMaxQualityFifty() {
        // Aged Brie darf Qualität 50 nicht überschreiten
        Item[] items = new Item[] { new Item("Aged Brie", 2, 50) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(50, items[0].quality);
    }

    @Test
    void sulfurasNeverChanges() {
        // Sulfuras verändert sich nie
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(80, items[0].quality);
        assertEquals(0, items[0].sellIn);
    }

    @Test
    void backstagePassesIncreaseAndDropToZero() {
        // Backstage Passes steigen bis Konzert, danach 0
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
        assertEquals(0, passes[3].quality); // 0 after concert
    }
    
    @Test
    void qualityNeverMoreThanFifty() {
        // Kein Item außer Sulfuras darf Qualität > 50 haben
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 50),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50)
        };

        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(50, items[0].quality);
        assertEquals(50, items[1].quality);
    }
}
