package com.example.wsbp;

import com.example.wsbp.domain.models.user.User;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import java.util.Objects;

// ブラウザ事に異なる情報を一時保存できるセッション
public class MySession extends AbstractAuthenticatedWebSession {

    // 認証の際に照合したユーザー名
    private User user;

    public MySession(Request request) {
        // コンストラクタ。初期状態は認証なし
        super(request);
        this.user = null;
    }

    public void sign(final User user) {
        // 認証したユーザを変更する。
        // セキュリティ上の配慮から、セッションを切り替える（古いセッションを使い回さない）
        replaceSession();
        this.user = user;
    }

    @Override
    public Roles getRoles() {
        // 認証結果OK(userName = ユーザー名)だった場合は、誰もが "USER" 権限を持っているとして返す
        // 認証結果NG(userName = null）だった場合は、権限なしとして返す
        // wicket-auth-roles　では、この権限の有無と種類でページを表示してよいか判断してくれる
        if (isSignedIn()) {
            if (Objects.equals(user.getUserType(), "SA")) {
                return new Roles(Roles.USER);
            }
            if (Objects.equals(user.getUserType(), "TA")) {
                return new Roles(Roles.USER);
            }
            if (Objects.equals(user.getUserType(), "ADMIN")) {
                return new Roles(Roles.ADMIN);
            }
        }

        return new Roles();
    }

    @Override
    public boolean isSignedIn() {
        // 認証結果OK(userName = ユーザー名)だった場合は、trueを返す
        // 認証結果NG(userName = null）だった場合は、falseを返す
        return Objects.nonNull(this.user);
    }

    public User getUser() {
        // ユーザー を返す
        return this.user;
    }

    public static MySession get() {
        // サーバーの中から Session を取り出す
        return (MySession) Session.get();
    }

}
