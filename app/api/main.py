from fastapi import FastAPI, testclient
from urllib.parse import urlencode
from domain.excel.Model.post import PostModel
from domain.excel.excel import Excel
from pydantic import BaseModel
from routers import subject

app = FastAPI()

@app.post("/pdj")
def read_json(post_data: PostModel):
  excel = Excel(post_data)
  excel.edit()
  excel.convertExcelToPdf()
  pass

app.include_router(subject.router)
