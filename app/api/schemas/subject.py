from pydantic import BaseModel, Field
from typing import List

class SubjectBase(BaseModel):
  name: str = Field(None, example="教科名")
  teacher_name: str = Field(None, example="担当教員")

class CreateSubject(SubjectBase):
  pass

class CreateSubjectResponse(CreateSubject):
  id: int

  class Config:
    orm_mode = True

class Subject(SubjectBase):
  id: int

  class Config:
    orm_mode = True