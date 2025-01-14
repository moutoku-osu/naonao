package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.Dao;

//SampleBBS/LoginServletにアクセスされると動作
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final int MAX_LOGIN_ATTEMPTS = 3; // 最大ログイン失敗回数

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        // Dao クラスを使ってユーザーを取得
        Dao dao = new Dao();
        User user = dao.getUserById(id);

        // セッションから失敗回数を取得、初期値は0
        Integer loginAttempts = (Integer) request.getSession().getAttribute("loginAttempts");
        if (loginAttempts == null) {
            loginAttempts = 0;
        }

        // 認証処理
        if (user != null && user.getPassword().equals(password)) {
            // 認証成功
            request.getSession().setAttribute("userId", id); // セッションにユーザーIDを保存
            request.getSession().setAttribute("loginAttempts", 0); // 失敗回数をリセット

            // 記事一覧画面に遷移
            RequestDispatcher dispatcher = request.getRequestDispatcher("./ArticleListServlet");
            dispatcher.forward(request, response);
        } else {
            // 認証失敗時の処理
            loginAttempts++;
            request.getSession().setAttribute("loginAttempts", loginAttempts); // 失敗回数を更新

            // 失敗回数が閾値を超えた場合
            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                request.setAttribute("errorMessage", "ログインに3回失敗しました。再試行してください。");
            } else {
                request.setAttribute("errorMessage", "IDまたはパスワードが正しくありません。失敗回数: " + loginAttempts);
            }

            // ログイン画面に戻す
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
