![issues](https://img.shields.io/github/issues/Aruminium/param-api?style=flat)
![docker](https://img.shields.io/badge/-Docker-EEE.svg?logo=docker&style=flat)
![postgres](https://img.shields.io/badge/-PostgreSQL-336791.svg?logo=postgresql&style=flat)
![FastAPI](https://img.shields.io/badge/-FastAPI-blue.svg?logo=fastAPI&style=flat)

# param-api

大学の課外活動の1つである学内アルバイトの業務報告入力をデジタル化するプロジェクトのAPIとデータベースのリポジトリ

# 目的

主にPostされたjsonを元にExcelファイルを扱うために作成されたリポジトリである。

これとは別にフロントエンドとバックエンドはJavaのWicketとSpringを用いて実装され、IDEにIntellijを対象としている

# 機能

- FastAPI 0.82.0
- postgreSQL 14
- SQLAlchemy 1.4.41
- openpyxl 3.0.10

[APIコンテナのドキュメント](https://github.com/Aruminium/param-api/tree/main/app)

[DBコンテナのドキュメント](https://github.com/Aruminium/param-api/tree/main/db)


## エクセルファイルを操作する



## xlsx -> pdf

オープンソースのibreofficeをDocker上にインストールし呼び出して用いる

```python
def convertExcelToPdf(self):
  cmd = []
  cmd.append("libreoffice")
  cmd.append("--headless")
  cmd.append("--nologo")
  cmd.append("--nofirststartwizard")
  cmd.append("--convert-to")
  cmd.append("pdf")
  cmd.append("--outdir")
  cmd.append("/app/domain/excel/")
  cmd.append(f"/app/domain/excel/{self.file_name}.xlsx")

  subprocess.run(" ".join(cmd), shell=True)
```

# APIエンドポイント



# セットアップ

## 1. docker/env/db.env を作成する

USERとPASSWORDは学籍番号

```env
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_SERVER=127.0.0.1
POSTGRES_PORT=5432
POSTGRES_DB="test"
```

### 1.1. docker/env/auth.env を作成する

デプロイ時にSECRET_KEYの値を変えます
```env
SECRET_KEY='e40e2e83874fef7b97a1442c359cfc9ec79b78e4b13a22a97f90a9b5eff6e469'
ALGORITHM='HS256'
ACCESS_TOKEN_EXPIRE_MINUTES=30
```

## 2. コンテナを立ち上げる

```shell
$ docker compose build # buildさせる -> docker image生成
$ docker compose up -d # 実際にコンテナを起動させる
$ docker compose down # 辞める時 (プロセスを止める)
```

## 3. intellijからDBにアクセス

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

## 4. FastAPIで教科情報を追加しよう

subjectsテーブルに値が入っていないと、javaの方でエラーが出るので追加しよう

### 4.1 dockerコンテナたちを起動する

```shell
docker compose up -d
```

でコンテナたちを立ち上げる(既に立ち上がっている場合は必要ない)

### 4.2 localhost:5000/docsにアクセス

[localhost:5000/docs](localhost:5000/docs)にアクセスすると以下のSwaggerUI(APIのドキュメント)が表示される

<img width="807" alt="スクリーンショット 2022-09-14 3 10 00" src="https://user-images.githubusercontent.com/73931800/189978181-ac7c45b4-aa58-4848-903a-43da7d05c9cf.png">

### 4.3 Postしよう

Post /subject/ の欄をクリックすると以下のようになるはずだ

<img width="796" alt="スクリーンショット 2022-09-14 3 10 49" src="https://user-images.githubusercontent.com/73931800/189978358-83ebcf9a-9255-49a4-b00c-2af69c1e6854.png">

Try it outを押すと「試すモード」になるので

<img width="143" alt="スクリーンショット 2022-09-14 3 12 08" src="https://user-images.githubusercontent.com/73931800/189978619-428aa398-2bb3-4c2b-bafa-b59c2d2401d4.png">

```json
{
  "name": "教科名1",
  "teacher_name": "担当教1"
}
```
としてみよう

<img width="753" alt="スクリーンショット 2022-09-14 3 13 10" src="https://user-images.githubusercontent.com/73931800/189978838-dceaa22b-9e05-441a-b73b-91307f1e762f.png">

Executeを押すと実行される

<img width="727" alt="スクリーンショット 2022-09-14 3 14 06" src="https://user-images.githubusercontent.com/73931800/189979016-f1d38a22-bf04-43fd-8820-cfc1d209891b.png">

実行してResponseの欄で200番が返ってきていたら成功だ

<img width="747" alt="スクリーンショット 2022-09-14 3 15 47" src="https://user-images.githubusercontent.com/73931800/189979301-2195dab4-49f9-48b1-aac0-ebcc0bb0be1c.png">

同様にこれら手順を試す

<img width="772" alt="スクリーンショット 2022-09-14 3 16 54" src="https://user-images.githubusercontent.com/73931800/189979507-ab9a0d28-e8af-4e56-88fb-214b7ecaedeb.png">

<img width="777" alt="スクリーンショット 2022-09-14 3 17 14" src="https://user-images.githubusercontent.com/73931800/189979563-38aee136-c7a4-4db7-b517-d59042180696.png">

<img width="770" alt="スクリーンショット 2022-09-14 3 17 38" src="https://user-images.githubusercontent.com/73931800/189979637-23de5058-b966-49a2-83c5-9b507f1469f5.png">

### 4.4 教科情報を確認する

Get /subject/ の欄をクリック -> Try it out -> Execute　と試すと

<img width="779" alt="スクリーンショット 2022-09-14 3 18 24" src="https://user-images.githubusercontent.com/73931800/189979797-a7f174f4-b761-40cf-bdf1-a1117e9db5dc.png">

先ほど登録した教科情報が取得されていることがわかる。

<img width="734" alt="スクリーンショット 2022-09-14 3 19 28" src="https://user-images.githubusercontent.com/73931800/189980001-b867f7ec-2247-47ff-9a95-05de3b518518.png">

これでセットアップは完了！


# 課題点

- Dockerfileのリファクタリングによる軽量化＆高速化
- データベースチューニング


# pythonでのDDD先行事例

- [Python で学ぶ実践的なドメイン駆動設計とレイヤードアーキテクチャ](https://speakerdeck.com/iktakahiro/ddd-and-onion-architecture-in-python)
- [ドメイン駆動でインターフェース指向な開発](https://qiita.com/yu-sa/items/e0033ae312669256cd8a)


# tips
- [Python で interface を扱う](https://zenn.dev/plhr7/articles/36ddd240ccbb97)
- [DDD基礎解説：Entity、ValueObjectってなんなんだ](https://little-hands.hatenablog.com/entry/2018/12/09/entity-value-object)
- [テスト](https://fastapi.tiangolo.com/ja/tutorial/testing/)
- [PythonでGoogle Driveにファイルをアップロードする](https://laboratory.kazuuu.net/upload-files-to-google-drive-with-python/)
- [データベース（RDB）設計の進め方！](https://qiita.com/ryota_i/items/294281b57cc9783bf2c1)
- [Dockerfileのベストプラクティス](https://qiita.com/Tsuyozo/items/c706a04848c3fbbaf055)
