from typing import List
from .ptj import PtjModel
from pydantic import BaseModel


class SubjectModel(BaseModel):
  """SubjectModel

  APIで受け取るsubject_dataの値オブジェクト(VO)

  Attributes:
      subject (str): 教科名
      teachers (List[str]): 担当教員のリスト
      ptjList (List[ptjModel]): ptjのリスト
  """
  subject: str
  teachers: List[str]
  ptjList: List[PtjModel]

