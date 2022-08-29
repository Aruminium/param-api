from pydanic import BaseModel
from datetime import datetime

class PtjModel(BaseModel):
  id: int
  name: str
  date: datetime
  working_start: float
  working_finish: float
  break_time: float
  working_type: str
  working_subject: str
  working_content: str
