package servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.Dao;

//SampleBBS/EntryUserServletにアクセスすると動作
@WebServlet("/EntryUserServlet")
public class EntryUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EntryUserServlet() {
        super();
    }

    // パスワードが強化基準を満たしているかチェックするメソッド
    private boolean isPasswordStrong(String password) {
        // パスワードが8文字以上でなければならない
        if (password.length() < 8) {
            return false;
        }

        // 正規表現: 小文字、大文字、数字、記号が含まれているかを確認
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }

    // POSTアクセス時に動作．データ登録系の処理なので，POSTのみ受け付け．
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // requestのデータの文字コードをUTF-8に設定
        request.setCharacterEncoding("UTF-8");

        // requestから，id,name,password1,password2の名前のデータを取得
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        // DBアクセスのためのクラスをインスタンス化
        Dao dao = new Dao();

        // パスワード強化チェック
        if (!isPasswordStrong(password1)) {
            // パスワードが強化基準を満たしていない場合、エラーメッセージを設定
            request.setAttribute("errorMessage", "パスワードは8文字以上、小文字、大文字、数字、記号を含む必要があります。");
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/entryUser.jsp");
            dispatcher.forward(request, response);
            return; // 処理を終了
        }

        // ユーザ情報が問題ないかチェック
        if (!id.isBlank() && dao.getUserById(id) == null && password1.equals(password2) && !password1.isBlank()) {
            // 登録用のユーザオブジェクトを作成（Userはbeansパッケージに定義）
            User userToEntry = new User(id, password1, name);
            // ユーザをDBに登録
            dao.insertUser(userToEntry);
            // ログインページへリダイレクト
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
        } else {
            // ユーザ情報に問題があった場合
            RequestDispatcher dispatcher = request.getRequestDispatcher("./WEB-INF/jsp/entryUser.jsp");
            dispatcher.forward(request, response);
        }
    }
}
