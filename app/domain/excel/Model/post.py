from .subject import SubjectModel
from .user import UserModel
from typing import List
from pydantic import BaseModel

class PostModel(BaseModel):
  user: UserModel
  subject_data: SubjectModel