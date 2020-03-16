(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["tags"], {
    1280: function (t, e, n) {
        "use strict";
        var a = n("d7aa"), i = n.n(a);
        i.a
    }, "173f": function (t, e, n) {
        "use strict";
        n.r(e);
        var a = function () {
            var t = this, e = t.$createElement, n = t._self._c || e;
            return n("div", {
                staticClass: "Tags",
                class: t.className
            }, [n("div", {staticClass: "Tags-Title Title"}, [t._v(" Темы ")]), n("div", {staticClass: "Tags-Content"}, t._l(t.tags, (function (e) {
                return n("div", {
                    key: e.id,
                    staticClass: "Tag",
                    style: {
                        marginBottom: t.marginBottom * e.weight + "px",
                        marginLeft: t.marginLeft * e.weight + "px",
                        paddingRight: t.padding * e.weight + "px",
                        paddingLeft: t.padding * e.weight + "px",
                        fontSize: t.fontSize * e.weight + "rem"
                    },
                    on: {
                        click: function (n) {
                            return t.onClickTag(e.name)
                        }
                    }
                }, [t._v(" #" + t._s(e.name) + " ")])
            })), 0)])
        }, i = [], r = (n("a4d3"), n("4de4"), n("e439"), n("dbb4"), n("b64b"), n("159b"), n("ade3")), s = n("2f62");

        function c(t, e) {
            var n = Object.keys(t);
            if (Object.getOwnPropertySymbols) {
                var a = Object.getOwnPropertySymbols(t);
                e && (a = a.filter((function (e) {
                    return Object.getOwnPropertyDescriptor(t, e).enumerable
                }))), n.push.apply(n, a)
            }
            return n
        }

        function o(t) {
            for (var e = 1; e < arguments.length; e++) {
                var n = null != arguments[e] ? arguments[e] : {};
                e % 2 ? c(Object(n), !0).forEach((function (e) {
                    Object(r["a"])(t, e, n[e])
                })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(t, Object.getOwnPropertyDescriptors(n)) : c(Object(n)).forEach((function (e) {
                    Object.defineProperty(t, e, Object.getOwnPropertyDescriptor(n, e))
                }))
            }
            return t
        }

        var g = {
            props: {className: String},
            data: function () {
                return {fontSize: 3, marginLeft: 50, marginBottom: 15, padding: 25}
            },
            computed: o({}, Object(s["mapGetters"])(["tags"])),
            methods: o({}, Object(s["mapMutations"])(["setSelectedTag"]), {}, Object(s["mapActions"])(["getTags"]), {
                onClickTag: function (t) {
                    this.setSelectedTag(t), this.$router.push("/")
                }
            }),
            mounted: function () {
                this.getTags()
            }
        }, p = g, u = (n("1280"), n("2877")), l = Object(u["a"])(p, a, i, !1, null, null, null);
        e["default"] = l.exports
    }, d7aa: function (t, e, n) {
    }
}]);
//# sourceMappingURL=tags.3d73b433.js.map