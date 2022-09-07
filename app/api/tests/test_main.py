import pytest
from httpx import AsyncClient
from sqlalchemy.ext.asyncio import create_async_engine, AsyncSession
from sqlalchemy.orm import sessionmaker
import starlette.status

from db import get_db, Base
from main import app

DB_URL = "sqlite+aiosqlite:///:memory:"

@pytest.fixture
async def async_client() -> AsyncClient:
  # Async用のengineとsessionを作成
  async_engine = create_async_engine(DB_URL, echo=True)
  async_session = sessionmaker(
    autocommit=False, autoflush=False, bind=async_engine, class_=AsyncSession
  )

  # テスト用にオンメモリのSQLiteテーブルを初期化(関数ごとにリセット)
  async with async_engine.begin() as conn:
    await conn.run_sync(Base.metadata.drop_all)
    await conn.run_sync(Base.metadata.create_all)

    # DIを使ってFastAPIのDBの向き先をテスト用に変更
    async def get_test_db():
      async with async_session() as session:
        yield session

    app.dependency_overrides[get_db] = get_test_db

    # テスト用に非同期HTTPクライアントを返却
    async with AsyncClient(app=app, base_url="http://test") as client:
      yield client

@pytest.mark.asyncio
async def test_create_and_read(async_client):
  response = await async_client.post("/subjects",json={"name": "教科名", "teacher_name": "担当教員"})
  assert response.status_code == starlette.status.HTTP_200_OK
  response_obj = response.json()
  assert response_obj["name"] == "教科名"
  assert response_obj["teacher_name"] == "担当教員"

  response = await async_client.get("/subjects")
  assert response.status_code == starlette.status.HTTP_200_OK
  response_obj = response.json()
  assert len(response_obj) == 1
  assert response_obj[0]["name"] == "教科名"
  assert response_obj[0]["teacher_name"] == "担当教員"