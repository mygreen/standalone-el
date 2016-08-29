package com.github.mygreen.expression.el.tld;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * TLDファイルを読み込むクラス。
 *
 * @since 1.5
 * @author T.TSUCHIE
 *
 */
public class TldLoader {
    
    /**
     * TLDファイルを読み込む
     * @param in
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public Taglib load(final InputStream in) throws ParserConfigurationException, SAXException, IOException {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(in);
        
        return parseTaglib(doc.getDocumentElement());
        
    }
    
    private Taglib parseTaglib(final Element element) {
        
        final Taglib taglib = new Taglib();
        
        taglib.setDescription(getFirstElemenetValue(element, "description"));
        taglib.setDisplayName(getFirstElemenetValue(element, "display-name"));
        taglib.setTlibVersion(getFirstElemenetValue(element, "tlib-version"));
        taglib.setShortName(trimToEmpty(getFirstElemenetValue(element, "short-name")));
        taglib.setUri(trimToEmpty(getFirstElemenetValue(element, "uri")));
        
        final NodeList list = element.getElementsByTagName("function");
        for(int i=0; i < list.getLength(); i++) {
            Function function = parseFunction((Element) list.item(i));
            taglib.add(function);
        }
        
        return taglib;
        
    }
    
    private Function parseFunction(final Element element) {
        
        final Function function = new Function();
        function.setDescription(getFirstElemenetValue(element, "description"));
        function.setName(trimToEmpty(getFirstElemenetValue(element, "name")));
        function.setFunctionClass(trimToEmpty(getFirstElemenetValue(element, "function-class")));
        function.setFunctionSignature(trimToEmpty(getFirstElemenetValue(element, "function-signature")));
        
        return function;
    }
    
    /**
     * 指定したタグの値を取得する。
     * 一致するタグが複数存在する場合は、初めの要素の値を返す。
     * @param parent 親の要素
     * @param tagName タグ名
     * @return 指定したタグを持たない場合は、nullを返す。
     */
    private String getFirstElemenetValue(final Element parent, final String tagName) {
        
        final NodeList list = parent.getElementsByTagName(tagName);
        if(list.getLength() == 0) {
            return null;
        }
        
        Element element = (Element) list.item(0);
        return element.getTextContent();
        
    }
    
    /**
     * 文字列をトリムする。
     * ただし、値がnullの場合、空文字を返す。
     * @param str
     * @return
     */
    private String trimToEmpty(final String str) {
        return str == null ? "" : str.trim();
    }
    
}
