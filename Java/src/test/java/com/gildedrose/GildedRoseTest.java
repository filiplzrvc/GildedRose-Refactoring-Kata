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

}
