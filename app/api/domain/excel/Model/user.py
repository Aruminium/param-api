from enum import Enum
from pydantic import BaseModel, Field

class UserType(str, Enum):
  SA = "SA"
  TA = "TA"

class UserModel(BaseModel):
  """UserModel

  APIで受け取るauth_userの値オブジェクト(VO)

  Attributes:
      student_number (str): ユーザの学籍番号
      name (str): ユーザの名前
      user_type (UserType): SAかTA
  """
  student_number: str = Field(None, example="b3332222")
  name: str = Field(None, example="山田太郎")
  user_type: UserType