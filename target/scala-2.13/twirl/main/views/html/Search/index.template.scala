
package views.html.Search

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
/*1.2*/import java.util._

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(title: String, heading: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""

"""),format.raw/*5.1*/("""<html lang="en">
    <head>
        <title>"""),_display_(/*7.17*/title),format.raw/*7.22*/("""</title>
        <link rel="stylesheet" media="screen" href='"""),_display_(/*8.54*/routes/*8.60*/.Assets.versioned("stylesheets/Search/main.css")),format.raw/*8.108*/("""'>
        <link rel="shortcut icon" type="image/png" href='"""),_display_(/*9.59*/routes/*9.65*/.Assets.versioned("images/favicon.png")),format.raw/*9.104*/("""'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    </head>
    <body>
        <div class="main">
            <h1>"""),_display_(/*15.18*/heading),format.raw/*15.25*/("""</h1>
            <form id="searchForm">
                <input class="form-control inputText" name="keyword" id="keyword" placeholder="Enter Search Items" required/>
                <input type="submit" name="Go" value="Go" class="btn btn-primary" />
            </form>
            <div>
                <p class="error"></p>
                <div id="searchResults">

                </div>
            </div>
            """),format.raw/*26.52*/("""
            """),format.raw/*27.70*/("""
            """),format.raw/*28.95*/("""
            """),format.raw/*29.99*/("""
            """),format.raw/*30.86*/("""
            """),format.raw/*31.19*/("""

            """),format.raw/*33.45*/("""
            """),format.raw/*34.100*/("""
            """),format.raw/*35.60*/("""
            """),format.raw/*36.43*/("""
            """),format.raw/*37.32*/("""
            """),format.raw/*38.24*/("""
            """),format.raw/*39.41*/("""
            """),format.raw/*40.75*/("""
            """),format.raw/*41.39*/("""
            """),format.raw/*42.64*/("""
            """),format.raw/*43.83*/("""
            """),format.raw/*44.162*/("""
            """),format.raw/*45.165*/("""
            """),format.raw/*46.177*/("""
            """),format.raw/*47.180*/("""
            """),format.raw/*48.22*/("""
            """),format.raw/*49.24*/("""
            """),format.raw/*50.22*/("""
            """),format.raw/*51.114*/("""
            """),format.raw/*52.24*/("""
            """),format.raw/*53.24*/("""

            """),format.raw/*55.51*/("""
            """),format.raw/*56.43*/("""
            """),format.raw/*57.28*/("""
            """),format.raw/*58.35*/("""
            """),format.raw/*59.41*/("""
            """),format.raw/*60.32*/("""
            """),format.raw/*61.31*/("""
            """),format.raw/*62.33*/("""
            """),format.raw/*63.38*/("""
            """),format.raw/*64.38*/("""
            """),format.raw/*65.26*/("""
            """),format.raw/*66.25*/("""
            """),format.raw/*67.81*/("""
            """),format.raw/*68.22*/("""
            """),format.raw/*69.39*/("""
            """),format.raw/*70.122*/("""
            """),format.raw/*71.54*/("""
            """),format.raw/*72.46*/("""
            """),format.raw/*73.45*/("""
            """),format.raw/*74.22*/("""
            """),format.raw/*75.62*/("""
            """),format.raw/*76.86*/("""
            """),format.raw/*77.19*/("""
            """),format.raw/*78.23*/("""
            """),format.raw/*79.22*/("""
            """),format.raw/*80.99*/("""
            """),format.raw/*81.23*/("""
            """),format.raw/*82.22*/("""
            """),format.raw/*83.90*/("""
            """),format.raw/*84.23*/("""
            """),format.raw/*85.23*/("""
            """),format.raw/*86.19*/("""
            """),format.raw/*87.26*/("""

            """),format.raw/*89.26*/("""
            """),format.raw/*90.19*/("""
            """),format.raw/*91.24*/("""


        """),format.raw/*94.9*/("""</div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src=""""),_display_(/*97.23*/routes/*97.29*/.Assets.versioned("javascripts/Search/main.js")),format.raw/*97.76*/("""" type="text/javascript"></script>
    </body>
</html>"""))
      }
    }
  }

  def render(title:String,heading:String): play.twirl.api.HtmlFormat.Appendable = apply(title,heading)

  def f:((String,String) => play.twirl.api.HtmlFormat.Appendable) = (title,heading) => apply(title,heading)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/Search/index.scala.html
                  HASH: 47e7ee496e194997e53b96a4aac2cceced218026
                  MATRIX: 617->1|947->21|1073->54|1101->56|1171->100|1196->105|1284->167|1298->173|1367->221|1454->282|1468->288|1528->327|1845->618|1873->625|2325->1088|2366->1158|2407->1253|2448->1352|2489->1438|2530->1457|2572->1503|2614->1603|2655->1663|2696->1706|2737->1738|2778->1762|2819->1803|2860->1878|2901->1917|2942->1981|2983->2064|3025->2226|3067->2391|3109->2568|3151->2748|3192->2770|3233->2794|3274->2816|3316->2930|3357->2954|3398->2978|3440->3030|3481->3073|3522->3101|3563->3136|3604->3177|3645->3209|3686->3240|3727->3273|3768->3311|3809->3349|3850->3375|3891->3400|3932->3481|3973->3503|4014->3542|4056->3664|4097->3718|4138->3764|4179->3809|4220->3831|4261->3893|4302->3979|4343->3998|4384->4021|4425->4043|4466->4142|4507->4165|4548->4187|4589->4277|4630->4300|4671->4323|4712->4342|4753->4368|4795->4395|4836->4414|4877->4438|4915->4449|5352->4860|5367->4866|5435->4913
                  LINES: 23->1|28->2|33->3|35->5|37->7|37->7|38->8|38->8|38->8|39->9|39->9|39->9|45->15|45->15|56->26|57->27|58->28|59->29|60->30|61->31|63->33|64->34|65->35|66->36|67->37|68->38|69->39|70->40|71->41|72->42|73->43|74->44|75->45|76->46|77->47|78->48|79->49|80->50|81->51|82->52|83->53|85->55|86->56|87->57|88->58|89->59|90->60|91->61|92->62|93->63|94->64|95->65|96->66|97->67|98->68|99->69|100->70|101->71|102->72|103->73|104->74|105->75|106->76|107->77|108->78|109->79|110->80|111->81|112->82|113->83|114->84|115->85|116->86|117->87|119->89|120->90|121->91|124->94|127->97|127->97|127->97
                  -- GENERATED --
              */
          