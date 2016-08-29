
# Standalone-EL

Servletコンテナ非依存のスタンドアローンでEL2.2を利用可能にするライブラリ。

# License
Apache License 2.0.

# 参考

## 参考にしたプログラム
BeanValidation1.1の実装であるHibernateValidator5.xのパッケージ「org.hibernate.validator.internal.engine.messageinterpolation.el」内のクラス。

## EL式の各クラスの役割が説明されているサイト。
- http://d.hatena.ne.jp/seraphy/20140205
- http://d.hatena.ne.jp/seraphy/touch/20140205/p2

## 同じようなライブラリ
- https://github.com/seraphy/StandaloneELContext


# 使い方

## ダウンロード
Mavenのセントラルリポジトリからダウンロードします。

```xml
<dependency>
    <groupId>com.github.mygreen</groupId>
    <artifactId>standalone-el</artifactId>
    <version>0.2</version>
</dependency>
```

## ELProcessorを呼ぶ場合

```java
ELProcessor elProc = new ELProcessor();

// 変数の登録
elProc.setVariable("currentDate", new Date());
elProc.setVariable("formatter", new FormatterWrapper(Locale.getDefault()));

String eval = elProc.eval("formatter.format('%1$tY/%1$tm/%1$td', currentDate)", String.class);

System.out.println(eval);
```

### EL関数を使用する場合
```java
// EL関数として呼び出すクラスの定義。
public class MyFunction {
        // staticメソッドで定義します。
    public static int sum(int a, int b) {
        return a + b;
    }
}

// ELProcessorにEL関数として登録する。
ELProcessor elProc = new ELProcessor();

/*
 * 第1引数 - EL式中で呼び出すための接頭語。
 * 第2引数 - EL式中で呼び出す関数名。空文字("")で指定した場合、Javaの関数名が使用されます。
 * 第3引数 - 呼び出すJavaのクラス名を指定します。
 * 第4引数 - 呼び出すメソッド名(static)の形式を指定します。
 *          戻り値と関数名の間にスペースを空けます。さらに、引数が複数ある場合、カンマで区切ります。その際にスペースは空けないようにします。
 */
elProc.defineFunction("my", "sum", "my.MyFunction", "int sum(int,int)");

elProc.setVariable("num", 5);

// EL式の評価
int eval = elProc.eval("my:sum(num, 2)", int.class);

```

### EL関数を使用する場合（TLDファイルから読み込む場合）
ver.0.2から追加された、``TldLoder``を使用します。

```java
// TLDファイルの読み込み
TldLoader loader = new TldLoader();
Taglib taglib = loader.load(/** tldファイルのInputStream */);

ELProcessor elProc = new ELProcessor();

//EL関数の登録
final String prefix = taglib.getShortName();
for(Function function : taglib.getFunctions()) {

    final String className = function.getFunctionClass();
    final String signature = function.getFunctionSignature();
    final String name = function.getName();

    elProc.defineFunction(prefix, name, className, signature);
}

```


## LocalELContextを直接呼ぶ場合

```java
LocalELContext context = new LocalELContext();

// EL式内で利用するローカル変数の作成
ExpressionFactory expressionFactory = ExpressionFactory.newInstance();

ValueExpression var1 =  expressionFactory.createValueExpression(new Date(), Date.class);
context.setVariable("currentDate", var1);

ValueExpression varFormatter = expressionFactory.createValueExpression(new FormatterWrapper(Locale.getDefault()), FormatterWrapper.class);
context.setVariable("formatter", varFormatter);

// 式の評価
ValueExpression eval = expressionFactory.createValueExpression(context, "${formatter.format('%1$tY/%1$tm/%1$td%n', currentDate)}", String.class);
System.out.println(eval.getValue(context));

```

## 注意事項

EL式中で呼び出すオブジェクトの中のメソッドは、オーバライドしていると、区別つかないためラップなどして使用する。

'java.util.Formatter#formatter'は、FormatterWrapperクラスでラップして呼び出している。

