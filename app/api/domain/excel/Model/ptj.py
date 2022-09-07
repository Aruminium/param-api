from datetime import date, time
from pydantic import BaseModel

class PtjModel(BaseModel):
  """PtjModel

  APIで受け取るptj_requestの値オブジェクト(VO)

  Attributes:
      date (date): ptjの日付(働いた日)
      working_start (time): 勤務開始時間
      working_finish (time): 勤務終了時間
      break_time (float): 休憩時間
      working_hours (float): 勤務時間
      working_content (str): 活動内容詳細

  """
  date: date
  working_start: time = "08:15"
  working_finish: time = "08:30"
  break_time: float
  working_hours: float = 0.25
  working_content: str = "eラーニング設定業務"