from typing import List
from .ptj import PtjModel
from pydantic import BaseModel, Field


class SubjectModel(BaseModel):
  """SubjectModel

  APIで受け取るsubject_dataの値オブジェクト(VO)

  Attributes:
      name (str): 教科名
      teachers (List[str]): 担当教員のリスト
  """
  name: str = Field(None, example="ソフトウェア工学概論")
  teacher_name: str = "小松川浩"


