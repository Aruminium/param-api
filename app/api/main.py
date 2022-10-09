from fastapi import FastAPI, Response
from urllib.parse import urlencode
from domain.excel.Model.post import PostModel
from domain.excel.excel import Excel
from routers import subject
from routers import auth
from domain.s3 import upload

app = FastAPI()

@app.post("/ptj")
def read_json(response: Response, post_data: PostModel):
  excel = Excel(post_data)
  excel.removeFiles()
  excel.edit()
  excel.convertExcelToPdf()
  response = excel.pdfCompress()
  response.headers["Cache-Control"] = "no-store"
  return response

app.include_router(subject.router)
app.include_router(auth.router)