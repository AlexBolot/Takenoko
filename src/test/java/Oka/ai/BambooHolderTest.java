package Oka.ai;

import Oka.model.Bamboo;
import Oka.model.Enums;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BambooHolderTest {
    private BambooHolder bambooholder;

    @Before
    public void init(){
        bambooholder = new BambooHolder();
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.GREEN));
        bambooholder.add(new Bamboo(Enums.Color.PINK));
        bambooholder.add(new Bamboo(Enums.Color.PINK));
        bambooholder.add(new Bamboo(Enums.Color.YELLOW));
    }
    @Test
    public void countBamboo() throws Exception {
        assertEquals(3,bambooholder.countBamboo(Enums.Color.GREEN));
        assertEquals(2,bambooholder.countBamboo(Enums.Color.PINK));
        assertEquals(1,bambooholder.countBamboo(Enums.Color.YELLOW));
    }

    @Test
    public void removeByColor() throws Exception {

        bambooholder.removeByColor(Enums.Color.GREEN,2);
        bambooholder.removeByColor(Enums.Color.PINK,1);
        bambooholder.removeByColor(Enums.Color.YELLOW,1);

        assertEquals(1,bambooholder.countBamboo(Enums.Color.GREEN));
        assertEquals(1,bambooholder.countBamboo(Enums.Color.PINK));
        assertEquals(0,bambooholder.countBamboo(Enums.Color.YELLOW));
    }

    @Test
    public void addBamboo() throws Exception {
        bambooholder.addBamboo(Enums.Color.GREEN);
        bambooholder.addBamboo(Enums.Color.GREEN);
        bambooholder.addBamboo(Enums.Color.PINK);
        bambooholder.addBamboo(Enums.Color.YELLOW);

        assertEquals(5,bambooholder.countBamboo(Enums.Color.GREEN));
        assertEquals(3,bambooholder.countBamboo(Enums.Color.PINK));
        assertEquals(2,bambooholder.countBamboo(Enums.Color.YELLOW));
    }

}