# apiコンテナドキュメント

FastAPIにおけるCRUD操作は[FastAPI入門](https://zenn.dev/sh0nk/books/537bb028709ab9/viewer/f1b6fc)を参考にしている

## subjectsテーブルの単体テスト

postとgetにおけるsubjectsテーブルのマイグレーションのテストを行う

コンテナ内で

```shell
/app # poetry run pytest --asyncio-mode=auto

collected 1 item

api/tests/test_main.py .
[100%]

===1 passed in 0.66s ===
```

### リファクタリング及びsubjectsテーブルのカラムを変更等した際にテストしよう

__必ず単体テストに合格してからpushすること__

# SwaggerUI以外で教科情報をCRUDする方法

<img width="618" alt="スクリーンショット 2022-10-06 13 02 44" src="https://user-images.githubusercontent.com/73931800/194211318-a13abcc7-3ee9-423c-8d92-eed6a9194429.png">

<img width="620" alt="スクリーンショット 2022-10-06 13 03 36" src="https://user-images.githubusercontent.com/73931800/194211407-85ca2f5d-db13-42ea-9048-5a23c9be9df6.png">

### CURL

```shell
curl -X 'POST' \
  'http://localhost:5000/token' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'grant_type=&username=hogehoge&password=fugafuga&scope=&client_id=&client_secret='
```

レスポンス

```json
{
  "access_token": "piyopiyo",
  "token_type": "bearer"
}
```

### access_tokenが返ってくることを確認する

リクエストヘッダーに「access_token」を追加してリクエストする

### CURL (/subjects/ GET)の場合
``` shell
curl -X 'GET' \
  'http://localhost:5000/subjects/' \
  -H 'accept: application/json' \
  -H 'Authorization: Bearer piyopiyo'
```
