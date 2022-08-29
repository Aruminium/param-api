from datetime import datetime

class PtjModel:
  def __init__(self, id: int, name: str, date: datetime, working_start: float, working_finish: float, break_time: float, working_type: str, working_subject: str, working_content: str):
    self.__id: int
    self.__name: str
    self.__date: datetime
    self.__working_start: float
    self.__working_finish: float
    self.__break_time: float
    self.__working_type: str
    self.__working_subject: str
    self.__working_content: str