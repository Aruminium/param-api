from fastapi import FastAPI
from database import PtjModel

app = FastAPI()

@app.post("/pdj")
def read_json(ptj_model: PtjModel):
    