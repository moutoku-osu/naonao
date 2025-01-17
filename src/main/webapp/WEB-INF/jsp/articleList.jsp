<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ここまで， JSPを書く時の決まり文句-->

<!-- ここから，インポート宣言 -->    
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import ="beans.Article" %>
<%@page import="java.sql.Timestamp"%>
<!-- ここまで，インポート宣言 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Article list page</title>
</head>
<body>
    <label>記事一覧</label><br><br>
    <!-- UpdateUserPageServletAnsにハイパーリンク．
    ハイパーリンクによるアクセスは，GETアクセスとなり，サーブレットのdoGet()が呼ばれる． -->
    <a href="./UpdateUserPageServletAns">ユーザ情報更新</a><br><br>
    <!-- EntryArticlePageServletにハイパーリンク．
    ハイパーリンクによるアクセスは，GETアクセスとなり，サーブレットのdoGet()が呼ばれる． -->
    <a href="./EntryArticlePageServlet">新規記事登録</a><br><br>

    <!-- 直前のサーブレットでrequestにsetAttributeしておいたオブジェクトを取り出す．
    普通に取り出すとObject型になってしまうので，適宜キャストして変数に受け取る． -->
    <%List<Article> aList = (List<Article>) (request.getAttribute("articleList"));%>
    
    <!-- javaの文法とhtmlを組み合わせて，記事リストを表示 -->
    <%if (aList != null && !aList.isEmpty()) { %>
        <%for (Article a : aList) { %>
            <!-- 記事情報を表示 -->
            <label>タイトル：<br><%= a.getTitle() %> (<%= a.getTitleLength() %>文字)</label> <br>
            <label>本文：<br><%= a.getBody() %> (<%= a.getBodyLength() %>文字)</label> <br>
            <label>登録者：<br><%= a.getEditorId() %></label> <br>
            <!-- 日時を指定のフォーマットで表示するための命令 -->
            <%SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");%>
            <label>登録日時：<br><%= a.getEntryDatetime() != null ? sdf.format(a.getEntryDatetime()) : "未登録" %></label>
            <br><br>
        <%} %>
    <%} else { %>
        <p>現在、記事は登録されていません。</p>
    <%} %>
</body>
</html>