package com.github.mygreen.expression.el;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.mygreen.expression.el.ELProcessor;
import com.github.mygreen.expression.el.FormatterWrapper;
import com.github.mygreen.expression.el.tld.Function;
import com.github.mygreen.expression.el.tld.Taglib;
import com.github.mygreen.expression.el.tld.TldLoader;


public class ELProcessorTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void test_normal() {
        
        Date date = Timestamp.valueOf("2015-04-15 10:20:30.000");
        
        ELProcessor elProc = new ELProcessor();
        
        elProc.setVariable("currentDate", date);
        elProc.setVariable("formatter", new FormatterWrapper(Locale.getDefault()));
        
        String result = elProc.eval("formatter.format('%1$tY/%1$tm/%1$td', currentDate)", String.class);
        assertThat(result, is("2015/04/15"));
//        System.out.println(result);
        
    }
    
    /**
     * EL関数の登録
     */
    @Test
    public void test_function() {
        
        ELProcessor elProc = new ELProcessor();
        
        try {
            Method method = MyFunction.class.getDeclaredMethod("strlen", String.class);
            
            // EL関数の登録 - メソッドオブジェクトで指定
            elProc.defineFunction("my", "strlength", method);
            
            // EL関数の指定 - 文字列で指定
            String className = ELProcessorTest.MyFunction.class.getName();
            elProc.defineFunction("my", "", className, "int sum(int,int)");
            
            elProc.setVariable("hello", "こんにちは。今日はいい天気ですね。");
            
            int result1 = elProc.eval("my:strlength(hello)", Integer.class);
            assertThat(result1, is(17));
//            System.out.println(result1);
            
            elProc.setVariable("num1", 1);
            int result2 = elProc.eval("my:sum(num1, 5)", Integer.class);
            assertThat(result2, is(6));
//            System.out.println(result2);
            
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    
    /**
     * TDLファイルから関数を登録する
     */
    @Test
    public void test_function_tld() throws Exception {
        
        // EL関数の登録
        TldLoader loader = new TldLoader();
        Taglib taglib = loader.load(ELProcessorTest.class.getResourceAsStream("/com/github/mygreen/expression/el/tld/xlsmapper.tld"));
        
        ELProcessor elProc = new ELProcessor();
        
        final String prefix = taglib.getShortName();
        
        for(Function function : taglib.getFunctions()) {
            
            final String className = function.getFunctionClass();
            final String signature = function.getFunctionSignature();
            final String name = function.getName();
            
            elProc.defineFunction(prefix, name, className, signature);
        }
        
        String expression = "x:colToAlpha(columnNumber)";
        elProc.setVariable("columnNumber", 1);
        
        String eval = elProc.eval(expression, String.class);
        assertThat(eval, is("A"));
        
    }
    
    public static class MyFunction {
        
        public static int strlen(String str) {
            return str.length();
        }
        
        public static int sum(int a, int b) {
            return a + b;
        }
        
    }
}
