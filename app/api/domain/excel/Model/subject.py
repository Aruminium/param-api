from typing import List
from .ptj import PtjModel
from pydantic import BaseModel, Field


class SubjectModel(BaseModel):
  """SubjectModel

  APIで受け取るsubject_dataの値オブジェクト(VO)

  Attributes:
      name (str): 教科名
      teacher_name (str): 担当教員
  """
  name: str = Field(None, example="教科")
  teacher_name: str = "担当教員"


