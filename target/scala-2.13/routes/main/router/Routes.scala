// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:4
  HomeController_2: controllers.HomeController,
  // @LINE:7
  SearchController_0: controllers.SearchController,
  // @LINE:26
  Assets_1: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:4
    HomeController_2: controllers.HomeController,
    // @LINE:7
    SearchController_0: controllers.SearchController,
    // @LINE:26
    Assets_1: controllers.Assets
  ) = this(errorHandler, HomeController_2, SearchController_0, Assets_1, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_2, SearchController_0, Assets_1, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot""", """controllers.SearchController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/search""", """controllers.SearchController.search()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/descriptionReadability""", """controllers.SearchController.descriptionReadability()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/search/""" + "$" + """keyword<[^/]+>/""" + "$" + """index<[^/]+>/readability""", """controllers.SearchController.showReadability(keyword:String, index:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/stats""", """controllers.SearchController.stats()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/search/""" + "$" + """keyword<[^/]+>/""" + "$" + """index<[^/]+>/projectstats""", """controllers.SearchController.projectStats(keyword:String, index:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/search/""" + "$" + """keyword<[^/]+>/globalstats""", """controllers.SearchController.globalStats(keyword:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/skills""", """controllers.SearchController.skills()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/projects/""" + "$" + """skill<[^/]+>""", """controllers.SearchController.projectSkills(skill:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/user""", """controllers.SearchController.user()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """freelancelot/userdetails/""" + "$" + """id<[^/]+>""", """controllers.SearchController.userDetails(id:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:4
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_2.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """Home""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_SearchController_index1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot")))
  )
  private[this] lazy val controllers_SearchController_index1_invoker = createInvoker(
    SearchController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "index",
      Nil,
      "GET",
      this.prefix + """freelancelot""",
      """Search""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_SearchController_search2_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/search")))
  )
  private[this] lazy val controllers_SearchController_search2_invoker = createInvoker(
    SearchController_0.search(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "search",
      Nil,
      "GET",
      this.prefix + """freelancelot/search""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_SearchController_descriptionReadability3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/descriptionReadability")))
  )
  private[this] lazy val controllers_SearchController_descriptionReadability3_invoker = createInvoker(
    SearchController_0.descriptionReadability(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "descriptionReadability",
      Nil,
      "GET",
      this.prefix + """freelancelot/descriptionReadability""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_SearchController_showReadability4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/search/"), DynamicPart("keyword", """[^/]+""",true), StaticPart("/"), DynamicPart("index", """[^/]+""",true), StaticPart("/readability")))
  )
  private[this] lazy val controllers_SearchController_showReadability4_invoker = createInvoker(
    SearchController_0.showReadability(fakeValue[String], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "showReadability",
      Seq(classOf[String], classOf[Integer]),
      "GET",
      this.prefix + """freelancelot/search/""" + "$" + """keyword<[^/]+>/""" + "$" + """index<[^/]+>/readability""",
      """GET     /freelancelot/session                               controllers.SearchController.createSession(request : Request)""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_SearchController_stats5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/stats")))
  )
  private[this] lazy val controllers_SearchController_stats5_invoker = createInvoker(
    SearchController_0.stats(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "stats",
      Nil,
      "GET",
      this.prefix + """freelancelot/stats""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val controllers_SearchController_projectStats6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/search/"), DynamicPart("keyword", """[^/]+""",true), StaticPart("/"), DynamicPart("index", """[^/]+""",true), StaticPart("/projectstats")))
  )
  private[this] lazy val controllers_SearchController_projectStats6_invoker = createInvoker(
    SearchController_0.projectStats(fakeValue[String], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "projectStats",
      Seq(classOf[String], classOf[Integer]),
      "GET",
      this.prefix + """freelancelot/search/""" + "$" + """keyword<[^/]+>/""" + "$" + """index<[^/]+>/projectstats""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val controllers_SearchController_globalStats7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/search/"), DynamicPart("keyword", """[^/]+""",true), StaticPart("/globalstats")))
  )
  private[this] lazy val controllers_SearchController_globalStats7_invoker = createInvoker(
    SearchController_0.globalStats(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "globalStats",
      Seq(classOf[String]),
      "GET",
      this.prefix + """freelancelot/search/""" + "$" + """keyword<[^/]+>/globalstats""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val controllers_SearchController_skills8_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/skills")))
  )
  private[this] lazy val controllers_SearchController_skills8_invoker = createInvoker(
    SearchController_0.skills(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "skills",
      Nil,
      "GET",
      this.prefix + """freelancelot/skills""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val controllers_SearchController_projectSkills9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/projects/"), DynamicPart("skill", """[^/]+""",true)))
  )
  private[this] lazy val controllers_SearchController_projectSkills9_invoker = createInvoker(
    SearchController_0.projectSkills(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "projectSkills",
      Seq(classOf[String]),
      "GET",
      this.prefix + """freelancelot/projects/""" + "$" + """skill<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val controllers_SearchController_user10_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/user")))
  )
  private[this] lazy val controllers_SearchController_user10_invoker = createInvoker(
    SearchController_0.user(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "user",
      Nil,
      "GET",
      this.prefix + """freelancelot/user""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val controllers_SearchController_userDetails11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("freelancelot/userdetails/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_SearchController_userDetails11_invoker = createInvoker(
    SearchController_0.userDetails(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.SearchController",
      "userDetails",
      Seq(classOf[String]),
      "GET",
      this.prefix + """freelancelot/userdetails/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val controllers_Assets_versioned12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned12_invoker = createInvoker(
    Assets_1.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:4
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_2.index)
      }
  
    // @LINE:7
    case controllers_SearchController_index1_route(params@_) =>
      call { 
        controllers_SearchController_index1_invoker.call(SearchController_0.index())
      }
  
    // @LINE:8
    case controllers_SearchController_search2_route(params@_) =>
      call { 
        controllers_SearchController_search2_invoker.call(SearchController_0.search())
      }
  
    // @LINE:10
    case controllers_SearchController_descriptionReadability3_route(params@_) =>
      call { 
        controllers_SearchController_descriptionReadability3_invoker.call(SearchController_0.descriptionReadability())
      }
  
    // @LINE:12
    case controllers_SearchController_showReadability4_route(params@_) =>
      call(params.fromPath[String]("keyword", None), params.fromPath[Integer]("index", None)) { (keyword, index) =>
        controllers_SearchController_showReadability4_invoker.call(SearchController_0.showReadability(keyword, index))
      }
  
    // @LINE:14
    case controllers_SearchController_stats5_route(params@_) =>
      call { 
        controllers_SearchController_stats5_invoker.call(SearchController_0.stats())
      }
  
    // @LINE:15
    case controllers_SearchController_projectStats6_route(params@_) =>
      call(params.fromPath[String]("keyword", None), params.fromPath[Integer]("index", None)) { (keyword, index) =>
        controllers_SearchController_projectStats6_invoker.call(SearchController_0.projectStats(keyword, index))
      }
  
    // @LINE:16
    case controllers_SearchController_globalStats7_route(params@_) =>
      call(params.fromPath[String]("keyword", None)) { (keyword) =>
        controllers_SearchController_globalStats7_invoker.call(SearchController_0.globalStats(keyword))
      }
  
    // @LINE:18
    case controllers_SearchController_skills8_route(params@_) =>
      call { 
        controllers_SearchController_skills8_invoker.call(SearchController_0.skills())
      }
  
    // @LINE:19
    case controllers_SearchController_projectSkills9_route(params@_) =>
      call(params.fromPath[String]("skill", None)) { (skill) =>
        controllers_SearchController_projectSkills9_invoker.call(SearchController_0.projectSkills(skill))
      }
  
    // @LINE:21
    case controllers_SearchController_user10_route(params@_) =>
      call { 
        controllers_SearchController_user10_invoker.call(SearchController_0.user())
      }
  
    // @LINE:22
    case controllers_SearchController_userDetails11_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_SearchController_userDetails11_invoker.call(SearchController_0.userDetails(id))
      }
  
    // @LINE:26
    case controllers_Assets_versioned12_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned12_invoker.call(Assets_1.versioned(path, file))
      }
  }
}
