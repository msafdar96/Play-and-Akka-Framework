
package views.html.Home

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.data._
import play.core.j.PlayFormsMagicForJava._
import scala.jdk.CollectionConverters._

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html lang="en">

<head>
    <title>"""),_display_(/*7.13*/title),format.raw/*7.18*/("""</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" media="screen" href='"""),_display_(/*9.50*/routes/*9.56*/.Assets.versioned("stylesheets/Home/main.css")),format.raw/*9.102*/("""'>
    <link rel="stylesheet" media="screen" href='"""),_display_(/*10.50*/routes/*10.56*/.Assets.versioned("stylesheets/Home/prism.css")),format.raw/*10.103*/("""'>
    <link rel="shortcut icon" type="image/png" href='"""),_display_(/*11.55*/routes/*11.61*/.Assets.versioned("images/favicon.png")),format.raw/*11.100*/("""'>
    <script src='"""),_display_(/*12.19*/routes/*12.25*/.Assets.versioned("javascripts/hello.js")),format.raw/*12.66*/("""' type="text/javascript"></script>
    <script src='"""),_display_(/*13.19*/routes/*13.25*/.Assets.versioned("javascripts/prism.js")),format.raw/*13.66*/("""' type="text/javascript"></script>
</head>

<body>
    <section id="top">
        <div class="wrapper">
            <h1 class="bold">FreeLancelot</h1>
        </div>
    </section>
    """),_display_(/*22.6*/content),format.raw/*22.13*/("""
"""),format.raw/*23.1*/("""</body>

</html>"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/Home/main.scala.html
                  HASH: e7ea8c774abdea8ad88e817ddf228c4c19c2ec80
                  MATRIX: 916->1|1040->32|1067->33|1146->86|1171->91|1330->224|1344->230|1411->276|1490->328|1505->334|1574->381|1658->438|1673->444|1734->483|1782->504|1797->510|1859->551|1939->604|1954->610|2016->651|2228->837|2256->844|2284->845
                  LINES: 27->1|32->2|33->3|37->7|37->7|39->9|39->9|39->9|40->10|40->10|40->10|41->11|41->11|41->11|42->12|42->12|42->12|43->13|43->13|43->13|52->22|52->22|53->23
                  -- GENERATED --
              */
          