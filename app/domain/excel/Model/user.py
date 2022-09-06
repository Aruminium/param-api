from enum import Enum
from pydantic import BaseModel


class UserType(str, Enum):
  SA = "SA"
  TA = "TA"

class UserModel(BaseModel):
  """UserModel

  APIで受け取るauth_userの値オブジェクト(VO)

  Attributes:
      student_number (str): ユーザの学籍番号
      user_name (str): ユーザの名前
      user_type (UserType): SAかTA
  """
  student_number: str
  user_name: str
  user_type: UserType