https://zenn.dev/sh0nk/books/537bb028709ab9/viewer/d3f074

https://libproc.com/fastapi-define-model-and-migration/#index_id0

https://zenn.dev/re24_1986/articles/8520ac3f9a0187

## 手順

### 1. docker/env/db.env を作成する

USERとPASSWORDは学籍番号

```env
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_SERVER=127.0.0.1
POSTGRES_PORT=5432
POSTGRES_DB="test"
```

### 2. コンテナを立ち上げる

```shell
$ docker compose build # buildさせる -> docker image生成
$ docker compose up -d # 実際にコンテナを起動させる
```

### 3. intellijからDBにアクセス

pom.xml にpostgresql Driver を追加する

```
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```

データベース -> データソース -> PostgreSQL を選択

接続タイプは default

<img width="751" alt="スクリーンショット 2022-08-12 12 07 40" src="https://user-images.githubusercontent.com/73931800/184277684-d5063423-9b6f-4192-bdde-0efde025a8ba.png">

接続のテストで成功したら、OKを押す

application.properties を書き換える

spring.datasource.driver-class-nameとspring.datasource.urlを以下のように書き換える
```
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/test
spring.datasource.username=db.envのPOSTGRES_USER
spring.datasource.password=db.envのPOSTGRES_PASSWORD
```

### 4. あとは今まで通り

試しにユーザ作成をしてみよう

### pythonでのDDD先行事例

- [Python で学ぶ実践的なドメイン駆動設計とレイヤードアーキテクチャ](https://speakerdeck.com/iktakahiro/ddd-and-onion-architecture-in-python)
- [ドメイン駆動でインターフェース指向な開発](https://qiita.com/yu-sa/items/e0033ae312669256cd8a)


### tips
- [Python で interface を扱う](https://zenn.dev/plhr7/articles/36ddd240ccbb97)
- [DDD基礎解説：Entity、ValueObjectってなんなんだ](https://little-hands.hatenablog.com/entry/2018/12/09/entity-value-object)
