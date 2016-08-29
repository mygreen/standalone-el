package com.github.mygreen.expression.el.tld;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * {@link TldLoader}のテスタ。
 *
 * @since 1.5
 * @author T.TSUCHIE
 *
 */
public class TldLoaderTest {
    
    private TldLoader loader;
    
    @Before
    public void setUp() throws Exception {
        this.loader = new TldLoader();
    }
    
    @Test
    public void load() throws Exception {
        
        Taglib taglib = loader.load(TldLoaderTest.class.getResourceAsStream("/com/github/mygreen/expression/el/tld/xlsmapper.tld"));
        assertThat(taglib.getShortName(), is("x"));
        
        Function function1 = taglib.getFunctions().get(0);
        assertThat(function1.getName(), is("colToAlpha"));
    }
}
