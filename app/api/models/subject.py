from sqlalchemy import Column, Integer,String
from db import Base

class Subject(Base):
  __tablename__ = "subjects"
  __table_args__ = {"schema": "subjects_schema"}

  id = Column(Integer, primary_key=True, nullable=False)
  name = Column(String(32), nullable=False)
  teacher_name = Column(String(32), nullable=False)
