from database import Base
from sqlalchemy import Column, String

class auth_user(Base):
  __tablename = "auth_user"
  user_name = Column("user_name",
    String(32),
    primary_key=True,
    nullable=False
  )
  user_pass = Column("user_pass",
    String(32),
    nullable=False
  )