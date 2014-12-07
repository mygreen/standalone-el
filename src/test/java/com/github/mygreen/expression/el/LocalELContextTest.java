package com.github.mygreen.expression.el;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * EL式のテスタ
 *
 */
public class LocalELContextTest {
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testNormal() {
        
        Map<String, Object> vars = new HashMap<>();
        vars.put("validatedValue", 3);
        vars.put("currentDate", new Date());
        vars.put("min", 1);
        vars.put("max", 10);
//        vars.put("formatter", new FormatterWrapper(Locale.getDefault()));
        
        try {
            LocalELContext context = new LocalELContext();
            
            ExpressionFactory expressionFactory = ExpressionFactory.newInstance();
            for(Map.Entry<String, Object> entry : vars.entrySet()) {
                
                // ローカル変数の作成
                ValueExpression exp =  expressionFactory.createValueExpression(context,
                        String.format("${%s}", entry.getKey()),
                        Object.class);
                exp.setValue(context, entry.getValue());
                
            }
            
            ValueExpression expFormatter = expressionFactory.createValueExpression(
                    new FormatterWrapper(Locale.getDefault()), FormatterWrapper.class);
            context.setVariable("formatter", expFormatter);
            
            ValueExpression exp2 = expressionFactory.createValueExpression(context, "${min + max}", Object.class);
            System.out.println(exp2.getValue(context));
            
            ValueExpression exp3 = expressionFactory.createValueExpression(context, "${formatter.format('%1$tY/%1$tm/%1$td%n', currentDate)}", String.class);
            System.out.println(exp3.getValue(context));
            
            
        } catch(Throwable e) {
            e.printStackTrace();
            fail();
        }
    }
    
    @Test
    public void testNormal2() {
        
        Map<String, Object> vars = new HashMap<>();
        vars.put("validatedValue", 3);
        vars.put("currentDate", new Date());
        vars.put("min", 1);
        vars.put("max", 10);
        vars.put("formatter", new FormatterWrapper(Locale.getDefault()));
        
        try {
            LocalELContext context = new LocalELContext();
            
            ExpressionFactory expressionFactory = ExpressionFactory.newInstance();
            
            for(Map.Entry<String, Object> entry : vars.entrySet()) {
                ValueExpression exp =  expressionFactory.createValueExpression(entry.getValue(), Object.class);
                context.setVariable(entry.getKey(), exp);
            }
            
            ValueExpression exp2 = expressionFactory.createValueExpression(context, "${min + max}", Object.class);
            System.out.println(exp2.getValue(context));
            
            ValueExpression exp3 = expressionFactory.createValueExpression(context, "${formatter.format('%1$tY/%1$tm/%1$td%n', currentDate)}", String.class);
            System.out.println(exp3.getValue(context));
            
        } catch(Throwable e) {
            e.printStackTrace();
            fail();
        }
    }
    
    // 変数定義のテスト
    @Test
    public void testDefineVar() {
        
        try {
            
            LocalELContext context = new LocalELContext();
            
            ExpressionFactory expressionFactory = ExpressionFactory.newInstance();
            
            ValueExpression eval = expressionFactory.createValueExpression(context, "${x=3;y=2;x+y}", Integer.class);
            System.out.println(eval.getValue(context));
            
        } catch(Throwable e) {
            e.printStackTrace();
            fail();
        }
    }
    
    // ラムダ式のテスト
    @Test
    public void testLambda() {
        
        Map<String, Object> vars = new HashMap<>();
        vars.put("list", Arrays.asList(1, 2, 3, 4, 5, 6));
        
        try {
            
            LocalELContext context = new LocalELContext();
            
            ExpressionFactory expressionFactory = ExpressionFactory.newInstance();
            
            for(Map.Entry<String, Object> entry : vars.entrySet()) {
                ValueExpression exp =  expressionFactory.createValueExpression(entry.getValue(), Object.class);
                context.setVariable(entry.getKey(), exp);
            }
            
            ValueExpression eval = expressionFactory.createValueExpression(context, 
                    "${sum=0;list.stream().forEach(x->(sum=sum+x));sum}", Integer.class);
            System.out.println(eval.getValue(context));
            
        } catch(Throwable e) {
            e.printStackTrace();
            fail();
        }
    }
    
//    // ELProcessorのテスト
//    @Test
//    public void testStandartd() {
//        
//        Map<String, Object> vars = new HashMap<>();
//        vars.put("list", Arrays.asList(1, 2, 3, 4, 5, 6));
//        
//        try {
//            
//            ELProcessor elProc = new ELProcessor();
////            elProc.getELManager().setELContext(new LocalELContext());
//            
//            elProc.defineBean("foo", new BigDecimal("123"));
//            elProc.defineBean("bar", "brabrabra");
//            elProc.defineBean("list", Arrays.asList(1, 2, 3, 4, 5, 6));
//            Number ret = (Number)elProc.eval("foo+1");
//            System.out.println(ret);
//            
//            System.out.println(elProc.eval("sum=0;list.stream().forEach(x->(sum=sum+x));sum"));
//            
//        } catch(Throwable e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
    
}
