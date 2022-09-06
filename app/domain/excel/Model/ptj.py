from datetime import date
from pydantic import BaseModel

class PtjModel(BaseModel):
  """PtjModel

  APIで受け取るptj_requestの値オブジェクト(VO)

  Attributes:
      date (date): ptjの日付(働いた日)
      working_start (float): 勤務開始時間
      working_finish (float): 勤務終了時間
      break_time (float): 休憩時間
      sum_working_hours (float): 合計勤務時間
      working_subject (str): 担当教科
      working_content (str): 活動内容詳細

  """
  date: date
  working_start: float
  working_finish: float
  break_time: float
  sum_working_hours: float
  working_subject: str
  working_content: str