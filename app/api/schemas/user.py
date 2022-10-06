from pydantic import BaseModel

class User(BaseModel):
  student_number: str
  password: str