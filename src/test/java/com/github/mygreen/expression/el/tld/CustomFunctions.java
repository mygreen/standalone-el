package com.github.mygreen.expression.el.tld;


/**
 * EL式中で利用可能なEL関数。
 *
 * @since 1.5
 * @author T.TSUCHIE
 *
 */
public class CustomFunctions {
    
    /**
     * 列番号を英字名に変換します。
     * @param col 列番号(1から始まる)
     * @return 列の英字名
     */
    public static String colToAlpha(final int col) {
        // Excel counts column A as the 1st column, we
        //  treat it as the 0th one
        int excelColNum = col;

        StringBuilder colRef = new StringBuilder(2);
        int colRemain = excelColNum;

        while(colRemain > 0) {
            int thisPart = colRemain % 26;
            if(thisPart == 0) { thisPart = 26; }
            colRemain = (colRemain - thisPart) / 26;

            // The letter A is at 65
            char colChar = (char)(thisPart+64);
            colRef.insert(0, colChar);
        }

        return colRef.toString();
    }
    
}
