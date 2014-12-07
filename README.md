
# Standalone-EL

Servletコンテナ非依存のスタンドアローンでEL2.2を利用可能にするライブラリ。

# 参考

## 参考にしたプログラム
BeanValidation1.1の実装であるHibernateValidator5.xのパッケージ「org.hibernate.validator.internal.engine.messageinterpolation.el」内のクラス。

## EL式の各クラスの役割が説明されているサイト。
- http://d.hatena.ne.jp/seraphy/20140205
- http://d.hatena.ne.jp/seraphy/touch/20140205/p2

## 同じようなライブラリ
- https://github.com/seraphy/StandaloneELContext


# 使い方

ELProcessorを呼ぶ場合。

```java
ELProcessor elProc = new ELProcessor();

elProc.setVariable("currentDate", new Date());
elProc.setVariable("formatter", new FormatterWrapper(Locale.getDefault()));

String eval = elProc.eval("formatter.format('%1$tY/%1$tm/%1$td%n', currentDate)", String.class);

System.out.println(eval);
```

LocalELContextを直接呼ぶ場合。

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
