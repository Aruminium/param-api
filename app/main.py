from fastapi import FastAPI, testclient
from urllib.parse import urlencode
from domain.excel.ptj import PtjModel
from domain.excel.excel import Excel
from typing import List

app = FastAPI()

@app.post("/pdj")
def read_json(ptjList: List[PtjModel]):
  excel = Excel(ptjList)
  excel.edit()
  return excel.ptj_list[0].name
