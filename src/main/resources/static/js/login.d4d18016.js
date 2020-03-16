(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["login"], {
    "013f": function (e, n, t) {
        "use strict";
        t.r(n);
        var r = function () {
            var e = this, n = e.$createElement, t = e._self._c || n;
            return t("main", {class: ["restorePassword" === e.path ? "Login Login--wide Wrapper" : "Login Wrapper"]}, [t("div", {staticClass: "Login-Section"}, [t("LoginHeader", [e._v(" " + e._s(e.title) + " ")]), t("router-view")], 1)])
        }, i = [], o = (t("b0c0"), t("d3b7"), function () {
            return t.e("loginHeader").then(t.bind(null, "7fcf"))
        }), s = {
            name: "Login", props: {className: {type: String, required: !1}}, computed: {
                path: function () {
                    return this.$route.name
                }, title: function () {
                    switch (this.$route.path) {
                        case"/login":
                            return "Вход";
                        case"/login/registration":
                            return "Регистрация";
                        case"/login/restore-password":
                            return "Восстановление пароля";
                        default:
                            return "Смена пароля"
                    }
                }
            }, components: {LoginHeader: o}, mounted: function () {
                this.$store.commit("clearAuthErrors")
            }
        }, a = s, u = (t("50ba"), t("2877")), c = Object(u["a"])(a, r, i, !1, null, null, null);
        n["default"] = c.exports
    }, "50ba": function (e, n, t) {
        "use strict";
        var r = t("eb90"), i = t.n(r);
        i.a
    }, eb90: function (e, n, t) {
    }
}]);
//# sourceMappingURL=login.d4d18016.js.map