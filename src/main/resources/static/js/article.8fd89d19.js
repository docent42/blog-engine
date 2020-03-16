(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["article"], {
    7999: function (t, e, r) {
        "use strict";
        var i = r("b948"), n = r.n(i);
        n.a
    }, 8192: function (t, e, r) {
        "use strict";
        r.r(e);
        var i = function () {
                var t = this, e = t.$createElement, r = t._self._c || e;
                return r("main", {staticClass: "Wrapper"}, [t.articleIsErrored ? r("div", {staticClass: "ServerInfo"}, [t._v(" Sorry, some error happened :( ")]) : t._e(), t.articleIsLoading || t.articleIsErrored ? t._e() : r("BaseArticle", {
                    key: t.article.id,
                    attrs: {
                        className: "Article--full",
                        id: t.article.id,
                        time: t.article.time,
                        author: t.article.user.name,
                        title: t.article.title,
                        text: t.article.text,
                        likeCount: t.article.likeCount,
                        dislikeCount: t.article.dislikeCount,
                        commentCount: t.article.commentCount,
                        viewCount: t.article.viewCount,
                        tags: t.article.tags
                    }
                }), t.articleIsLoading || t.articleIsErrored || !t.article.comments ? t._e() : r("div", {staticClass: "Comments"}, [r("div", {staticClass: "Title Comments-Title"}, [t._v(" Комментарии ")]), t._l(t.article.comments, (function (t) {
                    return r("Comment", {
                        key: t.id,
                        attrs: {
                            id: t.id,
                            authorId: t.user.id,
                            author: t.user.name,
                            time: t.time,
                            text: t.text,
                            className: "Comments-Comment"
                        }
                    })
                }))], 2), t.articleIsLoading || t.articleIsErrored || !t.isAuth ? t._e() : r("AddComment")], 1)
            }, n = [],
            c = (r("a4d3"), r("99af"), r("4de4"), r("e439"), r("dbb4"), r("b64b"), r("d3b7"), r("159b"), r("ade3")),
            o = r("2f62");

        function a(t, e) {
            var r = Object.keys(t);
            if (Object.getOwnPropertySymbols) {
                var i = Object.getOwnPropertySymbols(t);
                e && (i = i.filter((function (e) {
                    return Object.getOwnPropertyDescriptor(t, e).enumerable
                }))), r.push.apply(r, i)
            }
            return r
        }

        function l(t) {
            for (var e = 1; e < arguments.length; e++) {
                var r = null != arguments[e] ? arguments[e] : {};
                e % 2 ? a(Object(r), !0).forEach((function (e) {
                    Object(c["a"])(t, e, r[e])
                })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(t, Object.getOwnPropertyDescriptors(r)) : a(Object(r)).forEach((function (e) {
                    Object.defineProperty(t, e, Object.getOwnPropertyDescriptor(r, e))
                }))
            }
            return t
        }

        var s = function () {
            return r.e("baseArticle").then(r.bind(null, "5e98"))
        }, u = function () {
            return r.e("comment").then(r.bind(null, "d8f1"))
        }, m = function () {
            return r.e("addComment").then(r.bind(null, "d6de"))
        }, d = {
            name: "Article",
            components: {BaseArticle: s, Comment: u, AddComment: m},
            computed: l({}, Object(o["mapGetters"])(["blogInfo", "isAuth", "article", "articleIsLoading", "articleIsErrored"])),
            methods: l({}, Object(o["mapActions"])(["getArticle"])),
            created: function () {
                this.getArticle(this.$route.params.id)
            },
            metaInfo: function () {
                return {title: this.article && this.article.title ? "".concat(this.article.title, " | ").concat(this.blogInfo.title, " - ").concat(this.blogInfo.subtitle) : "".concat(this.blogInfo.title, " - ").concat(this.blogInfo.subtitle)}
            }
        }, b = d, f = (r("7999"), r("2877")), p = Object(f["a"])(b, i, n, !1, null, null, null);
        e["default"] = p.exports
    }, b948: function (t, e, r) {
    }
}]);
//# sourceMappingURL=article.8fd89d19.js.map