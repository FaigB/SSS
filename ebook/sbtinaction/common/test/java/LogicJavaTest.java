package com.preownedkittens;


import org.junit.*;
import scala.collection.immutable.*;
import com.preownedkittens.Kitten;

public class LogicJavaTest {
    @Test
    public void testKitten() {
        Kitten kitten = new Kitten(1, (new HashSet<String>()).toSeq());
        // in chapter 5 we have Assert.assertEquals(1, kitten.attributes().size());
        // but as part of the chapter, we correct it - this test should pass
        Assert.assertEquals(1, kitten.attributes().size());
    }
}