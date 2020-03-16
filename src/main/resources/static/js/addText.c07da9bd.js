(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["addText"], {
    "42ff": function (t, e, r) {
        "use strict";
        var n = r("4f50"), o = r.n(n);
        o.a
    }, "4f50": function (t, e, r) {
    }, ce13: function (t, e, r) {
        "use strict";
        r.r(e);
        var n = function () {
            var t = this, e = t.$createElement, r = t._self._c || e;
            return r("div", {
                staticClass: "AddText",
                class: t.className
            }, [r("div", {staticClass: "AddText-Edit"}, [r("Vueditor", {ref: "editor"})], 1)])
        }, o = [], s = (r("a4d3"), r("4de4"), r("e439"), r("dbb4"), r("b64b"), r("159b"), r("ade3")), i = r("2f62");

        function c(t, e) {
            var r = Object.keys(t);
            if (Object.getOwnPropertySymbols) {
                var n = Object.getOwnPropertySymbols(t);
                e && (n = n.filter((function (e) {
                    return Object.getOwnPropertyDescriptor(t, e).enumerable
                }))), r.push.apply(r, n)
            }
            return r
        }

        function a(t) {
            for (var e = 1; e < arguments.length; e++) {
                var r = null != arguments[e] ? arguments[e] : {};
                e % 2 ? c(Object(r), !0).forEach((function (e) {
                    Object(s["a"])(t, e, r[e])
                })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(t, Object.getOwnPropertyDescriptors(r)) : c(Object(r)).forEach((function (e) {
                    Object.defineProperty(t, e, Object.getOwnPropertyDescriptor(r, e))
                }))
            }
            return t
        }

        var d = {
            props: {className: {type: String, required: !1}},
            computed: a({}, Object(i["mapGetters"])(["nameToReply", "shouldGetEditorText"])),
            watch: {
                nameToReply: function () {
                    this.nameToReply && this.$refs.editor.setContent("<strong>".concat(this.nameToReply, "</strong>,"))
                }, shouldGetEditorText: function () {
                    this.shouldGetEditorText ? this.setEditorContent(this.$refs.editor.getContent()) : this.$refs.editor.setContent("")
                }
            },
            methods: a({}, Object(i["mapMutations"])(["setEditorContent", "clearEditorContent"]))
        }, u = d, f = (r("42ff"), r("2877")), p = Object(f["a"])(u, n, o, !1, null, null, null);
        e["default"] = p.exports
    }
}]);
//# sourceMappingURL=addText.c07da9bd.js.map