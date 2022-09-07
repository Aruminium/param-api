from .subject import SubjectModel
from .user import UserModel
from .ptj import PtjModel
from typing import List
from pydantic import BaseModel

class PostModel(BaseModel):
  user: UserModel
  subject_data: SubjectModel
  ptj_list: List[PtjModel]