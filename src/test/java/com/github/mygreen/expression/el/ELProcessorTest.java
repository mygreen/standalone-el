package com.github.mygreen.expression.el;

import java.util.Date;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ELProcessorTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test() {
        
        ELProcessor elProc = new ELProcessor();
        
        elProc.setVariable("currentDate", new Date());
        elProc.setVariable("formatter", new FormatterWrapper(Locale.getDefault()));
        
        String eval = elProc.eval("formatter.format('%1$tY/%1$tm/%1$td%n', currentDate)", String.class);
        System.out.println(eval);
        
    }
}
