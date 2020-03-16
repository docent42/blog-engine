(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["articles"], {
    2657: function (e, t, r) {
        "use strict";
        var a = r("f6f5"), s = r.n(a);
        s.a
    }, "3a03": function (e, t, r) {
        "use strict";
        r.r(t);
        var a = function () {
                var e = this, t = e.$createElement, r = e._self._c || t;
                return r("div", {class: e.classObject}, [e.daySelected ? e._e() : r("BaseNavbar", {
                    attrs: {
                        className: "Articles-Nav",
                        navItems: e.navItems,
                        activeNavIndex: e.activeNavIndex
                    }, on: {"set-nav-value": e.selectActiveNavIndex}
                }), r("div", {class: [e.forModeration || e.myPosts ? "Articles-Content Articles-Content--noborder" : "Articles-Content"]}, [e.articlesAreErrored ? r("div", {staticClass: "ServerInfo"}, [e._v(" Sorry, some error happened :( ")]) : [e.daySelected ? r("div", {staticClass: "Title Articles-Title"}, [e._v(" Публикации " + e._s(e.formatedDate) + " ")]) : e._e(), e.tagSelected ? r("div", {staticClass: "Title Articles-Title"}, [e._v(" Публикации по тэгу #" + e._s(e.tagSelected.toUpperCase()) + " ")]) : e._e(), e._l(e.articles, (function (t) {
                    return r("BaseArticle", {
                        key: t.id,
                        attrs: {
                            className: "Articles-ArticlePreview",
                            isPreview: !0,
                            forModeration: e.forModeration,
                            myPosts: e.myPosts,
                            id: t.id,
                            time: t.time,
                            author: t.user.name,
                            title: t.title,
                            text: t.announce,
                            likeCount: t.likeCount,
                            dislikeCount: t.dislikeCount,
                            commentCount: t.commentCount,
                            viewCount: t.viewCount
                        },
                        on: {moderated: e.onModerated}
                    })
                })), e.moreArticles && !e.articlesAreLoading ? r("div", {staticClass: "Articles-Button"}, [r("BaseButton", {
                    attrs: {
                        className: "Button--mode_add-load",
                        onClickButton: e.onLoadMore
                    }
                }, [e._v(" Ещё публикации (" + e._s(e.moreArticles) + ") ")])], 1) : e._e()]], 2)], 1)
            }, s = [],
            i = (r("a4d3"), r("99af"), r("4de4"), r("c740"), r("e439"), r("dbb4"), r("b64b"), r("d3b7"), r("159b"), r("ade3")),
            c = r("2f62");

        function n(e, t) {
            var r = Object.keys(e);
            if (Object.getOwnPropertySymbols) {
                var a = Object.getOwnPropertySymbols(e);
                t && (a = a.filter((function (t) {
                    return Object.getOwnPropertyDescriptor(e, t).enumerable
                }))), r.push.apply(r, a)
            }
            return r
        }

        function o(e) {
            for (var t = 1; t < arguments.length; t++) {
                var r = null != arguments[t] ? arguments[t] : {};
                t % 2 ? n(Object(r), !0).forEach((function (t) {
                    Object(i["a"])(e, t, r[t])
                })) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(r)) : n(Object(r)).forEach((function (t) {
                    Object.defineProperty(e, t, Object.getOwnPropertyDescriptor(r, t))
                }))
            }
            return e
        }

        var l = function () {
            return r.e("baseNavbar").then(r.bind(null, "c8ce"))
        }, u = function () {
            return r.e("baseArticle").then(r.bind(null, "5e98"))
        }, d = function () {
            return r.e("baseButton").then(r.bind(null, "82ea"))
        }, h = {
            components: {BaseNavbar: l, BaseArticle: u, BaseButton: d},
            props: {
                className: {type: String, required: !1},
                navItems: {type: Array, required: !0},
                forModeration: {type: Boolean, required: !1, default: !1},
                myPosts: {type: Boolean, required: !1, default: !1}
            },
            data: function () {
                return {activeNavIndex: 0, articlesNumber: 10, offset: 0}
            },
            computed: o({}, Object(c["mapGetters"])(["articles", "articlesCount", "articlesAreLoading", "articlesAreErrored", "tagSelected", "searchQuery", "daySelected"]), {
                classObject: function () {
                    var e = "Articles";
                    return this.className && (e += " ".concat(this.className)), (this.myPosts || this.forModeration) && (e += " Wrapper"), e
                }, moreArticles: function () {
                    var e = this.articlesCount - this.offset - this.articlesNumber;
                    return e > 0 ? e : 0
                }, formatedDate: function () {
                    return !!this.daySelected && new Date(this.daySelected).toLocaleString("ru-RU", {
                        year: "numeric",
                        month: "2-digit",
                        day: "2-digit"
                    })
                }
            }),
            watch: {
                $route: function () {
                    this.clearProps(), this.selectMethod()
                }, tagSelected: function () {
                    if (this.tagSelected) {
                        this.clearArticles(), this.clearSearchQuery(), this.clearSelectedDay(), this.offset = 0;
                        var e = this.makeQuery("tag", "/byTag");
                        this.getArticles(e)
                    }
                }, searchQuery: function () {
                    if (this.searchQuery) {
                        this.clearArticles(), this.clearSelectedTag(), this.offset = 0;
                        var e = this.makeQuery("query", "/search");
                        this.getArticles(e)
                    }
                }
            },
            methods: o({}, Object(c["mapMutations"])(["clearArticles", "clearSelectedTag", "clearSearchQuery", "clearSelectedDay"]), {}, Object(c["mapActions"])(["getArticles", "moderateArticle"]), {
                selectActiveNavIndex: function (e) {
                    this.activeNavIndex = e
                }, clearProps: function () {
                    this.clearArticles(), this.clearSelectedTag(), this.clearSearchQuery(), this.offset = 0
                }, selectMethod: function () {
                    var e;
                    e = this.forModeration ? this.makeQuery("status", "/moderation") : this.myPosts ? this.makeQuery("status", "/my") : this.tagSelected ? this.makeQuery("tag", "/byTag") : this.daySelected ? this.makeQuery("date", "/byDate") : this.searchQuery ? this.makeQuery("query", "/search") : this.makeQuery("mode"), this.getArticles(e)
                }, getValue: function () {
                    return this.tagSelected ? this.tagSelected : this.daySelected ? this.daySelected : this.searchQuery ? this.searchQuery : this.navItems[this.activeNavIndex].value
                }, makeQuery: function (e) {
                    var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "", r = this.getValue();
                    return "".concat(t, "?offset=").concat(this.offset, "&limit=").concat(this.articlesNumber, "&").concat(e, "=").concat(r)
                }, onLoadMore: function () {
                    this.articlesCount > this.offset + this.articlesNumber && (this.offset += this.articlesNumber, this.selectMethod())
                }, onModerated: function (e) {
                    var t = {post_id: e.id, decision: e.status};
                    this.moderateArticle(t)
                }
            }),
            mounted: function () {
                var e = this;
                this.activeNavIndex = this.navItems.findIndex((function (t) {
                    return t.value === e.$route.params.pathMatch
                })), this.clearProps(), this.selectMethod()
            },
            metaInfo: function () {
                var e;
                if (this.tagSelected) e = "Публикации по тэгу #".concat(this.tagSelected); else if (this.forModeration) e = "Модерирование публикаций"; else if (this.myPosts) e = "Мои публикации"; else if (this.postByDate) e = "Публикации за ".concat(this.formatedDate); else {
                    if (!this.searchQuery) return "DevPub - рассказы разработчиков";
                    e = 'Искать "'.concat(this.searchQuery, '"')
                }
                return {title: "".concat(e, " | DevPub - рассказы разработчиков")}
            }
        }, f = h, m = (r("2657"), r("2877")), v = Object(m["a"])(f, a, s, !1, null, null, null);
        t["default"] = v.exports
    }, c740: function (e, t, r) {
        "use strict";
        var a = r("23e7"), s = r("b727").findIndex, i = r("44d2"), c = "findIndex", n = !0;
        c in [] && Array(1)[c]((function () {
            n = !1
        })), a({target: "Array", proto: !0, forced: n}, {
            findIndex: function (e) {
                return s(this, e, arguments.length > 1 ? arguments[1] : void 0)
            }
        }), i(c)
    }, f6f5: function (e, t, r) {
    }
}]);
//# sourceMappingURL=articles.e1b95a98.js.map