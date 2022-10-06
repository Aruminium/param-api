from sqlalchemy import Column ,String
from db import Base

class User(Base):
  __tablename__ = "users"

  student_number = Column(String(8), primary_key=True, nullable=False)
  password = Column(String(64), nullable=False)