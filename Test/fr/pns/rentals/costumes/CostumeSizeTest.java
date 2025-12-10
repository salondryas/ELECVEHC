package fr.pns.rentals.costumes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CostumeSizeTest {

    @Test
    void testCostumeSize() {
        CostumeSize size = CostumeSize.SMALL;
        assertEquals("Petit", size.getFrenchName());
        size = CostumeSize.MEDIUM;
        assertEquals("Moyen", size.getFrenchName());
        size = CostumeSize.LARGE;
        assertEquals("Grand", size.getFrenchName());
        size = CostumeSize.XLARGE;
        assertEquals("Très grand", size.getFrenchName());
        size = CostumeSize.XXLARGE;
        assertEquals("Très très grand", size.getFrenchName());
        size = CostumeSize.ONE_SIZE;
        assertEquals("Taille unique", size.getFrenchName());
    }

    @Test
    void demo4Enumerate(){
        CostumeSize[] sizes = CostumeSize.values();
        for (CostumeSize size : sizes) {
            System.out.println(size + " : " + size.name() + " : " + size.ordinal());
        }
        assertEquals(6, sizes.length);
        assertEquals(CostumeSize.MEDIUM, sizes[1]);
        CostumeSize size = CostumeSize.valueOf("XLARGE");
        assertEquals(CostumeSize.XLARGE, size);
        assertEquals(3, size.ordinal());
    }
}