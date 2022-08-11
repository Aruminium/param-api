from database import Base
from sqlalchemy import Column, Integer, date, String, Float

class ptj_request(Base):
  __tablename = "ptj_request"
  id = Column("id",
    Integer,
    primary_key=True,
    autoincrement=True
  )
  user_name = Column("user_name",
    String(32),
    nullable=False,
  )
  ptj_date = Column("ptj_date",
    date,
    nullable=False
  )
  working_hours_start = Column("working_hours_start",
    Float,
    nullable=False
  )
  working_hours_finish = Column("working_hours_finish",
    Float,
    nullable=False
  )
  break_time = Column("break_time",
    Float,
    nullable=False
  )
  working_type = Column("working_type",
    String(64),
    nullable=False
  )
  working_subject = Column("working_subject",
    String(64),
    nullable=False
  )
  working_content = Column("working_content",
    String(64),
    nullable=False
  )