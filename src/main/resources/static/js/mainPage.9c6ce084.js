(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["mainPage"], {
    5872: function (t, e, n) {
        "use strict";
        var a = n("8abe"), r = n.n(a);
        r.a
    }, "6ccf": function (t, e, n) {
        "use strict";
        n.r(e);
        var a = function () {
                var t = this, e = t.$createElement, n = t._self._c || e;
                return n("main", {staticClass: "MainPage Wrapper"}, [n("Articles", {
                    attrs: {
                        navItems: t.navItems,
                        className: "MainPage-Articles",
                        tagSelected: t.tagSelected,
                        postByDate: t.postByDate
                    }
                }), n("Tags", {attrs: {className: "MainPage-Tags"}, on: {"select-tag": t.onClickTag}})], 1)
            }, r = [],
            o = (n("a4d3"), n("99af"), n("4de4"), n("e439"), n("dbb4"), n("b64b"), n("d3b7"), n("159b"), n("ade3")),
            c = n("2f62");

        function s(t, e) {
            var n = Object.keys(t);
            if (Object.getOwnPropertySymbols) {
                var a = Object.getOwnPropertySymbols(t);
                e && (a = a.filter((function (e) {
                    return Object.getOwnPropertyDescriptor(t, e).enumerable
                }))), n.push.apply(n, a)
            }
            return n
        }

        function i(t) {
            for (var e = 1; e < arguments.length; e++) {
                var n = null != arguments[e] ? arguments[e] : {};
                e % 2 ? s(Object(n), !0).forEach((function (e) {
                    Object(o["a"])(t, e, n[e])
                })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(t, Object.getOwnPropertyDescriptors(n)) : s(Object(n)).forEach((function (e) {
                    Object.defineProperty(t, e, Object.getOwnPropertyDescriptor(n, e))
                }))
            }
            return t
        }

        var u = function () {
            return n.e("articles").then(n.bind(null, "3a03"))
        }, l = function () {
            return n.e("tags").then(n.bind(null, "173f"))
        }, p = {
            name: "mainPage", components: {Articles: u, Tags: l}, data: function () {
                return {
                    navItems: [{name: "Новые", value: "recent"}, {
                        name: "Самые обсуждаемые",
                        value: "popular"
                    }, {name: "Лучшие", value: "best"}, {name: "Старые", value: "early"}], tagSelected: ""
                }
            }, computed: i({}, Object(c["mapGetters"])(["blogInfo"]), {
                postByDate: function () {
                    return this.$route.params.date ? this.$route.params.date : ""
                }
            }), methods: {
                onClickTag: function (t) {
                    this.tagSelected = t, "/" !== this.$route.path && this.$router.push("/")
                }
            }, mounted: function () {
                this.$route.params.tag && (this.tagSelected = this.$route.params.tag)
            }, metaInfo: function () {
                return {title: this.blogInfo ? "".concat(this.blogInfo.title, " - ").concat(this.blogInfo.subtitle) : ""}
            }
        }, b = p, f = (n("5872"), n("2877")), g = Object(f["a"])(b, a, r, !1, null, null, null);
        e["default"] = g.exports
    }, "8abe": function (t, e, n) {
    }
}]);
//# sourceMappingURL=mainPage.9c6ce084.js.map