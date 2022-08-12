https://zenn.dev/sh0nk/books/537bb028709ab9/viewer/d3f074

https://libproc.com/fastapi-define-model-and-migration/#index_id0

https://zenn.dev/re24_1986/articles/8520ac3f9a0187

## 手順

### 1. docker/env/db.env を作成する

```env
POSTGRES_USER="admin"
POSTGRES_PASSWORD="postgres"
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



application.properties を書き換える

spring.datasource.driver-class-nameとspring.datasource.urlを以下のように書き換える
```
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/test
```

### 4. あとは今まで通り

試しにユーザ作成をしてみよう