// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:4
package controllers.javascript {

  // @LINE:4
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:4
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:26
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:26
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseSearchController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def globalStats: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.globalStats",
      """
        function(keyword0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/search/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("keyword", keyword0)) + "/globalstats"})
        }
      """
    )
  
    // @LINE:10
    def descriptionReadability: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.descriptionReadability",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/descriptionReadability"})
        }
      """
    )
  
    // @LINE:15
    def projectStats: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.projectStats",
      """
        function(keyword0,index1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/search/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("keyword", keyword0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("index", index1)) + "/projectstats"})
        }
      """
    )
  
    // @LINE:12
    def showReadability: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.showReadability",
      """
        function(keyword0,index1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/search/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("keyword", keyword0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("index", index1)) + "/readability"})
        }
      """
    )
  
    // @LINE:22
    def userDetails: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.userDetails",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/userdetails/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:21
    def user: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.user",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/user"})
        }
      """
    )
  
    // @LINE:14
    def stats: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.stats",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/stats"})
        }
      """
    )
  
    // @LINE:8
    def search: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.search",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/search"})
        }
      """
    )
  
    // @LINE:18
    def skills: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.skills",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/skills"})
        }
      """
    )
  
    // @LINE:7
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot"})
        }
      """
    )
  
    // @LINE:19
    def projectSkills: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.SearchController.projectSkills",
      """
        function(skill0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "freelancelot/projects/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("skill", skill0))})
        }
      """
    )
  
  }


}
