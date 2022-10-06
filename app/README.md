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

