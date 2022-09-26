from datetime import date, time
from pydantic import BaseModel

class PtjModel(BaseModel):
  """PtjModel

  APIで受け取るptj_requestの値オブジェクト(VO)

  Attributes:
      date (date): ptjの日付(働いた日)
      start_time (time): 勤務開始時間
      finish_time (time): 勤務終了時間
      break_time_minutes (float): 休憩時間
      office_hours (float): 勤務時間
      duties (str): 活動内容詳細

  """
  date: date
  start_time: time = "08:15"
  finish_time: time = "08:30"
  break_time_minutes: float
  office_hours: float = 0.25
  duties: str = "eラーニング設定業務"